package com.e.auction.api.controller;

import com.e.auction.api.repository.ShippingCostRepository;
import com.e.auction.api.repository.ShippingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/ship")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ShipController extends BaseController {
    @Autowired
    ShippingCostRepository shippingCostRepository;
    @Autowired
    ShippingRepository shippingRepository;
}
