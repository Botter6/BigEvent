package cn.Botter.interceptors;

import cn.Botter.utils.JwtUtil;
import cn.Botter.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Botter
 * @date 2024/7/13
 * @Description
 */

@Component
//@RestController
public class LoginInterceptors implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String s = request.getRequestURI();
        if (s.contains("register") || s.contains("login"))
            return true; // 不需要token认证
        String Token = request.getHeader("Authorization");
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(Token);
            if (redisToken == null) {
                //token失效
                throw new RuntimeException();
            }
            Map<String,Object> claims = JwtUtil.parseToken(Token);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
//            System.out.println("注册不放行");
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据 ， 防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
