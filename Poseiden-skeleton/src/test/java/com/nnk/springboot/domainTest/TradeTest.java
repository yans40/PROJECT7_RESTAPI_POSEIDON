package com.nnk.springboot.domainTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
//import com.nnk.springboot.service.OAuth2AuthorizedClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeTest {

	@Autowired
	private TradeRepository tradeRepository;


	@Test
	public void tradeTest() {
		Trade trade = new Trade();

			trade.setAccount("Trade Account");
			trade.setType("Type");
			trade.setBuyQuantity(10d);
			trade.setSellQuantity(14d);
			trade.setBuyPrice(32d);
			trade.setSellPrice(45d);
			trade.setBook("booktest");
			trade.setBenchmark("benchMarkTest");
			trade.setSecurity("securityTest");
			trade.setStatus("statusTest");
			trade.setTrader("traderTest");
			trade.setCreationName("nametest");
			trade.setRevisionName("revisionnametest");
			trade.setDealName("dealNametest");
			trade.setDealType("dealtypetest");
			trade.setSourceListId("sourcelistIdtest");
			trade.setSide("sideTest");

		// Save
		trade = tradeRepository.save(trade);
		Assert.assertNotNull(trade.getBuyQuantity());
		Assert.assertNotNull(trade.getSellQuantity());
		Assert.assertNotNull(trade.getBuyPrice());
		Assert.assertNotNull(trade.getSellPrice());
		Assert.assertNotNull(trade.getBenchmark());
		Assert.assertNotNull(trade.getSecurity());
		Assert.assertNotNull(trade.getStatus());
		Assert.assertNotNull(trade.getTrader());
		Assert.assertNotNull(trade.getCreationName());
		Assert.assertNotNull(trade.getRevisionName());
		Assert.assertNotNull(trade.getDealName());
		Assert.assertNotNull(trade.getDealType());
		Assert.assertNotNull(trade.getSourceListId());
		Assert.assertNotNull(trade.getSide());
		Assert.assertNotNull(trade.getBook());

		Assert.assertTrue(trade.getAccount().equals("Trade Account"));
		Assert.assertTrue(trade.getSide().equals("sideTest"));
		Assert.assertTrue(trade.getBook().equals("booktest"));

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		Assert.assertFalse(tradeList.isPresent());
	}
}
