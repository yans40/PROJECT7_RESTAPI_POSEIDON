package com.nnk.springboot.domainTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class BidListTest {
    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {
        BidList bid = new BidList();
        bid.setAccount("accountTest");
        bid.setType("typetest");
        bid.setAsk(2d);
        bid.setAskQuantity(13d);
        bid.setBenchmark("benchmarktest");
        bid.setBook("booktest");
        bid.setBidQuantity(10d);
		bid.setCommentary("commentary test");
		bid.setSecurity("security test");
		bid.setTrader("traderTest");
		bid.setRevisionName("revicionTest");
		bid.setDealName("dealTest");
		bid.setDealType("dealTypeTest");
		bid.setSourceListId("sourListId");
		bid.setSide("sideTest");
		bid.setStatus("statusTest");

        // Save
        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
        Assert.assertNotNull(bid.getAskQuantity());
		Assert.assertNotNull(bid.getBenchmark());
		Assert.assertNotNull(bid.getBook());
		Assert.assertNotNull(bid.getBenchmark());
		Assert.assertNotNull(bid.getAsk());
		Assert.assertNotNull(bid.getCommentary());
		Assert.assertNotNull(bid.getSecurity());
		Assert.assertNotNull(bid.getTrader());
		Assert.assertNotNull(bid.getRevisionName());
		Assert.assertNotNull(bid.getDealName());
		Assert.assertNotNull(bid.getDealType());
		Assert.assertNotNull(bid.getSourceListId());
		Assert.assertNotNull(bid.getSide());
		Assert.assertNotNull(bid.getStatus());

		// Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        Assert.assertFalse(bidList.isPresent());
    }
}
