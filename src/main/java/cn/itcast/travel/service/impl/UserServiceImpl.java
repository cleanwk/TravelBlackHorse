package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

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
        //设置激活码，使用唯一字符串
        user.setCode(UuidUtil.getUuid());
        //设置激活状态
        user.setStatus("N");
        //保存用户
        userDao.save(user);

        //发送激活邮件,正文内容为
        String content = " <a href = 'http://localhost:8087/travel/user/activeUser?code="+user.getCode()+"'>【点击激活您的账号】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }


    /**
     * 修改用户激活状态
     * @param code
     * @return
     */
    public boolean active(String code) {
        //根据激活码查询对象
        User user = userDao.findByCode(code);
        if (user!=null){
            //调用dao层的修改激活状态的的方法
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }

    }

    /**
     * 登录的方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassWord(user.getUsername(),user.getPassword());
    }
}
