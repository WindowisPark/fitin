package com.fitin.profile.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitin.auth.util.JwtTokenProvider;
import com.fitin.profile.dto.NotificationSettingsDto;
import com.fitin.profile.dto.ProfileDto;
import com.fitin.profile.service.ProfileService;
import com.fitin.shopping.dto.CartDetailDto;
import com.fitin.shopping.dto.OrderHistoryDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final JwtTokenProvider jwtTokenProvider;

    public ProfileController(ProfileService profileService, JwtTokenProvider jwtTokenProvider) {
        this.profileService = profileService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<ProfileDto> createProfile(@Valid @RequestBody ProfileDto profileDto, Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        ProfileDto createdProfile = profileService.createProfile(email, profileDto);
        return ResponseEntity.ok(createdProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("id") Long id, Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        ProfileDto profile = profileService.getProfile(email, id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("id") Long id, @Valid @RequestBody ProfileDto profileDto, Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        ProfileDto updatedProfile = profileService.updateProfile(email, id, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
    
    @PutMapping("/{profileId}/notification-settings")
    public ResponseEntity<NotificationSettingsDto> updateNotificationSettings(
            @PathVariable("profileId") Long profileId,
            @Valid @RequestBody NotificationSettingsDto settingsDto,
            Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        NotificationSettingsDto updatedSettings = profileService.updateNotificationSettings(email, profileId, settingsDto);
        return ResponseEntity.ok(updatedSettings);
    }
    
    @GetMapping("/cart")
    public ResponseEntity<CartDetailDto> getCartSummary(Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        CartDetailDto cartDetail = profileService.getCartSummary(email);
        return ResponseEntity.ok(cartDetail);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderHistoryDto>> getOrderHistory(Authentication authentication) {
        String email = getEmailFromAuthentication(authentication);
        List<OrderHistoryDto> orderHistory = profileService.getOrderHistory(email);
        return ResponseEntity.ok(orderHistory);
    }
    
    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication information is missing");
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }
        
        throw new IllegalStateException("Unexpected authentication principal type: " + principal.getClass());
    }
}