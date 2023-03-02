package com.nnk.springboot.repositoryTests;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BidListRepositoryTest {

    @Autowired
    private BidListRepository bidListRepository;

//    @BeforeEach
//    void cleanBidListRepo(){
//        this.bidListRepository.deleteAll();
//    }

    @Test
    public void bidListTest() {
//		BidList bid =new BidList("accounted", "Type Test", 10d); pourquoi il ne fonctionne pas avec le constructeur

        BidList bid = new BidList();
        bid.setAccount("accountTest");
        bid.setType("typetest");
        bid.setBidQuantity(10d);


        // Save
        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

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