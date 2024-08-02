package cn.Botter.controller;

import cn.Botter.pojo.Article;
import cn.Botter.pojo.PageBean;
import cn.Botter.pojo.Result;
import cn.Botter.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    @GetMapping

    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId, //设置为非必要参数
            @RequestParam(required = false) String state
    ){
        PageBean<Article>pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article> detail(@RequestParam Integer id) {
        Article a = articleService.allDetails(id);
        return Result.success(a);
    }

    @PutMapping("/update")
    public Result updateArticle(@RequestBody @Validated Article article) {
        articleService.updateArticle(article);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteArticle(@RequestParam Integer id) {
        articleService.deleteArticle(id);
        return Result.success();
    }
}

