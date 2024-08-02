package cn.Botter.mapper;

import cn.Botter.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@Mapper
public interface ArticleMapper {
    @Insert("insert into article (title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);
//            List<Article> as = articleMapper.list(userId, categorId , state);

    List<Article> list(Integer userId, Integer categoryId, String state);
    @Select("select * from article where id = #{id}")
    Article allDetails(Integer id);

    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId} where id = #{id}")
    void updateArticle(Article article);
    @Delete("delete from article where id = #{id}")
    void deleteArticle(Integer id);
}
