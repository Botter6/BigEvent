package cn.Botter.controller;

import cn.Botter.pojo.Result;
import cn.Botter.pojo.User;
import cn.Botter.service.UserService;
import cn.Botter.utils.JwtUtil;
import cn.Botter.utils.Md5Util;
import cn.Botter.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Botter
 * @date 2024/7/13
 * @Description
 */
@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    public StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{6,16}$") String username, @Pattern(regexp = "^\\S{6,16}$") String password) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户已经被占用");
        }
        else{
            //注册
            userService.register(username,password);
            return Result.success("注册成功");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{6,16}$") String username, @Pattern(regexp = "^\\S{6,16}$") String password){
        User loginUser = userService.findByUserName(username);

        if (loginUser == null) {
            return Result.error("用户名错误");
        }

        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String , Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String Token = JwtUtil.genToken(claims);
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            //设置Redis中token的有限时间
            ops.set(Token, Token,1, TimeUnit.HOURS);
            return Result.success(Token);

        }

        return Result.error("密码错误");
    }

    @GetMapping("userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
//        Map<String, Object> parsedToken = JwtUtil.parseToken(token);
//        String  username = (String) parsedToken.get("username");

        //根据用户查询用户
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestParam @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
//        System.out.println(avatarUrl);
        userService.updateAvatar(avatarUrl);
        return Result.success(avatarUrl);
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params, @RequestHeader("Authorization") String token){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("请求必要参数");
        }
        //验证输入密码合法性
        if (oldPwd.length() < 6 || oldPwd.length() > 12){
            return Result.error("原密码的长度要在[6,12]");
        }
        Map<String,String > map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }
        if (!newPwd .equals(rePwd)){
            return Result.error("两次输入的密码不一致");
        }
        if (newPwd.length() < 6 || newPwd.length() > 12){
            return Result.error("新密码的长度要在[6,12]");
        }
        if (oldPwd.equals(newPwd)) {
            return Result.error("与原密码一样");
        }
        userService.updatePwd(newPwd);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success();
    }

}
