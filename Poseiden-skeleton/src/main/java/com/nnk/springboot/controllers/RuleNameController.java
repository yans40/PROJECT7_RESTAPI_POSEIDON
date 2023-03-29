package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;


    @RequestMapping("/ruleName/list")
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

        List<RuleName> ruleNameList = ruleNameService.findAll();
        model.addAttribute("rulenamelist", ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        RuleName ruleName = new RuleName();
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.save(ruleName);
            model.addAttribute("ratingList", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        }

        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("ruleName", ruleName);

        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleName.setName(ruleName.getName());
        ruleName.setJson(ruleName.getJson());
        ruleName.setSqlStr(ruleName.getSqlStr());
        ruleName.setTemplate(ruleName.getTemplate());
        ruleName.setDescription(ruleName.getDescription());
        ruleNameService.save(ruleName);
        model.addAttribute("ruleNameList", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ruleNameService.delete(ruleName);
        model.addAttribute("ruleNameList", ruleNameService.findAll());

        return "redirect:/ruleName/list";
    }
}
