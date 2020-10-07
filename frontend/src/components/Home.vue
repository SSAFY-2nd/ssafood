<template>
  <div id=app>
    <!-- 네비게이션 바 -->
    <Navbar></Navbar>
    <section id="intro">
      <div id="intro-background"></div>
      <div id="intro-tagline">
        
        <h1>맛있는 음식점을 한눈에!</h1>
        <h1>SSAFOOD!</h1>
     
        <v-container fill-height fluid grid-list-xl>
      <v-layout justify-center wrap mt-5>
        <v-flex xs12 md8>
          <card title="맛집 검색 GO!" @click="searchFood(search)" to = "/search" >
            <!-- <v-form>
              <v-container py-0>
                <v-layout wrap>
                  <v-flex xs12 md12>
                    <v-text-field v-model="search" label="음식점 이름" />
                  </v-flex>
                  <v-flex xs12 text-center>
                    <v-btn
                      large
                      class="indigo white--text ma-5"
                      rounded
                      color="orange darken-1"
                      @click="searchFood(search)" to = "/search"
                    >GO!</v-btn>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-form> -->
          </card>
          <v-divider class="mx-4" />
        </v-flex>
        <v-flex xs12 md8>
          <v-flex v-for="store in stores" :key="store.id" pa-4>
            <store-list-card
              :id="store.id"
              :name="store.name"
              :categories="store.categories"
              :address="store.address"
              :tel="store.tel"
            />
          </v-flex>
        </v-flex>
      </v-layout>
    </v-container>
     </div>
    </section>
    
    <main class="page-content">
    
    <div class="page-container">
      
        <h1>About SSAFOOD</h1>
        <hr />
        <h2>당신이 가고 싶은 음식점은 어디?</h2>
        <!-- <p>음식 추천 사이트</p> -->
        <br>
    </div>
    <div id="map"></div>
      <section id="folio" class="sec-folio">
        <div class="container">
          <h1>주변 음식점</h1>
          <hr />
          <div class="row">
            <div v-for="(listdata,index) in listData" :key="index">
               <img class="center-block" :src="require(`http://j3a407.p.ssafy.io/img/store/${listdata.name}1.jpg`)"  alt="By Scott Webb" /><br>
               <v-icon size="20">mdi-account-circle</v-icon> {{listdata.name}} <br>
               <v-icon size="20">mdi-badge-account-horizontal </v-icon> {{listdata.address}} <br>
               <v-icon size="20">mdi-cellphone-iphone</v-icon> {{listdata.tel}}<br>
            </div>
          </div>
        </div>
      </section>
      </main>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'

import AOS from 'aos'
import 'aos/dist/aos.css'
// import InfiniteLoading from 'vue-infinite-loading';
import Card from "@/components/Card";
import StoreListCard from "@/components/StoreListCard";
import axios from 'axios'
const API_URL = 'http://localhost:8081/'
AOS.init();

// const API_URL = 'http://j3a407.p.ssafy.io:8081/'
 //const API_URL = 'http://localhost:8081/'
// const storage = window.sessionStorage

