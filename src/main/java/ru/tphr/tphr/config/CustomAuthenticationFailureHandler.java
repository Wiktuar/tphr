package ru.tphr.tphr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.sendRedirect("/customError");


        response.setStatus(HttpStatus.UNAUTHORIZED.value());

//        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
//        response.getOutputStream().println(String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime()));
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
