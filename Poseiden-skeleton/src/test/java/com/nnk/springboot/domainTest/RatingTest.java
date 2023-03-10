package com.nnk.springboot.domainTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class RatingTest {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	 void ratingTest() {
		Rating rating = new Rating();
		rating.setMoodysRating("Moodys Rating");
		rating.setSandPRating("Sand PRating");
		rating.setFitchRating("Fitch Rating");
		rating.setOrderNumber(10);

		// Save
		rating = ratingRepository.save(rating);
		Assertions.assertNotNull(rating.getId());
		Assertions.assertEquals(10, (int) rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assertions.assertEquals(20, (int) rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assertions.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assertions.assertFalse(ratingList.isPresent());
	}
}
