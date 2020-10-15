package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/15.
 * @description:
 */

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService= new RouteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        int cid = 0;//类别id
        System.out.println(currentPageStr);
        System.out.println(pageSizeStr);
        System.out.println(cidStr);
        //处理参数
        if (cidStr !=null&&cidStr.length()>0){
            cid = Integer.parseInt(cidStr);
        }

        int currentPage =0;//当前页码,若不传递，则默认为第一页
        if (currentPageStr !=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage =1;
        }

        int pageSize= 0;//每页显示的条数，不传递默认每页为五条
        if (pageSizeStr !=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize =5;
        }
        System.out.println(currentPage);
        System.out.println(pageSize);
        System.out.println(cid);
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);
        //将PageBean对象序列化返回json
        writeValue(pb,response);
    }


}
