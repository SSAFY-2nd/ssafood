package com.backend.service;

import com.backend.dto.liketo.Liketo;
import com.backend.dto.store.Store;

import java.util.List;

public interface LiketoService {

    void likeFlag(int uid, int store_id, int isLike); // uid, store_id를 활용하여 좋아요 체크

    Liketo findStatus(int uid, int store_id); // uid, store_id를 활용하여 좋아요 상태 조회

    List<Store> findLikedList(int uid); // 회원의 고유 id를 활용해서 Liked된 음식점 리스트 조회

    Liketo findisExist(int uid, int store_id); // liketo 목록 확인

    void insertLike(int uid, int store_id, int isLike); // liketo insert

}
