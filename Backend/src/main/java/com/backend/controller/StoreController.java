package com.backend.controller;


import com.backend.dto.bhour.Bhour;
import com.backend.dto.review.Review;
import com.backend.dto.store.Store;
import com.backend.dto.store.StoreDetail;
import com.backend.service.BhourService;
import com.backend.service.ReviewService;
import com.backend.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class StoreController {

    @Autowired
    StoreService storeService;

    @Autowired
    BhourService bhourService;

    @Autowired
    ReviewService reviewService;


    /**
     * @param id : 음식점 고유 번호로 음식점리스트 검색( 음식점 이름과 주소 )
     * @return 상점 List 검색 없으면 null
     */
    @ApiOperation(value = "검색 : 음식점 id", notes = "음식점의 id를 이용하여 리스트 검색")
    @GetMapping("/api/v1/list/{id}")
    public List<Store> findListById(@PathVariable List<String> id) {

        List<Store> list = new ArrayList<Store>();
        for(int i=0; i<id.size(); i++){
            Long uid = Long.parseLong(id.get(i));
            
            // 상점명과 위치만 받기
            Store store = storeService.findById(uid);
            String storeName = store.getName();
            String storeAddress = store.getAddress();
            list.add(new Store(storeName, storeAddress));

        }
        return list;
    }

    /**
     *
     * @param id
     * @return 음식점 상세정보를 리턴
     */
    @ApiOperation(value = "검색 : 음식점 고유 id", notes = "음식점 고유 id를 통해 음식점 상세 정보 검색")
    @GetMapping("/api/v1/detail/{id}")
    public StoreDetail findDetailById(@PathVariable Long id) {

        StoreDetail details = new StoreDetail();

        Store store = storeService.findById(id);

        // Store
        details.setStore_id(store.getStore_id());
        details.setName(store.getName());
        details.setBranch(store.getBranch());
        details.setArea(store.getArea());
        details.setTel(store.getTel());
        details.setAddress(store.getAddress());
        details.setLatitude(store.getLatitude());
        details.setLongtitude(store.getLongtitude());

        // 카테고리 문자열처리
        String category = store.getCategory();
        String[] splitCate = category.split("\\|");
        List<String> categoryList = new ArrayList<>();
        for(int i=0; i<splitCate.length; i++){
            categoryList.add(splitCate[i]);
        }
        details.setCategory(categoryList);

        // 메뉴 문자열 처리
        String menu = store.getMenu();
        String[] splitMenu = menu.split("\\|");
        List<String> menuList = new ArrayList<>();
        for(int i=0; i<splitMenu.length; i++){
            menuList.add(splitMenu[i]);
        }
        details.setMenu(menuList);


        // Bhour
        List<Bhour> bhourList = bhourService.findById(id);
        String workingInfo;
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<bhourList.size(); i++){
            // type
            if(bhourList.get(i).getType() == 1) {
                sb.append("영업시간").append(" ");
            }
            else if(bhourList.get(i).getType() == 2){
                sb.append("브레이크 타임").append(" ");
            }else if(bhourList.get(i).getType() == 3){
                sb.append("휴무일").append(" ");
            }

            // weekType
            if(bhourList.get(i).getWeek_type() == 1){
                sb.append("매주").append(" ");
            }
            else if(bhourList.get(i).getWeek_type() == 2){
                sb.append("첫째 주").append(" ");
            }
            else if(bhourList.get(i).getWeek_type() == 2){
                sb.append("둘째 주").append(" ");
            }
            else if(bhourList.get(i).getWeek_type() == 2){
                sb.append("셋째 주").append(" ");
            }
            else if(bhourList.get(i).getWeek_type() == 2){
                sb.append("넷째 주").append(" ");
            }
            else if(bhourList.get(i).getWeek_type() == 2){
                sb.append("공휴일").append(" ");
            }

            // Day
            if(bhourList.get(i).getMon() == 1){
                sb.append("월").append(" ");
            }else if(bhourList.get(i).getTue() == 1){
                sb.append("화").append(" ");
            }else if(bhourList.get(i).getWed() == 1){
                sb.append("수").append(" ");
            }else if(bhourList.get(i).getThu() == 1){
                sb.append("목").append(" ");
            }else if(bhourList.get(i).getFri() == 1){
                sb.append("금").append(" ");
            }else if(bhourList.get(i).getSat() == 1){
                sb.append("토").append(" ");
            }else if(bhourList.get(i).getSun() == 1){
                sb.append("일").append(" ");
            }

            // 근무시간
            sb.append(bhourList.get(i).getStart_time()).append(" ~ ");
            sb.append(bhourList.get(i).getEnd_time());
            sb.append("\n").append(bhourList.get(i).getEtc());

        }

        details.setWorkingDay(sb.toString());

        // Review
        List<Review> reviewList = reviewService.findById(id);
        details.setReviewList(reviewList);

        return details;
    }

}
