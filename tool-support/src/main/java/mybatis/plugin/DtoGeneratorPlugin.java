package mybatis.plugin;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ConstUtils;
import mybatis.utils.LombokUtils;
import mybatis.utils.SwaggerUtils;
import mybatis.utils.ValidUtils;

/**
 * 生成Dto
 * 
 * @author xyb
 *
 */
@Slf4j
public class DtoGeneratorPlugin extends PluginAdapter {

	private ShellCallback shellCallback = null;

	private String dtoTargetDir;

	private String dtoTargetPackage;

	private String reqDtoTargetPackage;

	private String resDtoTargetPackage;

	private List<String> warnings;

	/**
	 * 是否使用lombok
	 */
	private boolean isLombok = true;

	/**
	 * 是否使用swagger
	 */
	private boolean isSwaggerFlag = true;

	public DtoGeneratorPlugin() {
		shellCallback = new DefaultShellCallback(false);
	}

	@Override
	public boolean validate(List<String> warnings) {
		log.info("Dto生成参数校验开始");

		dtoTargetDir = properties.getProperty("dtoTargetDir");
		boolean valid = stringHasValue(dtoTargetDir);

		dtoTargetPackage = properties.getProperty("dtoTargetPackage");
		boolean valid2 = stringHasValue(dtoTargetPackage);
		
//		String swaggerFlag = properties.getProperty("isSwaggerFlag");
//		if(stringHasValue(swaggerFlag)) {
//			isSwaggerFlag = Boolean.valueOf(swaggerFlag);
//		}

		boolean check = valid && valid2;
		if (!check) {
			log.error("Dto生成必须参数缺失");
		}else {
			log.info("Dto生成参数校验通过");
		}

		reqDtoTargetPackage = dtoTargetPackage + ".req";
		resDtoTargetPackage = dtoTargetPackage + ".res";

		this.warnings = warnings;
		return check;
	}

	/**
	 * 生成reqDto文件
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		log.info("------ 开始: 生成Dto类文件 ------");
		List<GeneratedJavaFile> dtoJavaFiles = new ArrayList<GeneratedJavaFile>();

		/**
		 * 生成请求Dto信息
		 */
		CompilationUnit reqDtoCu = this.generateReqDto(introspectedTable);
		try {
			GeneratedJavaFile javafile = new GeneratedJavaFile(reqDtoCu, dtoTargetDir, context.getJavaFormatter());
			//判断文件是否存在
			File dir = shellCallback.getDirectory(dtoTargetDir, reqDtoTargetPackage);
			File file = new File(dir, javafile.getFileName());
			// 文件不存在
			if (!file.exists()) {
				dtoJavaFiles.add(javafile);
			} else {
				log.info(javafile.getFileName() + "文件已存在（未覆盖）");
			}
		} catch (ShellException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		/**
		 * 生成返回Dto信息
		 */
		CompilationUnit resDtoCu = this.generateResDto(introspectedTable);
		try {
			GeneratedJavaFile javafile = new GeneratedJavaFile(resDtoCu, dtoTargetDir, context.getJavaFormatter());
			//判断文件是否存在
			File dir = shellCallback.getDirectory(dtoTargetDir, resDtoTargetPackage);
			File file = new File(dir, javafile.getFileName());
			// 文件不存在
			if (!file.exists()) {
				dtoJavaFiles.add(javafile);
			} else {
				log.info(javafile.getFileName() + "文件已存在（未覆盖）");
			}
		} catch (ShellException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		log.info("------ 结束: 生成Dto类文件 ------");
		return dtoJavaFiles;
	}

	/**
	 * 请求Dto信息
	 * 
	 * @param introspectedTable
	 * @return
	 */
	private CompilationUnit generateReqDto(IntrospectedTable introspectedTable) {
		// 设置reqDto类信息
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				this.reqDtoTargetPackage + "." + table.getDomainObjectName() + "ReqDto");
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);

		//保存reqDto类型
		introspectedTable.setAttribute(ConstUtils.REQ_DTO_TYPE, type);

		// 添加java文件注释
		Plugin plugins = context.getPlugins();
		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(topLevelClass);

		FullyQualifiedJavaType superClass = getSuperClass(introspectedTable);
		if (superClass != null) {
			topLevelClass.setSuperClass(superClass);
			topLevelClass.addImportedType(superClass);
		}

		// 添加类注释
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

		// 添加lombok注解
		LombokUtils.addLomBokAnnotation(topLevelClass, isLombok);

		//添加swagger注释
		SwaggerUtils.addClassAnnotation(topLevelClass, isSwaggerFlag, introspectedTable.getRemarks());

		//构造函数
		if (introspectedTable.isConstructorBased()) {
			addParameterizedConstructor(topLevelClass, introspectedTable);

			if (!introspectedTable.isImmutable()) {
				addDefaultConstructor(topLevelClass, introspectedTable);
			}
		}

		String rootClass = getRootClass(introspectedTable);
		
