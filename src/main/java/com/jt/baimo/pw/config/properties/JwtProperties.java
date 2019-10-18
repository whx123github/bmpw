package com.jt.baimo.pw.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * jwt的相关属性配置
 * <p>
 * Created by yb_li on 2019/1/8.
 */
@Component
@ConfigurationProperties(prefix = JwtProperties.JWT_PREFIX)
@Data
public class JwtProperties {

    public static final String JWT_PREFIX = "jwt";      // jwt在配置文件的前缀
    private boolean authOpen = false;                   // 是否使用jwt鉴权
    private String header = "token";            // http请求头所需要的字段
    private String secret = "jtbmpw";              // jwt的秘钥
    private Long expiration = 10L;                  // 7天，单位：秒
    private Long redisExpire = 2592000L;


    public final static List<String> notAuthPathList;
    private String md5Key = "randomKey";                // md5加密混淆key
    private int randomNumberLength = 6;// 随机字符串的长度


    private List<String> notAuthSwgUrls = new ArrayList<>();


    /**不需要认证的接口*/
    static {

        notAuthPathList = new ArrayList();
        notAuthPathList.add("/smsCode"); //短信
        notAuthPathList.add("/user/pwdLogin"); // 密码登录
        notAuthPathList.add("/user/register"); //注册
        notAuthPathList.add("/user/forgetPwd"); //忘记密码
        notAuthPathList.add("/user/pwd"); //修改密码
        notAuthPathList.add("/jpush/systemPush"); //系统消息推送(审核)
        notAuthPathList.add("/jpush/allPush"); //系统消息推送(全部人)
        notAuthPathList.add("/jpush/idPush"); //触发推送系统消息(某人)
        notAuthPathList.add("/pay/notifyUrl"); //支付宝回调的通知接口
        notAuthPathList.add("/test"); //支付宝回调的通知接口
        notAuthPathList.add("/pc"); //支付宝回调的通知接口


        /*swagger相关返回*/
        notAuthPathList.add("/swagger-ui.html");
        notAuthPathList.add("/webjars");
        notAuthPathList.add("/swagger-resources");
        notAuthPathList.add("/v2/api-docs");

        notAuthPathList.add("/pay/payNative");

    }


}
