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
                      @click="onSubmit"
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
    
		<section id="list" class="sec-list">
        <div class="container">
          <h1>검색 리스트</h1>
          <hr />
        <v-app id="list-sample">
          
           <v-list two-line
              v-for="(listItem, index) in listData"
              :key="index">
          <v-list-tile>
          <v-list-tile-content>
            <v-list-tile-title class="text--primary">
              {{ listItem.guide_title }}
            </v-list-tile-title>
            <v-list-tile-sub-title>
              {{ listItem.guide_type }}
            </v-list-tile-sub-title>
          </v-list-tile-content>
          </v-list-tile>
           <v-divider></v-divider>
           </v-list>
           <br/>
           <v-pagination
        v-model="curPageNum"
        :length="numOfPages">
      </v-pagination>

        </v-app>
        </div>
      </section>
       
      </main>

    
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'

import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'
// import InfiniteLoading from 'vue-infinite-loading';
import Card from "@/components/Card";
import StoreListCard from "@/components/StoreListCard";

AOS.init();

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
// const API_URL = 'http://localhost:8081/'
// const storage = window.sessionStorage

export default {
  name: 'Home',
  data() {
    return {
      listData: [],
      dataPerPage: 10,
        curPageNum: 1,
    }
  },
  created() {
     axios.get('http://localhost:8080/guide/list')
        .then((response) => {
          this.listData = response.data;
          console.log(this.listData);
        });
         //this.listData = testData
  },
  components: {
    // InfiniteLoading,
    Navbar,
    Card,
    StoreListCard
  },
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
