/**
 * 
 */
package tools;

/**
 * @author suziming
 * 
 * @param
 * @return
 */
public class Config {

    /**
     * 主地址
     * **/
    public static String URL = "http://mkll.weiqipingtai.com";

    /**
     * 登陆接口
     * **/
    public static String LOGIN_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_login&dh=";
    /**
     * 用户 用户验证注册验证码
     * **/
    public static String VALIDATE_CODE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_zh_yz&dh=";
    /**
     * 用户 忘记密码发送短信
     * **/
    public static String FORGET_MSG_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_dxfs&tel=";
    
    
    
    
    /**
     * 造型师 个人中心接口
     * **/
    public static String PERSONAL_CENTER_MAIN_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_grzx&zid=";
    /**
     * 造型师 个人信息接口
     * **/
    public static String PERSONAL_INFO_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_grxx&zid=";
    /**
     * 造型师 我的作品接口
     * **/
    public static String MY_WORK_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_wdzp&zid=";
    /**
     * 造型师 我更新造型师信息
     * **/
    public static String UPDATE_INFO_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_xinxixiugai&zid=";
    /**
     * 造型师 化妆品品牌添加展示
     * **/
    public static String BRAND_LIST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_hzppptjzs&zid=";
    /**
     * 造型师 化妆品品牌添加操作
     * **/
    public static String BRAND_POST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_hzppptj&zid=";
    /**
     * 造型师 作品添加
     * **/
    public static String MY_WORK_ADD_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_zptj&zid=";
    /**
     * 造型师 作品删除
     * **/
    public static String MY_WORK_DELETE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_zpsc&zid=";
    /**
     * 造型师 荣誉列表
     * **/
    public static String MY_HONOR_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_rylb&zid=";
    /**
     * 造型师 荣誉删除
     * **/
    public static String MY_HONOR_DELETE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_rysc&zid=";
    /**
     * 造型师 荣誉添加
     * **/
    public static String ADD_HONOR_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_rytj&zid=";
    /**
     * 造型师 作品添加，妆容类型
     * **/
    public static String MU_TYPE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_zptjzrlx&zid=";
    
    
    /**
     * 造型师 帐户信息
     * **/
    public static String ACCOUNT_MAIN_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_zh&zid=";
    /**
     * 造型师 收入明细
     * **/
    public static String ACCOUNT_INCOME_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_xzmx&zid=";
    

    /**
     * 造型师 我的时间表
     * **/
    public static String MY_SCHEDULE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_day&zid=";
    /**
     * 造型师 当天时忙碌时间设置
     * **/
    public static String MY_SCHEDULE_SET_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_sj&zid=";
    /**
     * 造型师 默认时间表
     * **/
    public static String DEFAULT_SCHEDULE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_mrsjb&zid=";
    /**
     * 造型师 默认忙碌时间设置
     * **/
    public static String DEFAULT_SCHEDULE_SET_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_mlsj&zid=";
    

    /**
     * 造型师 已完成订单
     * **/
    public static String ORDER_DONE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_ddwc&zid=";
    /**
     * 造型师 我的预约订单
     * **/
    public static String ORDER_RESERVE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_yydd&zid=";
    /**
     * 造型师 我的抢单
     * **/
    public static String ORDER_GRAB_LIST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_wdqd&zid=";
    /**
     * 造型师 我的抢单
     * **/
    public static String ORDER_GRAB_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_qrqd&zid=";
    
    /**
     * 造型师 我的预约订单查看 
     * **/
    public static String ORDER_RESERVE_CHECK_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_yyddck&zid=";
    
    /**
     * 造型师 评价用户
     * **/
    public static String COMMENT_POST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_pj&zid=";
    /**
     * 造型师 评价用户页面订单和用户信息
     * **/
    public static String COMMENT_MAIN_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_pjzs&zid=";
    
    /**
     * 造型师 开始, 结束服务
     * **/
    public static String START_END_SERVICE_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_ksfw&zid=";
    
    /**
     * 造型师 查看我是否有抢单
     * **/
    public static String GRAB_ORDER_CHECK_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_sfyqd&zid=";
    
    /**
     * 造型师 发送短信
     * **/
    public static String GETCODE_MSG_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_dxfs&tel=";
    
    /**
     * 造型师 忘记密码提交
     * **/
    public static String FORGET_PSW_POST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_wjmm&dh=";
    
    /**
     * 造型师每次启动App更新用户cid
     * **/
    public static String UPDATE_CID_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_qdapp&zid=";
    
    /**
     * 造型师 薪资名细
     * **/
    public static String SALARY_DETAIL_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_xzmx&zid=";
    
    /**
     * 造型师 查看造型师评价列表
     * 
     * 返回值 ： zp中评列表,cp差评列表,hp好评列表 列表字段tx头像，xm姓名，sj时间，pic图片以“,”分隔，nr评价内容 
     * **/
    public static String COMMENT_LIST_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_ckwdpjlb&zid=";
    
    
    /**
     * 造型师 违约
     * 请求参数 act:zxs_wy oid:订单id zid：造型师id
     * 
     * **/
    public static String WY_URL = "http://mkll.weiqipingtai.com/api/api.php?act=zxs_wy&oid=";
    
    
}
