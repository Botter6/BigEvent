package cn.Botter.service.impl;

import cn.Botter.mapper.ArticleMapper;
import cn.Botter.pojo.Article;
import cn.Botter.pojo.PageBean;
import cn.Botter.service.ArticleService;
import cn.Botter.utils.ThreadLocalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void addArticle(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);

        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
//        1.创建Bean对象
            PageBean<Article> pb = new PageBean<>();
//        2.开启分页查询
        PageHelper.startPage(pageNum,pageSize);
//        3.调用mapper查询
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId , state);
        PageInfo<Article> p = new PageInfo<Article>(as);
        //把数据填充到PageBean对象中去
        pb.setTotal(p.getTotal());
        pb.setItems(p.getList());
        return pb;

    }

    @Override
    public Article allDetails(Integer id) {
        Map<String,Object>  map = ThreadLocalUtil.get();
        Integer userid = (Integer) map.get("id");
        return articleMapper.allDetails(id);
    }

    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(Integer id) {

        articleMapper.deleteArticle(id);
    }

}
