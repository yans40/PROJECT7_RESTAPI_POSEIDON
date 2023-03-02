package com.nnk.springboot.serviceTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BidListServiceTest {

    @Autowired
    BidListService bidService;

    @Test
    public void shouldReturnBidServiceList() {
        BidList bid = new BidList();
        bid.setAccount("accountTest");
        bid.setType("typetest");
        bid.setBidQuantity(10d);


        // Save
        bid = bidService.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
    }


}
