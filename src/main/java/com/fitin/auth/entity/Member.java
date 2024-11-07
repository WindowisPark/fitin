package com.fitin.auth.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fitin.community.common.model.CommunityPost;
import com.fitin.profile.entity.Profile;
import com.fitin.shopping.entity.Cart;
import com.fitin.shopping.entity.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String membername;
    
    @Column(nullable = false, unique = true)
    private String email;   
    
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private Double height; // 키 (cm)

    @Column(nullable = false)
    private Double weight; // 체중 (kg)
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role = MemberRole.MEMBER;
    
    @Column(nullable = true)
    private LocalDateTime lastPasswordResetDate;

    @Column(nullable = false)
    private int passwordResetCount = 0;
    
    @OneToOne(mappedBy = "member")
    private Profile profile;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;
    
    // UserDetails 인터페이스 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public void incrementPasswordResetCount() {
        this.passwordResetCount++;
        this.lastPasswordResetDate = LocalDateTime.now();
    }
    
    // Cart와의 관계를 OneToMany로 수정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();
    // 현재 사용중인 카트 가져오기
    public Cart getCart() {
        if (this.carts.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setMember(this);
            this.carts.add(newCart);
        }
        // 가장 최근 카트 반환
        return this.carts.get(this.carts.size() - 1);
    }

    // 카트 추가
    public void addCart(Cart cart) {
        this.carts.add(cart);
        cart.setMember(this);
    }
    
    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<CommunityPost> posts;

}