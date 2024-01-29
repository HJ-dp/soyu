package com.ssafy.soyu.item.service;

import com.ssafy.soyu.chat.entity.Chat;
import com.ssafy.soyu.chat.repository.ChatRepository;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.dto.request.DepositInfoRequest;
import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.item.dto.request.ItemCreateRequest;
import com.ssafy.soyu.item.entity.ItemCategories;
import com.ssafy.soyu.item.entity.ItemStatus;
import com.ssafy.soyu.item.dto.request.ItemStatusRequest;
import com.ssafy.soyu.item.dto.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import com.ssafy.soyu.util.payaction.PayActionProperties;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final ChatRepository chatRepository;
  private final HistoryRepository historyRepository;
  private final PayActionProperties payActionProperties;


  public Item getItem(Long itemId) {
    return itemRepository.findItemById(itemId);
  }

  public List<Item> getItems() {
    return itemRepository.findAll();
  }

  public List<Item> getItemByMemberId(Long memberId) {
    return itemRepository.findItemByMember(memberRepository.getReferenceById(memberId));
  }

  public List<Item> getItemByKeyword(String keyword) {
    return itemRepository.findItemByKeyWord(keyword);
  }

  public List<Item> getItemByCategory(ItemCategories itemCategories) {
    return itemRepository.findItemByItemCategories(itemCategories);
  }

  public void save(Long memberId, ItemCreateRequest itemRequest) {

    Member member = memberRepository.getReferenceById(memberId);

    Item item = new Item(member, itemRequest.getTitle(), itemRequest.getContent(), LocalDateTime.now(), itemRequest.getPrice(), itemRequest.getItemCategories(), ItemStatus.ONLINE);

    itemRepository.save(item);
  }

  public void update(ItemUpdateRequest itemUpdateRequest) {
    // 바꾸려는 아이템 객체를 가져온다
    Item item = itemRepository.getReferenceById(itemUpdateRequest.getItemId());

    // item 의 값을 변경해서 더티체킹을 통한 업데이트를 진행한다
    item.updateItem(itemUpdateRequest.getTitle(), itemUpdateRequest.getContent(), itemUpdateRequest.getPrice(), itemUpdateRequest.getItemCategories());
  }

  public void updateStatus(ItemStatusRequest itemStatusRequest) {
    Item item = itemRepository.getReferenceById(itemStatusRequest.getItemId());

    // 더티 체킹을 통한 upaate
    item.updateItemStatus(itemStatusRequest.getItemStatus());
  }

  @Transactional
  public void makeReserve(Long chatId){
    Chat chat = chatRepository.findById(chatId).get();
    Item item = chat.getItem();
    ItemStatus status = ItemStatus.from("RESERVE");
    itemRepository.updateStatus(item.getId(), status); //아이템 상태 변경
    String today = getCurrentDateTime();
    History history = new History(item, chat.getBuyer());

    //구매내역에 추가
    historyRepository.save(history);

    //주문번호 업데이트
    String orderNumber = today + history.getId();
    historyRepository.updateOrderNumber(history.getId(), orderNumber);

    //payAction API
    String orderUri = payActionProperties.getOrderUri();
    WebClient webClient = WebClient.create(orderUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", orderNumber); // 주문 번호 수정 필요
    parameters.add("order_amount", item.getPrice().toString());
    parameters.add("order_date", today);
    parameters.add("orderer_name", chat.getBuyer().getName());
    parameters.add("orderer_phone_number", chat.getBuyer().getMobile());
    parameters.add("orderer_email", chat.getBuyer().getEmail());
    parameters.add("billing_name", chat.getBuyer().getName());

    //payAction에
    webClient.post()
            .uri(orderUri)
            .bodyValue(parameters)
            .retrieve()
            .bodyToMono(String.class)
            .block();
  }

  public String getCurrentDateTime() {
    LocalDateTime now = LocalDateTime.now();
    ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    return zonedDateTime.format(formatter);
  }

  public void deleteReserve(Long historyId) {
    History history = historyRepository.findById(historyId).get();
    ItemStatus status = ItemStatus.from("ONLINE");
    itemRepository.updateStatus(history.getItem().getId(), status); //아이템 상태 변경

    //histroy 변경
    historyRepository.updateIsDelete(historyId);

    //payAction 매칭 취소
    String orderExcludeUri = payActionProperties.getOrderExcludeUri();
    WebClient webClient = WebClient.create(orderExcludeUri);
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apikey", payActionProperties.getApiKey());
    parameters.add("secretkey", payActionProperties.getSecretKey());
    parameters.add("mall_id", payActionProperties.getStoreId());
    parameters.add("order_number", history.getOrder_number());

    webClient.post()
            .uri(orderExcludeUri)
            .bodyValue(parameters)
            .retrieve()
            .bodyToMono(String.class)
            .block();
  }

  public void depositMoney(DepositInfoRequest depositInfoRequest) {
    Long historyId = Long.parseLong(depositInfoRequest.getOrder_number().substring(25));
    History history = historyRepository.findById(historyId).get();
    Item item = history.getItem();

    // 아이템 판매 완료
    itemRepository.updateStatus(item.getId(), ItemStatus.SOLD);
  }
}