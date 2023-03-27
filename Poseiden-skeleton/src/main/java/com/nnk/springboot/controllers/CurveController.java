package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) { // si le principal est issu d'une connexion par OAuth2...
            log.info("authentification avec OAuth2");
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) principal;// on instancie un objet OAuth2AuthenticationToken ...
            Map<String, Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();// on Map les user Attributes...
            String OauthUser = (String) userAttributes.get("login");// on récupère le login

            model.addAttribute("oauthUser", OauthUser);
        } else if (principal instanceof UsernamePasswordAuthenticationToken) {// si le principal est issu d'une connexion d'une connexion standard ...
            log.info("authentification avec username & password");
            UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) principal);// on instancie un objet UsernamePasswordAuthenticationToken...

            model.addAttribute("springUsername", token.getName()); // on récupère par la méthode getName le username
        }
        List<CurvePoint> curvePoints = curvePointService.findAll();
        model.addAttribute("curvePoints", curvePoints);
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePoint(Model model) {
        CurvePoint curvePoint = new CurvePoint();
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.save(curvePoint);
            model.addAttribute("users", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePoint.setCurveId(curvePoint.getCurveId());
        curvePoint.setTerm(curvePoint.getTerm());
        curvePoint.setValue(curvePoint.getValue());
        curvePointService.save(curvePoint);
        model.addAttribute("users", curvePointService.findAll());

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curvePointService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        curvePointService.delete(curvePoint);
        model.addAttribute("curvepoints", curvePointService.findAll());


        return "redirect:/curvePoint/list";
    }
}
