package ru.tphr.tphr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String targetUrl = null;
        if(session.getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null){
            targetUrl = ((SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST")).getRedirectUrl();
        } else {
            targetUrl = "http://localhost:8070/";
        }
        System.out.println(targetUrl);
        httpServletResponse.setStatus(200);
        httpServletResponse.getOutputStream()
                .println(objectMapper.writeValueAsString(targetUrl));
    }
}
