//package com.nnk.springboot.controllerTests;
//
//import com.nnk.springboot.domain.CurvePoint;
//import com.nnk.springboot.service.CurvePointService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@SpringBootTest
//@AutoConfigureMockMvc
////@ContextConfiguration(classes = TestSecurityConfig.class)
//public class CurvePointsControllerTest {
//
//    @Autowired
//    private MockMvc mock;
//
////    @Autowired
////    private WebApplicationContext context;
//
//    @MockBean
//   private CurvePointService curvePointService;
//
//    @Test
//    @WithUserDetails(value = "testUser")
//   public void shouldReturnCurveList() throws Exception {
//
//
//        CurvePoint curvePoint = new CurvePoint();
//        curvePoint.setId(1);
//        curvePoint.setCurveId(10);
//        curvePoint.setTerm(10d);
//        curvePoint.setValue(30d);
//
//        List<CurvePoint> curvePointList = new ArrayList<>();
//
//        curvePointList.add(curvePoint);
//        when(curvePointService.findAll()).thenReturn(curvePointList);
//
//
//        this.mock.perform(get("/curvePoint/list"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//
//    }
//
////    @BeforeEach
////    public void setup(){
////        mock = MockMvcBuilders
////                .webAppContextSetup(context)
////                .apply(springSecurity())
////                .build();
////    }
//}
