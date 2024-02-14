package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV4 {
    //컨트롤러에 모델도 넘겨줌.
    /**
     *
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}

