package com.nnk.springboot.controllerTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TradeService tradeService;

    @WithMockUser(username = "jean",password = "azerty",authorities = "USER")
    @Test
    public void should_add_trade() throws Exception {
        this.mockMvc.perform(get("/trade/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void should_validate_trade_inscription() throws Exception {
        MultiValueMap<String,String> formTradeData =  new LinkedMultiValueMap<>();
        formTradeData.add("account","testaccount");
        formTradeData.add("type","testtype");
        formTradeData.add("buyquantity","12d");

        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formTradeData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }
    @WithMockUser(authorities = "USER")
    @Test
    public void testShowTradeUpdateForm() throws Exception {
        // Given
        int tradeId = 1;
        Trade trade = new Trade();
        trade.setId(tradeId);
        Mockito.when(tradeService.findById(tradeId)).thenReturn(Optional.of(trade));

        // When and Then
        mockMvc.perform(get("/trade/update/" + tradeId))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade));
    }

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
