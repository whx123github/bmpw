package com.jt.baimo.pw.constant;

/**
 * @author szd
 * @Description: 类描述
 * @data 2019/2/25 17:46
 */
public interface AliPayConfig {


    String CHARSET_UTF8 = "utf-8";
    String FORMAT = "json";
    String SIGN_TYPE = "RSA2";
    String SERVICE_ULR = "https://openapi.alipay.com/gateway.do";
    String TIME_OUT = "60m";


    String APP_ID = "2019073066093110";

    //  String APP_ID = "2019100968238557";

    //应用私钥
    String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPfgmB7MtDHyr203dT9U9l18ZJuVo5vw0LMWg7Dlsnvz1ON1REcjRrRfy7Jcm04i2jCAw/uXJt1o4VY96aWzJdVKdY0qCYFprPnvxu9z49SGiaP/uSts/5rpXMPcFccVevsW4neShKQDoreYTcaUtphlMd93LwC0uHAC7YaSpe6bOhgKe/RvxdDQfhc1356QKd3o73/ChvqFAx/K1+LjuNIzqa1ImbTPtoUWPvLw057zjzg6YVbpPsJfIayRlkgzsGoBif94+uyjjFAyQCIEXdW0mEEkTlVjw9gpv0YJomv9DZVY+I7VrClESMCp3at2ZDZ4TeBhHG+mDnLJoHbxD1AgMBAAECggEALEzEEKmTY0iXEhbdKz174rxICnC/6z4Rf9gkDr9y4wWGnsQ0LuF3T3sO+JRE/qQpZMDST0YojsKDoRTxH4RyOyAsfh6g1/N6ngi+dF++hlr5eE2ylGv+L153/tM+BBU19hb4JBWim7HSg6ODoiybzJtcw86mXHeQPMCQtd1Z6G0V0OfU7xgiZBoYq7sMh8tbol6G0Z3Daidf5iqJjISvNLUbxmL2xO8H+GHNPgpC5upfxx0ninHtu1ckV6Px7gsvdCFFTUeBkEj59zT6ksQffs5o4+cK8u/aJJbDZegB+Wf1GWeoui7crYNybd/+OmjPnJmmfTrZu1KEOT+84trdoQKBgQDGCLbWVcMACwyLNgrjMXFJ0SDZgQAjxrQCfKfYba+SqWpifjhGMljVukOCZCtu2sE1Ut0kfMdsaUh2XwlVFd2uOJ8V0P+KK8ShH35k7xRkySxghUfsi7xwps52V21BPVUSenx+0z+7DpNzm8T65ypq/Tflu6nSGRSmjw5KwVQq2QKBgQC5fllQKrUL15G3ZHfzUE6vH710EwjCTFPKwn9JDNKTQuQSbxiSt75zZkqkv9HYE/tbZ7Ox0gbLB6seCy2vdLRItFXXVv/Uhgn6yjyxT8UinsSyB0f5gaLXQu6aIpHMG8M0RoUgUzISr2ihptf5pFHUk8syYvxKvEe9nI6JV34tfQKBgA5RHvbL6HceVV/THmJidrBBgEB5RQtRGD59yJyrAWB3Awvv2B2rEiURfEP2/fTbt9AlF3vF0Z0S13xHTqNqG0a3LTkkjPTuSR7k6fxeFHlc2ep4eESvrYXvaUnA9FMdHgNh+5Inr5nUafCXuFvzTmvBtsLTGGnqCkYoXX4mD/GxAoGAZLiowpEOEcoSA6T4RRQ8RXdUpiyXuvk7HoQSHZZq2S+fLCtLCo/fSEYpDeBij5P6Dp32wv2qVqBpWDz1p6oVIs1ZNz60V7AYxyb9YcJQ5wWTBKBTgrXTuFNNwEA5Lr18d2w7aVwDeoXoqK4x5ApHrMg7s7ZwDBzET78s3oK1rvkCgYEAjgDQuj0OvH1L9gTnJdmhQnGzk3d69mC6gaCXhLDvYmqdYR1vK+lE0r8HnWkBDhvg9XHy2mErextR5ieVthDq1UoxjurnLRBs9yB0I4u4MHOYj+lJVdf6pTvF/dzFOvkm4PPYPWuPn+iEm/SmNFGfqNFFIVwGU4+FIZpXdnUK9ZI=";
    //String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC/oHDaoxG7eG73pkvrfGIInQ7lcHvf3tJnP7XCchONmfdDcFxRTRtcKVjDs5kiUXeufytUbNHYdjFlPlF8O3fO0ntB6eXGzVoTc/r4vyKu8BlsHMcCD6cWWfLiufuiKWFDnWXUx9Koy7ZZYfJyPE927zXzSdu2so74qztlbOjviLGJ7Rfj3G1HZhKpShkNnGzBDNgJYPMFBQIRY8ggWdXjDxaB6PKW8DGg9FoAjN0LGshad/80I65pVSbks9wxcXLgbPzzx81INPjM6RU9bDsVl9auTqkFbyNaPldbw0EuGbjVbKdUCtG3S6iRARanmJt7QMZbhI8y5uos/1HgTFmpAgMBAAECggEALC8LNm5ZHVfEhleSs8ZnOEM2dxK7jT/sVaAsAZyaA5FXoPYcYyabQ2HJNL6JdY9BQDvUOtAohneiJgz7buCrXEXN48HRmEgrF8crPAWeMqXrxrDP9hqz1QSCa2KksZxG7/cvzqMQYJkv/2YFJ+/eBrdbQjBqd7KuIoog1N8KyofcqXgqQ2BYEButlgBzfDxdN6URO9tz/4OYuw6QuRCwo/5nc1Iy2men51gxdS4YD4uS8Pl1PsVAds3OaS9AuVWEgM1tWeM4gp/Bwb0NmVoFmWk2Ru4EoB9DJ+s7QBUGBwKJ0hkI/KQmqj4MS0ZbpcRpC3ylDd+s5Wd2FYZ7e29L8QKBgQDv0oL2Jlx4K/HHv8vjNYD4w7+RJRk2cqZRdpyiLQe+1AAszyk4Y7ZKJzcutClM+Wtm+x2tWFumpJf6Xu7jmUllQWsbPMAeTvR/FP4upqk+lSe1Q1e716PvchKdkuHQrniIqN8Oe8HZIAN9TgnmqzL0l8o1tn4fMSTFm+oDxzz37QKBgQDMjaPn+mkUSdximfhMS/RT9AwOEmZVxcJ8psjHQT5gGR7hiYUOgbk606UQpLYlxL5u1N4rkTsxfnY8r0gDekjsDhNhJGBdTWC5qdezVamkmlguSnL+wva8uMeIMTNU85D6bOpiFEFB8KtIkIdb9qLUg2CZlLLEsfEc4CfWtsU5LQKBgD/c4xsBZCZsLVpv7K94k/uqxNy18FzbcwbGezeEnkdm1RUZpXRquSROBRfeyhDBaqRlwqViniVmN0Jo/VyGeVEJdqguKUro45rj0DxEDVWpCoXuhPJ0nicpTWPAtXmIvHhH3/5PXnOfXs6DdYeDl3Ii6aocCsHrqEsnFc4jxEyRAoGADrUAW+V9OKA24EABwjqTzpV1aG2aPgCCA0RamzjtqysjNUQV9P0OvFQLQi8ViZGjv4Q+lRNUFjLpfWN84gMgyxgX/JwI8axmj/LON26pS9RtEqb3ramG4/rFHd2ruyfn/JloILUkHAGZzxXVJZ+OBYmMoH44fDvvAgx70MxDUeECgYAFILC+aKuPs225KbljKiA4Gi7NUxsRg06rZCzWZv/E+p0yMnsPjysyD2D7ofh4xlFXhOvC8H8zQG7elxdvbZ/gIgDMf317BwPq3twUSglXNh+ERulEYcmKoVMQfnoWrOD/jZ4NncVVyswAtqbowuD5VZ68qQXuF6Tuv61gVlm44A==";

