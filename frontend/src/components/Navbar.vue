<template>
  <v-app-bar
      fixed
      color="white"
      elevate-on-scroll
			height="90px"
			justify
  >
    <h1 class="logo_title"><router-link to="/">DO!</router-link></h1>
    <v-toolbar-items style="align-items: center">
      <router-link to="/introduce">소개</router-link>
    </v-toolbar-items>
    <v-toolbar-items style="align-items: center">
      <router-link to="/forum">포럼</router-link>
    </v-toolbar-items>
    <v-toolbar-items style="align-items: center">
      <router-link to="/feed">피드</router-link>
    </v-toolbar-items>


      <div class="notice">
        <v-icon small>fas fa-volume-down</v-icon>
        <div class="banner" @click="gotonotice">
          {{list.title}}
        </div>
			</div>
			<v-spacer></v-spacer>
      <router-link to="/search">
      <v-icon small color="black" class="icon" style="margin-top:5px;">fas fa-search</v-icon>
      </router-link>
      <!-- 로그인 안했을 때 -->
      <div v-if="!isLogin" class="login">
				<span><router-link to="/login">로그인</router-link></span>
        <span>/</span>
        <span><router-link to="/register">회원가입</router-link></span>
      </div> 
      <!-- 로그인 했을때 -->
      <!-- <div v-if="isLogin" class="info_blog">
        <v-icon small color="black" class="icon">fas fa-bell</v-icon>
      </div> -->
      <div v-if="isLogin" class="newpost">
        <router-link to="/texteditor"><v-btn rounded outlined depressed color="#6e8af8">새 글 작성</v-btn></router-link>
      </div>
      <div v-if="isLogin" class="info_blog"> 
        <div @click="profileshow">
            <img v-bind:src=profileImage class="thumb_profile" alt>
        </div>
        <!--프로필 클릭시 팝업되는 드롭다운 메뉴-->
        <div class="shortcuts" id="shortcuts" v-if="profile">
          <div class="inner_shortcuts">
            <div class="info_profile">
              <span class="user_nickname">{{nickname}}</span>
              <span class="user_email">{{userEmail}}</span>
            </div>
            <div class="list_menu">
              <div class="myblog">
                <router-link :to="{path: `/doblog/${this.uid}`}">내 블로그</router-link>
              </div>
              <div class="setting">
                <router-link to="/setting">설정</router-link>
              </div>
              <div class="logout" v-if="!google_login" @click="onClickLogout">
                로그아웃
              </div>
              <div class="logout" v-if="google_login" @click="onClickLogout">
                <GoogleLogin :params="params" :logoutButton=true>로그아웃</GoogleLogin>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="isLogin" class="info_blog" @click="profileshow"> 
        <v-icon small color="black" style="margin: 20px 4px 0px 5px">fas fa-angle-down</v-icon>
      </div>
  </v-app-bar>
</template>

<script>
const storage = window.sessionStorage
import GoogleLogin from 'vue-google-login';
import axios from 'axios';

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'

export default {
  name: 'Navbar',
  component: {
    GoogleLogin,

  },
  data() {
    return {
      uid: storage.getItem("uid"),
      nickname: storage.getItem("login_user"),
      userEmail: storage.getItem("email"),
      profile: false,
      isLogin: this.$store.state.isLoggedIn,
      google_login: storage.getItem("google_login"),
      profileImage: storage.getItem("profileImage"),

      list: {
        title:''
      },
      index: 0,
      text: '',
      speed: 2000
    }
  },
  computed: {
    // 현재 로그인 한 상태인지 아닌지 체크
    init() {
      this.loginChecker()
      return this.$store.state.isLoggedIn
    }
  },
  methods: {
    onClickLogout() {
      // LOGOUT 변이 실행 후 리다이렉트
      this.$store.dispatch('logout')
      this.isLogin = this.$store.getters.isAuthenticated
    },
    loginChecker() {
      this.$store.commit('logincheck')
      this.isLogin = this.$store.getters.isAuthenticated
    },
    forceRender() {
      this.renderComponent = false
      this.$nextTick(() => {
        this.renderComponent = true
      })
    },
    profileshow() {
      this.profile = !this.profile
    },
    gotonotice(){
      this.$router.push({path:'notice'})
    }
  },
  created() {
    this.loginChecker()
    //공지사항 받아옴
    axios.get(API_URL + 'api/v2/notice')
        .then(res => {
          // this.post = res.data
          // pid를 활용하여 게시글로 이동
          this.list.title = res.data[0].title
    })
    sessionStorage.setItem("noticelist",this.list.title)
  },
  mounted(){
    // this.text = this.list[this.index];
    this.loginChecker();
    this.text = "공지사항";
    setInterval(this.getTextFromList, this.speed);
  },

  // watch: {
  //   isLogin (val, prev) {
  //     if (val !== prev) {
  //       val = this.$store.getters.isAuthenticated
  //     }
  //   
  // }
}
</script>

