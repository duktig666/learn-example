package reflectiontest.servlettest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * 测试使用反射，一个servlet处理多个接口请求
 *
 * @author rsw
 */
@WebServlet(name = "LoginServlet", value = "*.login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        System.out.println("servlet调用....");

        //获取servletPath： /add.do 或 /*.login 等
        String servletPath = request.getServletPath();
        //去除/和.do ： add 或 query
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0, methodName.length() - 6);

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            //利用反射获取methondNmae对应的方法
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            //利用反射调用对应的方法
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //response.sendRedirect("error.jsp");    //给用户提示
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        System.out.println("正在尝试登录...");
        //输出到页面
        out.println("正在尝试登录...");
    }

    private void exitLogin(HttpServletRequest request, HttpServletResponse response) throws IOException,
            SQLException {
        //获取请求参数
        PrintWriter out = response.getWriter();
        System.out.println("正在尝试退出...");
        //输出到页面
        out.println("正在尝试退出...");
    }


}
