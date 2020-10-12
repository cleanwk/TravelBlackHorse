package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template= new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查找数据库中是否有重名的
     * @param username
     * @return
     */
    @Override
    public User findByName(String username) {
        User user = null;
        try {
            //定义sql语句
            String sql = "select * from tab_user where username = ?";

            //执行sql语句
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (Exception e) {

        }
        return user;
    }

    /**
     * 将用户保存到数据库中
     * @param user
     */
    @Override
    public void save(User user) {
        //定义sql语句
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,code,status) values(?,?,?,?,?,?,?,?,?)";

        //执行sql语句
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getCode(),
                user.getStatus());

    }

    /**
     * 根据激活码返回对象
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //定义sql语句
            String sql = "select * from tab_user where code = ?";

            //执行sql语句
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),code);
        } catch (Exception e) {

        }
        return user;
    }

    /**
     * 更新用户的状态
     * @param user
     */
    @Override
    public  void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where id =?";
        template.update(sql,user.getUid());

    }
}
