package com.fitin.profile.entity;


import java.util.Set;

import com.fitin.auth.entity.Member;
import com.fitin.shopping.entity.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member member;
    
    @OneToMany(mappedBy = "profile")
    private Set<Order> orders;
    
    @Column(nullable = false, unique = true)
    private String nickname;

    @OneToMany(mappedBy = "follower")
    private Set<Follow> following;

    @OneToMany(mappedBy = "followed")
    private Set<Follow> followers;

    // 게시글 관련 필드 (커뮤니티 기능 구현 시 추가)
    // private Set<Post> posts;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private NotificationSettings notificationSettings;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Set<Follow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Follow> following) {
        this.following = following;
    }

    public Set<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Follow> followers) {
        this.followers = followers;
    }

    public NotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(NotificationSettings notificationSettings) {
        this.notificationSettings = notificationSettings;
        notificationSettings.setProfile(this);
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}