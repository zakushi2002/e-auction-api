package com.e.auction.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bid")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BidController {
}