<style scoped>
@import url('https://rsms.me/inter/inter.css');
@import url(http://fonts.googleapis.com/earlyaccess/notosanskr.css);

header{
	padding : 0 4rem;
  min-width:1100px;
}
h1{
	font-size : 2em;
	font-family: 'Inter', sans-serif;
  font-weight: 800;
}
a{
  color : black !important;
  text-decoration: none !important;
}
button > span {
  font-family: 'Noto Sans DemiLight', 'sans-serif';
  font-size: 13px;
}
div > .newpost > a >button{
  font-family: 'Noto Sans DemiLight', 'sans-serif';
  font-size: 13px;
}
div > .newpost > a >button:hover {
  background-color:#6e8af8;
  color:white !important; 
}
.logo_title{
	display : inline-block;
	margin: 0 43px 0 0;
	vertical-align: top;
}
.v-toolbar__items{
	font-size: 16px;
	font-family: 'Noto Sans KR', 'sans-serif';
	font-weight: 400;
	/* margin: 0 40px 0 0; */
}
.login span a{
	color: black;
	font-size: 13px;
	font-family: 'Noto Sans DemiLight', 'sans-serif';
	text-decoration: none;
}
.notice {
  display: flex;
  margin : 0px 0px 0px 54px ;
	/* padding: 0px 0px 0px 0px; */

}
.banner {
  width: 100%;
  height: 1.5rem;
  overflow: hidden;
  margin:0 auto; 
  padding-left:8px; 
}
.rolling {
  position: relative;
  width: 100%;
  height: auto;
  margin: 0px; 
  padding:0; 
  list-style: none; 
}
.rolling li {
  margin:0; 
  padding:0;
  font-family: 'NanumSquare', Noto Sans Light, sans-serif;
}
.notice::before {
  content:'|';
  padding-right: 24px;
}
.notice i {
	color : black;
}
.notice span {
	margin-left: 10px;
	font-size: 13px;
	font-family: 'Noto Sans DemiLight', 'sans-serif';
}
.icon{
  display: inline-block;
  margin: 0px 31px 0px 0px;
}
.thumb_profile{
  display: inline-block;
  width:50px;
  height:50px;
  border-radius:50%;
}
.shortcuts{
  position : absolute;
  top: 85px;
  width: 230px;
  right: 15px;
  border-radius: 3px;
  background-color: white;
  box-sizing: content-box;
  box-shadow: 0 2px 5px rgba(0,0,0,.1), 0 0 1px rgba(0,0,0,.3)
}
.inner_shortcuts{
  padding: 20px 0 15px 25px
}
.info_profile{
  padding-bottom: 20px;
}
.user_nickname{
  font-size:16px;
  display: block;
  font-family: 'NanumSquare', Noto Sans Light, sans-serif;
  font-weight: 800;
  overflow: hidden;
  text-overflow: ellipsis;
}
.user_email{
  font-family: Noto Sans Light, sans-serif;
  overflow: hidden;
  padding: 2px 0 0;
  text-overflow: ellipsis;
  white-space:nowrap;
  color:#909090;
  font-size:14px;
}
.list_menu{
  font-family: 'NanumSquare', Noto Sans Light, sans-serif;
  font-size: 14px;
}
.myblog, .setting, .logout {
  padding: 3px 0 8px ;
  cursor: pointer;
}
.newpost {
  margin-right:20px;
}
.v-toolbar__items{
  padding: 0 7px 0 17px;
}
</style>