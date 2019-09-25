package com.zxk.demossocore.conf;

public interface CommonConstants {
    /*参数不完整**/
    String ERROR_VERI_CODE = "000002";
    /*参数错误**/
    String ERROR_PARAM = "000001";
    /**
     * 通道错误
     */
    String ERROR_PASS_ERROR = "000003";
    /**
     * 支付错误
     */
    String ERROR_PAY_PASS = "000004";
    /**
     * 密令错误
     */
    String ERROR_TOKEN = "000005";
    /**
     * 卡教研失败
     ***/
    String ERROR_CARD_FAILED = "000006";
    /**
     * 用户不存在
     ***/
    String ERROR_USER_NOT_EXISTED = "000007";
    /***用户已存在**/
    String ERROR_USER_HAS_REGISTER = "000008";
    /**
     * 卡失败
     ***/
    String ERROR_CARD_ERROR = "000009";

    /**支付请求错误*/
    /**
     * 金额有误
     **/
    String ERROR_AMOUNT_ERROR = "000010";

    String ERRRO_ORDER_HAS_CHECKED = "000011";
    /***用户加入黑名单***/
    String ERROR_USER_BLACK = "000012";
    /**
     * 用户未注册
     ***/
    String ERROR_USER_NO_REGISTER = "000013";

    /**
     * 提现下单错误
     */
    String ERROR_WITHDRAW_ORDER_FAIL = "000014";
    /**
     * 默认卡有误
     **/
    String ERROR_USER_NO_DEFAULT_CARD = "000015";
    /***提现失败**/
    String ERROR_WITHDRAW_REQ_FAILD = "000016";
    /***提现余额不足**/
    String ERROR_WITHDRAW_BALANCE_NO_ENOUGH = "000017";
    /***不存在**/
    String ERROR_PAYMENT_NOT_EXIST = "000019";
    /**
     * 该笔订单已经提交
     */
    String ERROR_WITHDRAW_ORDER_HASREQ = "000020";


    /**
     * 签名无效
     */
    String ERROR_SIGN_NOVALID = "000018";

    /**
     * 成功
     */
    String SUCCESS = "000000";

    String BINDTASKCARD = "888888";
    /***失败*/
    String FALIED = "999999";
    /**
     * 系统异常
     */
    String SYS_FAIL = "999999";
    /**
     * 业务异常
     */
    String BUS_FAIL = "222222";
    /**
     * 等待处理
     **/
    String WAIT_CHECK = "666666";

    /***秘密密钥**/
    String SECRETKEY = "juhe-20170328";
    /**
     * 结果
     **/
    String RESULT = "result";
    /**
     * 有效
     **/
    String STATUS_VALID = "0";
    /**
     * 无效
     ***/
    String STATUS_INVALID = "1";
    /***返回码**/
    String RESP_CODE = "resp_code";
    /***返回描述**/
    String RESP_MESSAGE = "resp_message";


    /**品牌类型*/
    /***主品牌**/
    String BRAND_MAIN = "0";
    /***其他品牌**/
    String BRAND_OTHER = "1";


    /**银行卡的默认状态*/
    /**
     * 银行卡默认
     */
    String CARD_DEFAULT = "1";
    /**
     * 银行卡非默认
     */
    String CARD_NOT_DEFAULT = "0";


    /**充值/支付/代付的订单状态*/
    /***等待回执**/
    String ORDER_READY = "0";
    /**
     * 成功
     **/
    String ORDER_SUCCESS = "1";
    /**
     * 取消订单
     ***/
    String ORDER_CANCEL = "2";


    /**充值/支付/提现/退款的类型标识*/
    /**
     * 充值
     **/
    String ORDER_TYPE_TOPUP = "0";
    /**
     * 支付购买产品
     **/
    String ORDER_TYPE_PAY = "1";
    /**
     * 提现
     ***/
    String ORDER_TYPE_WITHDRAW = "2";
    String ORDER_TYPE_REFUND = "3";
    /***待结算***/
    String ORDER_TYPE_BILL = "4";
    String ORDER_TYPE_WZDJ = "5";
    String ORDER_TYPE_WZDJ_PAY = "6";
    String ORDER_TYPE_PAYMENT = "7";//7信用卡还款和提现
    String ORDER_TYPE_BALANCE = "8";//8信用卡提现（未使用）

    String ORDER_TYPE_CONSUME = "10";//10新信用卡管家消费订单

    String ORDER_TYPE_REPAYMENT = "11";//11新信用卡管家还款订单

    String ORDER_TYPE_AUTHCOUNT = "12";//12银行卡校验次数充值类型

    String ORDER_TYPE_COIN = "13";//13积分抽奖订单
    String   ORDER_TYPE_PURCHASE_COLUMN ="14"; //14购买广告栏
    String  ORDER_TYPE_CRAD_EVALUATION ="15";//卡测评
    String ORDER_TYPE_EMPTYCARD_CONSUME ="16";//空卡消费
    String ORDER_TYPE_EMPTYCARD_REPAYMENT ="17";//空卡还款
    String ORDER_TYPE_CREDIT_INVESTIGATION ="18";//征信
    String ORDER_TYPE_EXCHANGE_COIN = "exchange_coin";//JF积分兑换类型
    String ORDER_TYPE_AGENTIAL_WITHDRAW = "19";//代理商提现
    /**积分类型*/
    /***添加分**/
    String COIN_TYPE_ADD = "0";
    /***减少积分**/
    String COIN_TYPE_SUB = "1";

    /**
     * 访问被拒绝
     */
    String SC_FORBIDDEN = "403";


    /**订单的结算类型*/
    /**
     * 工作日结算
     **/
    String CLEARING_T_0 = "0";
    /**
     * 下个工作日结算
     **/
    String CLEARING_T_1 = "1";
    /***当天结算**/
    String CLEARING_D_0 = "2";
    /**
     * 第二天结算
     **/
    String CLEARING_D_1 = "3";

    /**
     * 微恰通第三方通道返回码--成功
     **/
    String LEY_RESULT_SUCCESS = "200";

    /**
     * 保存计划事变
     */
    int SAVE_PLNE_FALIED = 9;

    /**
     * 没有产品通道
     */
    String NOT_FOUND_PRODUCE = "没有查询到可用通道！";

}
