<template>
  <div>
    <div id="mapContainer" style="width:100%;height:450px;"/><br>
    <select v-model="sido" v-on:change="getGunguList()">
          <option v-for="s in sidoList" v-bind:value="s.SIDO_CODE">{{s.SIDO}}</option>
      </select>
      <select v-model="gungu" v-on:change="getTotalList()">
          <option v-for="s in gunguList" v-bind:value="s.SIGUNGU_CODE">{{s.SIGUNGU}}</option>
      </select>
  </div>
</template>

<script>
var map
export default {
  name: 'Map',
  data () {
    return {
      sidoList: [],
      sido: '',
      gunguList: [],
      gungu: '',
      totalList: []
    }
  },
  methods: {
    getSidoList: function () {
      const baseURI = '/api/main/main.do'
      this.$http.get(baseURI).then((res) => {
        var responseData = res.data
        this.sidoList = responseData.data
        this.sido = this.sidoList[0].SIDO_CODE
        this.getGunguList()
      }).catch((err) => {
        console.log('err::', err)
      })
    },
    getGunguList: function () {
      const baseURI = '/api/main/gunGu.do'
      this.$http.post(baseURI, {'sido': this.sido, 'nuri':'semi'}).then((res) => {
        var responseData = res.data
        this.gunguList = responseData.data
        this.gungu = this.gunguList[0].SIGUNGU_CODE
        this.getTotalList()
      }).catch((err) => {
        console.log('err::', err)
      })
    },
    getTotalList: function () {
      const baseURI = '/api/main/SidogunGu.do'
      var param = {
        sido: this.sido,
        gungu: this.gungu,
        url: 'http://e-childschoolinfo.moe.go.kr/api/notice/basicInfo.do?'
      }
      this.$http.post(baseURI, param).then((res) => {
        var responseData = res.data
        this.totalList = responseData.data.kinderInfo
        for (var i = 0; i < this.totalList.length; i++) {
          var address = this.totalList[i].addr
          this.geocoder(i, address)
        }
        setTimeout(() => {
          this.loadMap()
        }, 300)
      }).catch((err) => {
        console.log('err::', err)
      })
    },
    loadMap: function () {
      var container = document.getElementById('mapContainer') //지도를 담을 영역의 DOM 레퍼런스
      var options = { //지도를 생성할 때 필요한 기본 옵션
        center: new daum.maps.LatLng(this.totalList[0].lat, this.totalList[0].lng), //지도의 중심좌표.
        level: 6 //지도의 레벨(확대, 축소 정도)
      }
      map = new daum.maps.Map(container, options)
      console.log(this.totalList[0])
      this.makeMarker()
    },
    geocoder: async function (index, address) {
      var geocoder = new daum.maps.services.Geocoder()
      await geocoder.addressSearch(address, (result, status) => {
        if (status === daum.maps.services.Status.OK) {
          this.totalList[index]['lat'] = result[0].y
          this.totalList[index]['lng'] = result[0].x
        }
      })
    },
    makeMarker: function () {
      var positions = []
      for (var i = 0; i < this.totalList.length; i++) {
        var obj = {
          title: this.totalList[i].kindername,
          latlng: new daum.maps.LatLng(this.totalList[i].lat, this.totalList[i].lng)
        }
        positions.push(obj)
      }
      var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      for (var j = 0; j < positions.length; j++) {
        var imageSize = new daum.maps.Size(24, 35);
        var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);
        var marker = new daum.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[j].latlng, // 마커를 표시할 위치
          title: positions[j].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage // 마커 이미지
        })
      }
    }
  },
  mounted () {
    var scriptUrl = '//dapi.kakao.com/v2/maps/sdk.js?appkey=7930d3e1a9dbd297532a9f92cba7d9fb&autoload=false&libraries=services,clusterer,drawing'
    this.$loadScript(scriptUrl).then(() => {
      console.log('script_load_complete')
      daum.maps.load(() => {
        this.getSidoList()
      })
    }).catch((err) => {
      console.log(err)
      console.log('script_load_error')
    })
  },
  created () {

  }
}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

</style>
