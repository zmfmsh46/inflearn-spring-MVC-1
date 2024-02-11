package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
    서블릿은 기본적으로 HttpServlet을 상속받는다.
    @WebServlet(name = "helloServlet", urlPatterns = "/hello")
    name : 서블릿 이름, urlPatterns : URL 매핑
    HTTP 요청을 통해 /hello 라는 매핑된 url이 호출되면, 서블릿 컨테이너는 service() 메서드를 실행한다.
 */
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        //쿼리 파라미터(?username=yuhoyeon)를 서블릿이 읽는 기능 : request.getParameter() 메서드 사용
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.getWriter().write() : http 메세지 body에 데이터가 들어감.
        response.getWriter().write("hello " + username);
    }
}
