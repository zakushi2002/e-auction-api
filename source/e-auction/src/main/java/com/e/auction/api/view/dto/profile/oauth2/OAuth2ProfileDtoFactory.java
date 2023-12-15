package com.e.auction.api.view.dto.profile.oauth2;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.exception.oauth.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2ProfileDtoFactory {
    public static OAuth2ProfileDto getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(EAuctionConstant.PROVIDER_GOOGLE)) {
            return new GoogleOAuth2ProfileDto(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
