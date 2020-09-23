<template>
    <div>
    <Navbar></Navbar>
    <div class="doContent">
      <div class="feature_feed">
        <div class="inner_feature">
          <h2 id="do_top do_feed">Notice</h2>
          <p class="top_desc">공지사항과 새로운 업데이트 소식을 게시합니다</p>
        </div>
      </div>
      <div class="content">
        <v-row v-if=posts.length>
          <v-col cols="4" v-for="(post, index) in posts" :key="index+'_posts'">
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
                    <!-- <v-card-subtitle class="pb-0">{{ item }}</v-card-subtitle> -->
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
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import AOS from 'aos'
import 'aos/dist/aos.css'

AOS.init();

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

export default {
  name: 'Notice',
  components: {
    Navbar,
  },
  data() {
    return{
      posts : {
        pid: '',
        title: '',
        content: '',
        publishedTime: '',
        thumbnail:'',
      }
    }
  },
  created() {
    this.postread()
  },
  methods: {
    postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      this.$router.push({name: 'postdetail', params: {data: one_post}})
    },
    postread() {
      axios.get(API_URL+`/api/v2/notice`)
      .then(({data})=>{
        // 콘텐츠 미리보기 슬라이스
        data.forEach(el => {
          el.tmp = el.content
          if (el.content.length > 40) {
            // 마크다운 사진 제외
            el.content = el.content.replace(/!\[.*\)+/, "")
            // 최대 길이 슬라이스
            el.content = el.content.slice(0, 40)
          }
          // 작성 날짜만 보이게 수정
          let year = el.publishedTime.slice(0,4);
          let month = el.publishedTime.slice(5,7);
          let day = el.publishedTime.slice(8,10);
          el.publishedTime = year+"년 "+month+"월 "+day+"일"
          this.posts = data
        });
      })
      .catch(err => console.log(err))
    }
  }
}
</script>

<style>
.inner_feature h2 {
  font-family: 'Inter', sans-serif;
  font-size: 24px;
  font-weight: 800;
}
.top_desc {
  padding:0 0;
  font-size: 20px;
  line-height: 1;
  letter-spacing: -0.6px;
  font-family: Noto Sans Light, sans-serif;
}
.info_feed {
  float: right;
  margin: -39px 0 0;
  color: #909090;
  font-size: 13px;
}
.feed_cnt {
  display: inline-block;
  font-size: 24px;
  vertical-align: middle;
  padding: 0 0 0 4px;
}
.doContent {
  max-width: 1150px;
  min-width: 1100px; 
  margin: 0 auto 0;
  padding: 0 20px;
}
.feature_feed{
  height: 133px;
  padding: 151px 0 110px;
  border-bottom: solid 2px black;;
}
.feature_main {
  width: 100%;
  height: 133px;
  padding: 151px 0 0;
}
.content {
  min-width: 1100px; 
  padding: 30px 0 0;
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