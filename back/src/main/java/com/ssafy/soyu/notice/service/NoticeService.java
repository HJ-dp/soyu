package com.ssafy.soyu.notice.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.soyu.history.dto.response.PurchaseResponseDto;
import com.ssafy.soyu.member.entity.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.notice.entity.Notice;
import com.ssafy.soyu.notice.dto.request.NoticeRequestDto;
import com.ssafy.soyu.notice.dto.response.NoticeResponseDto;
import com.ssafy.soyu.notice.repository.NoticeRepository;
import com.ssafy.soyu.util.fcm.entity.Fcm;
import com.ssafy.soyu.util.fcm.dto.FcmMessage;
import com.ssafy.soyu.util.fcm.repository.FcmRepository;
import com.ssafy.soyu.util.fcm.service.FcmService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.exception.CustomException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final MemberRepository memberRepository;
  private final FcmRepository fcmRepository;
  private final FcmService fcmService;

  /**
   * 알림 생성 및 전송<br/>
   *
   * @param receiverId       알림을 받을 유저의 식별자
   * @param noticeRequestDto 알림 정보를 가진 객체
   */
  @Transactional
  public void createNotice(Long receiverId, NoticeRequestDto noticeRequestDto) {
    // 알림 생성하기
    Member receiver = memberRepository.findMemberById(receiverId);
    Notice notice = new Notice(receiver, noticeRequestDto);
    noticeRepository.save(notice);

    sendNotice(receiverId, notice);
  }

  @Transactional
  public void createNoticeWithSender(Long receiverId, Long senderId,
      NoticeRequestDto noticeRequestDto) {
    // 알림 생성하기
    Member receiver = memberRepository.findMemberById(receiverId);
    Member sender = memberRepository.findMemberById(senderId);
    Notice notice = new Notice(receiver, sender, noticeRequestDto);
    noticeRepository.save(notice);

    sendNotice(receiverId, notice);
  }

  public void sendNotice(Long receiverId, Notice notice) {
    // 알림 전송을 위한 토큰 조회
    // 유저의 로그인 기기 개수에 따라 Token의 개수가 달라지므로 List 형식으로 조회
    List<Fcm> fcmList = fcmRepository.findByMemberId(receiverId);
    fcmList.forEach(fcm -> {
          FcmMessage fcmMessage = FcmMessage.builder()
              .token(fcm.getToken())
              .title(notice.getTitle())
              .content(notice.getContent())
              .build();
          try {
            fcmService.sendNotice(fcmMessage);
          } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public List<NoticeResponseDto> findNotice(Long memberId) {
    return noticeRepository.findByMemberId(memberId)
        .stream()
        .map(NoticeResponseDto::new)
        .sorted(Comparator.comparing(NoticeResponseDto::getStatus))
        .collect(Collectors.toList());
  }

  @Modifying
  @Transactional
  public void readNotice(Long noticeId) {
    noticeRepository.readNoticeByNoticeId(noticeId);
  }

  @Modifying
  @Transactional
  public void deleteNotice(Long noticeId) {
    noticeRepository.deleteNoticeByNoticeId(noticeId);
  }

  public void checkNotice(Long memberId, Long noticeId) {
    if (noticeRepository.checkNoticeMatchMember(memberId, noticeId) == null) {
      throw new CustomException(ErrorCode.NOT_MATCH_NOTICE);
    }
  }
}
