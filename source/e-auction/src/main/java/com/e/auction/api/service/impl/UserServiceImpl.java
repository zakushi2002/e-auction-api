package com.e.auction.api.service.impl;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.exception.oauth.OAuth2AuthenticationProcessingException;
import com.e.auction.api.jwt.EAuctionJwt;
import com.e.auction.api.model.Group;
import com.e.auction.api.model.UserProfile;
import com.e.auction.api.repository.AccountRepository;
import com.e.auction.api.model.Account;
import com.e.auction.api.model.Permission;
import com.e.auction.api.repository.GroupRepository;
import com.e.auction.api.repository.UserProfileRepository;
import com.e.auction.api.view.dto.profile.oauth2.OAuth2ProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        Account user = accountRepository.findAccountByEmail(userId);
        if (user == null) {
            log.error("Invalid email or password!");
            throw new UsernameNotFoundException("Invalid email or password!");
        }
        boolean enabled = true;
        if (!Objects.equals(user.getStatus(), EAuctionConstant.STATUS_ACTIVE)) {
            log.error("User had been locked");
            enabled = false;
        }
        Collection<GrantedAuthority> grantedAuthorities = getAccountPermission(user);
        return new User(user.getEmail(), user.getPassword(), enabled, true, true, true, grantedAuthorities);
    }

    private Collection<GrantedAuthority> getAccountPermission(Account user) {
        List<String> roles = user.getGroup().getPermissions().stream().map(Permission::getPermissionCode).filter(permissionCode -> permissionCode != null).collect(Collectors.toList());
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).collect(Collectors.toSet());
    }

    public Account getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                return accountRepository.findAccountByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public OAuth2AccessToken getAccessTokenForMultipleTenancies(ClientDetails client, TokenRequest tokenRequest, String email, String password, AuthorizationServerTokenServices tokenServices) throws GeneralSecurityException, IOException {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("grantType", tokenRequest.getGrantType());

        String clientId = client.getClientId();
        boolean approved = true;
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");
        Map<String, Serializable> extensionProperties = new HashMap<>();

        UserDetails userDetails = loadUserByUsername(email);
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId,
                userDetails.getAuthorities(), approved, client.getScope(),
                client.getResourceIds(), null, responseTypes, extensionProperties);
        User userPrincipal = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(), userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userDetails.getAuthorities());
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        return tokenServices.createAccessToken(auth);
    }

    public OAuth2AccessToken getAccessTokenForGoogle(ClientDetails client, TokenRequest tokenRequest, OAuth2ProfileDto oAuth2ProfileDto, AuthorizationServerTokenServices tokenServices) throws GeneralSecurityException, IOException {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("grantType", tokenRequest.getGrantType());

        String clientId = client.getClientId();
        boolean approved = true;
        Set<String> responseTypes = new HashSet<>();
        responseTypes.add("code");
        Map<String, Serializable> extensionProperties = new HashMap<>();

        UserDetails userDetails = processOAuth2User(oAuth2ProfileDto, tokenRequest.getGrantType());
        OAuth2Request oAuth2Request = new OAuth2Request(requestParameters, clientId,
                userDetails.getAuthorities(), approved, client.getScope(),
                client.getResourceIds(), null, responseTypes, extensionProperties);
        User userPrincipal = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(), userDetails.isAccountNonLocked(), userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userDetails.getAuthorities());
        OAuth2Authentication auth = new OAuth2Authentication(oAuth2Request, authenticationToken);
        return tokenServices.createAccessToken(auth);
    }

    public EAuctionJwt getAdditionalInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            OAuth2AuthenticationDetails oauthDetails =
                    (OAuth2AuthenticationDetails) authentication.getDetails();
            if (oauthDetails != null) {
                Map<String, Object> map = (Map<String, Object>) oauthDetails.getDecodedDetails();
                String encodedData = (String) map.get("additional_info");
                //idStr -> json
                if (encodedData != null && !encodedData.isEmpty()) {
                    return EAuctionJwt.decode(encodedData);
                }
                return null;
            }
        }
        return null;
    }

    private UserDetails processOAuth2User(OAuth2ProfileDto oAuth2ProfileDto, String provider) {
        if (StringUtils.isEmpty(oAuth2ProfileDto.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Account account = accountRepository.findAccountByEmail(oAuth2ProfileDto.getEmail());
        if (account != null) {
            if (account.getProviderId() != null && account.getProvider() != null
                    && (!account.getProvider().equals(provider) || !account.getProviderId().trim().equals(oAuth2ProfileDto.getId().trim()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        account.getProvider() + " account. Please use your " + account.getProvider() +
                        " account to login.");
            }
            account = updateExistingUser(account, oAuth2ProfileDto);
        } else {
            account = registerNewUser(oAuth2ProfileDto, provider);
        }

        return loadUserByUsername(account.getEmail());
    }

    private Account registerNewUser(OAuth2ProfileDto oAuth2ProfileDto, String provider) {
        Account account = new Account();
        Group group = groupRepository.findFirstByKind(EAuctionConstant.ACCOUNT_KIND_USER);
        if (group == null) {
            throw new OAuth2AuthenticationProcessingException("Group not found");
        }
        account.setGroup(group);
        account.setKind(EAuctionConstant.ACCOUNT_KIND_USER);
        account.setProvider(provider);
        account.setProviderId(oAuth2ProfileDto.getId());
        account.setFullName(oAuth2ProfileDto.getName());
        account.setEmail(oAuth2ProfileDto.getEmail());
        account.setAvatarPath(oAuth2ProfileDto.getImageUrl());
        accountRepository.save(account);
        UserProfile userProfile = new UserProfile();
        userProfile.setAccount(account);
        userProfileRepository.save(userProfile);
        return account;
    }

    private Account updateExistingUser(Account existingUser, OAuth2ProfileDto oAuth2ProfileDto) {
        if (existingUser.getProvider() == null && existingUser.getProviderId() == null ) {
            existingUser.setProvider(EAuctionConstant.GRANT_TYPE_GOOGLE);
            existingUser.setProviderId(oAuth2ProfileDto.getId());
        }
        return accountRepository.save(existingUser);
    }
}
