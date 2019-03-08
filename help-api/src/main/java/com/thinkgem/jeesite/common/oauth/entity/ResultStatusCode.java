package com.thinkgem.jeesite.common.oauth.entity;


/**
 * @param
 * @author wcf
 * @description 错误代码
 * @date 2018/1/5
 * @return
 */
public enum ResultStatusCode {
    OK(0, "OK"),
    SIGN_ERROR(120, "签名错误"),
    TIME_OUT(130, "访问超时"),
    BAD_REQUEST(40005, "参数解析失败"),
    INVALID_TOKEN(401, "无效的授权码"),
    INVALID_CLIENTID(402, "无效的密钥"),
    METHOD_NOT_ALLOWED(405, "不支持当前请求方法"),
    SYSTEM_ERR(500, "服务器运行异常"),
    NOT_EXIST_USER_OR_ERROR_PWD(10000, "该用户不存在或密码错误"),
    LOGINED_IN(10001, "该用户已登录"),
    INVALID_CAPTCHA(30005, "无效的验证码"),
    HAVE_PLATENUMBER(30006, "车牌号已存在，请勿重新添加");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResultStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
