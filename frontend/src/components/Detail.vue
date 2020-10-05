<template>
    <div>
        <Navbar></Navbar>
        <div class="detail">
            <div class="content">
            <img src="../assets/img/food.png" width="300" height="300"> 
            <h1 class="rest-title">{{restaurant.name}}</h1>
            <br>
            <v-icon slot="append" >mdi-eye</v-icon>조회수
            <v-icon slot="append" >mdi-pencil</v-icon>리뷰수
            <v-icon slot="append" >mdi-star</v-icon>즐겨찾기수
            <hr>
                <table class = "story">
                    <tr>
                        <td>주소<hr></td>
                        <td><div style="margin-left:150px">{{restaurant.address}}<hr></div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>체인명<hr></td>
                        <td><div style="margin-left:150px">{{restaurant.branch}}<hr></div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>지역분류<hr></td>
                        <td><div style="margin-left:150px">{{restaurant.area}}<hr></div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>전화번호<hr></td>
                        <td><div style="margin-left:150px">{{restaurant.tel}}<hr></div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>음식 종류<hr></td>
                        
                        <td><div style="margin-left:150px">
                            <li v-for="(kind,index) in restaurant.category" :key="index">
                            {{kind}}
                            </li>
                            <hr></div></td>
                        
                    </tr>
                     <div style="margin-top:10px"></div>
                     
                     <!-- <tr>
                        <td>가격대</td>
                        <td><div style="margin-left:150px">음식점 가격대</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                    <tr>
                        <td>주차</td>
                        <td><div style="margin-left:150px">발렛</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                      <tr>
                        <td>영업 시간</td>
                        <td><div style="margin-left:150px">10:00 ~ 24:00</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>휴일</td>
                        <td><div style="margin-left:150px">일, 둘째 / 넷째 월(bhour_list)</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>웹사이트</td>
                        <td><div style="margin-left:150px">식당 홈페이지로 가기</div></td>
                    </tr>
                    <div style="margin-top:10px"></div> -->
                     <tr>
                        <td rowspan=3>메뉴<hr></td>
                        <td><div style="margin-left:150px"><li v-for="(menu,index) in restaurant.menu" :key="index">
                            {{menu}}</li>
                            <hr></div></td>
                    </tr> 
                </table>
                <br><br>
                <div class="review">
                    <h1>리뷰({{restaurant.reviewList.length}})</h1>
                    <div class="link">
                      <router-link :to="{name:'reviewinsert',params:{store_id : $route.params.store_id}}">
                       <v-btn color = "warning" elevation="9" x-large @click="checkLogin()" >
                          리뷰 쓰기
                        </v-btn>
                      </router-link>
                </div>
                </div>
                
                
                <br> 
                <hr>
                <br>




          <div v-for="(reviewList,index) in restaurant.reviewList" :key="index">
          <v-card class="mx-auto mb-3" max-width="1000" color="#FB8C00" dark>
          <v-card-subtitle>
            <span class="font-weight-black">
             {{reviewList.reg_time}}
            </span>
            <v-btn icon class="mr-4">
              <img class="gender" src="../assets/img/pen.png"/>
            </v-btn>
            
             <v-btn icon class="mr-4">
              <img class="gender" src="../assets/img/garbage.png"/>
            </v-btn>
            <span class="font-italic">
              
            </span>
          </v-card-subtitle>
          <v-card-text class="title white--text">
            <v-row align="start" justify="start" no-gutters>
              <v-col cols="2">
                <v-avatar color="grey">
                  <span class="white--text headline">{{reviewList.writer_id}}</span>
                </v-avatar>
              </v-col>
              <v-col>
               {{reviewList.content}}
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions>
            <v-btn icon class="mr-1">
              <v-icon>mdi-heart</v-icon>
            </v-btn>
            <span class="subheading mr-3"></span>
            <v-btn icon class="mr-1">
              <v-icon>mdi-share-variant</v-icon>
            </v-btn>
            <div v-if ="reviewList.gender===0">
            <v-btn icon class="mr-3">
              <img class="gender" src="../assets/img/man.png"/>
            </v-btn>
            </div>
            <div v-if ="reviewList.gender===1">
            <v-btn icon class="mr-3">
              <img class="gender" src="../assets/img/woman.png"/>
            </v-btn>
            </div>
            <v-btn icon class="mr-2">
              <v-icon color = "yellow">mdi-star</v-icon> {{reviewList.total_score}}
            </v-btn>
            
            
            
            <span class="subheading mr-3"></span>
          </v-card-actions>
        </v-card>
    
          
        </div>   
                
            </div>
            <div class = "right-side">
               <div id="map"></div>
               <br>
                <h1 class="rest-back">주변 인기 식당</h1><br>
                <table>
                    <tr>
                        <td rowspan=3><img src="../assets/img/food.png" width="70" height="70"></td><td class="td-header"><div style="margin-left:20px">음식점1</div></td>
                    </tr>
                    <tr><div style="margin-left:20px">hello</div></tr>
                    <tr><div style="margin-left:20px">bye</div></tr>
                     <tr>
                        <td rowspan=3><img src="../assets/img/food2.jpg" width="70" height="70"></td><td class="td-header"><div style="margin-left:20px">음식점2</div></td>
                    </tr>
                    <tr><div style="margin-left:20px">hello</div></tr>
                    <tr><div style="margin-left:20px">bye</div></tr>
                     <tr>
                        <td rowspan=3><img src="../assets/img/food3.jpg" width="70" height="70"></td><td class="td-header"><div style="margin-left:20px">음식점3</div></td>
                    </tr>
                    <tr><div style="margin-left:20px">hello</div></tr>
                    <tr><div style="margin-left:20px">bye</div></tr>
                     <tr>
                        <td rowspan=3><img src="../assets/img/food4.jpg" width="70" height="70"></td><td class="td-header"><div style="margin-left:20px">음식점4</div></td>
                    </tr>
                    <tr><div style="margin-left:20px">hello</div></tr>
                    <tr><div style="margin-left:20px">bye</div></tr>
                </table>
            </div>
           
        </div>
        
    </div>
    
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
//import axios from 'axios'

