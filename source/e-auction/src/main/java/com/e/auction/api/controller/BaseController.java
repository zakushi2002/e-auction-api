package com.e.auction.api.controller;

import com.e.auction.api.jwt.EAuctionJwt;
import com.e.auction.api.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseController {
    @Autowired
    private UserServiceImpl userService;

    public long getCurrentUser() {
        EAuctionJwt EAuctionJwt = userService.getAdditionalInfo();
        return EAuctionJwt.getAccountId();
    }

    public long getTokenId() {
        EAuctionJwt EAuctionJwt = userService.getAdditionalInfo();
        return EAuctionJwt.getTokenId();
    }

    public EAuctionJwt getSessionFromToken() {
        return userService.getAdditionalInfo();
    }

    public boolean isSuperAdmin() {
        EAuctionJwt EAuctionJwt = userService.getAdditionalInfo();
        if (EAuctionJwt != null) {
            return EAuctionJwt.getIsSuperAdmin();
        }
        return false;
    }
}
