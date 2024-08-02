package cn.Botter.service;

import cn.Botter.pojo.Article;
import cn.Botter.pojo.PageBean;
import org.springframework.stereotype.Service;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@Service
public interface ArticleService {
    // 新增
    void addArticle(Article article);
    //条件分类列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);


    Article allDetails(Integer id);

    void updateArticle(Article article);

    void deleteArticle(Integer id);
}
