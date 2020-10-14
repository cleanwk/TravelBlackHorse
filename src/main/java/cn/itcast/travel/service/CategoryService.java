package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/14.
 * @description:
 */

public interface CategoryService {

    /**
     * 查询所有
     * @return
     */
    public List<Category> findAll();

}
