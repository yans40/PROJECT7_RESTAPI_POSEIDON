package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class CurvePointServiceTest {

    @Autowired
    private CurvePointService curvePointService;

    @Test
    public void curvePointTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);

        // Save
        curvePoint = curvePointService.save(curvePoint);
        Assertions.assertNotNull(curvePoint.getId());
        Assertions.assertEquals(10, curvePoint.getCurveId());

        // Update
        curvePoint.setTerm(22d);
        curvePoint = curvePointService.save(curvePoint);
        Assertions.assertEquals(22d,curvePoint.getTerm());

        // Find
        List<CurvePoint> listResult = curvePointService.findAll();
        Assertions.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointService.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointService.findById(id);
        Assertions.assertFalse(curvePointList.isPresent());


    }

}

