<template>
  <div>
    <Navbar/>
    <v-container fluid fill-height>
      <div class="reg_do">
        <v-form name="form" @submit.prevent>
          <div class="cont_data">
          <span><h2>Sign up</h2></span>
          <p>DO!를 시작해보세요.</p>
          <div style="border:1px solid #ddd">
          <table v-if="!successful">
            <tr>
              <td class="def">
                아이디
              </td>
              <td>
                <v-text-field
                  solo
                  flat
                  hide-details
                  placeholder="이메일 주소를 입력해주세요"
                  name="email"
                  v-model="user.email"
                  v-validate="'required|email|max:50'"
                  type="email"
                />
                <div class="warn" v-if="submitted && errors.has('email')">
                  {{errors.first('email')}}
                </div>
                <!-- 아이디 중복체크 메세지 -->
                <div class="warn" v-if="message">
                  * {{message}}
                </div>
              </td>
              <td style="width:5%; min-width: 63px; padding-right:10px;">
                <div class="dup_check" @click="dupCheck">
                  <!-- 중복이 아닌 메일을 입력했다가 중복인 메일을 입력했을 때 -->
                  <v-btn x-small depressed color="#6e8af8" class="white--text">                
                    중복체크
                  </v-btn>
                </div>
              </td>
            </tr>
            <tr>
              <td class="def">
                비밀번호
              </td>
              <td>
                <v-text-field
                  solo
                  flat
                  placeholder="영문, 숫자, 특수문자를 조합하여 8자 이상 적어주세요"
                  hide-details
                  name="password"
                  v-model="user.password"
                  v-validate="'required|min:8|max:20'"
                  type="password"
                  ref="password"
                />
                <div class="warn" v-if="submitted && errors.has('password')">
                  * 비밀번호는 8자 이상 입력해주세요
                </div>
              </td>
            </tr>
            <tr>
              <td class="def">
                비밀번호 확인
              </td>
              <!-- <label for="password">비밀번호 확인</label> -->
              <td>
              <v-text-field
                solo
                flat
                placeholder="비밀번호를 한 번 더 입력해주세요"
                hide-details
                type="password"
                name="confirmPassword"
                v-model="user.confirmPassword"
                v-validate="'confirmed:password'"
                data-vv-as="password"
              />
              <div
                class="warn"
                v-if="submitted && errors.has('confirmPassword')"
              >* {{errors.first('confirmPassword')}}</div>
              </td>
            </tr>
            <tr>
              <td class="def">
                닉네임
              </td>
              <!-- <label for="password">비밀번호 확인</label> -->
              <td>
                <v-text-field
                  @keyup.enter="handleRegister"
                  solo
                  flat
                  hide-details
                  placeholder="닉네임을 지어 주세요(3~20자)"
                  name="username"
                  v-model="user.username"
                  v-validate="'required|min:3|max:20'"
                  data-vv-as="password"
                />
                <div
                  class="warn" v-if="submitted && errors.has('username')">
                  * 닉네임은 3자 이상 입력해주세요.
                </div>
              </td>
            </tr>
          </table>
        </div>
          <!-- user field와 중복체크를 했을 때만 활성화-->
          <div class="reg_btn" style="text-align:right;">
            <v-btn 
              rounded
              depressed 
              color="#6e8af8"
              class="white--text"
              style="display:inline-block"
              @click="handleRegister" 
            >
              회원가입
            </v-btn> 
          </div>
          </div>
        </v-form>
      </div>
    </v-container>
  </div>
</template>

<script>
import Navbar from './Navbar.vue'
import User from '../models/user'

import axios from 'axios'
const API_URL = 'http://localhost:8081/'
// const API_URL = 'http://i3a507.p.ssafy.io:8081/'

export default {
  name: 'register',
  components: {
    Navbar,
  },
  data() {
    return {
      user: new User('', '', '', ''),
      isLogin: this.$store.state.isLoggedIn,
      submitted: false,
      successful: false,
      message: '',
      dupcheck: false,//중복체크 했는지
      fillcheck: false,
    }
  },
  mounted() {
    if (this.isLogin) {
      this.$router.push('/')
    }
  },
  watch: {
    fillCheck(){ 
      // user = this.user.email;
      // console.log(user)
      // if(){
      // }
      if(this.user.email && this.user.password && this.user.confirmPassword && this.user.name){
        this.fillcheck= true;
        return true;
      }
      else {
        this.fillcheck = false;
        return false;
      }
    },
  },
  methods: {
    dupCheck(){
      //이메일 정규식
      var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      axios.post(API_URL+'api/emailduplicate',{
        email : this.user.email
      }).then(res=>{
        console.log(res.data)
        if(res.data){
          if(regExp.test(this.user.email)){
            this.dupcheck = true;
            this.message = "사용할 수 있는 이메일입니다."
          }
          else {
            this.dupcheck = false;
            this.message = " 아이디로 사용할 수 없는 이메일 주소입니다. 다시 입력해주세요."
          }
        } 
        else {
          this.dupcheck = false;
          this.message = "이미 사용 중인 이메일입니다. 다시 입력해주세요."
        }
      }).catch(err => console.log(err))
    },
    handleRegister() {
      this.message = ''
      this.submitted = true
      this.$validator.validate().then(valid => {
        if (valid) {
          this.$store.dispatch('register', this.user)
            .then(
              data => {
                this.message = data.message
                this.successful = true
              },
              error => {
                this.message = error.message
                this.successful = false
              })
            .then(
              this.$router.push('/')
          )
        }
      })
    },
  computed: {
    loggedIn() {
      return this.$store.getters.isAuthenticated
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/');
      }
    },
  }
}
</script>

<style scoped>
header {
  background-color:transparent;
}
table {
  width:100%
}
td {
  vertical-align: center;
  text-align: left;
  padding: 18px 0px 16px 19px; 
  /* font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif; */

}
.def{
  width:20%;
  font-family: 'NanumSquare','나눔스퀘어','Noto Sans','Apple SD Gothic',sans-serif;
}
.container {
  padding-top:150px; 
}
/* login 영역 화면 가운데에 고정 어떻게 하지? */
.reg_do{
  width: 100%;
}
.reg_do > span{
  display : inline-block;
}
.v-text-field{
  border-radius: 0;
}
.reg_btn{
  margin : 20px 0 0 0;
}

.v-input{
  margin : 0;
}

.login_append {
  margin : 12px 0 0 0;
  font-size : 13px;
}

.login_append > .v-input {
  display: inline-block;
  /* float: left; */
}

.login_append > span {
  float:right;
  padding-top:7px;
}
.v-input >>> label {
  font-size: 13px;
}

.cont_data {
  text-align: left;
  width: 700px;
  margin: auto;
}

.alert-danger {
  padding: 0 0 0 9px;
  color:red;
}

.warn {
  padding-left: 12px;
  font-size:0.9rem;
}
</style>