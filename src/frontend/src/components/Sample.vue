<template>
  <div>
      <h1>{{msg}}</h1><button><router-link to="/main">Go to Mainr</router-link></button>
      <button class="btn btn-primary" v-on:click="fn_get_api()">{{msg}}</button>
      <br>
      <b-table striped hover v-bind:items="output"></b-table>
      <b-table striped hover v-bind:items="output2" style="width:100%"></b-table>
  </div>
</template>

<script>
export default {
  name: 'Sample',
  data () {
    return {
      msg: 'Sample Page �엯�땲�떎222222',
      output: [],
      output2: []
    }
  },
  methods: {
    fn_onload: function () {
      const baseURI = '/api/sample/json.do'
      this.$http.get(baseURI).then((res) => {
        var responseData = res.data
        this.output = responseData.data
      }).catch((err) => {
        console.log('err::', err)
      })
    },
    fn_get_api: function () {
      const baseURI = '/api/sample/apiSample.do'
      const apiURL = 'http://e-childschoolinfo.moe.go.kr/api/notice/basicInfo.do?sidoCode=11&sggCode=11110'
      this.$http.post(baseURI, {'url': apiURL}).then((res) => {
        var responseData = res.data
        this.output2 = responseData.data.kinderInfo
      }).catch((err) => {
        console.log('err::', err)
      })
    }
  },
  created () {
    this.fn_onload()
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
