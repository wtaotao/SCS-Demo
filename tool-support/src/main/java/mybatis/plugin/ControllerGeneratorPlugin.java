package mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.ConstUtils;
import mybatis.utils.DateUtils;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 生成Controller类代码
 *
 * @author xyb
 */
@Slf4j
public class ControllerGeneratorPlugin extends PluginAdapter {

    private ShellCallback shellCallback = null;

    private String targetDir;

    private String targetPackage;

    private String servicePackage;

    private String responseDataPackage;

	private String controllerName;

	private String serviceName;

	private String serviceFieldName;

    /**
     * 获取操作人
     */
    private String author = System.getProperty("user.name");

    public ControllerGeneratorPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    @Override
    public boolean validate(List<String> warnings) {
        log.info("Controller生成参数校验开始");
        targetDir = properties.getProperty("targetDir");
        boolean valid = stringHasValue(targetDir);

        targetPackage = properties.getProperty("targetPackage");
        boolean valid2 = stringHasValue(targetPackage);

        servicePackage = properties.getProperty("servicePackage");
        boolean valid3 = stringHasValue(servicePackage);

        responseDataPackage = properties.getProperty("responseDataPackage");

        boolean check = valid && valid2 && valid3;
        if (!check) {
            log.error("Controller生成缺少必要参数");
        } else {
            log.info("Controller生成参数校验通过");
        }
        return check;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
    	log.info("initialized start. ");

    	serviceName = (String)introspectedTable.getAttribute(ConstUtils.SERVICE);
    	controllerName = (String)introspectedTable.getAttribute(ConstUtils.CONTROLLER);
     	
    	serviceFieldName = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);

