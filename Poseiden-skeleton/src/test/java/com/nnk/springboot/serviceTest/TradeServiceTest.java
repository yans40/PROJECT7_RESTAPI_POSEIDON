package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;

    @Test
    public void tradeTest() {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        // Save
        trade = tradeService.save(trade);
        Assert.assertNotNull(trade.getId());
        Assert.assertEquals("Trade Account", trade.getAccount());

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeService.save(trade);
        Assert.assertEquals("Trade Account Update", trade.getAccount());

        // Find
        List<Trade> listResult = tradeService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getId();
        tradeService.delete(trade);
        Optional<Trade> tradeList = tradeService.findById(id);
        Assert.assertFalse(tradeList.isPresent());
    }
}

