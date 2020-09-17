import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/Home.vue'
import Login from '../components/Login.vue';
import Register from '../components/Register.vue'
import TextEditor from '../components/TextEditor.vue'
import Setting from '../components/Setting.vue'
import PostDetail from '../components/PostDetail.vue'
import Introduce from '../components/Introduce.vue'
import Search from '../components/Search.vue'
import Feed from '../components/Feed.vue'
import Forum from '../components/Forum.vue'
import Myblog from '../components/Myblog.vue'
import Notice from '../components/Notice.vue'

Vue.use(VueRouter)
import store from '../router/index.js'

const requireAuth = () => (from, to, next) => {
  if (store.app.$store.getters.isAuthenticated) {
    return next() // isAuth === true면 페이지 이동
  }
  // 홈 화면으로 제대로 보내지지 않는 문제
  return next('/') // isAuth === false면 다시 로그인 화면으로 이동
}


export default new VueRouter({
  mode: 'history'
  , routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path:'/introduce',
      name:'introduce',
      component: Introduce
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/texteditor',
      name : 'texteditor',
      component: TextEditor
    },
    {
      path: '/setting',
      name : 'setting',
      component: Setting,
      beforeEnter: requireAuth()
    },
    {
      path: '/postdetail',
      name: 'postdetail',
      component: PostDetail
    },
    {
      path: '/search',
      name: 'search',
      component: Search
    },
    {
      path:'/feed',
      name:'feed',
      component: Feed
    },
    {
      path:'/forum',
      name:'forum',
      component: Forum
    },
    {
      path:'/doblog/:username',
      name:'myblog',
      component: Myblog,
    },
    {
      path:'/notice',
      name:'notice',
      component: Notice,
    }
  ]
})
