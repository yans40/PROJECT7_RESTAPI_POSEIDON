package com.nnk.springboot.controllerTests;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.RatingService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RatingService ratingService;

    @WithMockUser(username = "michel",password = "azerty",authorities = {"USER","ADMIN"})
    @Test
    public void should_add_rating() throws Exception {
        this.mockMvc.perform(get("/rating/add"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void should_validate_rating_inscription() throws Exception {
        MultiValueMap<String,String> formRatingData =  new LinkedMultiValueMap<>();
        formRatingData.add("moodysRating","moodystest");
        formRatingData.add("fitchRating","fitchTest");
        formRatingData.add("sandPRating","sandTest");
        formRatingData.add("orderNumber","12");

        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formRatingData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testShowTradeUpdateForm() throws Exception {
        // Given
        int ratingId = 1;
        Rating rating = new Rating();
        rating.setId(ratingId);
        Mockito.when(ratingService.findById(ratingId)).thenReturn(Optional.of(rating));

        // When and Then
        mockMvc.perform(get("/rating/update/" + ratingId))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testDeleteUser() throws Exception {

        Rating rating = new Rating();
        rating.setId(1);

        when(ratingService.findById(1)).thenReturn(Optional.of(rating));

        mockMvc.perform(get("/rating/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        // Vérification que l'utilisateur a été supprimé
        verify(ratingService).delete(rating);
    }

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
}
