<template>
  <div style="width:100%;text-align:center;height:50px;line-height:50px;background:#333333;">
    <ul>
      <li style="margin-left:25px;" v-on:mouseover="mouseOverEvent" v-on:mouseout="mouseOutEvent" onclick="location.href='/'">
        유치원공시정보
      </li>
      <li v-on:mouseover="mouseOverEvent" v-on:mouseout="mouseOutEvent" onclick="location.href='/sample'">메뉴1</li>
      <li v-on:mouseover="mouseOverEvent" v-on:mouseout="mouseOutEvent">메뉴2</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Top',
  data () {
    return {
      noticeMessage: ''
    }
  },
  methods: {
    mouseOverEvent: function (event) {
      if (event.target.tagName !== 'A') {
        event.target.classList.add('mouseOverClass')
      }
    },
    mouseOutEvent: function (event) {
      event.target.classList.remove('mouseOverClass')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
ul {
    list-style:none;
    margin:0;
    padding:0;
}

li {
    padding: 0px 20px 0px 20px;
    border : 0;
    float: left;
    color:#aaaaaa;
    font-size:16px;
    font-weight: bold;
}

.mouseOverClass {
  color: #ffffff;
  background:#444444;
}

</style>
