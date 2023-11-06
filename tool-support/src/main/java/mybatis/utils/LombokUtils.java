package mybatis.utils;

import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 添加lombok工具类
 * 
 * @author xyb
 *
 */
public class LombokUtils {

	/**
	 * 给头部添加是否使用lombok注解
	 * 
	 * @param topLevelClass
	 * @param isLombok      : 当为true的时候表示使用lombok
	 */
	public static void addLomBokAnnotation(TopLevelClass topLevelClass, boolean isLombok) {
		if (isLombok) {
			topLevelClass.addImportedType("lombok.Data");
			topLevelClass.addAnnotation("@Data");

			if (topLevelClass.getSuperClass() != null) {
				topLevelClass.addImportedType("lombok.EqualsAndHashCode");
				topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper=false)");
			}
		}
	}
}
