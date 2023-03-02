package com.nnk.springboot.serviceTests;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        Assert.assertNotNull(curvePoint.getId());
        Assert.assertTrue(curvePoint.getCurveId() == 10);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointService.save(curvePoint);
        Assert.assertTrue(curvePoint.getCurveId() == 20);

        // Find
        List<CurvePoint> listResult = curvePointService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointService.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointService.findById(id);
        Assert.assertFalse(curvePointList.isPresent());
    }

}