		log.info("initialized end. ");
    }

    /**
     * 生成Controller文件
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        log.info("------ 开始: 生成Controller类文件 ------");
        List<GeneratedJavaFile> controllerJavaFiles = new ArrayList<GeneratedJavaFile>();

		if (!introspectedTable.hasPrimaryKeyColumns()) {
			log.error("缺少唯一主键设置");
			return controllerJavaFiles;
		}
		if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
			log.error("不支持复合主键类型");
			return controllerJavaFiles;
		}

        try {
	        // 生成Controller实现类
	        CompilationUnit controller = this.generateImplController(introspectedTable);
            GeneratedJavaFile controllerJavafile = new GeneratedJavaFile(controller, targetDir, context.getJavaFormatter());
            // 判断文件是否存在
            File controllerDir = shellCallback.getDirectory(targetDir, targetPackage);
            File controllerFile = new File(controllerDir, controllerJavafile.getFileName());
            // 文件不存在
            if (!controllerFile.exists()) {
            	controllerJavaFiles.add(controllerJavafile);
            } else {
            	log.info("{}文件已存在（未覆盖）", controllerJavafile.getFileName());
            }

        } catch (ShellException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        log.info("------ 结束: 生成Controller类文件 ------");
        return controllerJavaFiles;
    }

    /**
     * 生成Controller代码
     *
     * @param introspectedTable
     * @return
     */
    private CompilationUnit generateImplController(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.targetPackage + "." + controllerName);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // 添加文件注释
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addJavaFileComment(topLevelClass);

		// 添加类注释
		this.addControllerClassComment(topLevelClass, introspectedTable);

        // 添加注解
        topLevelClass.addAnnotation("@RestController");
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        // 添加日志注解
        topLevelClass.addAnnotation("@Slf4j");
        topLevelClass.addImportedType("lombok.extern.slf4j.Slf4j");
        // 添加Swagger注解
        topLevelClass.addAnnotation("@Api(tags=\"" + introspectedTable.getRemarks() + "接口\")");
        topLevelClass.addImportedType("io.swagger.annotations.Api");
        //topLevelClass.addImportedType("org.springframework.http.MediaType");
        // 添加requestMapping
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
        topLevelClass.addAnnotation("@RequestMapping(value = \"/v1/" + controllerName + "\")");
        // 导入HTTP请求相关包
        topLevelClass.addImportedType("io.swagger.annotations.ApiOperation");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.PostMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.GetMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.PutMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.DeleteMapping");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.PathVariable");
        topLevelClass.addImportedType("org.springframework.web.bind.annotation.RequestBody");
        topLevelClass.addImportedType("javax.validation.Valid");
        topLevelClass.addImportedType(responseDataPackage);

        // 添加Service属性字段
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(this.servicePackage + "." + serviceName);
        topLevelClass.addImportedType(service);
        this.setServiceField(service, topLevelClass, introspectedTable);

        // 编辑SelectByPrimaryKey方法
        this.editImplSelectMethod(topLevelClass, introspectedTable);
        
        // 编辑insert方法
        this.editImplInsertMethod(topLevelClass, introspectedTable);
        
        // 编辑UpdateByPrimaryKey方法
        this.editUpdateMethod(topLevelClass, introspectedTable);
        
        // 编辑删除方法
        this.editImplDeleteMethod(topLevelClass, introspectedTable);

        // TODO 实现导出方法

        return topLevelClass;
    }

	/**
	 * 表对应Controller类注释
	 */
	private void addControllerClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addJavaDocLine("/**");
		String remarks = introspectedTable.getRemarks();
		topLevelClass.addJavaDocLine(" * " + remarks + "Controller实现类");
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
     * 引入service属性
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void setServiceField(FullyQualifiedJavaType service, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(service);
        field.addAnnotation("@Autowired");
        topLevelClass
                .addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        // 属性名
        String name = serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
        field.setName(name);
        topLevelClass.addField(field);
        topLevelClass.addImportedType(service);
        
        // 属性注释
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + introspectedTable.getRemarks() + "Service");
        field.addJavaDocLine(" */");
    }

    /**
     * 生成delete实现方法
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    private void editImplDeleteMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("deleteByPrimaryKey");

        // 返回类型
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(responseDataPackage);
        returnType.addTypeArgument(new FullyQualifiedJavaType("java.lang.Integer"));
        method.setReturnType(returnType);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键删除方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @return " + method.getReturnType().getShortName());
        method.addJavaDocLine(" */");

		// 方法参数
		Parameter primaryKeyParam = this.getPrimaryKeyParameter(introspectedTable);
		primaryKeyParam.addAnnotation("@PathVariable");
		method.addParameter(primaryKeyParam);

        // 方法注解
        String param = primaryKeyParam.getName();
        method.addAnnotation("@ApiOperation(value = \"主键删除" + introspectedTable.getRemarks() + "\")");
        method.addAnnotation("@DeleteMapping(value = \"/{" + param + "}\")");

        // 方法内容
        method.addBodyLine("log.info(\"Controller deleteByPrimaryKey start.\");");
        method.addBodyLine("int result = this." + serviceFieldName + ".deleteByPrimaryKey(" + param + ");");
        method.addBodyLine("log.info(\"Controller deleteByPrimaryKey end.\");");
        method.addBodyLine("return new ResponseData<>(result);");

        topLevelClass.addMethod(method);
    }

    /**
     * 生成selectByPrimaryKey实现方法
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    private void editImplSelectMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("queryByPrimaryKey");

        // 返回类型
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(responseDataPackage);
        FullyQualifiedJavaType dtoType = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE);
        returnType.addTypeArgument(dtoType);
        method.setReturnType(returnType);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键查询方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @return " + method.getReturnType().getShortName());
        method.addJavaDocLine(" */");

		// 方法参数
		Parameter primaryKeyParam = this.getPrimaryKeyParameter(introspectedTable);
		primaryKeyParam.addAnnotation("@PathVariable");
		method.addParameter(primaryKeyParam);

        // 方法注解
		String param = primaryKeyParam.getName();
        method.addAnnotation("@ApiOperation(value = \"主键查询" + introspectedTable.getRemarks() + "\")");
        method.addAnnotation("@GetMapping(value = \"/{" + param + "}\")");

		// 方法内容
        method.addBodyLine("log.info(\"Controller queryByPrimaryKey start.\");");
        method.addBodyLine(dtoType.getShortName() + " resDto =  this." + serviceFieldName + ".queryByPrimaryKey(" + param + ");");
        method.addBodyLine("log.info(\"Controller queryByPrimaryKey end.\");");
        method.addBodyLine("return new ResponseData<>(resDto);");

        topLevelClass.addImportedType((FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.RES_DTO_TYPE));
        topLevelClass.addMethod(method);
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
        String methodName = "create" + introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        method.setName(methodName);

        // 返回类型
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(responseDataPackage);
        returnType.addTypeArgument(new FullyQualifiedJavaType("java.lang.Integer"));
        method.setReturnType(returnType);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 基础新增方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param reqDto 请求Dto");
        method.addJavaDocLine(" * @return " + method.getReturnType().getShortName());
        method.addJavaDocLine(" */");

        // 方法注解
        method.addAnnotation("@ApiOperation(value = \"新增" + introspectedTable.getRemarks() + "\")");
        method.addAnnotation("@PostMapping(value = \"\")");
        
        // 方法参数
        FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
        topLevelClass.addImportedType(type);
        Parameter parameter = new Parameter(type, "reqDto");
        parameter.addAnnotation("@RequestBody");
        parameter.addAnnotation("@Valid");
        method.addParameter(parameter);

        // 方法内容
        method.addBodyLine("log.info(\"Controller queryByPrimaryKey start.\");");
        method.addBodyLine("int result = this." + serviceFieldName + "." + methodName + "(reqDto);");
        method.addBodyLine("log.info(\"Controller queryByPrimaryKey end.\");");
        method.addBodyLine("return new ResponseData<>(result);");
        topLevelClass.addMethod(method);
    }

    /**
     * 实现updateByPrimaryKey方法
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    private void editUpdateMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("updateByPrimaryKey");

        // 导入类型
        FullyQualifiedJavaType type = (FullyQualifiedJavaType) introspectedTable.getAttribute(ConstUtils.REQ_DTO_TYPE);
        topLevelClass.addImportedType(type);

        // 返回类型
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(responseDataPackage);
        returnType.addTypeArgument(new FullyQualifiedJavaType("java.lang.Integer"));
        method.setReturnType(returnType);

		// 方法注释
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * 主键更新方法");
        method.addJavaDocLine(" * ");
        method.addJavaDocLine(" * @param pkId 主键");
        method.addJavaDocLine(" * @param reqDto 请求Dto");
        method.addJavaDocLine(" * @return " + method.getReturnType().getShortName());
        method.addJavaDocLine(" */");

		// 方法参数
		Parameter primaryKeyParam = this.getPrimaryKeyParameter(introspectedTable);
		primaryKeyParam.addAnnotation("@PathVariable");
		method.addParameter(primaryKeyParam);
        Parameter parameter = new Parameter(type, "reqDto");
        parameter.addAnnotation("@RequestBody");
        parameter.addAnnotation("@Valid");
        method.addParameter(parameter);

        // 添加注解
        method.addAnnotation("@ApiOperation(value = \"主键更新" + introspectedTable.getRemarks() + "\")");
        method.addAnnotation("@PutMapping(value = \"/{" + primaryKeyParam.getName() + "}\")");

        // 方法内容
        method.addBodyLine("log.info(\"Controller updateByPrimaryKey start.\");");
        method.addBodyLine("int result = this." + serviceFieldName + ".updateByPrimaryKey(" + primaryKeyParam.getName() + ", reqDto);");
        method.addBodyLine("log.info(\"Controller updateByPrimaryKey end.\");");
        method.addBodyLine("return new ResponseData<>(result);");
        topLevelClass.addMethod(method);
    }

}
