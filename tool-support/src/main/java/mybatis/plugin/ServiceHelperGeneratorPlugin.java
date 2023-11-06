package mybatis.plugin;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ConstUtils;
import mybatis.utils.DateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 生成Service Helper（辅助类）
 * 
 * @author xyb
 *
 */
@Slf4j
public class ServiceHelperGeneratorPlugin extends PluginAdapter {

	private ShellCallback shellCallback = null;

	private String helperTargetDir;

	private String helperTargetPackage;

	private String helperName;

    /**
     * 获取操作人
     */
    private String author = System.getProperty("user.name");

	public ServiceHelperGeneratorPlugin() {
		shellCallback = new DefaultShellCallback(false);
	}

	@Override
	public boolean validate(List<String> warnings) {
		log.info("Service helper生成参数校验开始");
		helperTargetDir = properties.getProperty("helperTargetDir");
		boolean valid = stringHasValue(helperTargetDir);

		helperTargetPackage = properties.getProperty("helperTargetPackage");
		boolean valid2 = stringHasValue(helperTargetPackage);
		boolean check = valid && valid2;
		if (!check) {
			log.error("Service Helper生成缺少必须参数");
		} else {
			log.info("Service helper生成参数校验通过");
		}
		
    	helperTargetPackage = helperTargetPackage + ".helper";

		return check;
	}

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
    	log.info("initialized start. ");

    	helperName = (String)introspectedTable.getAttribute(ConstUtils.HELPER);
    	log.info("helper Name is {}", helperName);

