package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.domain.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //根据用户名查询用户对象
        User u = userDao.findByName(user.getUsername());
        //判断u是否是mull
        if (u!=null){
            //注册失败
            return false;
        }
        userDao.save(user);
        return true;
    }
}
