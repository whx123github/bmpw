package com.jt.baimo.pw.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Forever丶诺
 * @data 2019/8/5 9:57
 */
@Component
@ConfigurationProperties(prefix = "bm.common")
@Data
public class CommonProperties {


   private  String jPushAppKey;

   private String jPushMasterSecret;

   private boolean jPushApnsProduction;


}
