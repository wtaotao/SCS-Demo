package com.nikki.common.constant;

/**
 * 常量类（常量、格式定义、正则表达式等）
 *
 * @author xieyb
 */
public class CommConst {
    /**
     * 导出最大行数（默认1万行）
     */
    public static final int MAX_EXPORT_LINES = 10000;

    /**
     * 费用金额最大值 decimal(9,2)
     */
    public static final String MAX_AMOUNT = "999999999.99";

    /**
     * 费用金额最小负值 decimal(9,2)
     */
    public static final String MIN_MINUS_AMOUNT = "-999999999.99";

    /**
     * 费用金额合计最大值 decimal(13,2)
     */
    public static final String MAX_SUM_AMOUNT = "99999999999.99";

    /**
     * SQL文In最多个数限制.
     */
    public static final int MAX_SQLIN_COUNT = 1000;

}
