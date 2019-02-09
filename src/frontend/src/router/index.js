import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Sample from '@/components/Sample'
import Sample2 from '@/components/Sample2'
import main from '@/components/main'
import map from '@/components/map'

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
    },
    {
      path: '/main',
      name: 'main',
      component: main
    },
    {
      path: '/map',
      name: 'map',
      component: map
    }
  ]
})
