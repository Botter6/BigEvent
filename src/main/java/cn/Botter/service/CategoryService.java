package cn.Botter.service;

import cn.Botter.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
//@Service
public interface CategoryService {
    //新增分类
    void add(Category category);

    List<Category> findAll();
    //根据id查询分类信息
    Category findById(Integer id);
    //更新分类
    void update(Category category);

    void delete(Integer id);
}
