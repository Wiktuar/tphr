package ru.tphr.tphr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.tphr.tphr.DTO.LikesPoemDto;
import ru.tphr.tphr.services.PoemService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class MainController {

    private PoemService poemService;

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

//  метод получения стихотворений на индексной странице
    @GetMapping("/")
    public String getMainPage(Model model, Principal principal){ ;
        String principalName = null;
        if(principal != null){
            principalName = principal.getName();
        } else {
            principalName = "default";
        }
        List<LikesPoemDto> lpd =  poemService.getAllPoems(principalName);
        model.addAttribute("poems", lpd);
        return "index";
    }

    @PostMapping("/fail")
    public RedirectView getLoginPage(@RequestParam String username,
                                     @RequestParam String password,
                                     HttpServletRequest request,
                                     RedirectAttributes ra){
        System.out.println(username);
        ra.addFlashAttribute("flashAttr", "Пожалуста, подтвердите, что Вы не робот!");
        ra.addFlashAttribute("username", username);
        ra.addFlashAttribute("password", password);;
        return new RedirectView("/login", true);
    }

    @GetMapping("/auth-error")
    public RedirectView getFailAuth(@RequestParam String username,
                              @RequestParam String password,
                              HttpServletRequest request,
                              RedirectAttributes ra){
        System.out.println("Вызов GET метода при ошибке " + username);
        System.out.println("Вызов GET метода при ошибке " + password);
        ra.addFlashAttribute("username", username);
        ra.addFlashAttribute("password", password);
        ra.addFlashAttribute("attention", "true");
        return new RedirectView("/login", true);
    }
}
