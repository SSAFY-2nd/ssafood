package com.backend.mapper;

import com.backend.dto.liketo.Liketo;
import com.backend.dto.store.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LiketoMapper {
    void likeFlag(int uid, int store_id, int isLike); // uid, store_id를 활용하여 좋아요 체크

    Liketo findStatus(int uid, int store_id); // uid, store_id를 활용하여 좋아요 상태 조회

    List<Store> findLikedList(int uid); // 회원의 고유 id를 활용해서 Liked된 음식점 리스트 조회
}
