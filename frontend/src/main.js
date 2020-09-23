import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import BootstrapVue from 'bootstrap-vue'
// import 'bootstrap/dist/css/bootstrap.min.css'
// import 'bootstrap-vue/dist/bootstrap-vue.min.css'
// import VeeValidate, { localize  } from 'vee-validate'
import VeeValidate from 'vee-validate'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt
} from '@fortawesome/free-solid-svg-icons'

import vuetify from './plugins/vuetify';

// bootStrap
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt)

Vue.config.productionTip = false

// Vue.use(BootstrapVue)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(VeeValidate)
Vue.use(BootstrapVue)

new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  mounted() {
    this.$validator.localize('en', {
      messages: {
        email: () => "* 유효한 이메일 형식이 아닙니다.",
        required: (field) => '* ' + field + '항목은 필수 항목 입니다',
        confirmed: () =>  "비밀번호가 일치하지 않습니다",
      },
      attributes: {
        email: 'Email'
      } 
    })
  },
  render: h => h(App)
})//.$mount('#app')
