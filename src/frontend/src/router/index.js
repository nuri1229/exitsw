import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Sample from '@/components/Sample'
import Sample2 from '@/components/Sample2'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Sample',
      component: Sample
    },
    {
      path: '/sample2',
      name: 'Sample2',
      component: Sample2
    }
  ]
})
