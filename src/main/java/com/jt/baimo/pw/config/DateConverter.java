package com.jt.baimo.pw.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳转成时间类型
 *
 * @author Forever丶诺
 * @data 2019/5/31 17:51
 */

@Component
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        Date date = null;
        try {
            date = new Date(Long.valueOf(s));
        } catch (Exception e) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = dateFormat.parse(s);
            } catch (Exception en) {
                System.out.println(en);
            }
        }
        return date;
    }


}