const API_URL = 'http://localhost:8081/'

export default {
    name: 'detail',
    components:{
        Navbar
    },
    data(){
        return{
            restaurant:{
            id:'',
            name:'',
            branch:'',
            area:'',
            tel:'',
            address:'',
            latitude:'',
            longtitude:'',
            category:[],
            menu:[],
            reviewList:[],
            bhour_list:[],
            review_cnt:''
            
            }
        }
    },
    created() {
     axios.get(API_URL+'api/v1/detail/'+this.$route.params.store_id)
        .then((response) => {
          this.restaurant = response.data;
          console.log(this.restaurant.length);
          
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement('script');
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=YOUR_APPKEY';
            document.head.appendChild(script);
        }
        });
         //this.listData = testData
    },
     mounted() {
        
    },
    methods: {
       initMap() {
        var mapContainer = document.getElementById('map'),  
        mapOption = {
            //center: new kakao.maps.LatLng(37.556862, 126.926666), 
             center: new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude), 
            level: 3 
        };     
        var map = new kakao.maps.Map(mapContainer, mapOption); 
        // var geocoder = new kakao.maps.services.Geocoder();
        // var address = this.address
        // var coords = new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude);
        var imageSrc = 'https://ifh.cc/g/PIvBP3.png',    
            imageSize = new kakao.maps.Size(64, 69), 
            imageOption = {offset: new kakao.maps.Point(27, 69)}; 
      
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
            markerPosition = new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude); 
            //markerPosition = new kakao.maps.LatLng(37, 123); 

        var marker = new kakao.maps.Marker({
            position: markerPosition, 
            image: markerImage // 마커이미지 설정 
            });

        marker.setMap(map);  
        var resname = this.restaurant.name;
        var jwcontent='<div style="width:150px;text-align:center;padding:6px 0;">'+resname +'</div>';
        var infowindow = new kakao.maps.InfoWindow({
            content: jwcontent
            // content: this.restaurant.name  
            });
        infowindow.open(map, marker);

        
        },
      //   checkLogin(){
      //     if(this.$store.state.isLoggedIn){

      //     } else {
      //   alert('로그인이 필요합니다.')
      // }
      //   }
        
    }
}
</script>

<style scoped>
#map {
    width: 500px;
    height: 400px;
}
.detail {
  padding-top:151px;
  width:1400px;
  margin-left : 100px;
  height : 1500px;
}
.story{
    margin-top : 50px;
}
.story tr{
    margin-top : 10px;
}
.content {
    width:900px;
    float:left;
    height:800px;
}
.header{
    width:900px;
    float:left;
}
.rest-title {
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 36px;
  font-weight: 800;
}

.rest-back {
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 26px;
  font-weight: 800;
}
.eye{
    size : 100px;
}
.footer{
    height:300px;
}
.right-side{
    width : 300px;
    float:left;
    height:800px;
}
.td-header{
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 22px;
  font-weight: 800;
}
.rest-foot {
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 26px;
  font-weight: 800;
}
.all {
  
}

.shadow-1:before {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  width: inherit;
  height: inherit;
  z-index: -2;
  box-sizing: border-box;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.13);
}

.shadow-1:after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  width: inherit;
  height: inherit;
  z-index: -2;
  box-sizing: border-box;
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.08);
}

.card {
  position: relative;
  height: 100px;
  background: #fcfcfc;
  margin: 20px 40px;
  transition: 0.4s all;
}

.card.open {
  height: 200px;
  background: #ffffff;
}
.mr-1{
  font-size: large;
}
.mr-2{
 width: 40px;
 height: 40px;
 left: 700px;
 font-size: x-large;
}
.mr-3{
 left : 10px;
}
.mr-4{
 left: 620px;
 
}
.gender{
  -webkit-filter:opacity(0.5) drop-shadow(0 0 0 grey);
  filter: opacity(0.5) drop-shadow(0 0 0 grey);
  width : 25px;
  height : 25px;
}
.link{
  position: relative;
  float : right;
  bottom : 50px;
}
</style>