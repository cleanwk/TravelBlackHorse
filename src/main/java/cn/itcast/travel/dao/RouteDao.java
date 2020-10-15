package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/15.
 * @description:
 */

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     * @param cid
     */
    public int findTotalCount(int cid);

    /**
     * 根据cid ，start，pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(int cid,int start,int pageSize);
}