		// 获取所有列
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
		introspectedColumns.removeAll(primaryKeyColumns);
		int i=0;
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			if (RootClassInfo.getInstance(rootClass, warnings).containsProperty(introspectedColumn)) {
				continue;
			}
			
			Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
			
			//添加属性swagger注释
			SwaggerUtils.addFieldAnnotation(field, isSwaggerFlag, introspectedColumn.getRemarks(),i++,"reqDto");
			
			if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				topLevelClass.addField(field);
				topLevelClass.addImportedType(field.getType());
			}

			//给字段添加校验注解
			ValidUtils.addValidationAnnotation(topLevelClass, field, introspectedColumn.getRemarks(), introspectedColumn.getLength());;

			String getName="";
			String setName="";
			// 不使用lombok注解时生成get/set方法,否则需要生成get/set方法
			Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
			if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				if (!isLombok) {
					topLevelClass.addMethod(method);
				}
			}
			getName=method.getName();
			if (!introspectedTable.isImmutable()) {
				method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
				setName=method.getName();
				if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
						Plugin.ModelClassType.BASE_RECORD)) {
					if (!isLombok) {
						topLevelClass.addMethod(method);
					}
				}
			}
		}

		return topLevelClass;
	}

	/**
	 * 返回Dto信息
	 * 
	 * @param introspectedTable
	 * @return
	 */
	private CompilationUnit generateResDto(IntrospectedTable introspectedTable) {
		// 设置Dto类信息
		FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(
				this.resDtoTargetPackage + "." + table.getDomainObjectName() + "ResDto");
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);

		// 保存Dto类型
		introspectedTable.setAttribute(ConstUtils.RES_DTO_TYPE, type);

		// 添加java文件注释
		Plugin plugins = context.getPlugins();
		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(topLevelClass);

		FullyQualifiedJavaType superClass = getSuperClass(introspectedTable);
		if (superClass != null) {
			topLevelClass.setSuperClass(superClass);
			topLevelClass.addImportedType(superClass);
		}

		// 添加类注释
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);

		// 添加lombok注解
		LombokUtils.addLomBokAnnotation(topLevelClass, isLombok);

		// 添加swagger注释
		SwaggerUtils.addClassAnnotation(topLevelClass, isSwaggerFlag, introspectedTable.getRemarks());

		// 构造函数
		if (introspectedTable.isConstructorBased()) {
			addParameterizedConstructor(topLevelClass, introspectedTable);

			if (!introspectedTable.isImmutable()) {
				addDefaultConstructor(topLevelClass, introspectedTable);
			}
		}

		String rootClass = getRootClass(introspectedTable);
		
		// 获取所有列
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		int i=0;
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			if (RootClassInfo.getInstance(rootClass, warnings).containsProperty(introspectedColumn)) {
				continue;
			}

			Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
			// 添加属性swagger注释
			SwaggerUtils.addFieldAnnotation(field, isSwaggerFlag, introspectedColumn.getRemarks(),i++,"dto");
			
			// 添加日期类型格式（注释提示选择）
			if (FullyQualifiedJavaType.getDateInstance().equals(field.getType())) {
				field.addJavaDocLine("// @JSONField(format = DateUtil.DATETIMESHOWFORMAT)");
			}
			
			if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				topLevelClass.addField(field);
				topLevelClass.addImportedType(field.getType());
			}
			
			String getName="";
			String setName="";
			// 不使用lombok注解时生成get/set方法,否则需要生成get/set方法
			Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
			if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
					Plugin.ModelClassType.BASE_RECORD)) {
				if (!isLombok) {
					topLevelClass.addMethod(method);
				}
			}
			getName=method.getName();
			if (!introspectedTable.isImmutable()) {
				method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
				setName=method.getName();
				if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
						Plugin.ModelClassType.BASE_RECORD)) {
					if (!isLombok) {
						topLevelClass.addMethod(method);
					}
				}
			}
		}

		return topLevelClass;
	}

	private FullyQualifiedJavaType getSuperClass(IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType superClass;
		String rootClass = getRootClass(introspectedTable);
		if (rootClass != null) {
			superClass = new FullyQualifiedJavaType(rootClass);
		} else {
			superClass = null;
		}

		return superClass;
	}

	private void addParameterizedConstructor(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		List<IntrospectedColumn> constructorColumns = introspectedTable.getAllColumns();

		for (IntrospectedColumn introspectedColumn : constructorColumns) {
			method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(),
					introspectedColumn.getJavaProperty()));
		}

		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" = "); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		topLevelClass.addMethod(method);
	}

	public String getRootClass(IntrospectedTable introspectedTable) {
		String rootClass = introspectedTable.getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_CLASS);
		if (rootClass == null) {
			Properties properties = context.getJavaModelGeneratorConfiguration().getProperties();
			rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
		}

		return rootClass;
	}

	protected void addDefaultConstructor(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addMethod(getDefaultConstructor(topLevelClass, introspectedTable));
	}

	protected Method getDefaultConstructor(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());
		method.addBodyLine("super();"); //$NON-NLS-1$
		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		return method;
	}
}
