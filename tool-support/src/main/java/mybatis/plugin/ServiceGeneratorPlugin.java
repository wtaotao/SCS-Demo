package mybatis.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ConstUtils;
import mybatis.utils.DateUtils;

/**
 * 生成service类代码
 *
 * @author xyb
 */
@Slf4j
public class ServiceGeneratorPlugin extends PluginAdapter {
	private ShellCallback shellCallback = null;

	private String targetDir;

	private String targetPackage;

	private String implTargetPackage;

	private String busiExceptionPackage;

	private String serviceName;

	private String serviceImplName;
	
	private String helperFieldName;
	
	private String mapperFieldName;

	/**
	 * 获取操作人
	 */
	private String author = System.getProperty("user.name");

	public ServiceGeneratorPlugin() {
		shellCallback = new DefaultShellCallback(false);
	}

	@Override
	public boolean validate(List<String> warnings) {
		log.info("servcie生成参数校验开始");

		targetDir = properties.getProperty("targetDir");
		boolean valid = stringHasValue(targetDir);

		targetPackage = properties.getProperty("targetPackage");
		boolean valid2 = stringHasValue(targetPackage);

		implTargetPackage = targetPackage + ".impl";

		busiExceptionPackage = properties.getProperty("busiExceptionPackage");

		boolean check = valid && valid2;
		if (!check) {
			log.error("Servcie生成缺少必要参数.");
		} else {
			log.info("Servcie生成参数校验通过");
		}
		
		return check;
	}

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
    	log.info("initialized start. ");

    	serviceName = (String)introspectedTable.getAttribute(ConstUtils.SERVICE);
    	serviceImplName = (String)introspectedTable.getAttribute(ConstUtils.SERVICE_IMPL);
    	log.info("Service Name is {}", serviceName);
    	log.info("ServiceImpl Name is {}", serviceImplName);
    	
