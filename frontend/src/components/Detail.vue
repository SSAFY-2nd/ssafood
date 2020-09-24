<template>
    <div>
        <Navbar></Navbar>
        <div class="detail">
            <div calss="header">
            <img src="../assets/img/food.png" width="300" height="300"> 
            </div>
            <div class="content">
            <h1 class="rest-title">음식점 이름 들어갈 곳</h1>
            <v-icon slot="append" >mdi-eye</v-icon>조회수
            <v-icon slot="append" >mdi-pencil</v-icon>리뷰수
            <v-icon slot="append" >mdi-star</v-icon>즐겨찾기수
            <hr>
                <table class = "story">
                    <tr>
                        <td>주소</td>
                        <td><div style="margin-left:150px">음식점 주소</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>전화번호</td>
                        <td><div style="margin-left:150px">음식점 전화번호</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>음식 종류</td>
                        <td><div style="margin-left:150px">음식점 음식 종류</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
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
                        <td><div style="margin-left:150px">일, 둘째 / 넷째 월</div></td>
                    </tr>
                     <div style="margin-top:10px"></div>
                     <tr>
                        <td>웹사이트</td>
                        <td><div style="margin-left:150px">식당 홈페이지로 가기</div></td>
                    </tr>
                    <div style="margin-top:10px"></div>
                     <tr>
                        <td rowspan=3>메뉴</td>
                        <td><div style="margin-left:150px">메뉴1 가격</div></td>
                    </tr>
                    <tr>
                        <div style="margin-left:150px">메뉴2 가격</div>
                    </tr>
                    <tr>
                        <div style="margin-left:150px">메뉴3 가격</div>
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

export default {
    name: 'detail',
    components:{
        Navbar
    },
    data(){
        return{
            restraunt:{
            id:'',
            name:'',
            branch:'',
            area:'',
            tel:'',
            }
        }
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
            var container = document.getElementById('map');
            var options = {
              center: new kakao.maps.LatLng(33.450701, 126.570667),
              level: 3
            };

            var map = new kakao.maps.Map(container, options);
            map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);
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
    height:700px;
}
.header{
    padding :0 0 0 200px;
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
.div-pad{
    margin : 0 40px 0 0;
}
.left-pad{
    margin : 0 20px 0 0;
}
.footer{
    height:300px;
}
.right-side{
    width : 300px;
    float:left;
    height:700px;
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