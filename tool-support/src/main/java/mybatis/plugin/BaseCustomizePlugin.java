package mybatis.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ConstUtils;

/**
 * 基础自定义Plugin <br>
 * 
 * 自定义功能： <br>
 * 1. 生成新Dao类并继承统一基类，Dao类名规则可自定义。 <br>
 * 2. 生成扩展MapperXml文件，Xml文件名规则可自定义。 <br>
 * 
 * @author xyb
 *
 */
@Slf4j
public class BaseCustomizePlugin extends PluginAdapter {

	private FullyQualifiedJavaType pkType = null;

	private ShellCallback shellCallback = null;

	private String daoTargetDir;

	private String daoTargetPackage;

	private String daoSuperClass;
	
	private String javaMapperFileName;

	public BaseCustomizePlugin() {
		shellCallback = new DefaultShellCallback(false);		
	}

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
		String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
		log.info("initialized start. Tartget: " + tableName);
        
        pkType = this.getPkType(introspectedTable);
        
        printIntrospectedTableInfo(introspectedTable);
        
        String baseRecordType = introspectedTable.getBaseRecordType();
        
        // 自定义JavaMapper文件名
        String shortName = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1);
        if (shortName.startsWith("Tb")) {
            shortName = shortName.substring(2);
        }
        String daoName = shortName + "Mapper";
        javaMapperFileName = daoTargetPackage + "." + daoName;
        log.info("Java Mapper FileName = " + javaMapperFileName);
        FullyQualifiedJavaType daoType = new FullyQualifiedJavaType(javaMapperFileName);

        // 设置SqlMapper的namespace
        introspectedTable.setMyBatis3JavaMapperType(javaMapperFileName);

    	// 自定义Example文件名
    	String exampleType = introspectedTable.getExampleType();
    	exampleType = exampleType.replace("entity.Tb", "entity.").replace("Example", "EntityExample");
    	introspectedTable.setExampleType(exampleType);
    	log.info("Java Example FileName= " + exampleType);

    	// 自定义Entity文件名
    	baseRecordType = baseRecordType.replace("entity.Tb", "entity.");
        introspectedTable.setBaseRecordType(baseRecordType + "Entity");
        log.info("Java Entity FileName = " + introspectedTable.getBaseRecordType());

        // 自定义扩展Controller、Service、Helper、Dto文件名
        introspectedTable.setAttribute(ConstUtils.RECORD_SHORT_NAME, shortName);
        introspectedTable.setAttribute(ConstUtils.CONTROLLER, shortName + "Controller");
        introspectedTable.setAttribute(ConstUtils.SERVICE, "I" + shortName + "Service");
        introspectedTable.setAttribute(ConstUtils.SERVICE_IMPL, shortName + "ServiceImpl");
        introspectedTable.setAttribute(ConstUtils.HELPER, shortName + "Helper");
        introspectedTable.setAttribute(ConstUtils.REQ_DTO, shortName + "ReqDto");
        introspectedTable.setAttribute(ConstUtils.RES_DTO, shortName + "ResDto");
        introspectedTable.setAttribute(ConstUtils.DAO, daoName);
        introspectedTable.setAttribute(ConstUtils.DAO_TYPE, daoType);

		log.info("initialized end. ");
    }

	@Override
	public boolean validate(List<String> warnings) {
		log.info("validate start ");
        
		daoTargetDir = properties.getProperty("daoTargetDir");
		boolean valid = stringHasValue(daoTargetDir);

		daoTargetPackage = properties.getProperty("daoTargetPackage");
		boolean valid2 = stringHasValue(daoTargetPackage);

		daoSuperClass = properties.getProperty("daoSuperClass");
		boolean valid3 = stringHasValue(daoSuperClass);

		boolean check = valid && valid2 && valid3;
		if (!check) {
			log.error("Required parameter missing.");
		}
		return check;
	}

	/**
	 * 生成扩展Dao文件
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		log.info("------ 开始: 生成Dao类文件 ------");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = formatter.format(new Date());

        String shortClassName = javaMapperFileName.substring(javaMapperFileName.lastIndexOf(".") + 1);

		List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();

		Interface mapperInterface = new Interface(javaMapperFileName);
		// 添加生成类注解内容
		mapperInterface.setVisibility(JavaVisibility.PUBLIC);
		mapperInterface.addFileCommentLine(" /**");
		mapperInterface.addFileCommentLine(" * " + shortClassName + ".java");
		mapperInterface.addFileCommentLine(" * Created at " + createDate);
		mapperInterface.addFileCommentLine(" * Created by mybatis generator");
		mapperInterface.addFileCommentLine(" * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.");
		mapperInterface.addFileCommentLine(" **/");

		mapperInterface.addJavaDocLine(" /**");
		mapperInterface.addJavaDocLine(" * ClassName: " + shortClassName);
		mapperInterface.addJavaDocLine(" * Description: " + introspectedTable.getTableConfiguration().getTableName() + " dao class");
		mapperInterface.addJavaDocLine(" * Author: mybatis generator");
		mapperInterface.addJavaDocLine(" * Date: " + createDate);
		mapperInterface.addJavaDocLine(" **/");

		// 添加import包名
		FullyQualifiedJavaType subModelJavaType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		FullyQualifiedJavaType subModelExampleJavaType = new FullyQualifiedJavaType(introspectedTable.getExampleType());
		FullyQualifiedJavaType daoSuperType = new FullyQualifiedJavaType(daoSuperClass);
		mapperInterface.addImportedType(daoSuperType);
		mapperInterface.addImportedType(subModelJavaType);
		mapperInterface.addImportedType(subModelExampleJavaType);

		// 添加泛型支持
		FullyQualifiedJavaType daoExtendsType = new FullyQualifiedJavaType(daoSuperType.getShortName());
		daoExtendsType.addTypeArgument(subModelJavaType);
		daoExtendsType.addTypeArgument(subModelExampleJavaType);
		daoExtendsType.addTypeArgument(pkType); // 主键类型
		mapperInterface.addSuperInterface(daoExtendsType);
		
		try {
			GeneratedJavaFile mapperJavafile = new GeneratedJavaFile(mapperInterface, daoTargetDir, context.getJavaFormatter());
			File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);
			File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
			// 文件不存在
			if (!mapperFile.exists()) {
				mapperJavaFiles.add(mapperJavafile);
			} else {
				log.info(mapperJavafile.getFileName() + "文件已存在（未覆盖）");
			}
		} catch (ShellException e) {
			e.printStackTrace();
		}

		log.info("------ 结束: 生成Dao类文件 ------");
		return mapperJavaFiles;
	}

    /* 
     * 生成扩展Mapper Xml文件
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
    	log.info("------ 开始：生成扩展xml文件 ------");

		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>(1);

		try {
			// 生成新的空的xml
			Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID, XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
			XmlElement root = new XmlElement("mapper");
			root.addAttribute(new Attribute("namespace", introspectedTable.getMyBatis3SqlMapNamespace()));
			document.setRootElement(root);

			String fileName = introspectedTable.getMyBatis3XmlMapperFileName();
			String targetPackage = introspectedTable.getMyBatis3XmlMapperPackage() + ".ext";
			String targetProject = context.getSqlMapGeneratorConfiguration().getTargetProject();

			GeneratedXmlFile gxf = new GeneratedXmlFile(document, fileName, targetPackage, targetProject, true, context.getXmlFormatter());

			File mapperXmlDir = shellCallback.getDirectory(targetProject, introspectedTable.getMyBatis3XmlMapperPackage());
			File mapperXmlFile = new File(mapperXmlDir, fileName);
			// 删除原生Mapper Xml文件，否则会被merge。
			if (mapperXmlFile.exists()) {
				mapperXmlFile.delete();
			}

			answer.add(gxf);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ShellException e) {
			e.printStackTrace();
		}

		log.info("------ 结束：生成扩展xml文件 ------");
    	
    	return answer;
    }

	/**
	 * 获取主键类型
	 * 
	 * @param introspectedTable
	 * @return
	 */
	private FullyQualifiedJavaType getPkType(IntrospectedTable introspectedTable) {
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		FullyQualifiedJavaType primaryType = primaryColumn.getFullyQualifiedJavaType();
		return primaryType;
	}

	/**
	 * 打印表生成对象基本信息
	 * 
	 * @param introspectedTable
	 */
	private void printIntrospectedTableInfo(IntrospectedTable introspectedTable) {
    	System.out.println("introspectedTable.getTableType() = " + introspectedTable.getTableType());
    	System.out.println("introspectedTable.getRemarks() = " + introspectedTable.getRemarks());
    	System.out.println("introspectedTable.getPrimaryKeyType() = " + introspectedTable.getPrimaryKeyType());
    	System.out.println("introspectedTable.getBaseRecordType() = " + introspectedTable.getBaseRecordType());
    	System.out.println("introspectedTable.getFullyQualifiedTable() = " + introspectedTable.getFullyQualifiedTable());
    	System.out.println("introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime() = " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
    	System.out.println("introspectedTable.getExampleType() = " + introspectedTable.getExampleType());
    	System.out.println("introspectedTable.getMyBatis3FallbackSqlMapNamespace() = " + introspectedTable.getMyBatis3FallbackSqlMapNamespace());
    	System.out.println("introspectedTable.getMyBatis3JavaMapperType() = " + introspectedTable.getMyBatis3JavaMapperType());
    	System.out.println("introspectedTable.getMyBatis3SqlMapNamespace() = " + introspectedTable.getMyBatis3SqlMapNamespace());
    	System.out.println("introspectedTable.getMyBatis3XmlMapperFileName() = " + introspectedTable.getMyBatis3XmlMapperFileName());
    	System.out.println("introspectedTable.getMyBatis3XmlMapperPackage() = " + introspectedTable.getMyBatis3XmlMapperPackage());
	}
	
}
