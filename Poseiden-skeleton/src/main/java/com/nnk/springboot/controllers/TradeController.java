package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
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
        List<Trade> tradeList = tradeService.findAll();
        model.addAttribute("tradeList", tradeList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        Trade trade = new Trade();
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("tradeList", tradeService.findAll());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/update";
        }
        trade.setId(id);
        trade.setAccount(trade.getAccount());
        trade.setType(trade.getType());
        trade.setBuyQuantity(trade.getBuyQuantity());
        trade.setBuyPrice(trade.getBuyPrice());
        tradeService.save(trade);
        model.addAttribute("tradeList", tradeService.findAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        Trade trade = tradeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        tradeService.delete(trade);
        model.addAttribute("tradeList", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
