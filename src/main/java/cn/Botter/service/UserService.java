package cn.Botter.service;

import cn.Botter.pojo.User;

/**
 * @author Botter
 * @date 2024/7/13
 * @Description
 */
public interface UserService {
    //查询是否存在该用户
    User findByUserName(String username);


    //注册用户
    void register(String username, String password);


    //更新用户
    void update(User user);
    //更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

}
