package com.jt.baimo.pw.filter;

import com.jt.baimo.pw.config.properties.JwtProperties;
import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.JwtTokenService;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.RedisYwService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.jt.baimo.pw.constant.CommonCst.REDIS_JWT_TOKEN_EXPIRE;
import static com.jt.baimo.pw.constant.CommonCst.REDIS_LOGIN_EXPIRE;
import static com.jt.baimo.pw.constant.RedisPrefixCst.REDIS_RC_TOKEN_PRE;


/**
 * 对客户端请求的jwt token验证过滤器
 * <p>
 *
 * @author Forever
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtProperties jwtProperties;


    @Autowired
    private RedisService redisService;


    @Autowired
    private RedisYwService redisYwService;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> notAuthPathList = JwtProperties.notAuthPathList;
        notAuthPathList.removeAll(jwtProperties.getNotAuthSwgUrls());
        String requestPath = request.getServletPath();


        if (notAuthPathList.stream().anyMatch(notAuthPath -> requestPath.startsWith(notAuthPath))) {
            filterChain.doFilter(request, response);
            return;
        }


        //获取客户端传送过来的token
        String authToken = request.getHeader(jwtProperties.getHeader());
        String userId = getUserId(authToken);
        if (userId == null) {
            response.setStatus(401);
            return;
        }


        // 查看是否冻结 todo 后台进行删除
        User user = userMapper.selectById(userId);
        if (new DateTime(user.getClosedTime()).isAfterNow()) {
            redisService.del(REDIS_RC_TOKEN_PRE + userId);
            return;
        }

        //重置过期时间
        redisService.setExpireTime(REDIS_RC_TOKEN_PRE + userId, REDIS_JWT_TOKEN_EXPIRE);

        if (!redisYwService.hasLoginKey(userId)) {
            //解决登录的token过期后用户会变成未登录状态,
            userMapper.updateById(new User().setIsLogin(1).setId(userId));
        }
        //重置登录的过期时间
        redisYwService.setLoginKey(userId);


        // 将用户id传入到 request中,方便目标接口中获取
        request.setAttribute(CommonCst.USER_ID, userId);
        filterChain.doFilter(request, response);
    }


    /***
     * 校验token 同时获取用户id
     * @param authToken
     * @return
     */
    public String getUserId(String authToken) {
        //验证客户端是否传token
        if (authToken == null) return null;
        //从token中获取用户id
        String userId = jwtTokenService.getUserId(authToken);
        if (userId == null) return null;
        //看缓存里的token和 用户请求的token是否一致
        if (!StringUtils.equals(authToken, redisYwService.getJwtToken(userId))) return null;
        return userId;
    }


}
