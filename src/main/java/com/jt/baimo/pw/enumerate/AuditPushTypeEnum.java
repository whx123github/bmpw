package com.jt.baimo.pw.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ms.WenJing
 * @data 2019/08/08 16:19
 */
@Getter
@AllArgsConstructor
public enum AuditPushTypeEnum {
    CERTIFIED_GAME_by(1, "您的顾问资格认证成功！点击查看详情>>"),
    CERTIFIED_GAME_FAIL(2, "您的顾问资格认证失败！点击查看详情>>"),
    USER_DYNAMICS_FAIL(3, "您的动态存在违规情况，已被系统删除，点击查看详情>>"),
    USER_PHOTO_DOES_FAIL(4, "您的照片存在违规情况，已被系统删除，点击查看详情>>"),
    USER_THE_AVATAR_DOES_FAIL(5, "您的头像存在违规情况，已被系统删除，点击查看详情>>"),
    USER_NICKNAME_SHIELD(6, "您的昵称存在违规情况，已被系统屏蔽，点击查看详情>>"),
    USER_VOICE_DOES_FAIL(7, "您的语音存在违规情况，已被系统删除，点击查看详情>>"),
    USER_PERSONAL_INTRODUCTION_DOES_FAIL(8, "您的个人介绍存在违规情况，已被系统删除，点击查看详情>>"),
    USER_RELEASE_DYNAMICS(9, "xxxx发布了一条新动态，点击查看详情>>"),
    USER_SYSTEM_NOTIFICATION(10, "（显示需要发送的活动内容或者系统消息内容）"),
    ACCOMPANY_WAITING_ORDER(11, "您有新的订单请尽快接单，点击查看详情>>"),
    PLAYER_PENDING_SERVICE(12, "您的服务已被接单，点击查看详情>>"),
    PLAYER_CUSTOMER_SERVICE_CANCELLATION_CANCELLED(13, "您的订单已被客服取消，点击查看详情>>"),
    PLAYER_CANCELED_PAYMENT_TIMEOUT(14, "您的订单因超时支付被取消，点击查看详情>>"),
    ACCOMPANY_USER_CANCELED(15, "您的待服务订单被取消，点击查看详情>>"),
    PLAYER_PROCESSING(16, "您的服务已开始，点击查看详情>>"),
    PLAYER_CLICK_COMPLETE_SERVICE(17, "您的服务已完成，点击查看详情>>"),
    PLAYER_PLAYER_CLICK_COMPLETE_SERVICE(18, "您完成的订单已被确认，点击查看详情>>"),
    ACCOMPANY_PLAYER_BAIMOCOIN(19, "您有百陌币到账啦，点击查看详情>>"),
    ACCOMPANY_PLAYER_REIMBURSE(20, "您有一笔退款到账啦，点击查看详情>>"),
    ;
    int code;
    String notice;

    public static AuditPushTypeEnum jPushNotice(Integer type){
        if (null == type){
            return null;
        }
        for (AuditPushTypeEnum temp:AuditPushTypeEnum.values()) {
            if (temp.getCode() == type){
                return temp;
            }
        }
        return null;
    }
}
