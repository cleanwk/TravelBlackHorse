package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/15.
 * @description:
 */

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        //封装pagebean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid);
        System.out.println(totalCount);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据结合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize);
        pb.setList(list);

        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize:totalCount/pageSize -1;
        pb.setTotalPage(totalPage);
        System.out.println(pb);
        return pb;


    }
}
