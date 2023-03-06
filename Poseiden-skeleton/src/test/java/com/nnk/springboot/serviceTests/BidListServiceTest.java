package com.nnk.springboot.serviceTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.BidListService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BidListServiceTest {

    @Autowired
    BidListService bidService;

    @Test
    public void shouldReturnBidServiceList() {
        BidList bid = new BidList();
        bid.setId(15);
        bid.setAccount("accountTest");
        bid.setType("typetest");
        bid.setBidQuantity(10d);


        // Save
        bid = bidService.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidService.save(bid);
        Assert.assertTrue(bid.getBidQuantity()== 20d);

        // Find
        List<BidList> listResult = bidService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id =  bid.getId();
        bidService.delete(bid);
        Optional<BidList> bidList = bidService.findById(id);
        Assert.assertFalse(bidList.isPresent());
    }



}
