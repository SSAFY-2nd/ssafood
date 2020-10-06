package com.backend.controller;


import com.backend.dto.bhour.Bhour;
import com.backend.dto.review.Review;
import com.backend.dto.store.Store;
import com.backend.dto.store.StoreDetail;
import com.backend.dto.store.StoreLength;
import com.backend.dto.store.StoreLocation;
import com.backend.dto.user.User;
import com.backend.service.BhourService;
import com.backend.service.ReviewService;
import com.backend.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
     * @param 
     * @return 상점 100개 List 조회 없으면 null
     */
    @ApiOperation(value = "검색 : None", notes = "상점 100개 List 조회 ")
    @GetMapping("/api/v1/allStore")
    public List<Store> AllStore() {

        return storeService.findAllStore();
    }


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

    /**
     * @param keyword : 지역명(위치), 음식점명, 음식이름으로 음식점리스트 검색( 음식점 이름과 주소 )
     * @return 상점 List 검색 없으면 null
     */
    @ApiOperation(value = "검색 : 지역, 음식점명, 음식", notes = "지역, 음식점명, 음식이름 검색")
    @GetMapping("/api/v1/search/{keyword}")
    public List<Store> findListByKeyword(@PathVariable String keyword) {

        List<Store> storeList = storeList = storeService.findByKeyword(keyword);

        return storeList;
    }

    /**
     * @param address, -> 도로명 주소
     * @return 상점 List 검색 없으면 null
     */
    @ApiOperation(value = "검색 : 도로명 주소", notes = "주변 인기 식당 검색")
    @PostMapping("/api/v1/search/popular/{address}")
    public List<Store> popularNearLocation(@PathVariable String address){
        // 같은 지역에 위치한 상점 리스트 불러오기

        String inputAddr = address.substring(0,address.lastIndexOf("동")+1);
        if(inputAddr.equals("")){
            inputAddr = address.substring(0, address.lastIndexOf("구")+1);
        }
        System.out.println(inputAddr);

        List<Store> storeList = storeService.findPopularLocation(inputAddr);
        System.out.println(storeList);
        return storeList;
    }

    /**
     * @param location, -> 위도/경도
     * @return 상점 List 검색 없으면 null
     */
    @ApiOperation(value = "검색 : 위도, 경도", notes = "거리별 근처 식당 검색")
    @PostMapping("/api/v1/search/near")
    public List<StoreLength> NearLocation(@RequestBody Store location){
        // 같은 지역에 위치한 상점 리스트 불러오기
        // 거리 비교해서 1km이내 가장 가까운 상점 리스트 반환
        System.out.println(location.getLatitude() + " : "+ location.getLongtitude());
        List<StoreLength> storeList = storeService.findNearLocation(location.getLatitude(), location.getLongtitude());

        return storeList;
    }


}
