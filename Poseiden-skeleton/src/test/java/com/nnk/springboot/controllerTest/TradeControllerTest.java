package com.nnk.springboot.controllerTest;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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


    @WithMockUser(authorities = "USER")
    @Test
    public void testHome() throws Exception {
        // Given
        List<Trade> tradeList = new ArrayList<>();
        Trade trade = new Trade();

        trade.setAccount("testaccount");
        trade.setType("testtype");
        trade.setBuyQuantity(12d);


        tradeList.add(trade);
        when(tradeService.findAll()).thenReturn(tradeList);

        // When and then
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());
    }
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

    @WithMockUser(authorities = "USER")
    @Test
    public void testDeleteTrade() throws Exception {

        Trade trade = new Trade();
        trade.setId(1);

        when(tradeService.findById(1)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        // Vérification que l'utilisateur a été supprimé
        verify(tradeService).delete(trade);
    }

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
