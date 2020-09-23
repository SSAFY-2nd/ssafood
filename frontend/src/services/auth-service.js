import axios from 'axios'
import store from '../router/index.js'

// const API_URL = 'http://i3a507.p.ssafy.io:8081/'
const API_URL = 'http://localhost:8081/'
const storage = window.sessionStorage

class AuthService {
    // 로그인, 로그아웃, 접속 체크
    // Login
    login(user) {
        // this.loading = true
        // this.$validator.validateAll()
  
        // if (this.errors.any()) {
        //   this.loading = false
        //   return
        // }
        if (user.email && user.password) {
          storage.setItem("jwt-auth-token", "")
          storage.setItem("login_user", "")
          storage.setItem("uid", "")
          storage.setItem("google_login", "")
          axios.post(API_URL+'api/signin', {
            email: user.email,
            password: user.password
          }).then(res => {
              if (res.data.status) {
                this.message = res.data.request_body.email + "로 로그인 되었습니다."
                storage.setItem("jwt-auth-token", res.data["jwt-auth-token"])
                storage.setItem("login_user", res.data.request_body.email)
                storage.setItem("uid", res.data.request_body.uid)
                storage.setItem("google_login", false)
                store.app.$store.commit('logincheck')
                // this.$router.push('/')
              } else {
                alert("입력 정보를 확인해주세요.")
              }
            })
            .catch(err => console.log(err))
        } else {
          alert('이메일 또는 패스워드를 입력하지 않았습니다.')
        }
      }

    // Logout
    logout() {
        storage.setItem("jwt-auth-token", "")
        storage.setItem("login_user", "")
        storage.setItem("uid", "")
        storage.setItem("google_login", "")
        // this.message = "로그인 해주세요"
        // this.setInfo("로그아웃 성공", "", "")
    }
    // 

    // User CRUD
    // C: User
    register(user){
        return axios.post(API_URL + 'api/v1/', {
            nickname: user.username,
            email: user.email,
            password: user.password,
        })
    }
    // R: User
    // detail(user){
    //     return axios.get(API_URL + `api/v1/${user.id}`)
    // }
    detail() {
        return axios.get(API_URL + `api/v1/`)
    }
    // U: User
    update(){
        return axios.put(API_URL + 'api/v1/', {
            // Body, 유저가 수정한 정보만 업데이트
            // 백엔드랑 상의하고 정하자
        })
    }
    // D: User
    delete(user){
        // uid를 이렇게 확인해서 받아온 뒤에 가야하나?
        // let tmp_uid = 
        // if (tmp_uid !== null) {}
        return axios.delete(API_URL + `api/v1/${user.id}`)
    }


    // ??
    // handelResponse(response) {
    //     if(response.status === 401) {
    //         this.logout()
    //         location.reload(true)
    //         const error = response.data && response.data.message
    //         return Promise.reject(error)
    //     }
    //     return Promise.resolve(response)
    // }
}

export default new AuthService()