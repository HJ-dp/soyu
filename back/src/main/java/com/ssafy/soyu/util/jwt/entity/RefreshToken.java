package com.ssafy.soyu.util.jwt.entity;

import com.ssafy.soyu.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private Member member;

    public static RefreshToken createRefreshToken(Member member, String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.member = member;
        refreshToken.token = token;
        return refreshToken;
    }

    public RefreshToken updateRefreshToken(String refreshToken){
        this.token = refreshToken;
        return this;
    }

}