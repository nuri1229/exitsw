<template>
  <div>
      <h1>{{msg}}</h1>
      <button class="btn btn-primary" v-on:click="fn_onload()">{{msg}}</button>
      <br>
      <b-table striped hover v-bind:items="output"></b-table>
  </div>
</template>

<script>
export default {
  name: 'Sample',
  data () {
    return {
      msg: 'Sample Page �엯�땲�떎',
      output: []
    }
  },
  methods: {
    fn_onload: function () {
      const baseURI = 'http://e-childschoolinfo.moe.go.kr/api/notice/basicInfo.do?key=0a76b8c490dc4fcfaff0bb1228d22921&sidoCode=11&sggCode=11110'
      this.$http.get(baseURI).then((res) => {
        this.output.push(res.data)
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
