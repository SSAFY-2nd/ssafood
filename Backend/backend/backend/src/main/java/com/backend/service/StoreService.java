package com.backend.service;

import com.backend.dto.store.Store;
import com.backend.dto.user.User;

public interface StoreService {

    public void create(Store c); // store 생성
    public void read(String id); // store 읽기
    public void delete(String id); // store 삭제
    public void update(Store c); // store 정보 수정


}
