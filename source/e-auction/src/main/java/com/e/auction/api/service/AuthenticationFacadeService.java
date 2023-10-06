package com.e.auction.api.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeService {
    Authentication getAuthentication();
}
