package com.nnk.springboot.serviceTests;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Test
    public void ratingTest() {
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        // Save
        rating = ratingService.save(rating);
        Assert.assertNotNull(rating.getId());
        Assert.assertTrue(rating.getOrderNumber() == 10);

        // Update
        rating.setOrderNumber(20);
        rating = ratingService.save(rating);
        Assert.assertTrue(rating.getOrderNumber() == 20);

        // Find
        List<Rating> listResult = ratingService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingService.delete(rating);
        Optional<Rating> ratingList = ratingService.findById(id);
        Assert.assertFalse(ratingList.isPresent());
    }
}

