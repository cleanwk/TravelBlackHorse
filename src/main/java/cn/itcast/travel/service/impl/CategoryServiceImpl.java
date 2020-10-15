package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/14.
 * @description:
 */

public class CategoryServiceImpl implements CategoryService {
    private CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {

        System.out.println("执行了findall方法------------------------------------------------");
        //从redis中查询
        //获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        jedis.auth("wk4478200");
        //使用sortdedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);

        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //判断查询集合是否为空
        List<Category> cs=null;
        if (categorys==null || categorys.size()==0 ){
            //collection is empty ,then querry form sql
            cs = categoryDao.findAll();
            //save cs to redis  "category-key"
            for (int i = 0; i <cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            //如果不为空，将set的数据存入list
             cs = new ArrayList<Category>();

            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
        //return categoryDao.findAll();
    }


}