export default {
  name: 'Home',
  data() {
    return {
        search: '',
        listData: [],
        searchData: [],
        dataPerPage: 10,
        curPageNum: 1,
        curSelectIndex: 0,

        lat : '',
        lng : '',
        
        newData: {
          id: Number,
          guide_title: '',
          guide_type: ''
        },
        selectedData: {
          id: Number,
          guide_title: '',
          guide_type: ''
        }
    }
  },
  mounted(){
      if (navigator.geolocation) { // GPS를 지원하면
          navigator.geolocation.getCurrentPosition(function(position) {
          console.log(position.coords.latitude + ' ' + position.coords.longitude);
          
      }, function(error) {
        console.error(error);
      }, {
        enableHighAccuracy: false,
        maximumAge: 0,
        timeout: Infinity
      });
      this.near();
  } else {
    alert('GPS를 지원하지 않습니다');
  }
  },
  components: {
    // InfiniteLoading,
    Navbar,
    Card,
    StoreListCard
  },
   computed: {
      startOffset() {
        return ((this.curPageNum - 1) * this.dataPerPage);
      },
      endOffset() {
		console.log('startoffset : ')
		console.log(this.startOffset)
        return (this.startOffset + this.dataPerPage);
      },
      numOfPages() {
		console.log('endoffset : ')
		console.log(this.endOffset)
        return Math.ceil(this.listData.length/this.dataPerPage);
      },
      calData() {
		console.log('numofPages : ')
		console.log(this.startOffset)
         return this.listData.slice(this.startOffset, this.endOffset)
      }
  },
  methods: {
        locatorButtonPressed(){
          if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition(position=>{
              console.log(position.coords.latitude);
              console.log(position.coords.longitude);
            },
            error =>{
                console.log(error.message);
              }
            );
          }else{
            console.log("your browser does not support geolocation API");
            }
        },
        searchFood(search) {
            if (search == null) {
                alert("내용을 입력해주세요")
                return
            }
        },
        near(){
         if(navigator.geolocation){
            navigator.geolocation.getCurrentPosition(position=>{
              this.lat = position.coords.latitude;
              this.lng = position.coords.longitude;
              axios.post(API_URL+'/api/v1/search/near',
            {
                latitude : this.lat,
                longtitude : this.lng
            }) .then((response) => {
                  this.listData = response.data;
                  console.log("위치 : " +this.lat)
                  if (window.kakao && window.kakao.maps) {
                        var mapContainer = document.getElementById('map'),  
                        mapOption = {
                            //center: new kakao.maps.LatLng(37.556862, 126.926666), 
                            center: new kakao.maps.LatLng(this.lat, this.lng), 
                            level: 3 
                        };     
                        console.log("안뇽 : "+this.lat)
                        var map = new kakao.maps.Map(mapContainer, mapOption); 
                        // var geocoder = new kakao.maps.services.Geocoder();
                        // var address = this.address
                        // var coords = new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude);
                        var imageSrc = 'https://ifh.cc/g/PIvBP3.png',    
                            imageSize = new kakao.maps.Size(64, 69), 
                            imageOption = {offset: new kakao.maps.Point(27, 69)}; 
                      
                        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                            markerPosition = new kakao.maps.LatLng(this.lat, this.lng); 
                            //markerPosition = new kakao.maps.LatLng(37, 123); 

                        var marker = new kakao.maps.Marker({
                            position: markerPosition, 
                            image: markerImage // 마커이미지 설정 
                            });

                        marker.setMap(map);  
                        var resname = "현재 위치";
                        var jwcontent='<div style="width:150px;text-align:center;padding:6px 0;">'+resname +'</div>';
                        var infowindow = new kakao.maps.InfoWindow({
                            content: jwcontent
                            // content: this.restaurant.name  
                            });
                        infowindow.open(map, marker);
                        var imageSrc2 = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
                        console.log(this.listData.length)
                        for (var i = 0; i < this.listData.length; i ++) {
                              var imageSize2 = new kakao.maps.Size(24, 35); 
                              // 마커 이미지를 생성합니다    
                              console.log(this.listData[i].name)
                              var markerImage2 = new kakao.maps.MarkerImage(imageSrc2, imageSize2); 
                              // 마커를 생성합니다
                              var markerPosition2 = new kakao.maps.LatLng(this.listData[i].latitude, this.listData[i].longtitude)
                              var marker2 = new kakao.maps.Marker({
                                  map: map, // 마커를 표시할 지도
                                  position: markerPosition2, // 마커를 표시할 위치
                                  title : this.listData[i].name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                                  image : markerImage2 // 마커 이미지 
                              })
                              marker2.setMap(map);
                        }                
                                } else {
                                  const script = document.createElement('script');
                                  /* global kakao */
                                  script.onload = () => kakao.maps.load(this.initMap);
                                  script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=YOUR_APPKEY';
                                  document.head.appendChild(script);
                            }
            });
            },
            error =>{
                console.log(error.message);
              }
            );
          }else{
            console.log("your browser does not support geolocation API");
            }
          
        },
        initMap() {
        
        },
  }
}
</script>

<style scoped>
/* @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap'); */
@import url('http://fonts.googleapis.com/earlyaccess/notosanskr.css');

.ui.button,
.dot.circle.icon{
  background-color:  #ff5a5f;
  color : white;
}
.doasis{
  position: relative;
  line-height: 1;
  letter-spacing: 1px;
  text-align: right;
  padding-right:20px;
}
.blink{
  width:34px;
  height:4px;
  background-color:black;
  float:right;
  animation: .8s blink infinite;
}
.doasis span {
  display: block;
  font-size:60px;
  font-family: 'Inter', sans-serif;
  font-weight: 600;
}
em {
  font-style:normal;
}
.toptab {
  padding-top : 60px;
  padding-left: 20px;
  min-width: 1100px; 
  max-width: 1150px;
  margin: 35px auto 0;
}
.contain {
  /* padding-top : 150px; */
  max-width: 1150px;
  margin: 0 auto 0;
  padding: 0 20px;
}
.v-tab {
  letter-spacing: -1px;
  font-size: 16px;
  font-family: 'NanumSquareR','나눔스퀘어','Noto Sans DemiLight','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif;
}
.content_area{
  min-width: 1100px; 
}
.inner_card {
  padding: 1.5rem;
}
.card_title{
  line-height:1.2rem;
  height: 1.2rem;
  overflow: hidden;
  text-overflow:ellipsis;
  margin: 0 0 5px;
}
.card_title h5{
  font-size: 1rem;
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif;
  font-weight: 800;
}
.card_content{
  width:100%;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  white-space: normal;
  line-height: 1.5;
  height:4.5em;
  color: rgb(73, 80, 87);
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic','맑은고딕','Nanum Gothic',sans-serif;
  font-weight:400;

  margin: 0 0 1.5rem;
}
.card_content p{
  height: 100%
}
.card_date {
  font-size:0.75rem;
  padding: 5px 0 5px;
  color: rgb(134, 142, 150);
}
.liked{
  float:right; 
  margin-top: -24px
}

