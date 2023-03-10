package com.nnk.springboot.domainTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
class BidListTest {

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
        bid.setCreationName("creaotionNameTest");
		bid.setDealType("dealTypeTest");
		bid.setSourceListId("sourListId");
		bid.setSide("sideTest");
		bid.setStatus("statusTest");

        // Save
        bid = bidListRepository.save(bid);
        Assertions.assertNotNull(bid.getId());
        Assertions.assertNotNull(bid.getAccount());
        Assertions.assertEquals(bid.getBidQuantity(), 10d, 10d);
        Assertions.assertNotNull(bid.getAskQuantity());
		Assertions.assertNotNull(bid.getBenchmark());
		Assertions.assertNotNull(bid.getBook());
		Assertions.assertNotNull(bid.getBenchmark());
		Assertions.assertNotNull(bid.getAsk());
		Assertions.assertNotNull(bid.getCommentary());
		Assertions.assertNotNull(bid.getSecurity());
		Assertions.assertNotNull(bid.getTrader());
		Assertions.assertNotNull(bid.getRevisionName());
        Assertions.assertNotNull(bid.getDealName());
        Assertions.assertNotNull(bid.getCreationName());
		Assertions.assertNotNull(bid.getDealType());
		Assertions.assertNotNull(bid.getSourceListId());
		Assertions.assertNotNull(bid.getSide());
		Assertions.assertEquals(bid.getStatus(),"statusTest","statusTest");

		// Update
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        bid.setStatus("updatedStatus");
        Assertions.assertEquals(bid.getBidQuantity(), 20d, 20d);
        Assertions.assertEquals(bid.getStatus(), "updatedStatus", "updatedStatus");

        // Find
        List<BidList> listResult = bidListRepository.findAll();
        Assertions.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        Assertions.assertFalse(bidList.isPresent());
    }
}
