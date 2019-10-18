package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.task.AppTask;
import io.swagger.annotations.Api;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 16:51
 */
@RestController
@Api(tags = "测试")
@RequestMapping("test")
public class TestController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private JpushService jpushService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AppTask appTask;

    @GetMapping("task")
    public void test() {
        redisService.lRightPush(RedisPrefixCst.PW_NOT_PAY, "name_" + System.currentTimeMillis());
    }

    @GetMapping("get")
    public void test1() {
        User user = userMapper.selectById("a105e4f6cd3dd3097a3397f5ecc448");
    }


    @GetMapping("test2")
    public void test2(String data, String name) {
        System.out.println(data + name);
        redisService.lRightPush(RedisPrefixCst.PW_NOT_PAY, "name_" + System.currentTimeMillis());
    }


    @GetMapping("sub")
    public void sub() {

        Long aLong = redisService.lLen(RedisPrefixCst.PW_NOT_PAY);
        redisService.lTrim(RedisPrefixCst.PW_NOT_PAY, 2, aLong + 10000);


    }


    @Test
    public void testMethod() {

        DateTime dateTime1 = new DateTime(1569375529717L);

        DateTime dateTime = new DateTime(2018, 9, 22, 16, 02, 00);
        //当前时间
        LocalDate localDate = new LocalDate();
        DateTime now = DateTime.now();
        //判断是不是刚刚
        if (dateTime.plusMinutes(1).isAfterNow()) {
            System.out.println("刚刚");
        } else if (dateTime.plusHours(1).isAfterNow()) {
            //超过1分钟,不超过60分钟
            long i = (now.getMillis() - dateTime.getMillis()) / (1000 * 60);
            System.out.println(i + "分钟前");
        } else if (dateTime.plusDays(1).isAfterNow()) {
            //超过1个小时不超过 24 小时
            long i = (now.getMillis() - dateTime.getMillis()) / (1000 * 60 * 60);
            System.out.println(i + "小时前");
        } else if (dateTime.getDayOfYear() + 1 == localDate.getDayOfYear()) {
            //昨天
            System.out.println("昨天");
        } else if (dateTime.getYear() == localDate.getYear()) {
            //超过昨天 ,在今年范围内
            System.out.println(dateTime.getMonthOfYear() + "月" + dateTime.getDayOfMonth() + "日");
        } else {
            System.out.println(dateTime.toString("yyyy年MM月dd日"));
        }

    }

    @Test
    public void test5() {
        long l = System.currentTimeMillis() - Long.parseLong("1569555559366");
            if(l< CommonCst.ONE_DAY_MS){
                System.out.println("没超过时间");
            }else {
                System.out.println("超过时间");
            }
        System.out.println(l );
    }


}
