/**
 * DimMonitorPointEntityExample.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.anji.allways.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DimMonitorPointEntityExample {
    /**
     * orderByClause
     */
    protected String orderByClause;

    /**
     * distinct
     */
    protected boolean distinct;

    /**
     * oredCriteria
     */
    protected List<Criteria> oredCriteria;

    /**
     * DimMonitorPointEntityExample 
     */
    public DimMonitorPointEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * setOrderByClause 
     * @param orderByClause 
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * getOrderByClause 
     * @return java.lang.String 
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * setDistinct 
     * @param distinct 
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * isDistinct 
     * @return boolean 
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * getOredCriteria 
     * @return java.util.List<Criteria> 
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * or 
     * @param criteria 
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * or 
     * @return Criteria 
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * createCriteria 
     * @return Criteria 
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * createCriteriaInternal 
     * @return Criteria 
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * clear 
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /** 
     * Example标准条件内部类
     * 监控点维度表
     * table:dim_monitor_point
     * @author wangyanjun  2022-11-16
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMonitorPointIdIsNull() {
            addCriterion("monitor_point_id is null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdIsNotNull() {
            addCriterion("monitor_point_id is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdEqualTo(Integer value) {
            addCriterion("monitor_point_id =", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdNotEqualTo(Integer value) {
            addCriterion("monitor_point_id <>", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdGreaterThan(Integer value) {
            addCriterion("monitor_point_id >", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("monitor_point_id >=", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdLessThan(Integer value) {
            addCriterion("monitor_point_id <", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdLessThanOrEqualTo(Integer value) {
            addCriterion("monitor_point_id <=", value, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdIn(List<Integer> values) {
            addCriterion("monitor_point_id in", values, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdNotIn(List<Integer> values) {
            addCriterion("monitor_point_id not in", values, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdBetween(Integer value1, Integer value2) {
            addCriterion("monitor_point_id between", value1, value2, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointIdNotBetween(Integer value1, Integer value2) {
            addCriterion("monitor_point_id not between", value1, value2, "monitorPointId");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameIsNull() {
            addCriterion("monitor_point_name is null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameIsNotNull() {
            addCriterion("monitor_point_name is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameEqualTo(String value) {
            addCriterion("monitor_point_name =", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameNotEqualTo(String value) {
            addCriterion("monitor_point_name <>", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameGreaterThan(String value) {
            addCriterion("monitor_point_name >", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_point_name >=", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameLessThan(String value) {
            addCriterion("monitor_point_name <", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameLessThanOrEqualTo(String value) {
            addCriterion("monitor_point_name <=", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameLike(String value) {
            addCriterion("monitor_point_name like", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameNotLike(String value) {
            addCriterion("monitor_point_name not like", value, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameIn(List<String> values) {
            addCriterion("monitor_point_name in", values, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameNotIn(List<String> values) {
            addCriterion("monitor_point_name not in", values, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameBetween(String value1, String value2) {
            addCriterion("monitor_point_name between", value1, value2, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointNameNotBetween(String value1, String value2) {
            addCriterion("monitor_point_name not between", value1, value2, "monitorPointName");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtIsNull() {
            addCriterion("monitor_point_amt is null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtIsNotNull() {
            addCriterion("monitor_point_amt is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtEqualTo(BigDecimal value) {
            addCriterion("monitor_point_amt =", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtNotEqualTo(BigDecimal value) {
            addCriterion("monitor_point_amt <>", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtGreaterThan(BigDecimal value) {
            addCriterion("monitor_point_amt >", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("monitor_point_amt >=", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtLessThan(BigDecimal value) {
            addCriterion("monitor_point_amt <", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("monitor_point_amt <=", value, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtIn(List<BigDecimal> values) {
            addCriterion("monitor_point_amt in", values, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtNotIn(List<BigDecimal> values) {
            addCriterion("monitor_point_amt not in", values, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("monitor_point_amt between", value1, value2, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("monitor_point_amt not between", value1, value2, "monitorPointAmt");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeIsNull() {
            addCriterion("monitor_point_risk_grade is null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeIsNotNull() {
            addCriterion("monitor_point_risk_grade is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeEqualTo(String value) {
            addCriterion("monitor_point_risk_grade =", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeNotEqualTo(String value) {
            addCriterion("monitor_point_risk_grade <>", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeGreaterThan(String value) {
            addCriterion("monitor_point_risk_grade >", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_point_risk_grade >=", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeLessThan(String value) {
            addCriterion("monitor_point_risk_grade <", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeLessThanOrEqualTo(String value) {
            addCriterion("monitor_point_risk_grade <=", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeLike(String value) {
            addCriterion("monitor_point_risk_grade like", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeNotLike(String value) {
            addCriterion("monitor_point_risk_grade not like", value, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeIn(List<String> values) {
            addCriterion("monitor_point_risk_grade in", values, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeNotIn(List<String> values) {
            addCriterion("monitor_point_risk_grade not in", values, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeBetween(String value1, String value2) {
            addCriterion("monitor_point_risk_grade between", value1, value2, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andMonitorPointRiskGradeNotBetween(String value1, String value2) {
            addCriterion("monitor_point_risk_grade not between", value1, value2, "monitorPointRiskGrade");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(String value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(String value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(String value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(String value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(String value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(String value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLike(String value) {
            addCriterion("flag like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotLike(String value) {
            addCriterion("flag not like", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<String> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<String> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(String value1, String value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(String value1, String value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }
    }

    /** 
     * Example内部类Criteria
     * 监控点维度表
     * table:dim_monitor_point
     * @author wangyanjun  2022-11-16
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /** 
     * Example标准条件内部类
     * 监控点维度表
     * table:dim_monitor_point
     * @author wangyanjun  2022-11-16
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}