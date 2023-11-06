package mybatis.utils;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 添加swagger工具类
 *
 * @author xyb
 */
public class SwaggerUtils {
    /**
     * 给头部添加是否使用swagger注解
     *
     * @param topLevelClass
     * @param isSwaggerFlag : 当为true的时候表示使用swagger注释类
     * @param tableRemark   :表注释
     */
    public static void addClassAnnotation(TopLevelClass topLevelClass, boolean isSwaggerFlag, String tableRemark) {
        String className = topLevelClass.getType().getShortName();
        //生成的model使用lombok注解，不生成get/set方法
        if (isSwaggerFlag) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
            topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
            String remark = "输出Dto类";
            if (className.endsWith("ReqDto")) {
                remark = "请求Dto";
            } else if (className.endsWith("ResDto")) {
                remark = "响应Dto";
            }
            topLevelClass.addAnnotation("@ApiModel(value = \"" + tableRemark + remark + "\")");
        }
    }

    /**
     * 给属性添加是否使用swagger注解
     *
     * @param field         : 属性
     * @param isSwaggerFlag : 当为true的时候表示使用swagger注释类
     * @param fieldName     : 属性注释
     * @param position      : 属性的位置
     * @param dtoType       : 添加swagger dto类型
     */
    public static void addFieldAnnotation(Field field, boolean isSwaggerFlag, String fieldName, int position, String dtoType) {
        //生成的model使用lombok注解，不生成get/set方法
        if (isSwaggerFlag) {
            String swaggerAnnotation = "@ApiModelProperty(value = \"" + fieldName + "\"";
            field.addAnnotation(swaggerAnnotation + " )");
        }
    }
}
