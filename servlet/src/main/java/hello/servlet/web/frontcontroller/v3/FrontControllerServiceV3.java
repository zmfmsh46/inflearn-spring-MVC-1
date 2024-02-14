package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServiceV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServiceV3 extends HttpServlet {
    //해당 url에 대한 컨트롤러 Map 변수 생성.
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    //생성자로 해당 url에 대한 컨트롤러들 등록
    public FrontControllerServiceV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServiceV3.service");

        String requestURI = request.getRequestURI();


        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            //페이지가 없거나 못찾을때 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        //컨트롤러에서 ModelView 반환
        ModelView modelView = controller.process(paramMap);
        //viewResolver 호출하여 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경
        // Ex) 논리이름 : new-form -> /WEB-INF/views/new-form.jsp
        String viewName = modelView.getViewName();
        MyView view = viewResolver(viewName);
        //MyView 렌더링
        view.render(modelView.getModel(), request, response);

    }

    private static MyView viewResolver(String viewName) {

        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //컨트롤+알트+M : 메서드 생성
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
