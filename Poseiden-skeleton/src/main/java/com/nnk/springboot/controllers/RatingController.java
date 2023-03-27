package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
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
        List<Rating> ratingList = ratingService.findAll();
        model.addAttribute("ratingList", ratingList);
        return "rating/list";
    }


    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        Rating rating = new Rating();
        model.addAttribute("rating", rating);
        return "rating/add";
    }


    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("ratingList", ratingService.findAll());
            return "redirect:/rating/list";
        }

        return "rating/add";
    }


    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("rating", rating);

        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        rating.setId(id);
        rating.setMoodysRating(rating.getMoodysRating());
        rating.setSandPRating(rating.getSandPRating());
        rating.setFitchRating(rating.getFitchRating());
        rating.setOrderNumber(rating.getOrderNumber());
        ratingService.save(rating);
        model.addAttribute("ratingList", ratingService.findAll());

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ratingService.delete(rating);
        model.addAttribute("ratings", ratingService.findAll());

        return "redirect:/rating/list";
    }
}
