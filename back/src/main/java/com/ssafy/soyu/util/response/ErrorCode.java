package com.ssafy.soyu.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 예시 필요한 것 추가해서 사용*/
    TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "전달할 메시지"),

    EMPTY_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "request 값이 비어있습니다."),

    //회원 관련 예외
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    EXPIRED_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    NOT_FOUND_AUTH_TOKEN(HttpStatus.BAD_REQUEST, "토큰 정보가 없습니다."),
    NAVER_ERROR(HttpStatus.BAD_REQUEST, "네이버 오류입니다."),
    UNAUTHORIZED_FUNCTION_ACCESS(HttpStatus.UNAUTHORIZED, "로그인 후 이용할 수 있습니다."),
    INVALID_AUTH_CODE(HttpStatus.NOT_FOUND, "인증코드가 일치하지 않습니다."),
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),
    NON_MEMBER_ACCESS(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."),
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "등록된 계좌정보가 없습니다."),
    HAS_ACTIVE_ITEM(HttpStatus.CONFLICT, "거래중인 물품이 존재합니다."),
    DUPLICATE_USER_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다."),
    ITEM_NOT_ONLINE(HttpStatus.UNAUTHORIZED, "온라인으로 판매중인 물건이 아닙니다."),

    INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "입력값을 확인하세요"),
    STATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 정보의 스테이션을 찾을 수 없습니다."),
    FAVORITE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 정보의 즐겨찾기를 찾을 수 없습니다."),


    //알림 관련 예외
    NOT_MATCH_NOTICE(HttpStatus.BAD_REQUEST, "해당하는 정보가 유저의 알림이 아닙니다."),

    //보관함 관련 예외
    IN_USE_LOCKER(HttpStatus.UNAUTHORIZED, "이미 사용 중인 보관함입니다."),
    ALREADY_FAVORITE_STATION(HttpStatus.BAD_REQUEST, "이미 즐겨찾기 된 스테이션입니다."),
    NOT_DP_ITEM(HttpStatus.BAD_REQUEST, "DP 판매중인 물건아닙니다. 거래 예약 구매자인 경우 거래 코드를 입력하세요"),
    EMPTY_ITEM_LOCKER(HttpStatus.BAD_REQUEST, "DP 판매중인 물건이 없는 보관함입니다"),

    //아이템 관련 예외
    NO_RESULT_ITEM(HttpStatus.NOT_FOUND, "결과에 해당하는 Item 이 없습니다."),
    NO_MATCH_CATEGORY(HttpStatus.BAD_REQUEST, "매칭되는 CATEGORY 가 존재하지 않습니다."),
    IMPOSSIBLE_ITEM_DP(HttpStatus.BAD_REQUEST, "해당 Item은 아직 DP 조건이 충족되지 않았습니다."),
    NO_HAVE_IMAGE(HttpStatus.BAD_REQUEST, "이미지를 같이 로드 해주세요 ."),
    NOT_IN_LOCKER(HttpStatus.BAD_REQUEST, "락커에 보관된 물건이 아닙니다."),
    NO_HAVE_ACCOUNT(HttpStatus.NOT_FOUND, "계좌 등록후 물품 등록을 진행하세요"),
    CANT_DELETE_RESERVED_ITEM(HttpStatus.BAD_REQUEST, "거래 약속된 아이템을 삭제할수 없습니다."),
    ALREADY_RESERVED_ITEM(HttpStatus.BAD_REQUEST, "이미 거래가 약속된 물건입니다."),

    //내역 관련 예외
    NO_MATCH_HISTORY(HttpStatus.BAD_REQUEST, "해당하는 정보의 내역을 찾을 수 없습니다."),
    NOT_READY_YET(HttpStatus.BAD_REQUEST, "아직 물품이 스테이션에 도착하지 않았습니다."),
    IS_NOT_YOURS(HttpStatus.BAD_REQUEST, "Item과 사용자의 정보가 일치하지 않습니다."),
    IMPOSSIBLE_WITHDRAW(HttpStatus.BAD_REQUEST, "회수 할 수 없는 상태입니다."),
    ALREADY_REGISTER_TOKEN(HttpStatus.BAD_REQUEST, "이미 등록된 토큰입니다."),
    IS_NOT_TRADE_RESERVE(HttpStatus.BAD_REQUEST, "TRADE_RESERVE 상태가 아닙니다."),
    IS_NOT_WITHDRAW(HttpStatus.BAD_REQUEST, "WITHDRAW 상태가 아닙니다."),
    IS_NOT_DP_RESERVE(HttpStatus.BAD_REQUEST, "DP_RESERVE 상태가 아닙니다.");

    private final HttpStatus httpStatus;

    private final String message;
}
