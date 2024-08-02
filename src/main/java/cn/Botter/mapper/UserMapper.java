package cn.Botter.mapper;

import cn.Botter.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Botter
 * @date 2024/7/13
 * @Description
 */
@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("SELECT  * FROM  user WHERE  username = #{username}")
    User findByUserName(String username);
    //添加用户
    @Insert("insert into user(username,password,create_time,update_time)" +
            "values(#{username},#{password},now(),now())")
    void addUser(String username, String password);
    //更新用户
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    void update(User user);

    //更新头像
    @Update("update user set user_pic = #{avatarUrl},update_time=now() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);
    @Update("update user set password = #{md5String} , update_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
