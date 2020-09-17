<template>
  <div id="#app">
    <!-- 네비게이션 바 -->
    <Navbar></Navbar>
    <!-- 유효성 검사 추가해야함 -->
    <!-- navbar end -->

    <v-container fluid fill-height>
      <div class="login_do">
        <span><h2><span class="logo">DO!</span>에 로그인하세요</h2></span>
        <v-form name="form" @submit.prevent style="width:318px">
          <v-text-field
            outlined
            placeholder="email"
            hide-details
            name="email"
            v-model="email"
          >
          </v-text-field>
          <v-text-field
            @keyup.enter="login"
            type="password"
            outlined
            placeholder="password"
            hide-details 
            sytle="top:-1px;"
            name="password"
            v-model="password"
          >
          </v-text-field>
          
          <div class="login_append">
            <v-checkbox
              hide-details
              dense
              label="아이디/비밀번호 저장"
              color="#6e8af8"
            ></v-checkbox>
            <span>아이디/비밀번호 찾기</span> 
          </div>
          <div class="login_btn" @click="login">
            <v-btn 
              block 
              depressed 
              color="#6e8af8"
              height="48px"
              class="white--text"
            >
              로그인
            </v-btn>
          </div>
        </v-form>
        <!-- 소셜 로그인 단 -->
        <div class="google mt-2">
          <v-btn 
            block 
            depressed 
            color="#ff7f00"
            height="48px"
            class="white--text"
          >
            <GoogleLogin class="big-button" :renderParams="renderParams" :params="params" :onSuccess="onSuccess" :onFailure="onFailure"> Google Login</GoogleLogin>
          </v-btn>
        </div>
      </div>
    </v-container>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'
import axios from 'axios'
import GoogleLogin from 'vue-google-login';

const storage = window.sessionStorage
const API_URL = 'http://localhost:8081/'
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
  name: 'login',
  components: {
    Navbar,
    GoogleLogin
  },
  computed: {
  },
  data() {
    // user: new User('', ''),
    return {
      isLogin: this.$store.state.isLoggedIn,
      email: "",
      password: "",
      loading: false,
      message: "",
      renderComponent: true,
      params :{
        client_id : '464615746303-dj9932cqlcukr92o1ig3eci1ubc5vlui.apps.googleusercontent.com',
        scope : 'email profile'
      },
    }
  },
  methods: {
    login() {
      if (this.email && this.password) {
        storage.setItem("jwt-auth-token", "")
        storage.setItem("login_user", "")
        storage.setItem("email", "")
        storage.setItem("uid", "")
        storage.setItem("google_login", "")
        storage.setItem("profileImage", "")
        axios.post(API_URL+'api/signin', {
          email: this.email,
          password: this.password
        }).then(res => {
            if (res.data.status) {
              this.message = res.data.request_body.email + "로 로그인 되었습니다."
              storage.setItem("jwt-auth-token", res.data["jwt-auth-token"])
              storage.setItem("login_user", res.data.request_body.nickname)
              storage.setItem("email", res.data.request_body.email)
              storage.setItem("uid", res.data.request_body.uid)
              storage.setItem("google_login", false)
              storage.setItem("profileImage", res.data.request_body.profileImage)
              this.$router.push('/')
            } else {
              this.message = "로그인해주세요."
              alert("입력 정보를 확인해주세요.")
            }
          })
      } else {
        alert('이메일 또는 패스워드를 입력하지 않았습니다.')
      }
    },
    
    // 로그인 했는지 체크
    init() {
      // 스토리지에 토큰 있으면
      if (storage.getItem("jwt-auth-token")) {
        this.message = storage.getItem("login_user") + "로 로그인 되었습니다."
      } else {
        storage.setItem("jwt-auth-token", "");
      }
    },
    forceRender() {
      this.renderComponent = false
      this.$nextTick(() => {
        this.renderComponent = true
      })
    },
    onSuccess(googleUser){
      storage.setItem("jwt-auth-token", "")
      storage.setItem("login_user", "")
      storage.setItem("uid", "")
      storage.setItem("google_login", "")
      let id_token = googleUser.wc.id_token;
      const config = {
        headers: {Authorization: `Bearer ${id_token}`}
      }
      axios.post(API_URL + 'googlelogin', null, config)
      .then(res => {
        storage.setItem("jwt-auth-token", res.data["jwt-auth-token"])
        storage.setItem("login_user", res.data.request_body.email)
        storage.setItem("uid", res.data.request_body.uid)
        storage.setItem("google_login", true)
        storage.setItem("profileImage", res.data.request_body.profileImage)
        // storage.setItem("profileImage", res.data.request_body.profileImage)
        this.$router.push('/')
      })
      // .catch((err) => console.log(err))
    }
  },
  mounted() {
    this.init()
    // 로그인한 상태로 로그인 페이지에 진입하면 홈으로 돌려보냄
    if (this.isLogin) {
      this.$router.push('/')
    }
  },
}
</script>

<style scoped>
header {
  background-color:transparent;
}

.container {
  padding-top:120px; 
}
/* login 영역 화면 가운데에 고정 어떻게 하지? */
.login_do{
  text-align: center;
  margin : 50px auto;
}
.login_do > span{
  display : inline-block;
  margin-bottom : 25px;
  font-family: 'NanumSquareR','나눔스퀘어', "나눔고딕", NanumGothic, -apple-system, BlinkMacSystemFont,"Apple SD Gothic Neo","Helvetica Neue", Helvetica, Arial, sans-serif;
}
.v-text-field{
  border-radius: 0;
}
.login_btn{
  margin : 60px 0 0 0;
}

.v-input{
  margin : 0;
}

.login_append {
  /* margin : 12px 0 0 0; */
  font-size : 13px;
}

.login_append > .v-input {
  float: left;
}

.login_append > span {
  float:right;
  padding-top:12px;
}
.v-input >>> label {
  font-size: 13px;
  margin-top:7px;
}
.google {
  padding: 0;
}
.big-button {
  margin: 0 0;
  padding: 15px 101px;
}
.logo {
  font-family: 'Inter', sans-serif;
  font-weight: 800;
}
</style>