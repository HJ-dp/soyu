import API, { ImgAPI } from './config';

export const makeChat = ({ itemId, buyerId, sellerId }) =>
  API.post('/chat', {
    itemId,
    buyerId,
    sellerId,
  });

export const getRooms = () => API.get('/chats');

export const getChats = (roomId) => API.get(`/chat/${roomId}`);

/* 로그인 & 로그아웃 */

/** 네이버 소셜 로그인 */
export const getNaverCode = (code, state) =>
  API.post('/naver/login', {
    authorizationCode: `${code}`,
    state: `${state}`,
  });
/** 로그아웃 */
export const postLogout = () => API.post('/member');

/** 마이페이지 정보 불러오기 */
export const getMine = () => API.get(`/member`);

/** 닉네임 수정 */
export const patchNickname = (nickname) =>
  API.patch(`/member/nickname`, { nickName: `${nickname}` });

/** 계좌 조회 */
export const getAccount = () => API.get(`/member/account`);

/** 계좌 변경 */
export const patchAccount = (a, b) =>
  API.patch(`/member/account`, {
    bankName: a,
    accountNumber: b,
  });

/** 계좌 삭제 */
export const deleteAccount = () => API.delete(`/member/account`);

/** 회원 탈퇴 */
export const deleteMember = () => API.delete(`/member`);

/* CRUD */

/** 아이템 리스트 불러오기 */
export const getItemList = () => API.get('/item/items');

/** 판매내역 리스트 불러오기 */
export const getHistoryList = () => API.get('/history/sale');

/** 구매내역 리스트 불러오기 */
export const getPurchaseHistoryList = () => API.get('/history/purchase');

/** 아이템 상세정보 불러오기 */
export const getItem = (item) => API.get(`/item/${item}`);

/** 키워드로 검색 */
export const getKeyword = (keyword) =>
  API.post(`/item/keyword`, { keyword: `${keyword}` });

/** 카테고리별 등록물품 조회하기 */
export const getCategory = (category) => API.get(`/item/category/${category}`);

/** 아이템 아이디로 상태 변경하기 */
export const updateStatus = () =>
  API.put('/item/status', {
    itemId: `6`,
    itemStatus: `WITHDRAW`,
  });

/** 아이템 상태 변경 */
export const deleteItemStatus = (itemId) =>
  API.put('/item/status', {
    itemId,
    itemStatus: 'DELETED',
  });

/* 이미지 */

/** 이미지 불러오기 */
export const loadImg = (folder, file) =>
  API.get(`image/${folder}/${file}`, {
    responseType: 'blob',
  });

/** 이미지 업로드 */
export const postImg = (data, img) => {
  const formData = new FormData();
  Array.from(img).forEach((item) => {
    formData.append('image', item);
  });
  formData.append(
    'itemCreateRequest',
    new Blob(
      [
        JSON.stringify({
          title: data.title,
          content: data.content,
          price: data.price,
          itemCategories: data.itemCategories,
        }),
      ],
      { type: 'application/json' },
    ),
  );
  return ImgAPI.post('item', formData);
};

/* 좋아요 */

/** 찜 리스트 조회 */
export const getLikes = () => API.get(`/likes`);

/** 찜 On Off */
export const getLikeOnOff = (itemId) => API.post(`/likes/${itemId}`);

/* 키오스크 */

/** 키오스크 회수 코드 확인 */
export const kioskWithdraw = (code) => API.get(`/kiosk/withdraw/${code}`);

/**  키오스크 DP/거래예약 보관시작 코드 확인 */
export const kioskSell = (code) => API.get(`/kiosk/sell/${code}`);
/** 키오스크 회수 코드 확인 */
export const kioskBuy = (stationId, code) =>
  API.get(`/kiosk/buy/${stationId}/${code}`);
/** 키오스크 물건 구매 결정시에만  */
export const kioskMakePurchase = () => API.get(`/kiosk/dp`);

/* 라커 */

/** lockerId, itemI로 보관함 예약 */
export const makeReservation = ({ lockerId, itemId }) =>
  API.post(`/locker/dp`, {
    lockerId,
    itemId,
  });

/** 보관함 상태 확인 */
export const getLockerStatus = (lockerId) => API.get(`/locker/${lockerId}`);

/** 거래 예약 물품 DP 전환 */
export const patchChangeDp = (itemId) => API.patch(`/locker/${itemId}`);

/** 오프라인 물품 DP 취소 */
export const postWithdraw = (itemId) => API.post(`/locker/${itemId}`);

/* 알림 */

/** 로그인시 디바이스토큰 등록 */
export const postFcm = (fcm) => API.post(`/fcm`, { token: `${fcm}` });

/** 알림 내역 조회 */
export const getNotice = () => API.get(`/notice`);

/* 스테이션 */

/** 스테이션 리스트 조회 */
export const getStation = () => API.get(`/station`);

/** 스테이션 즐겨찾기 On / Off */
export const favoriteOnOff = (stationId) => API.post(`/favorite/${stationId}`);

/** 즐겨찾기한 스테이션 조회 */
export const getFavorite = () => API.get(`/favorite`);

/* 거래 */

/** 판매자의 거래예약 코드 조회 */
export const getSellerCode = (itemId) => API.get(`/trade/sale/code/${itemId}`);

/** 구매자의 거래예약 코드 조회 */
export const getPurchaseCode = (itemId) =>
  API.get(`/trade/purchase/code/${itemId}`);

/** 입금 매칭 */
export const postMatch = ({
  mallId,
  orderNumber,
  orderStatus,
  processingDate,
}) =>
  API.post(`/trade/match`, {
    mall_id: { mallId },
    order_number: { orderNumber },
    order_status: { orderStatus },
    processing_date: { processingDate },
  });

/** 판매자가 거래 취소 */
export const deleteSale = (itemId) => API.delete(`/trade/sale/${itemId}`);

/** 구매자가 거래 취소 */
export const deleteReservation = (historyId) =>
  API.delete(`/trade/code/${historyId}`);
/** 스테이션 상세 조회 */
export const getStationDetail = (stationId) => API.get(`/station/${stationId}`);

/* 거래 */

/** 거래 약속 잡기 */
export const makeAppointment = (body) => API.post(`/trade/reserve`, body);

/** 거래 물품 구매 결정 */
export const makePurchase = (buy, itemId) =>
  API.get(`/kiosk/reserve`, {
    params: { isBuy: `${buy}`, itemId: `${itemId}` },
  });
