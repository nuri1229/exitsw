// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import axios from 'axios'
import top from './components/Top.vue'
import bottom from './components/Bottom.vue'

Vue.config.productionTip = false
Vue.use(BootstrapVue)
Vue.prototype.$http = axios

Vue.component('top', top)
Vue.component('bottom', bottom)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
