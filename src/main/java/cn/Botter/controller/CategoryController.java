package cn.Botter.controller;

import cn.Botter.pojo.Category;
import cn.Botter.pojo.Result;
import cn.Botter.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class)  Category category) {
        categoryService.add(category);
//        System.out.println("空:" +category.getCategoryName() +"|");
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> findAll() {
        List<Category> all = categoryService.findAll();
        return Result.success(all);
    }
    @GetMapping("/detail")
    public Result<Category> detail(@RequestParam Integer id) {
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping("")
    public Result update(@RequestBody @Validated(Category.Update.class)  Category category) {
        categoryService.update(category);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
}
