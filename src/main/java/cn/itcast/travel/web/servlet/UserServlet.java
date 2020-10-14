package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Wuk
 * @Company: jlu.edu.cn
 * @date: 2020/10/14.
 * @description:
 */

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    UserServiceImpl userService = new UserServiceImpl();//避免多次调用，直接声明一个

    /**
     * 提供用户激活功能，改变数据库中的激活状态码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("acticeuser---------------");
        //获取激活码
        String code = request.getParameter("code");
        if (code!=null){
            //调用service完成激活
            boolean flag = userService.active(code);

            //判断标记是否激活成功
            String msg = null;
            if(flag){
                //激活成功
                msg = "激活成功，请<a href = 'login.html'>登陆</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员！！";
            }

            response.setContentType("text/html;charset= utf-8");
            response.getWriter().write(msg);
        }

    }

    /**
     * 向浏览器返回生成的验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("checkcode-------------------------------");
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        System.out.println("验证码输出了>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        ImageIO.write(image,"PNG",response.getOutputStream());
    }

    /**
     * 实现退出登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("exit----------------------------------");
        //销毁session即退出功能实现
        request.getSession().invalidate();
        //跳转登录界面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 查找一个用户是否存在
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     * 实现登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service查询

        User u = userService.login(user);

        ResultInfo info = new ResultInfo();

        //判断用户对象是否为null
        if (u == null){
            //用户名或者密码输入错误
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");
        }

        //判断用户是否激活
        if(u!=null&&"Y".equals(u.getStatus())){
            //登录成功
            info.setFlag(true);
        }else{
            //未激活
            info.setFlag(false);
            info.setErrorMsg("用户未激活");
        }

        //相应数据
        //FindUserServlet里面用到
        request.getSession().setAttribute("user",u);
        /*ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);*/
        writeValue(info,response);
    }

    /**
     * 实现注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server= (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        System.out.println("11111111111111");
        //比较
        if (checkcode_server == null|!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            //将resultInfo对象序列化为json返回客户端
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            //将json数据写回客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            System.out.println("2222222222222222222");
            return;
        }
        System.out.println("333333333333333333333");

        //获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("dopost........................");
        //封装对象
        User user = new User();
        System.out.println(user);
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service完成注册

        boolean flag = userService.register(user);
        ResultInfo resultInfo = new ResultInfo();
        // 响应结果
        if (flag){
            //注册成功
            resultInfo.setFlag(true);
        }else{
            //注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败！！！");
        }

        //将resultInfo对象序列化为json返回客户端
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(resultInfo);

     String json = writeValueAsString(resultInfo);
        //将json数据写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);



    }

    /**
     * 产生4位随机字符串
     */
    public String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

}