    //支付宝公钥
    String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv4Mnf+f74k3bhSEf+uUY8Rglzy2yM0I6caRPrQKWZGgpHepQVC7J5n8rMhibVUVNOOGuNdyvca1KSwLudmCxEOejtRHOuKHiJO9hCht0nDf0oKbLBXiu4bkGuakv4dDa+zTBpQbq8KEgBF7RPKv4vX1g6IPNZbaW1We5M7JZUDjut/HqGLeajE3YCNKQ6ImLEVMGKrEwtzNJV5tQWeTkfDeYSYsl2fBUVuUy1H9FS++2zn2t8p4O0IKF5jxHoR2mpqZQHwrpIlTDB7fao9kZl+Z6F4Rd3Oz7GpMN33CaY4kBUv9oivQwG+8Dx7z1y/K53nbQ6APvnmRVJCELKyiMOQIDAQAB";
    //String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv4Mnf+f74k3bhSEf+uUY8Rglzy2yM0I6caRPrQKWZGgpHepQVC7J5n8rMhibVUVNOOGuNdyvca1KSwLudmCxEOejtRHOuKHiJO9hCht0nDf0oKbLBXiu4bkGuakv4dDa+zTBpQbq8KEgBF7RPKv4vX1g6IPNZbaW1We5M7JZUDjut/HqGLeajE3YCNKQ6ImLEVMGKrEwtzNJV5tQWeTkfDeYSYsl2fBUVuUy1H9FS++2zn2t8p4O0IKF5jxHoR2mpqZQHwrpIlTDB7fao9kZl+Z6F4Rd3Oz7GpMN33CaY4kBUv9oivQwG+8Dx7z1y/K53nbQ6APvnmRVJCELKyiMOQIDAQAB";


}
