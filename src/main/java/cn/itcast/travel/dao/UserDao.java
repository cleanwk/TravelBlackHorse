package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;



public interface UserDao {
    /**
     * 根据激活码查询用用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 根据用户更新用户的激活状态
     * @param user
     */
     void updateStatus(User user);
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByName(String username);

    /**
     * 保存用户
     * @param user
     */
    public void save(User user);

    /**
     * 根据用户名,密码查找数据库中是否有符合的的
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassWord(String username,String password);
}
