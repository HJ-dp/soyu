package com.ssafy.soyu.util.fcm.entity;

import com.ssafy.soyu.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fcm")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fcm {

  @Id
  @GeneratedValue
  @Column(name = "fcm_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name="member_id")
  private Member member;

  private String token;

  public Fcm(Member member, String token){
    this.member = member;
    this.token = token;
  }

  public static Fcm createFcm(Member member, String token){
    Fcm fcm = new Fcm(member, token);
    return fcm;
  }
}
