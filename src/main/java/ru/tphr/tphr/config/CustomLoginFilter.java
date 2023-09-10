package ru.tphr.tphr.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.tphr.tphr.utils.ReCaptchaV2Handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    public CustomLoginFilter(String loginURL, String httpMethod){
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginURL,httpMethod));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String recaptchaFormResponse = request.getParameter("g-recaptcha-response");
        System.out.println("Before processing authentication.......");

        ReCaptchaV2Handler handler = new ReCaptchaV2Handler();
        boolean result = handler.verify(recaptchaFormResponse);

        if (!result){
            try {
                System.out.println("Srevlet error: " + HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                String username = request.getParameter("username");
                String password = request.getParameter("password");
//                String failUrl = String.format("/fail?username=%s&password=%S", username, password);
//                System.out.println("Фэйл урлЖ " + failUrl);
                request.getRequestDispatcher("/fail").forward(request, response);
                return null;
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }
}
