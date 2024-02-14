package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServiceV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServiceV2 extends HttpServlet {
    //해당 url에 대한 컨트롤러 Map 변수 생성.
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    //생성자로 해당 url에 대한 컨트롤러들 등록
    public FrontControllerServiceV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServiceV2.service");

        String requestURI = request.getRequestURI();


        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            //페이지가 없거나 못찾을때 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //Ex) new MyView("/WEB-INF/views/new-form.jsp");
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
