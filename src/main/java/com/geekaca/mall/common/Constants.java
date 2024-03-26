package com.geekaca.mall.common;

public class Constants {
    //    public static String FILE_UPLOAD_DIC ="C:\\Users\\86139\\Desktop\\Tool\\MallImagespublic final static String FILE UPLOAD DICdevllcodesllnewbee-mall-apilstatic-
    public final static int INDEX_CAROUSEL_NUMBER = 5; //首页轮播图数量(可根据自身需求修改)
    public final static int INDEX_CATEGORY_NUMBER = 10;//首页一级分类的最大数量
    public final static int INDEX_G00DS_HOT_NUMBER = 4; //首页热卖商品数量
    public final static int INDEX_GOODS_NEW_NUMBER = 5;  //   首页新品数量
    public final static int INDEX_GOODS_RECOMMOND_NUMBER = 10;//首页推荐商品数量

    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 20; //购物车中商品的最大数量(可根据自身需
    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;
    public final static int GOODS_SEARCH_PAGE_LIMIT = 10; //搜索分页的默认条数（每页10条）

    public final static int SHOPPING_CART_PAGE_LIMIT = 5;  //购物车分页的默认条数(每页5条)
    public final static int ORDER_SEARCH_PAGE_LIMIT = 5; //我的订单列表分页的默认条数(每页5条)
    public final static int SELL_STATUS_UP = 0; //商品上架状态
    public final static int SELL_STATUS_DOWN = 1; //商品下架状态
    public final static int TOKEN_LENGTH = 32; //token字段长度

    public final static String USER_INTRO = "丰富种类。选你所爱"; //默认简介
    // 未登录编码 ，前端收到返回代码419 就知道了，是未登录，会把用户踢到登陆页面
    public static final int NO_LOGIN = 419;

    /**
     * 首页 商品板块 在GoodsInfoServiceImpl中，查询用到
     * 3-热销  4-新品  5-推荐
     */
    public static final int CONFIG_TYPE_HOT = 3;
    public static final int CONFIG_TYPE_NEW = 4;
    public static final int CONFIG_TYPE_RECOMMEND = 5;

}
