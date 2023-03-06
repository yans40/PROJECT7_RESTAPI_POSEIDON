package com.nnk.springboot.controllerTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.CurvePointService;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CurvePointService curvePointService;


    @WithMockUser(authorities = "USER")
    @Test
    public void testHome() throws Exception {
        // Given
        List<CurvePoint> curvePoints = new ArrayList<>();
        CurvePoint curvePoint = new CurvePoint();

        curvePoint.setCurveId(11);
        curvePoint.setTerm(12d);
        curvePoint.setValue(11d);

        curvePoints.add(curvePoint);
        when(curvePointService.findAll()).thenReturn(curvePoints);

        // When and then
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "jean",password = "azerty",authorities = "USER")
    @Test
   public void should_add_curve() throws Exception {

        this.mockMvc.perform(get("/curvePoint/add"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @WithMockUser(authorities = "USER")
    @Test
    public void should_validate_curvepoints_inscription() throws Exception {
        MultiValueMap<String,String> formCurvePointsData =  new LinkedMultiValueMap<>();
        formCurvePointsData.add("curveId","11");
        formCurvePointsData.add("term","12d");
        formCurvePointsData.add("value","11");

        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(formCurvePointsData)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testShowCurvePointUpdateForm() throws Exception {
        // Given
        int curvePointId = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(curvePointId);
        Mockito.when(curvePointService.findById(curvePointId)).thenReturn(Optional.of(curvePoint));

        // When and Then
        mockMvc.perform(get("/curvePoint/update/" +curvePointId))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attribute("curvePoint", curvePoint));
    }

    @WithMockUser(authorities = "USER")
    @Test
    public void testDeleteUser() throws Exception {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);

        when(curvePointService.findById(1)).thenReturn(Optional.of(curvePoint));

        mockMvc.perform(get("/curvePoint/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        // Vérification que l'utilisateur a été supprimé
        verify(curvePointService).delete(curvePoint);
    }


    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
}
