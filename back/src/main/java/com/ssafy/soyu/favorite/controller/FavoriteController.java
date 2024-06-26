package com.ssafy.soyu.favorite.controller;

import static com.ssafy.soyu.util.response.CommonResponseEntity.getResponseEntity;
import static com.ssafy.soyu.util.response.ErrorResponseEntity.toResponseEntity;

import com.ssafy.soyu.favorite.dto.response.FavoriteListResponseDto;
import com.ssafy.soyu.favorite.service.FavoriteService;
import com.ssafy.soyu.util.response.ErrorCode;
import com.ssafy.soyu.util.response.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
@Tag(name = "Favorite 컨트롤러", description = "Favorite API 입니다.")
public class FavoriteController {

  private final FavoriteService favoriteService;

  @PostMapping("/{stationId}")
  @Operation(summary = "즐겨찾기 On & Off", description = "사용자 ID와 스테이션 ID를 이용해 즐겨찾기를 On / Off합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "즐겨찾기 여부 전환 성공"),
      @ApiResponse(responseCode = "400", description = "즐겨찾기 여부 전환 실패")
  })
  public ResponseEntity<?> register(HttpServletRequest request, @PathVariable("stationId") Long stationId) {
    Long memberId = (Long) request.getAttribute("memberId");

    favoriteService.onOffFavorite(memberId, stationId);

    return getResponseEntity(SuccessCode.OK);
  }

  @GetMapping("")
  @Operation(summary = "즐겨찾기 조회", description = "사용자 ID를 이용해 즐겨찾기를 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "즐겨찾기 조회 성공", content = @Content(schema = @Schema(implementation = FavoriteListResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "즐겨찾기 조회 실패")
  })
  public ResponseEntity<?> getList(HttpServletRequest request) {
    Long memberId = (Long) request.getAttribute("memberId");

    List<FavoriteListResponseDto> result = favoriteService.findByMemberId(memberId);
    if (result == null || result.isEmpty()) {
      return toResponseEntity(ErrorCode.FAVORITE_NOT_FOUND);
    }

    return getResponseEntity(SuccessCode.OK, result);
  }
}
