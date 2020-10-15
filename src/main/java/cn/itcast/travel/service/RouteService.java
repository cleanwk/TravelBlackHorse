package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/15.
 * @description: 线路的service
 */

public interface RouteService {
    /**
     * 根据类别查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
     public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize);
}
