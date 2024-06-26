package com.ssafy.soyu.item.entity;

import com.ssafy.soyu.image.entity.Image;
import com.ssafy.soyu.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "image"})
public class Item {
  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Setter
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
  List<Image> image;

  private String title;

  private String content;

  private LocalDateTime regDate;

  private Integer price;
  private Integer discountPrice;

  @Enumerated(EnumType.STRING)
  private ItemStatus itemStatus;

  @Enumerated(EnumType.STRING)
  private ItemCategories itemCategories;

  private String orderNumber;

  public Item(Member member, String title, String content, LocalDateTime regDate, Integer price, ItemCategories itemCategories, ItemStatus itemStatus ) {
    this.member = member;
    this.title = title;
    this.content = content;
    this.regDate = regDate;
    this.price = price;
    this.itemCategories = itemCategories;
    this.itemStatus = itemStatus;
  }

  static public Item createItem(Member member, String title, String content, LocalDateTime regDate, Integer price, ItemCategories itemCategories, ItemStatus itemStatus) {
    Item item = new Item();

    item.member = member;
    item.title = title;
    item.content = content;
    item.regDate = regDate;
    item.price = price;
    item.itemCategories = itemCategories;
    item.itemStatus = itemStatus;
    return item;
  }

  public void updateItem(String title, String content, Integer price, ItemCategories itemCategories ) {
    this.title = title;
    this.content = content;
    this.price = price;
    this.itemCategories = itemCategories;
  }

  public void updateItemStatus(ItemStatus itemStatus) {
    this.itemStatus = itemStatus;
  }

}
