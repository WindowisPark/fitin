package com.fitin.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitin.auth.entity.Member;
import com.fitin.auth.repository.MemberRepository;
import com.fitin.profile.dto.NotificationSettingsDto;
import com.fitin.profile.dto.ProfileDto;
import com.fitin.profile.entity.NotificationSettings;
import com.fitin.profile.entity.Profile;
import com.fitin.profile.repository.NotificationSettingsRepository;
import com.fitin.profile.repository.ProfileRepository;
import com.fitin.shopping.dto.CartDetailDto;
import com.fitin.shopping.dto.OrderHistoryDto;
import com.fitin.shopping.repository.CartRepository;
import com.fitin.shopping.service.CartService;
import com.fitin.shopping.service.OrderService;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class ProfileService {

    private final MemberRepository memberRepository;
    private final NotificationSettingsRepository notificationSettingsRepository;
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderService orderService;

    public ProfileService(
            ProfileRepository profileRepository,
            MemberRepository memberRepository,
            NotificationSettingsRepository notificationSettingsRepository,
            CartRepository cartRepository,
            CartService cartService,
            OrderService orderService) {
        this.profileRepository = profileRepository;
        this.memberRepository = memberRepository;
        this.notificationSettingsRepository = notificationSettingsRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.orderService = orderService;
    }
    @Transactional
    public ProfileDto createProfile(ProfileDto profileDto) {
        if (profileRepository.existsByNickname(profileDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        Profile profile = new Profile();
        profile.setNickname(profileDto.getNickname());
        // 필요한 다른 필드들 설정
        profile = profileRepository.save(profile);
        return convertToDto(profile);
    }
    @Transactional
    public ProfileDto createProfile(String email, ProfileDto profileDto) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        
        if (profileRepository.existsByNickname(profileDto.getNickname())) {
            throw new IllegalArgumentException("Nickname already exists");
        }

        Profile profile = new Profile();
        profile.setMember(member);
        profile.setNickname(profileDto.getNickname());
        // 기타 필드 설정...

        profile = profileRepository.save(profile);
        return convertToDto(profile);
    }
    @Transactional(readOnly = true)
    public ProfileDto getProfile(String email, Long id) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found with email: " + email));
        Profile profile = profileRepository.findByMemberAndId(member, id)
            .orElseThrow(() -> new EntityNotFoundException("Profile not found for member with email: " + email));
        return convertToDto(profile);
    }

    @Transactional
    public ProfileDto updateProfile(String email, Long id, ProfileDto profileDto) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found with email: " + email));
        Profile profile = profileRepository.findByMemberAndId(member, id)
            .orElseThrow(() -> new EntityNotFoundException("Profile not found for member with email: " + email));
        
        if (!profile.getNickname().equals(profileDto.getNickname()) &&
            profileRepository.existsByNickname(profileDto.getNickname())) {
            throw new IllegalArgumentException("Nickname already exists");
        }
        profile.setNickname(profileDto.getNickname());
        // 필요한 다른 필드들 업데이트
        profile = profileRepository.save(profile);
        return convertToDto(profile);
    }
    
    private ProfileDto convertToDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setNickname(profile.getNickname());
        // 필요한 다른 필드들 설정
        return dto;
    }
    
    @Transactional
    public NotificationSettingsDto updateNotificationSettings(String email, Long profileId, NotificationSettingsDto settingsDto) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found with email: " + email));
        Profile profile = profileRepository.findByMemberAndId(member, profileId)
            .orElseThrow(() -> new EntityNotFoundException("Profile not found for member with email: " + email));

        NotificationSettings settings = profile.getNotificationSettings();
        if (settings == null) {
            settings = new NotificationSettings();
            profile.setNotificationSettings(settings);
        }

        settings.setEmailNotifications(settingsDto.getEmailNotifications());
        settings.setPushNotifications(settingsDto.getPushNotifications());
        settings.setNewFollowerNotifications(settingsDto.getNewFollowerNotifications());
        settings.setNewMessageNotifications(settingsDto.getNewMessageNotifications());

        notificationSettingsRepository.save(settings);

        return convertToDto(settings);
    }

    private NotificationSettingsDto convertToDto(NotificationSettings settings) {
        NotificationSettingsDto dto = new NotificationSettingsDto();
        dto.setEmailNotifications(settings.isEmailNotifications());
        dto.setPushNotifications(settings.isPushNotifications());
        dto.setNewFollowerNotifications(settings.isNewFollowerNotifications());
        dto.setNewMessageNotifications(settings.isNewMessageNotifications());
        return dto;
    }
    @Transactional(readOnly = true)
    public CartDetailDto getCartSummary(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found with email: " + email));
        try {
            Long cartId = findCartIdByMember(member);
            return cartService.getCartDetails(cartId);
        } catch (EntityNotFoundException e) {
            return new CartDetailDto(null, Collections.emptyList(), BigDecimal.ZERO);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderHistoryDto> getOrderHistory(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Member not found with email: " + email));
        return orderService.getOrderHistory(member.getId());
    }


    @Transactional(readOnly = true)
    public CartDetailDto getCartDetails(Long memberId) {
        return cartService.getCartDetails(memberId);
    }
    
    private Long findCartIdByMember(Member member) {
        return cartRepository.findByMember(member)
            .orElseThrow(() -> new EntityNotFoundException("Cart not found for member: " + member.getEmail()))
            .getId();
    }
}