body {
    font-size: 100%;
    font-family: "Open Sans", sans-serif;
    color: #f35535;
    background-color: #FB8C00;
}
html * {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
body {
    line-height: 1;
}


#intro {
    position: absolute;
    /*position: fixed;*/
    top: 0;
    left: 0;
    width: 100%;
    height: 700px;
    margin-top: 0;
}

#intro #intro-background {
    height: 100%;
    width: 100%;
    /* background: url("http://www.hdwallpapers.in/walls/milky_way_sky-wide.jpg") no-repeat center center; */
    background: url("../assets/img/food2.jpg") no-repeat center center;
    /* background: url("../assets/img/benchpress_wallpaper.jpg") no-repeat center center; */
    background-size: cover;
    box-shadow: 0 0 30px rgba(0, 0, 0, 1);
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}

#intro #intro-tagline{
    position: absolute;
    width: 90%;
    max-width: 1170px;
    left: 50%;
    top: 50%;
    bottom: auto;
    right: auto;
    -webkit-transform: translateX(-50%) translateY(-50%);
    -moz-transform: translateX(-50%) translateY(-50%);
    -ms-transform: translateX(-50%) translateY(-50%);
    -o-transform: translateX(-50%) translateY(-50%);
    transform: translateX(-50%) translateY(-50%);
    color: white;
    font-size: 20px;
    font-size: 1.5rem;
    font-weight: 300;
    text-align: center;
}

#intro h1 {
    font-family: "Open Sans", helvetica, arial, sans-serif;
    text-transform: uppercase;
    font-size: 1.8em;
    font-weight: 300;
    text-align: center;
    color: #ffffff;
    text-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
}

.page-content {
    position: relative;
    padding: 2em 0;
    line-height: 1.8;
    color: #65676f;
    font-weight: 100;
    /* background-color: #E6F6F4; */
    background-color: #ffffff;
    z-index: 2;
}

.page-content::before {
    content: '';
    position: absolute;
    bottom: 100%;
    left: 0;
    width: 100%;
    height: 50px;
    background: -webkit-linear-gradient( bottom , rgba(46, 49, 61, 0.5), rgba(46, 49, 61, 0));
    background: linear-gradient(to top, rgba(46, 49, 61, 0.5), rgba(46, 49, 61, 0));
}

@media only screen and (min-width: 1170px) {
.page-content {
    padding: 4em 0;
    margin-top: 570px;
 }
}

@media only screen and (min-width: 768px) { 
.cd-content {
    line-height: 1.8;
 }
}

.page-container {
    width: 90%;
    max-width: 768px;
    margin: 0 auto;
}

.page-container::after {
    content: '';
    display: table;
    clear: both;
}

@media only screen and (min-width: 1170px) {
.page-content p {
    font-size: 20px;
    font-size: 1.25rem;
 }
}

@media only screen and (min-width: 768px) {
.page-content h2 {
    font-size: 30px;
    font-size: 1.875rem;
 }
}

.page-content h2 {
    margin: 2em 0;
    /* font-size: 60px; 20 */
    /* font-size: 1.25rem; */
}

.page-content p {
    margin: 1em 0;
}
/* About */


/*Services*/

/* .sec-services {
  background: #f9f9f9;
} */

.sec-services i {
  display: block;
  margin: auto;
  width: 45px;
}

.sec-services .fa-paint-brush {
  width: 64px;
}

.sec-services .fa-code {
  width: 68px
}

.sec-services h2 {
  margin: 1em 0;
}

/* image layout*/
.sec-folio .row {
  /* margin-top: 2em; */
  margin-bottom: 1em;
  font-size: 16px;
  font-size: 1.00em;
}

.sec-folio img {
  max-width: 100%;
}

.score {
  font-size : 20px;
  color : orange;
  font-weight: bold;
}

#map {
  margin-left: 600px;
  width: 500px;
  height: 400px;
}
</style>
