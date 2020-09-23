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
          <card title="맛집 검색">
            <v-form>
              <v-container py-0>
                <v-layout wrap>
                  <v-flex xs12 md12>
                    <v-text-field v-model="storeName" label="음식점 이름" />
                  </v-flex>
                  <v-flex xs12 text-center>
                    <v-btn
                      large
                      class="indigo white--text ma-5"
                      rounded
                      color="orange darken-1"
                      @click="onSubmit" to = "/search"
                    >GO!</v-btn>
                  </v-flex>
                </v-layout>
              </v-container>
            </v-form>
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

        <p>음식 추천 사이트</p>
        
        </div>
        <section id="services" class="sec-services">
        <div class="container">
          <h1>Services</h1>
          <hr/>
          <div class="row">
            <div class="col-sm-4">
              <div class="col" style="right:30px">
                <v-icon size="100" >mdi-kabaddi</v-icon>
              </div>
              <h2 class="h3"> 맛집 추천</h2>
              <p>카테고리 별로 맛집을 추천해줍니다.</p>
            </div>
            <div class="col-sm-4">
              <div class="col" style="right:30px">
                <v-icon size="100" >mdi-calendar-clock</v-icon>
              </div>
              <h2 class="h3">다이어리</h2>
              <p>나의 맛집 방문기록을 확인하세요</p>
            </div>
            <div class="col-sm-4">
              <div class="col" style="right:30px">
                <v-icon size="100" >mdi-nutrition</v-icon>
              </div>
              <h2 class="h3">식단</h2>
              <p>적절한 칼로리의 식단을 제공합니다</p>
            </div>
          </div>
        </div>
      </section>
      <section id="folio" class="sec-folio">
        <div class="container">
          <h1>평점 높은 음식들</h1>
          <hr />
          <div class="row">
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/순대국.png" alt="By Håkon Sataøen" /><br>
              <v-icon size="20">mdi-account-circle</v-icon> 순대천국 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468           
            </div>
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/육회.png" alt="By Samuel Zeller" /><br>
               <v-icon size="20">mdi-account-circle</v-icon> 육회천국 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468      
            </div>
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/조각케이크.png" alt="By Scott Webb" /><br>
               <v-icon size="20">mdi-account-circle</v-icon> 케이크천국 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468   
            </div>
          </div>

          <div class="row">
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/조각피자.png" alt="By Daniel Lozano Valdés" /><br>
              <v-icon size="20">mdi-account-circle</v-icon> 피자공주 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468   
            </div>
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/치킨.png" alt="By Scott Webb" /><br>
               <v-icon size="20">mdi-account-circle</v-icon> 치킨나라 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468    
            </div>
            <div class="col-md-4">
              <img class="center-block" src="../assets/img/햄버거.png" alt="By Daniel Lozano Valdés" /><br>
               <v-icon size="20">mdi-account-circle</v-icon> 햄버거파티 <br>
              <v-icon size="20">mdi-badge-account-horizontal </v-icon> 서울시 노원구 <br>
              <v-icon size="20">mdi-cellphone-iphone</v-icon> 010 1357 2468   
            </div>
          </div>
        </div>
      </section>
      </main>




    
    
    <!-- <div class="toptab">
      <div class="doasis" 
        data-aos="fade-up" 
        data-aos-offset="50"
        data-aos-easing="ease-in-sine"
        data-aos-duration="500">
          <span><em style="color: #6987f7; padding-left:3px">D</em>eveloper</span>
      </div>
      <div class="doasis" 
        data-aos="fade-up" 
        data-aos-offset="50"
        data-aos-easing="ease-in-sine"
        data-aos-duration="500"
        data-aos-delay="400"
        >
          <span class="spot_title"><em style="color: #08d3bc;">O</em>asis</span>
        <div class="blink"></div>
      </div>
      
      <v-tabs v-model="tab" 
        class="nav"
        background-color="transparent"
        color="black"
        slider-color="#6987f7"
        slider-size="3"
        >
        <v-tab @click="postread(item)" v-for="item in items" :key="item">
          <div>{{ item }}</div>
        </v-tab>
      </v-tabs>
    </div> --> 


    <div class="contain">
      <div class="content_area">
        <!-- 게시글 탭 -->
        <v-tabs-items v-model="tab">
          <!-- 게시글 미리보기 -->
          <v-tab-item v-for="(item) in items" :key="item">
            <v-row v-if="posts.length">
              <v-col cols="4" v-for="(post, index) in posts" :key="index+'_posts'"
                style="padding:1rem">
                <v-hover>
                  <template v-slot="{hover}">
                  <v-card 
                    @click="postdetail(post)"
                    height="406px"
                    max-width="350px"
                    :elevation="hover ? 8 : 2"
                  >
                    <v-img
                      class="white--text align-end"
                      height="180px"
                      :src="post.thumbnail"
                    />
                    <div class="inner_card">
                      <!-- 제목이 가로범위 초과시 ... -->
                      <div class="card_title"><h5>{{post.title}}</h5></div>
                      <!-- 내용 -->
                      <div class="card_content">
                        <p>{{post.content}}</p>
                        <div v-if="!post.content">게시글 내용이 비어있습니다.</div>
                      </div>
                      <!-- 날짜 -->
                      <div class="card_date">{{post.publishedTime}}</div>
                      <!-- 작성자, 좋아요 -->
                      <div class="card_info">
                        <div class="author">
                          {{post.author}}
                        </div>
                        <div class="liked">
                          <v-icon x-small style="margin-top:-2px" color="black">fas fa-heart</v-icon>
                          {{post.likes}}
                        </div>
                      </div>
                    </div>
                  </v-card>
                </template>
                </v-hover>
              </v-col>
            </v-row>
            <v-row v-else>
              게시글이 없습니다.
            </v-row>
            <infinite-loading @infinite="infiniteHandler" spinner="waveDots">
              <div slot="no-more" style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;">목록의 끝입니다 :)</div>
            </infinite-loading>
          </v-tab-item>
        </v-tabs-items>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'