		log.info("initialized end. ");
    }

	/**
	 * 生成扩展Helper文件
	 */
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		log.info("------ 开始: 生成Service helper类文件 ------");
		List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<GeneratedJavaFile>();

		CompilationUnit helperCu = this.generateHelper(introspectedTable);
		try {
			GeneratedJavaFile helperJavafile = new GeneratedJavaFile(helperCu, helperTargetDir, context.getJavaFormatter());
			// 判断文件是否存在
			File dir = shellCallback.getDirectory(helperTargetDir, helperTargetPackage);
			File file = new File(dir, helperJavafile.getFileName());
			// 文件不存在
			if (!file.exists()) {
				mapperJavaFiles.add(helperJavafile);
			}
		} catch (ShellException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		log.info("------ 结束: 输出Service helper类文件 ------");
		return mapperJavaFiles;
	}

	/**
	 * 编辑Helper内容
	 * 
	 * @param introspectedTable
	 * @return
	 */
	private CompilationUnit generateHelper(IntrospectedTable introspectedTable) {
		// Helper类型保存，其它类引用。
		FullyQualifiedJavaType helperType = new FullyQualifiedJavaType(this.helperTargetPackage + "." + helperName);
		introspectedTable.setAttribute(ConstUtils.HELPER_TYPE, helperType);
		
		// 类定义
		TopLevelClass topLevelClass = new TopLevelClass(helperType);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);

		// 添加文件注释
		CommentGenerator commentGenerator = context.getCommentGenerator();
		commentGenerator.addJavaFileComment(topLevelClass);
		FullyQualifiedJavaType fromType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		topLevelClass.addImportedType(fromType);

		topLevelClass.addAnnotation("@Component");
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));

		// 添加类注释
		topLevelClass.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		topLevelClass.addJavaDocLine(" * " + remarks + "Service helper类");
		topLevelClass.addJavaDocLine(" * @author " + author + "  " + DateUtils.getNowTime());
		topLevelClass.addJavaDocLine(" */");

		// 设置helper内部方法
		setMethod(context, introspectedTable, topLevelClass, fromType);
		return topLevelClass;
	}

	/**
	 * 设置helper内部方法
	 * 
	 * @param context
	 * @param introspectedTable
	 * @param topLevelClass
	 */
	private void setMethod(Context context, IntrospectedTable introspectedTable, TopLevelClass topLevelClass,
			FullyQualifiedJavaType fromType) {
		editResDto(context, introspectedTable, fromType, topLevelClass);
		editResDtoList(context, introspectedTable, fromType, topLevelClass);
		editEntity(context, introspectedTable, fromType, topLevelClass);
		editEntityList(context, introspectedTable, fromType, topLevelClass);
	}

	/**
	 * Dto列表转Entity列表方法
	 * 
	 * @param context
	 * @param introspectedTable
	 * @return
	 */
	private Method editEntityList(Context context, IntrospectedTable introspectedTable, FullyQualifiedJavaType fromType,
			TopLevelClass topLevelClass) {
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		returnType.addTypeArgument(fromType);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		topLevelClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());
		// 参数名称
		String parameterName = "reqDtoList";
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(returnType);
		method.setName("editEntityList");
		
		method.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		method.addJavaDocLine(" * " + remarks + "Service实现类");
		method.addJavaDocLine(" * @author " + author + "  " + DateUtils.getNowTime());
		method.addJavaDocLine(" */");
		
		FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType reqDtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		paramType.addTypeArgument(reqDtoType);
		Parameter parameter = new Parameter(paramType, parameterName);
		method.addParameter(parameter);
		
		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Dto列表转换Entity列表方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param reqDtoList dto列表");
        method.addJavaDocLine(" * @return Entity列表");
        method.addJavaDocLine(" */");

        // 方法体
		method.addBodyLine("List<" + fromType.getShortName() + "> entityList = new ArrayList<>();");
		method.addBodyLine("if (" + parameterName + " == null || " + parameterName + ".isEmpty()){");
		method.addBodyLine("return entityList;");
		method.addBodyLine("}");
		method.addBodyLine(parameterName + ".forEach(reqDto -> {");
		method.addBodyLine("entityList.add(this.editEntity(reqDto));");
		method.addBodyLine("});");
		method.addBodyLine("return entityList;");
		topLevelClass.addMethod(method);
		return method;
	}

	/**
	 * Entity列表转Dto列表方法
	 * 
	 * @param context
	 * @param introspectedTable
	 * @return
	 */
	private Method editResDtoList(Context context, IntrospectedTable introspectedTable, FullyQualifiedJavaType fromType,
			TopLevelClass topLevelClass) {
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType resDtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE);
		returnType.addTypeArgument(resDtoType);
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		topLevelClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());
		// 参数名称
		String parameterName = "entityList";
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(returnType);
		method.setName("editResDtoList");
		FullyQualifiedJavaType paramType = FullyQualifiedJavaType.getNewListInstance();
		paramType.addTypeArgument(fromType);
		Parameter parameter = new Parameter(paramType, parameterName);
		method.addParameter(parameter);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Entity列表转换Dto列表方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param entityList Dto列表");
        method.addJavaDocLine(" * @return Dto列表");
        method.addJavaDocLine(" */");

        // 方法体
		method.addBodyLine("List<" + resDtoType.getShortName() + "> resDtoList = new ArrayList<>();");
		method.addBodyLine("if (" + parameterName + " == null || " + parameterName + ".isEmpty()){");
		method.addBodyLine("return resDtoList;");
		method.addBodyLine("}");
		method.addBodyLine(parameterName + ".forEach(entity -> {");
		method.addBodyLine("resDtoList.add(this.editResDto(entity));");
		method.addBodyLine("});");
		method.addBodyLine("return resDtoList;");
		topLevelClass.addMethod(method);
		return method;
	}

	/**
	 * Dto转Entity方法
	 * 
	 * @param context
	 * @param introspectedTable
	 * @param topLevelClass
	 * @return
	 */
	private Method editEntity(Context context, IntrospectedTable introspectedTable, FullyQualifiedJavaType fromType,
			TopLevelClass topLevelClass) {
		FullyQualifiedJavaType reqDtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
		topLevelClass.addImportedType(reqDtoType);
		topLevelClass.addImportedType(FullyQualifiedJavaType.getDateInstance());
		// 参数名称
		String parameterName = "reqDto";
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(fromType);
		method.setName("editEntity");
		Parameter parameter = new Parameter(reqDtoType, parameterName);
		method.addParameter(parameter);
		
		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Dto转换Entity方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param reqDto Dto对象");
        method.addJavaDocLine(" * @return Entity");
        method.addJavaDocLine(" */");

        // 方法体
		method.addBodyLine("if (" + parameterName + " == null) {");
		method.addBodyLine("return null;");
		method.addBodyLine("}");
		method.addBodyLine(fromType.getShortName() + " entity = new " + fromType.getShortName() + "();");
		// 设置set方法
		setEntityValue(method, introspectedTable, parameterName);

		method.addBodyLine("return entity;");
		topLevelClass.addMethod(method);
		return method;
	}

	private void setEntityValue(Method method, IntrospectedTable introspectedTable, String pn) {
		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			if (ConstUtils.FIELD_PK.equals(introspectedColumn.getJavaProperty())) {
				continue;
			}
			Method setMethod = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
			String setName = setMethod.getName();
			Method getMethod = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
			String getName = getMethod.getName();
			sb.setLength(0);
			if (ConstUtils.FIELD_MODIFY_TIME.equals(introspectedColumn.getJavaProperty()) || 
					ConstUtils.FIELD_CREATE_TIME.equals(introspectedColumn.getJavaProperty())) {
				sb.append("entity.");
				sb.append(setName);
				sb.append("(new Date());  // TODO 注意新增和更新方法");
				method.addBodyLine(sb.toString());
			} else {
				sb.append("entity.");
				sb.append(setName);
				sb.append("(reqDto."); //$NON-NLS-1$
				sb.append(getName);
				sb.append("());");
				method.addBodyLine(sb.toString());
			}
		}
	}

	/**
	 * Entity转Dto
	 * 
	 * @param context
	 * @param introspectedTable
	 * @param topLevelClass
	 * @return
	 */
	private Method editResDto(Context context, IntrospectedTable introspectedTable, FullyQualifiedJavaType fromType,
			TopLevelClass topLevelClass) {
		FullyQualifiedJavaType resDtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE);
		topLevelClass.addImportedType(resDtoType);
		// 参数名称
		String parameterName = "entity";
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(resDtoType);
		method.setName("editResDto");
		Parameter parameter = new Parameter(fromType, parameterName);
		method.addParameter(parameter);
		
		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * Entity转换Dto方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param " + parameterName + " Entity对象");
        method.addJavaDocLine(" * @return Dto");
        method.addJavaDocLine(" */");

        // 方法体
		method.addBodyLine("if (" + parameterName + " == null) {");
		method.addBodyLine("return null;");
		method.addBodyLine("}");
		method.addBodyLine(resDtoType.getShortName() + " resDto = new " + resDtoType.getShortName() + "();");
		// 设置set方法
		setResDtoValue(method, introspectedTable, parameterName);

		method.addBodyLine("return resDto;");
		topLevelClass.addMethod(method);
		return method;
	}

	/**
	 * 设置set方法
	 * 
	 * @param method
	 * @param introspectedTable
	 * @param pn
	 */
	private void setResDtoValue(Method method, IntrospectedTable introspectedTable, String pn) {
		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			Method setMethod = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
			String setName = setMethod.getName();
			Method getMethod = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
			String getName = getMethod.getName();
			sb.setLength(0);
			sb.append("resDto.");
			sb.append(setName);
			sb.append("(entity.");
			sb.append(getName);
			sb.append("());");
			method.addBodyLine(sb.toString());
		}
	}

}
