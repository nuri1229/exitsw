<template>
  <div>
      <h1>{{msg}}</h1>
      <button> <router-link to="/">Go to Bar</router-link></button>
      <select v-model="sido" v-on:change="getGunguList()">
          <option v-for="s in sidoList" v-bind:value="s.SIDO_CODE">{{s.SIDO}}</option>
      </select>
      <select v-model="gungu" v-on:change="getTotalList()">
          <option v-for="s in gunguList" v-bind:value="s.SIGUNGU_CODE">{{s.SIGUNGU}}</option>
      </select>
        <button v-on:click="test()">button</button><br><br>
        <div style="width:300px; background: black; color:white;">{{sample}}</div>
        <input type="text" v-model="sample">

  </div>
</template>

<script>

export default {
  name: 'Sample',
  data () {
    return {
      msg: 'Main Page',
      sidoList: [],
      sido: '',
      sample: '',
      gunguList :[],
      gungu :''
    }
  },
  methods: {
    fn_onload: function () {
      const baseURI = '/api/main/main.do'
      this.$http.get(baseURI).then((res) => {
        var responseData = res.data
        console.log('res::', responseData) 
        this.sidoList=responseData.data
      }).catch((err) => {
        console.log('err::', err)
      })
    },
    test: function () {
        alert(this.sido)
    }, 
    getGunguList : function(){
        alert(this.sido);
      const baseURI = '/api/main/gunGu.do'
      this.$http.post(baseURI, {'sido': this.sido, 'nuri':'semi'}).then((res) => {
        var responseData = res.data
        console.log("responseData" , responseData )
        this.gunguList = responseData.data
      }).catch((err) => {
        console.log('err::', err)
      })
    },
        getTotalList : function(){
        alert(this.sido);
      const baseURI = '/api/main/SidogunGu.do'
      var param = {
          sido: this.sido,
          gungu: this.gungu,
          url: 'http://e-childschoolinfo.moe.go.kr/api/notice/basicInfo.do?'
      }
      console.log(param)
      this.$http.post(baseURI, param).then((res) => {
        var responseData = res.data
        console.log("responseData" , responseData )
      }).catch((err) => {
        console.log('err::', err)
      })
    }

  },
  created () {
      this.fn_onload();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
