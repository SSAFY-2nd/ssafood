<template>
  <div id=app>
    <!-- 네비게이션 바 -->
    <Navbar></Navbar>
    <div class="toptab">
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
    </div>
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
</style>
