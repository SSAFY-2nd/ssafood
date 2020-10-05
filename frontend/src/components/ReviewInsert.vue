<template>
    <div>
        <Navbar></Navbar>
        <div class="detail">
            <div class="content">
          <h1>리뷰쓰기</h1>
          <hr>
         <h3>평점</h3>
         <br>
         <input type="radio" id="one" value=1 v-model="picked">
         <label for="one" style="margin:10px"><v-icon color = "yellow">mdi-star</v-icon></label>
         &nbsp;
         <input type="radio" id="two" value=2 v-model="picked">
         <label for="two" style="margin:10px"><v-icon color = "yellow">mdi-star</v-icon><v-icon color = "yellow">mdi-star</v-icon></label>

         <input type="radio" id="three" value=3 v-model="picked">
         <label for="three" style="margin:10px">
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           </label>

         <input type="radio" id="four" value=4 v-model="picked">
         <label for="four" style="margin:10px">
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           </label>

         <input type="radio" id="five" value=5 v-model="picked">
         <label for="five" style="margin:10px">
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
           <v-icon color = "yellow">mdi-star</v-icon>
         </label>
          <br>
          <!-- <span>선택: {{ picked }}</span> -->
          <hr>

             <h3>리뷰내용</h3>
              <textarea class="content" placeholder="content" v-model="content"/>
              
              <br><br><br><br><br><br><br><br><br><br><br><br><br>

         <hr>
         <v-btn color = "warning" elevation="9" x-large >
                          리뷰 등록
                        </v-btn>
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
          picked:'',
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
.content{
  height:300px;
  outline-color: orange;
}
</style>