package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class BidListController {
    @Autowired
    private BidListService bidService;

    @RequestMapping ("/bidList/list")
    public String home(Model model) {
        log.info("return la bidlist");
        List<BidList> bidList = bidService.findAll();
        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidList(Model model) {
        BidList bidList = new BidList();
        model.addAttribute("bidList", bidList);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidService.save(bidList);
            log.info("bidList saved");
            model.addAttribute("bidList", bidService.findAll());
            return "redirect:/bidList/list";
        }
        return "bidList/add";

    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        BidList bidList = bidService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("bidList", bidList);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        bidList.setAccount(bidList.getAccount());
        bidList.setType(bidList.getType());
        bidList.setBidQuantity(bidList.getBidQuantity());
        bidService.save(bidList);
        log.info("bidList updated !");
        model.addAttribute("bidLists", bidService.findAll());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        bidService.delete(bidList);
        log.info("bidList deleted");
        model.addAttribute("bidLists", bidService.findAll());
        return "redirect:/bidList/list";
    }
}
