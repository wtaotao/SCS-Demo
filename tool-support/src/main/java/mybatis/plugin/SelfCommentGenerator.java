package mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import mybatis.utils.DateUtils;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 注释信息配置
 * 
 * @author xyb
 *
 */
@Slf4j
public class SelfCommentGenerator implements CommentGenerator {

    /**
     * 是否所有都不加注释
     */
    private boolean suppressAllComments = false;

    /**
     * 是否都不添加时间标签
     */
    private boolean suppressDate = false;

    /**
     * 注释中的时间格式
     */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取操作人
     */
    private String author = System.getProperty("user.name");

    /**
     * 获取注释中的时间格式,如果不需要，返回null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else if (StringUtility.stringHasValue(dateFormat)) {
            return DateUtils.getNowTime(dateFormat);
        } else {
            return DateUtils.getNowTime();
        }
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        suppressDate = isTrue(properties.getProperty("suppressDate"));
        suppressAllComments = isTrue(properties.getProperty("suppressAllComments"));
        String dateFormatString = properties.getProperty("dateFormat");
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = dateFormatString;
        }
    }

    /**
     * 对实体对象的字段进行加注解
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        } else {
            field.addJavaDocLine(" * " + introspectedColumn.getActualColumnName());
        }
        if (StringUtility.stringHasValue(introspectedColumn.getDefaultValue())) {
            field.addJavaDocLine(" * 默认值:" + introspectedColumn.getDefaultValue());
        }
        field.addJavaDocLine(" */");
    }

    /**
     * 对实体对象的example字段注解
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(field.getName());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }

    /**
     * 为表对应的实体类class添加类注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
            return;
        }
		//setTableRemark(introspectedTable);
        StringBuilder sb = new StringBuilder();
        topLevelClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(sb.toString().replace("\n", " "));
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(author);
        sb.append("  ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString().replace("\n", " "));
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * 生成内部标准类的注释(类GeneratedCriteria的class注释)
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/** ");
        innerClass.addJavaDocLine(" * Example标准条件内部类");
        // 获取对应数据库表的注释
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                innerClass.addJavaDocLine(" * " + remarkLine);
            }
        }
        innerClass.addJavaDocLine(" * table:" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @author " + author + "  " + getDateString());
        innerClass.addJavaDocLine(" */");
    }

    /**
     * 内部类Criteria的注释(Criteria extends GeneratedCriteria)
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }

        innerClass.addJavaDocLine("/** ");
        innerClass.addJavaDocLine(" * Example内部类Criteria");
        // 获取对应数据库表的注释
        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                innerClass.addJavaDocLine(" * " + remarkLine);
            }
        }
        innerClass.addJavaDocLine(" * table:" + introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(" * @author " + author + "  " + getDateString());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
        innerEnum.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            method.addJavaDocLine(" * 返回字段:" + introspectedColumn.getRemarks());
        } else {
            method.addJavaDocLine(" * 返回字段:" + introspectedColumn.getActualColumnName());
        }
        if (StringUtility.stringHasValue(introspectedColumn.getDefaultValue())) {
            method.addJavaDocLine(" * 默认值:" + introspectedColumn.getDefaultValue());
        }
        // field.addJavaDocLine(" * @author "+author+" "+getDateString());
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            method.addJavaDocLine(" * 设置字段值:" + introspectedColumn.getRemarks());
        } else {
            method.addJavaDocLine(" * 设置字段值:" + introspectedColumn.getActualColumnName());
        }
        if (StringUtility.stringHasValue(introspectedColumn.getDefaultValue())) {
            method.addJavaDocLine(" * 默认值:" + introspectedColumn.getDefaultValue());
        }
        // field.addJavaDocLine(" * @author "+author+" "+getDateString());
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
      method.addJavaDocLine("/**");
      StringBuilder sb=new StringBuilder();
      sb.append(" * ");
      sb.append(method.getName());
      sb.append(" ");
      method.addJavaDocLine(sb.toString().replace("\n", " "));
      List<Parameter> parameters=method.getParameters();
      if(!CollectionUtils.isEmpty(parameters)){
          for(Parameter p: parameters){
              sb.setLength(0);;
              sb.append(" * @param ");
              sb.append(p.getName());
              sb.append(" ");
              method.addJavaDocLine(sb.toString().replace("\n", " "));
          }
      }
      
      if(null != method.getReturnType()){
          sb.setLength(0);;
          sb.append(" * @return ");
          sb.append(method.getReturnType());
          sb.append(" ");
          method.addJavaDocLine(sb.toString().replace("\n", " "));
      }
      method.addJavaDocLine(" */");
    }

    /**
     * 添加到java文件头部
     */
    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        compilationUnit.addFileCommentLine("/**");
        compilationUnit.addFileCommentLine(" * " + compilationUnit.getType().getShortName() + ".java");
        compilationUnit.addFileCommentLine(" * Created at " + getDateString());
        compilationUnit.addFileCommentLine(" * Created by " + author);
        compilationUnit.addFileCommentLine(" * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.");
        compilationUnit.addFileCommentLine(" **/");
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source Table1: " + introspectedTable.getFullyQualifiedTable().toString(); //$NON-NLS-1$
        method.addAnnotation(getGeneratedAnnotation(comment));
    }

    @Override
    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
                                           IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source field2: " //$NON-NLS-1$
                + introspectedTable.getFullyQualifiedTable().toString()
                + "." //$NON-NLS-1$
                + introspectedColumn.getActualColumnName();
        method.addAnnotation(getGeneratedAnnotation(comment));
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source Table3: " + introspectedTable.getFullyQualifiedTable().toString(); //$NON-NLS-1$
        field.addAnnotation(getGeneratedAnnotation(comment));
    }

    @Override
    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
                                   IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source field4: " //$NON-NLS-1$
                + introspectedTable.getFullyQualifiedTable().toString()
                + "." //$NON-NLS-1$
                + introspectedColumn.getActualColumnName();
        field.addAnnotation(getGeneratedAnnotation(comment));

        if (!suppressAllComments) {
            String remarks = introspectedColumn.getRemarks();
            if (StringUtility.stringHasValue(remarks)) {
                field.addJavaDocLine("/**"); //$NON-NLS-1$
                field.addJavaDocLine(" * Database Column Remarks:"); //$NON-NLS-1$
                String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
                for (String remarkLine : remarkLines) {
                    field.addJavaDocLine(" *   " + remarkLine);  //$NON-NLS-1$
                }
                field.addJavaDocLine(" */"); //$NON-NLS-1$
            }
        }
    }

    private String getGeneratedAnnotation(String comment) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("@Generated("); //$NON-NLS-1$
        if (suppressAllComments) {
            buffer.append('\"');
        } else {
            buffer.append("value=\""); //$NON-NLS-1$
        }

        buffer.append(MyBatisGenerator.class.getName());
        buffer.append('\"');

        if (!suppressDate && !suppressAllComments) {
            buffer.append(", date=\""); //$NON-NLS-1$
            buffer.append(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now()));
            buffer.append('\"');
        }

        if (!suppressAllComments) {
            buffer.append(", comments=\""); //$NON-NLS-1$
            buffer.append(comment);
            buffer.append('\"');
        }

        buffer.append(')');
        return buffer.toString();
    }

    @Override
    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable,
                                   Set<FullyQualifiedJavaType> imports) {
        imports.add(new FullyQualifiedJavaType("javax.annotation.Generated")); //$NON-NLS-1$
        String comment = "Source Table6: " + introspectedTable.getFullyQualifiedTable().toString(); //$NON-NLS-1$
        innerClass.addAnnotation(getGeneratedAnnotation(comment));
    }

    /**
     * 生成的xml注释，暂不处理
     */
    @Override
    public void addComment(XmlElement xmlElement) {

    }

    /**
     * 生成的xml注释，暂不处理
     */
    @Override
    public void addRootComment(XmlElement rootElement) {

    }

}
