package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import jdk.internal.net.http.ResponseSubscribers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/14.
 * @description:
 */

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    //调用service查询所有
    private CategoryServiceImpl categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //调用service查询所有
        List<Category> all = categoryService.findAll();

        //序列化json并且返回
        writeValue(all,response);
        System.out.println("--------------");

    }

}
