<template>
    <div>
        <Navbar></Navbar>
        <div class="detail">
            <div class="content">
            <h1 class="rest-title">음식점 이름 들어갈 곳</h1>
            <br>
            <v-icon slot="append" >mdi-eye</v-icon>조회수
            <v-icon slot="append" >mdi-pencil</v-icon>리뷰수
            <v-icon slot="append" >mdi-star</v-icon>즐겨찾기수
            <hr>
            <div class="d-flex justify-content-between">
                    <div>주소</div> 음식점 주소 <br><br>
                    <div>전화번호</div> 음식점 전화번호<br><br>
                    <div>주소</div>
                </div>
            <br><br>
                    <br><br>
            음식종류     음식점 음식종류<br><br>
            가격대  음식점 가격대<br><br>
            주차   발렛<br><br>
            영업 시간   10:00 ~ 24:00<br><br>
            휴일   일, 둘째 / 넷째 월 <br><br>
            웹사이트    식당 홈페이지로 가기<br>
            메뉴  메뉴1 가격<br>
            </div>
            <div class = "right-side">
                지도<br><br><br><br><br><br><br><br>
                <h1 class="rest-back">주변 인기 식당</h1><br>
                <img src="../assets/img/food.png" width="50" height="50"><p>음식점1 <br>hello</p><br>
                <img src="../assets/img/food2.jpg"  width="50" height="50">음식점2<br>
                <img src="../assets/img/food3.jpg"  width="50" height="50">음식점3<br>
                <img src="../assets/img/food4.jpg"  width="50" height="50">음식점3<br>
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
    }
}
</script>

<style scoped>
.detail {
  padding-top:151px;
  width:1400px;
  margin-left : 100px;
  height : 1500px;
}
.content {
    width:900px;
    float:left;
    height:700px;
}
.header{
    height:100px;
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

.rest-foot {
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
  font-size: 26px;
  font-weight: 800;
}
</style>