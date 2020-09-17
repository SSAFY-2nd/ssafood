<template>
  <div>
    <Navbar></Navbar>
    <div class="inner">
      <div class="blog_info">
        <div class="profile_img">
          <img v-bind:src=profileImage class="thumb_profile" alt>
        </div>
        <div class="profile_user">
          {{nickname}}
          <p>
            {{introduce}}
          </p>
        </div>
      </div>
      <div class="line"></div>
      <div class="sns_link">
        <div v-if="github" class="sns_addr">
          <a :href="github"><v-icon style="padding-right:5px" large>fab fa-github</v-icon></a>
        </div>
        <div v-if="instagram" class="sns_addr">
          <a :href="instagram"><v-icon style="padding-right:5px" large>fab fa-instagram</v-icon></a>
        </div>
        <div v-if="facebook" class="sns_addr">
          <a :href="facebook"><v-icon style="padding-right:5px" large>fab fa-facebook</v-icon></a>
        </div>
      </div>
      <div class="toptab">
        <v-tabs
          v-model="tab"
					background-color="transparent"
					centered
					color="black"
					slider-color="#6987f7"
					slider-size="3"
				>
					<v-tab @click="postread(item)" v-for="item in items" :key="item">
						{{ item }}
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
          </v-tab-item>
        </v-tabs-items>
      </div>
    </div>
    </div>
  </div>
</template>

<script>

const storage = window.sessionStorage
import Navbar from '../components/Navbar.vue'
import axios from 'axios'

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
  name: 'Myblog',
  components: {
		Navbar,
  },
  data(){
    return{
      profileImage: storage.getItem("profileImage"),
      nickname: storage.getItem("login_user"),
      introduce : '',
      github: '',
      facebook: '',
      instagram: '',
      tab: null,
      items: [
        '내글', '임시저장',
      ],
      posts : {
        pid: '',
        title: '',
        content: '',
        publishedTime: '',
        thumbnail:'',
      }
    }
  },
  created(){
    var uid = sessionStorage.uid;
		axios
		.get(API_URL+'api/v1/'+uid)
		.then(({data})=>{
      this.introduce = data.introduce
      this.github = 'https://github.com/'+data.github
			this.facebook = 'https://facebook.com/'+data.facebook
			this.instagram = 'https://instagram.com/'+data.instagram
    })
    this.postread("내글")
  },
  methods: {
    postread(item){
      var uid = sessionStorage.uid;
      this.posts=[]
      if (item === "내글") {
        axios.get(API_URL+'api/v2/u/'+uid)
          .then(({data})=>{
            data.forEach(el => {
            el.tmp = el.content
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
          })
      } else if (item === '임시저장') {
        axios.get(API_URL+'api/v2/temp/'+uid)
          .then(({data})=>{
          // 콘텐츠 미리보기 슬라이스
            data.forEach(el => {
              el.tmp = el.content
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
          }).catch(err => console.log(err))
      }
    },
    postdetail(one_post) {
      storage.removeItem("pid")
      storage.setItem("pid", one_post.pid)
      // 임시 저장
      if(one_post.isTemp){
        this.$router.push({name: 'texteditor', params: {data: one_post}})
      }else if(!one_post.isTemp){
        this.$router.push({name: 'postdetail', params: {data: one_post}})
      }
    },
  }
}
</script>

<style>
.inner {
  min-width: 1100px;
  max-width: 1150px;
  padding: 130px 20px 0px 20px;
  margin: 0 auto;
}
.blog_info{
  display: flex;
  align-items: center;
}
.profile_img{
  box-sizing: border-box;
}
.profile_img img{
  display: block;
  width: 8rem;
  height: 8rem;
  object-fit: cover;
  box-shadow: rgba(0, 0, 0, 0.06) 0px 0px 4px 0px;
  border-radius: 50%;
  
}
.profile_user{
  display: flex;
  flex-direction: column;
  -webkit-box-pack: center;
  justify-content: center;
  margin-left: 1rem;
}
.line {
  width: 100%;
  height: 1px;
  margin-top: 2rem;
  margin-bottom: 1.5rem;
  background: rgb(233, 236, 239);
}
.sns_link {
  display: flex;
}
.sns_addr a i {
  margin: 0 10px 0 0;
}
</style>