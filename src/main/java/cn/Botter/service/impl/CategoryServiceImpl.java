package cn.Botter.service.impl;

import cn.Botter.mapper.CategoryMapper;
import cn.Botter.pojo.Category;
import cn.Botter.service.CategoryService;
import cn.Botter.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Botter
 * @date 2024/7/14
 * @Description
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        //补充属性
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> findAll() {
        Map<String,Object>  map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return categoryMapper.findAll(id);
    }

    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);

        return c;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
