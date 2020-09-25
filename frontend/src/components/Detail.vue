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
            <div class="footer">
            <h1 class="rest-foot">리뷰(32)</h1>
            </div>    
        </div>
    </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
//import axios from 'axios'
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
            bhour_list:[],
            review_cnt:''
            
            }
        }
    },
    created() {
     axios.get('http://localhost:8081/api/v1/detail/'+this.$route.params.store_id)
        .then((response) => {
          this.restaurant = response.data;
          console.log(this.restaurant.length);
        });
         //this.listData = testData
    },
     mounted() {
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement('script');
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=YOUR_APPKEY';
            document.head.appendChild(script);
        }
    },
    methods: {
        initMap() {
        var mapContainer = document.getElementById('map'),  
        mapOption = {
            center: new kakao.maps.LatLng(37.556862, 126.926666), 
             //center: new kakao.maps.LatLng(this.restaurant.latitude, this.restaurant.longtitude), 
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
            markerPosition = new kakao.maps.LatLng(37.556862, 126.926666); 
            //markerPosition = new kakao.maps.LatLng(37, 123); 

        var marker = new kakao.maps.Marker({
            position: markerPosition, 
            image: markerImage // 마커이미지 설정 
            });

        marker.setMap(map);  
        var resname = this.restaurant.name;
        var jwcontent='<div style="width:150px;text-align:center;padding:6px 0;">'+resname +'</div>';
        var infowindow = new kakao.maps.InfoWindow({
            //content: '<div style="width:150px;text-align:center;padding:6px 0;">this.restaurant.name</div>'
            content: jwcontent 
            });
            infowindow.open(map, marker);
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
    height:1500px;
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
</style>