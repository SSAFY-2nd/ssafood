<template>
    <div>
        <Navbar></Navbar>
        <div class="detail">
            <div class="content">
                  <img :src="require(`@/assets/img/${restaurant.store_id}_1.jpg`)" width="250" height="300" >
                  <img :src="require(`@/assets/img/${restaurant.store_id}_2.jpg`)" width="250" height="300" > 
                  <img :src="require(`@/assets/img/${restaurant.store_id}_3.jpg`)" width="250" height="300" >
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
                      <!-- <router-link :to="{name:'reviewinsert',params:{store_id : $route.params.store_id}}"> -->
                       <v-btn color = "warning" elevation="9" x-large @click="checkLogin()"  >
                          리뷰 쓰기
                        </v-btn>
                      <!-- </router-link> -->
            
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
            <v-btn icon class="mr-4" v-if="login_user === reviewList.writer_id && login_user !==''" @click="ReviewUpdate(reviewList)">
              <img class="gender" src="../assets/img/pen.png"/>
            </v-btn>
            
             <v-btn icon class="mr-4" v-if="login_user === reviewList.writer_id && login_user !==''" @click="ReviewDelete(reviewList)">
              <img class="gender"  src="../assets/img/garbage.png"/>
            </v-btn>
            <span class="font-italic">
              
            </span>
          </v-card-subtitle>
          <v-card-text class="title white--text">
            <v-row align="start" justify="start" no-gutters>
              <v-col cols="2">
                <v-avatar color="grey" v-model= "nickname" >
                  <span class="white--text headline" >{{reviewList.writer_id}}</span>
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
                <table v-for="(listdata,index) in around_list" :key="index">
                    
                    <tr>
                        <td rowspan=3><img :src="require(`@/assets/img/${listdata.store_id}_1.jpg`)" width="70" height="70"></td>
                        <router-link :to="{name:'detail',params:{store_id : listdata.store_id}}">
                        <td class="td-header"><div style="margin-left:20px"><v-icon size="20">mdi-account-circle</v-icon>{{listdata.name}}</div></td>
                         </router-link>
                    </tr>
                   
                    <tr><div style="margin-left:20px"><v-icon size="20">mdi-badge-account-horizontal </v-icon> {{listdata.address}}</div></tr>
                    <tr><div style="margin-left:20px"><v-icon size="20">mdi-cellphone-iphone</v-icon> {{listdata.tel}}</div></tr>
                    
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
const storage = window.sessionStorage
export default {
    name: 'detail',
    components:{
        Navbar
    },
    data(){
        return{
            restaurant:{
              store_id:'',
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
            },
            address:'',
            around_list:[],
            nickname:'',
            login_user: storage.getItem("login_user"),
        }
    },
    created() {
     axios.get(API_URL+'api/v1/detail/'+this.$route.params.store_id)
        .then((response) => {
          this.restaurant = response.data;
           axios.post(API_URL+'api/v1/search/popular/'+this.restaurant.address,
            ).then((response) => {
              console.log("HIHI")
              this.around_list = response.data;
              console.log(this.around_list)
               if (window.kakao && window.kakao.maps) {
                this.initMap();
              } else {
                  const script = document.createElement('script');
                  /* global kakao */
                  script.onload = () => kakao.maps.load(this.initMap);
                  script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=55f188e963076732f3ffcc14970d59b1';
                  document.head.appendChild(script);
             }
            })
        });
         //this.listData = testData
    },
     mounted() {
        
    },
   
    methods: {
      checkLogin(){
      console.log("클릭확인")
      
      if(storage.getItem("jwt-auth-token") === 'undefined'|| storage.value === undefined)
      {
         alert('로그인이 필요합니다.')
         this.$router.push("/detail/"+this.$route.params.store_id);
      }else{
        this.$router.push("/detail/reviewinsert/"+this.$route.params.store_id);
      }
    },
    ReviewUpdate(reviewList){
      this.$router.push("/detail/reviewupdate/"+this.$route.params.store_id+"/"+reviewList.review_id);
    },
    push(store_id){
      this.$router.push("/detail/"+store_id);
    },
    ReviewDelete(reviewList){
      console.log("삭제확인")
      console.log(reviewList.review_id)
      // if(storage.getItem("login_user")===this.nickname)
      // {
       
        axios.delete(API_URL+'/api/v1/review/'+reviewList.review_id) 
        .then(res => { 
          console.log(res.data) 
          alert("삭제에 성공하였습니다!") 
          // this.$router.push("/detail/"+this.$route.params.store_id)
          location.reload();
          }).catch(err => {
          console.log(err)
          console.log("삭제에 실패하였습니다.")
        })
      // }else{
      //   alert("본인이 쓴글 만 지울 수 있습니다.")
      //   this.$router.push("/detail/"+this.$route.params.store_id);
      // }
    },
       initMap() {
         console.log(this.restaurant.address)
           
              var mapContainer = document.getElementById('map'),  
              mapOption = {
                  center: new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude), 
                  level: 3 
              };     
              console.log(this.restaurant.latitude+' '+this.restaurant.longtitude)
              var map = new kakao.maps.Map(mapContainer, mapOption); 
  
              var imageSrc = 'https://ifh.cc/g/PIvBP3.png',    
                  imageSize = new kakao.maps.Size(64, 69), 
                  imageOption = {offset: new kakao.maps.Point(27, 69)}; 
            
              var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                  markerPosition = new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude); 

              var marker = new kakao.maps.Marker({
                  position: markerPosition, 
                  image: markerImage // 마커이미지 설정 
                  });

              marker.setMap(map);  
              var resname = this.restaurant.name;
              console.log("res:"+resname);

              var jwcontent='<div style="width:150px;text-align:center;padding:6px 0;">'+resname +'</div>';
              var infowindow = new kakao.maps.InfoWindow({
                  content: jwcontent
                  });
              infowindow.open(map, marker);
              var imageSrc2 = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
              console.log(this.around_list.length)
                  for (var i = 0; i < this.around_list.length; i ++) {
                              var imageSize2 = new kakao.maps.Size(24, 35); 
                              // 마커 이미지를 생성합니다    
                              var markerImage2 = new kakao.maps.MarkerImage(imageSrc2, imageSize2); 
                              // 마커를 생성합니다
                              var markerPosition2 = new kakao.maps.LatLng(this.around_list[i].latitude, this.around_list[i].longtitude)
                              var marker2 = new kakao.maps.Marker({
                                  map: map, // 마커를 표시할 지도
                                  position: markerPosition2, // 마커를 표시할 위치
                                  title : this.around_list[i].name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                                  image : markerImage2, // 마커 이미지
                                  clickable : true
                              })
                              marker2.setMap(map);
                              var naming = this.around_list[i].address
                              kakao.maps.event.addListener(marker2,'click',function(){
                                  console.log(naming)
                              })
                        }                
        },
        init(){
            
        },
        pushing(naming){
          this.$router.push("/detail/"+naming);
        }
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