		log.info("initialized end. ");
    }

	/**
	 * 生成servcie文件
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		log.info("------ 开始: 生成servcie类文件 ------");
		List<GeneratedJavaFile> serviceJavaFiles = new ArrayList<GeneratedJavaFile>();

		if (!introspectedTable.hasPrimaryKeyColumns()) {
			log.error("缺少唯一主键设置");
			return serviceJavaFiles;
		}
		if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
			log.error("不支持复合主键类型");
			return serviceJavaFiles;
		}

		try {
			/**
			 * 生成service接口定义类
			 */
			CompilationUnit service = this.generateService(introspectedTable);
			GeneratedJavaFile serviceJavafile = new GeneratedJavaFile(service, targetDir, context.getJavaFormatter());
			// 生成文件，已存在不覆盖。
			File serviceIntfDir = shellCallback.getDirectory(targetDir, targetPackage);
			File serviceIntfFile = new File(serviceIntfDir, serviceJavafile.getFileName());
			if (!serviceIntfFile.exists()) {
				serviceJavaFiles.add(serviceJavafile);
			} else {
				log.info("{}文件已存在（未覆盖）", serviceJavafile.getFileName());
			}

			/**
			 * 生成service接口实现类
			 */
			CompilationUnit serviceImpl = this.generateServiceImpl(introspectedTable);
			GeneratedJavaFile serviceImplJavafile = new GeneratedJavaFile(serviceImpl, targetDir, context.getJavaFormatter());
			// 生成文件，已存在不覆盖。
			File serviceImplDir = shellCallback.getDirectory(targetDir, implTargetPackage);
			File serviceImplFile = new File(serviceImplDir, serviceImplJavafile.getFileName());
			if (!serviceImplFile.exists()) {
				serviceJavaFiles.add(serviceImplJavafile);
			} else {
				log.info("{}文件已存在（未覆盖）", serviceImplJavafile.getFileName());
			}

		} catch (ShellException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		log.info("------ 结束: 生成servcie类文件 ------");
		return serviceJavaFiles;
	}

	/**
	 * 生成service接口
	 *
	 * @param introspectedTable
	 * @return
	 */
	private CompilationUnit generateService(IntrospectedTable introspectedTable) {
		// 生成service接口类信息
		FullyQualifiedJavaType serviceIntfType = new FullyQualifiedJavaType(this.targetPackage + "." + serviceName);

		Interface intf = new Interface(serviceIntfType);
		intf.setVisibility(JavaVisibility.PUBLIC);

		// 添加java文件注释
		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(intf);

		// 添加service类注解
		this.addIntfClassComment(intf, introspectedTable);

		// 生成Select方法
		this.editIntfSelectMethod(intf, introspectedTable);

		// 生成Insert方法
		this.editIntfInsertMethod(intf, introspectedTable);

		// 生成Update方法
		this.editIntfUpdateMethod(intf, introspectedTable);

		// 生成Delete方法
		this.editIntfDeleteMethod(intf, introspectedTable);

		// TODO 导出方法
		// editIntfExportMethod

		return intf;
	}

	/**
	 * 生成service实现类
	 *
	 * @param introspectedTable
	 * @return
	 */
	private CompilationUnit generateServiceImpl(IntrospectedTable introspectedTable) {
		// 生成servcie实现类信息
		FullyQualifiedJavaType serviceImplType = new FullyQualifiedJavaType(this.implTargetPackage + "." + serviceImplName);
		TopLevelClass topLevelClass = new TopLevelClass(serviceImplType);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);

		// 添加java文件注释
		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(topLevelClass);

		// 继承父接口
		FullyQualifiedJavaType supperInterFace = new FullyQualifiedJavaType(this.targetPackage + "." + serviceName);
		topLevelClass.addSuperInterface(supperInterFace);
		topLevelClass.addImportedType(supperInterFace);

		topLevelClass.addAnnotation("@Service");
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		topLevelClass.addAnnotation("@Slf4j");
		topLevelClass.addImportedType("lombok.extern.slf4j.Slf4j");
		topLevelClass.addImportedType(busiExceptionPackage);

		// 添加类注释
		this.addImplClassComment(topLevelClass, introspectedTable);

		// 添加Mapper属性字段
		this.setMapperField(topLevelClass, introspectedTable);
		
		// 引入Helper转换属性
		this.setHelperField(topLevelClass, introspectedTable);
		
		// 实现SelectByPrimaryKey方法
		this.editImplSelectMethod(topLevelClass, introspectedTable);
		
		// 实现insert方法
		this.editImplInsertMethod(topLevelClass, introspectedTable);
		
		// 实现UpdateByPrimaryKey方法
		this.editImplUpdateMethod(topLevelClass, introspectedTable);

		// 实现删除方法
		this.editImplDeleteMethod(topLevelClass, introspectedTable);

		// TODO 实现导出方法
		// editImplExportMethod

		return topLevelClass;
	}

	/**
	 * 引入Mapper属性
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void setMapperField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(introspectedTable.getAttribute(ConstUtils.DAO_TYPE).toString());
		topLevelClass.addImportedType(mapper);

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(mapper);
		field.addAnnotation("@Autowired");
		topLevelClass
				.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
		// mapper属性名
		mapperFieldName = mapper.getShortName().substring(0, 1).toLowerCase() + mapper.getShortName().substring(1);
		field.setName(mapperFieldName);
		topLevelClass.addField(field);
		//topLevelClass.addImportedType(field.getType());
		
        // 属性注释
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedTable.getRemarks() + "Mapper");
        field.addJavaDocLine(" */");
	}

	/**
	 * 引入Helper属性
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 */
	private void setHelperField(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType helperType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.HELPER_TYPE);
		topLevelClass.addImportedType(helperType);
		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(helperType);
		field.addAnnotation("@Autowired");
		helperFieldName = helperType.getShortName().substring(0, 1).toLowerCase()
				+ helperType.getShortName().substring(1);
		field.setName(helperFieldName);
		topLevelClass.addField(field);

        // 属性注释
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedTable.getRemarks() + "Helper");
        field.addJavaDocLine(" */");
	}

	/**
	 * 表对应Service接口类注释
	 */
	private void addIntfClassComment(Interface interfaze, IntrospectedTable introspectedTable) {
		interfaze.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		interfaze.addJavaDocLine(" * " + remarks + "Service接口类");
		interfaze.addJavaDocLine(" * @author " + author + "  " + DateUtils.getNowTime());
		interfaze.addJavaDocLine(" */");
	}

	/**
	 * 表对应Service实现类注释
	 */
	private void addImplClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		topLevelClass.addJavaDocLine(" * " + remarks + "Service实现类");
		topLevelClass.addJavaDocLine(" * @author " + author + "  " + DateUtils.getNowTime());
		topLevelClass.addJavaDocLine(" */");
	}

	/**
	 * 获取主键参数
	 * 
	 * @param introspectedTable
	 * @return
	 */
	private Parameter getPrimaryKeyParameter(IntrospectedTable introspectedTable) {
		// 主键参数
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		FullyQualifiedJavaType primaryType = primaryColumn.getFullyQualifiedJavaType();
		String primaryKey = primaryColumn.getJavaProperty();
		return new Parameter(primaryType, primaryKey);
	}

	/**
	 * 生成delete方法
	 *
	 * @param interfaze
	 * @param introspectedTable
	 * @return
	 */
	private void editIntfDeleteMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("deleteByPrimaryKey");

		// 方法参数
		method.addParameter(this.getPrimaryKeyParameter(introspectedTable));

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键删除方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @return 删除数量");
        method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	/**
	 * 生成selectByPrimaryKey方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editIntfSelectMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType((FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE));
		method.setName("queryByPrimaryKey");

		// 方法参数
		method.addParameter(this.getPrimaryKeyParameter(introspectedTable));

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键查询方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @return " + method.getReturnType().getShortName());
        method.addJavaDocLine(" */");

		importedTypes.add((FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE));
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	/**
	 * 生成接口insert方法
	 *
	 * @param interfaze
	 * @param introspectedTable
	 * @return
	 */
	private void editIntfInsertMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("create" + introspectedTable.getFullyQualifiedTable().getDomainObjectName());

		FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		importedTypes.add(type);
		// 方法参数
		Parameter parameter = new Parameter(type, "reqDto");
		method.addParameter(parameter);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 基础新增方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param reqDto 请求Dto");
        method.addJavaDocLine(" * @return 新增数量");
        method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	/**
	 * 生成接口update方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editIntfUpdateMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("updateByPrimaryKey");

		// 方法参数
		method.addParameter(this.getPrimaryKeyParameter(introspectedTable));

		FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		importedTypes.add(type);
		Parameter parameter = new Parameter(type, "reqDto");
		method.addParameter(parameter);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键更新方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @param reqDto 请求Dto");
        method.addJavaDocLine(" * @return 更新数量");
        method.addJavaDocLine(" */");

		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);
	}

	/**
	 * 生成实现类delete方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editImplDeleteMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addAnnotation("@Override");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("deleteByPrimaryKey");

		topLevelClass.addImportedTypes(importedTypes);

		// 方法参数
		Parameter primaryKeyParameter = this.getPrimaryKeyParameter(introspectedTable);
		method.addParameter(primaryKeyParameter);

		String primaryKey = primaryKeyParameter.getName();
		method.addBodyLine("log.info(\"Service deleteByPrimaryKey start. pkId={}\", " + primaryKey + ");");

		// 参数判断
		method.addBodyLine("if(" + primaryKey + " == null) {");
		method.addBodyLine("throw new BusiException(\"主键参数为空\");");
		method.addBodyLine("}");

		method.addBodyLine("int ret = this." + mapperFieldName + ".deleteByPrimaryKey(" + primaryKey + "); ");
		method.addBodyLine("log.info(\"Service deleteByPrimaryKey end. ret={}\", ret);");
		method.addBodyLine("return ret;");
		topLevelClass.addMethod(method);
	}

	/**
	 * 实现selectByPrimaryKey方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editImplSelectMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addAnnotation("@Override");
		FullyQualifiedJavaType resDtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE);
		topLevelClass.addImportedType(resDtoType);
		method.setReturnType(resDtoType);
		method.setName("queryByPrimaryKey");

		// 方法参数
		Parameter primaryKeyParameter = this.getPrimaryKeyParameter(introspectedTable);
		method.addParameter(primaryKeyParameter);
		String primaryKey = primaryKeyParameter.getName();

		FullyQualifiedJavaType fromType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		String fn = "selectByPrimaryKey";
		method.addBodyLine("log.info(\"Service selectByPrimaryKey start. primaryKey={}\", " + primaryKey + ");");
		method.addBodyLine(
				fromType.getShortName() + " entity = this." + mapperFieldName + "." + fn + "(" + primaryKey + ");");
		method.addBodyLine(((FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE)).getShortName()
				+ " resDto = this." + helperFieldName + ".editResDto(entity);");
		method.addBodyLine("log.info(\"Service selectByPrimaryKey end. res={}\", resDto);");
		method.addBodyLine("return resDto;");
		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

	/**
	 * 实现insert方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editImplInsertMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addAnnotation("@Override");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		String methodname = "create" + introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		method.setName(methodname);

		FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		topLevelClass.addImportedType(type);
		Parameter parameter = new Parameter(type, "reqDto");
		method.addParameter(parameter);

		FullyQualifiedJavaType entity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		method.addBodyLine("log.info(\"Service " + methodname + " start. reqDto={}\", reqDto);");
		method.addBodyLine(entity.getShortName() + " entity = this." + helperFieldName + ".editEntity(reqDto);");
		method.addBodyLine("// TODO 添加主键");
		method.addBodyLine("// entity.setPkId(UuidUtil.getUuid());");
		method.addBodyLine("// entity.setPkId(SnowflakeId.getSfid());");
		method.addBodyLine("int ret = this." + mapperFieldName + ".insert(entity);");
		method.addBodyLine("log.info(\"Service " + methodname + " end. ret={}\", ret);");
		method.addBodyLine("return ret;");
		topLevelClass.addMethod(method);
	}

	/**
	 * 实现updateByPrimaryKey方法
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return
	 */
	private void editImplUpdateMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addAnnotation("@Override");
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("updateByPrimaryKey");
		// 方法参数
		Parameter primaryKeyParameter = this.getPrimaryKeyParameter(introspectedTable);
		String primaryKey = primaryKeyParameter.getName();
		method.addParameter(primaryKeyParameter);
		
		FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		topLevelClass.addImportedType(type);
		Parameter reqDtoParameter = new Parameter(type, "reqDto");
		method.addParameter(reqDtoParameter);

		FullyQualifiedJavaType entity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		topLevelClass.addImportedType(entity);
		method.addBodyLine("log.info(\"Service updateByPrimaryKey start. pkId={}, reqDto={}\", pkId, reqDto);");

		// 参数判断
		method.addBodyLine("if(" + primaryKey + " == null) {");
		method.addBodyLine("throw new BusiException(\"主键参数为空\");");
		method.addBodyLine("}");

		method.addBodyLine(entity.getShortName() + " entity = this." + helperFieldName + ".editEntity(reqDto);");
		String fn = "updateByPrimaryKey";
		method.addBodyLine("entity.set" + primaryKey.substring(0, 1).toUpperCase() + primaryKey.substring(1) + "("
				+ primaryKey + ");");
		method.addBodyLine("int ret = this." + mapperFieldName + "." + fn + "(entity);");
		method.addBodyLine("log.info(\"Service updateByPrimaryKey end. ret={}\", ret);");
		method.addBodyLine("return ret;");
		topLevelClass.addMethod(method);
	}

}