import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'
import InfiniteLoading from 'vue-infinite-loading';
import Card from "@/components/Card";
import StoreListCard from "@/components/StoreListCard";

AOS.init();

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
  name: 'Home',
  data() {
    return {
      tab: null,
      items: [
        '조회수', '좋아요', '최신'
      ],
      posts : {
        pid: '',
        title: '',
        content: '',
        publishedTime: '',
        thumbnail:'',
      },
      limit: 1
    }
  },
  created() {
    this.postread('최신')
    // 무한 스크롤
    async function getTopicFromApi() {
          try {
              const init = await fetch(`/api/idol/uwasa/pages/0`, {method: "GET"})
              const data = await init.json()
              return data
          } catch(exc) {
              console.error(exc)
          }
      }
      getTopicFromApi().then(data => {
          console.log("fromAPI", data)
          this.topicData = data
      })
  },
  components: {
    InfiniteLoading,
    Navbar,
    Card,
    StoreListCard
  },
  methods: {
    postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      // this.$router.push({name: 'postdetail', params: {data: one_post}})
      this.$router.push({name: 'postdetail',  params: {data: one_post}})
    },
    postread(item) {
      let option = ""
      if (item === "최신") {
        option = "latest"
      } else if (item === '조회수') {
        option = "hits"
      } else if (item === '좋아요') {
        option = "likes"
      }
      axios.get(API_URL+`api/v2/${option}`)
      .then(({data})=>{
        // 콘텐츠 미리보기 슬라이스
        data.forEach(el => {
          // el.tmp = el.content
          if (el.content.length > 120) {
            // 마크다운 사진 제외
            el.content = el.content.replace(/!\[.*\)+/, "")
          }
          // 작성 날짜만 보이게 수정
          let year = el.publishedTime.slice(0,4);
          let month = el.publishedTime.slice(5,7);
          let day = el.publishedTime.slice(8,10);
          el.publishedTime = year+"년 "+month+"월 "+day+"일"
          this.posts = data
        });
        console.log(data);
      })
      .catch(err => console.log(err))
    },
    // 무한스크롤 핸들러
    infiniteHandler($state) {
      const EACH_LEN = 30
      fetch("/api/idol/uwasa/pages/" + (this.limit), {method: "get"}).then(resp => {
        return resp.json()
      }).then(data => {
        setTimeout(() => {
          if(data.length) {
            this.topicData = this.topicData.concat(data)
            $state.loaded()
            this.limit += 1
            // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면 
            if(data.length / EACH_LEN < 1) {
              $state.complete()
            }
          } else {
            // 끝 지정(No more data)
            $state.complete()
          }
        }, 1000)
      }).catch(err => {
        console.error(err);
      })
    }
  }
}
</script>

<style scoped>
/* @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap'); */
@import url('http://fonts.googleapis.com/earlyaccess/notosanskr.css');

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

</style>
