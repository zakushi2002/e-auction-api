package com.e.auction.api.config.security;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.view.dto.AccountForTokenDto;
import com.e.auction.api.model.TablePrefix;
import com.e.auction.api.utils.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    private JdbcTemplate jdbcTemplate;

    public CustomTokenEnhancer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CustomTokenEnhancer() {
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String email = authentication.getName();
        AccountForTokenDto a = getAccountByEmail(email);

        if (a != null) {
            Long accountId = a.getId();
            String kind = a.getKind() + "";//token kind
            String permission = "<>";//empty string
            Integer userKind = a.getKind(); //loại user là admin hay là gì
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            additionalInfo.put("user_id", a.getId());
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", EAuctionConstant.GRANT_TYPE_PASSWORD);
            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + userKind + DELIM
                    + email + DELIM
                    + orderId + DELIM
                    + isSuperAdmin);
            additionalInfo.put("additional_info", additionalInfoStr);
        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    public AccountForTokenDto getAccountByEmail(String email) {
        try {
            String query = "SELECT id, kind, email, full_name, is_super_admin FROM " + TablePrefix.PREFIX_TABLE +
                    "account WHERE email = ? and status = 1 limit 1";
            log.debug(query);
            List<AccountForTokenDto> dto = jdbcTemplate.query(query, new Object[]{email}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
            if (dto.size() > 0) return dto.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
