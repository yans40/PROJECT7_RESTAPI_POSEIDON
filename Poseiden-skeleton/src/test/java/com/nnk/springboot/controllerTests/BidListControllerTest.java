package com.nnk.springboot.controllerTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
import org.springframework.test.context.web.WebAppConfiguration;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @Autowired
    private WebApplicationContext context;

    @WithMockUser(username = "jean",password = "azerty",authorities = "USER")
    @Test
    public void should_add_bidlist() throws Exception {
        this.mockMvc.perform(get("/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "yan",password = "azerty",authorities = "USER")
    @Test
    public void should_validate_bidlist_inscription() throws Exception {
        MultiValueMap<String,String> formBidListData =  new LinkedMultiValueMap<>();
        formBidListData.add("account","testaccount");
        formBidListData.add("type","testtype");
        formBidListData.add("bidquantity","12d");

        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formBidListData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testShowBidListUpdateForm() throws Exception {
        // Given
        int bidId = 1;
        BidList bidList = new BidList();
        bidList.setId(bidId);
        Mockito.when(bidListService.findById(bidId)).thenReturn(Optional.of(bidList));

        // When and Then
        mockMvc.perform(get("/bidList/update/" + bidId))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attribute("bidList", bidList));
    }



    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

}
