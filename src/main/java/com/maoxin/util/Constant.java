package com.maoxin.util;

/**
 * Created by matthewyao on 2015/9/19.
 */
public class Constant {
    //是否被删除
    public static final int NOT_DELETED = 0;
    public static final int IS_DELETED = 1;

    //操作类型
    public static final String ACT_NEW = "new";
    public static final String ACT_MODIFY = "modify";

    //超级管理员用户名
    public static final String SUPER_MANAGER_NAME = "maoxin";

    //提成管理
    public static final int DEFAULT_ORIGIN_ID = 0;
    public static final int DEFAULT_DEDUCT_LEVEL = 1;
    public static final int FIRST_DEDUCT_LEVEL = 2;
    public static final int SECOND_DEDUCT_LEVEL = 3;

    //推荐人等级
    public static final int RECOMMEND_LEVEL_FIRST = 1;
    public static final int RECOMMEND_LEVEL_SECOND = 2;
    public static final int RECOMMEND_LEVEL_THIRD = 3;

    //推荐人获奖比例
    public static final double RECOMMEND_FIRST_RATIO = 0.2;
    public static final double RECOMMEND_SECOND_RATIO = 0.1;

    //推荐中文
    public static final String CHINESE_FIRST = "\u4e00\u7ea7\u63a8\u8350";
    public static final String CHINESE_SECOND = "\u4e8c\u7ea7\u63a8\u8350";
    public static final String CHINESE_THIRD = "\u4e09\u7ea7\u63a8\u8350";
//    public static final String CHINESE_RECOMMEND = "\u63a8\u8350";
    public static final String CHINESE_AT = "\u4e8e";
    public static final String CHINESE_AWARD = "\u4e0a\u73ed\u4e00\u5929\u5956\u52b1";

}
