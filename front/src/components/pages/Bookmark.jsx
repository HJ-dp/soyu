import styled from 'styled-components';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import BottomNav from '../molecules/BottomNav';
import useManageTab from '../../hooks/useManageTab';
import Station from '../molecules/Station';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import useBookmark from '../../hooks/useBookmark';
import useLike from '../../hooks/useLike';
import ItemList from '../molecules/ItemList';

function Bookmark() {
  const { heart } = useParams();
  const [state, Handler] = useManageTab();
  const data = useBookmark();
  const likes = useLike();
  useEffect(() => {
    Handler(heart === 'heart' ? heart : 'bookmark');
  }, []);
  return (
    <>
      <LocalHeader>
        <BackBtn />내 소유
        <div />
      </LocalHeader>
      <SBookmarkTap>
        <SBookmark onClick={() => Handler('bookmark')} current={state}>
          북마크
        </SBookmark>
        <SHeart onClick={() => Handler('heart')} current={state}>
          찜
        </SHeart>
      </SBookmarkTap>
      <MainContainerWithNav>
        {state === 'heart' ? (
          <ItemList data={likes} />
        ) : (
          data &&
          data.map((item) => item && <Station key={item?.itemId} data={item} />)
        )}
      </MainContainerWithNav>
      <BottomNav />
    </>
  );
}

export default Bookmark;

const SBookmarkTap = styled.nav`
  width: 100%;
  padding-top: 44px;
  @media screen and (min-width: 769px) {
    max-width: 1024px;
    margin: 0 auto;
  }
`;

const SButton = styled.button`
  width: 50%;
  padding: 10px;
`;

const SBookmark = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'bookmark' ? '1px solid #4827E9' : ''};
`;

const SHeart = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'heart' ? '1px solid #4827E9' : ''};
`;
