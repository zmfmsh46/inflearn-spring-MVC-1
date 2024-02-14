package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServiceV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServiceV1 extends HttpServlet {
    //해당 url에 대한 컨트롤러 Map 변수 생성.
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    //생성자로 해당 url에 대한 컨트롤러들 등록
    public FrontControllerServiceV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServiceV1.service");

        String requestURI = request.getRequestURI();


        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null) {
            //페이지가 없거나 못찾을때 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response);
    }
}
