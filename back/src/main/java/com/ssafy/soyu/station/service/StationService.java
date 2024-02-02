package com.ssafy.soyu.station.service;

import com.ssafy.soyu.favorite.repository.FavoriteRepository;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.likes.service.LikesService;
import com.ssafy.soyu.locker.entity.Locker;
import com.ssafy.soyu.locker.repository.LockerRepository;
import com.ssafy.soyu.station.dto.response.FindResponseDto;
import com.ssafy.soyu.station.entity.Station;
import com.ssafy.soyu.station.dto.response.DetailResponseDto;
import com.ssafy.soyu.station.dto.response.ListResponseDto;
import com.ssafy.soyu.station.repository.StationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {

  private final StationRepository stationRepository;
  private final LockerRepository lockerRepository;
  private final LikesService likesService;
  private final FavoriteRepository favoriteRepository;

  public List<ListResponseDto> findAllStation(Long memberId) {
    return stationRepository.findAllWithMemberId(memberId)
        .stream()
        .map(o -> {
          Station s = (Station) o[0];
          boolean isFavorite = (Boolean) o[1];
          Integer count = lockerRepository.countNotEmptyLocker(s.getId());
          return new ListResponseDto(s, count, isFavorite);
        })
        .collect(Collectors.toList());
  }

  public List<DetailResponseDto> findOneStation(Long memberId, Long stationId) {
    return stationRepository.findOneWithMemberId(memberId, stationId)
        .stream()
        .map(o -> {
          Station s = (Station) o[0];
          List<FindResponseDto> ls =
              s.getLockers()
                  .stream()
                  .map(l ->{
                    if(l.getItem() == null)
                      return new FindResponseDto(l);
                    return new FindResponseDto(l, likesService.getByMemberWithItem(memberId, l.getItem().getId()));
                  })
                  .collect(Collectors.toList());
          boolean isFavorite = (Boolean) o[1];
          return new DetailResponseDto(s, ls, isFavorite);
        })
        .collect(Collectors.toList());
  }

}
