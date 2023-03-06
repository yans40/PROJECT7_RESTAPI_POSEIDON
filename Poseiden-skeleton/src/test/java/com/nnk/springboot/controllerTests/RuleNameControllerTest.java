package com.nnk.springboot.controllerTests;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RuleNameService ruleNameService;

    @WithMockUser(username = "jean",password = "azerty",authorities = "USER")
    @Test
    public void should_add_rulename() throws Exception {
        this.mockMvc.perform(get("/ruleName/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void should_validate_curvepoints_inscription() throws Exception {
        MultiValueMap<String,String> formRuleNameData =  new LinkedMultiValueMap<>();
        formRuleNameData.add("name","nametest");
        formRuleNameData.add("description","description test");
        formRuleNameData.add("json","jsontest");
        formRuleNameData.add("template","templatetest");
        formRuleNameData.add("sqlStr","sqlStrtest");
        formRuleNameData.add("sqlPart","sqlParttest");

        mockMvc.perform(post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formRuleNameData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowRuleNameUpdateForm() throws Exception {
        // Given
        int ruleNameId = 1;
        RuleName ruleName = new RuleName();
        ruleName.setId(ruleNameId);
        Mockito.when(ruleNameService.findById(ruleNameId)).thenReturn(Optional.of(ruleName));

        // When and Then
        mockMvc.perform(get("/ruleName/update/" + ruleNameId))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attribute("ruleName", ruleName));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testDeleteRuleName() throws Exception {

        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        when(ruleNameService.findById(1)).thenReturn(Optional.of(ruleName));

        mockMvc.perform(get("/ruleName/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));

        // Vérification que l'utilisateur a été supprimé
        verify(ruleNameService).delete(ruleName);
    }

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
}
