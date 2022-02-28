package com.camellia.util;

public enum ResultCode {
    /* 成功 */
    SUCCESS(200,"成功"),
    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    HAS_NO_TOKEN(4001,"没有登录或没有令牌"),
    TOKEN_HAS_EXPIRED(4002,"令牌过期"),
    TOKEN_FORMAT_ERROR(4003,"Token格式错误"),
    TOKEN_CONSTRUCTOR(4004,"Token构造错误"),
    SIGNATURE_ERROR(4005,"签名错误"),
    TOKEN_ILLEGAL_ARGUMENT(4006,"Token非法参数"),
    TOKEN_INVALID(4007,"无效Token"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限");

    private Integer code;
    private String message;
    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static String getMessageByCode(Integer code){
        for (ResultCode ele : values()){
            if (ele.getCode().equals(code)){
                return ele.getMessage();
            }
        }
        return null;
    }
}
