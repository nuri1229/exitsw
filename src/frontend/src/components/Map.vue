<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-6" >
        <select v-model="sido" v-on:change="getGunguList()" class="form-control" placeholder="시,도를 선택하세요">
              <option v-for="s in sidoList" v-bind:value="s.SIDO_CODE">{{s.SIDO}}</option>
          </select>
          <select v-model="gungu" v-on:change="getTotalList()" class="form-control" placeholder="군,구를 선택하세요">
              <option v-for="s in gunguList" v-bind:value="s.SIGUNGU_CODE">{{s.SIGUNGU}}</option>
          </select>
          <div class="table-wrapper-scroll-y">
          <table class="table table-hover">
            <thead>
              <tr>
                <td style="text-align:right;" colspan="3">총 <span style="color:#3897F0;font-weight:bold">{{totalList.length}}</span>개의 유치원이 검색되었습니다</td>
              </tr>
              <tr>
                <td style="font-weight: bold;">
                  <div style="width:60%;display:inline-block">유치원</div>
                  <div style="width:17%;display:inline-block">설립구분</div>
                  <div style="width:17%;display:inline-block">전화번호</div>
                </td>
              </tr>
            </thead>
            <tbody>
              <template v-for="(kindergarten, index) in totalList">
                <tr v-bind:key="kindergarten.key" v-on:click="rowClickEvent(index)">
                  <td>
                    <div style="width:60%;display:inline-block">{{kindergarten.kindername}}</div>
                    <div style="width:17%;display:inline-block">{{kindergarten.establish.substring(0,2)}}</div>
                    <div style="width:17%;display:inline-block">{{kindergarten.telno}}</div>
                    <div style="margin-top:20px;background:#ffffff;display:none;border-radius:5px;padding10px;border:1px solid #888888" class="detailDiv">
                      <table class="table">
                        <tr>
                          <td>유치원</td>
                          <td align="right">{{kindergarten.kindername}}</td>
                        </tr>
                        <tr>
                          <td>교육청</td>
                        <td align="right">{{kindergarten.officeedu}}</td>
                        </tr>
                        <tr>
                          <td>개원일</td>
                        <td align="right">{{kindergarten.odate}}</td>
                        </tr>
                        <tr>
                          <td>주소</td>
                        <td align="right">{{kindergarten.addr}}</td>
                        </tr>
                        <tr>
                          <td>홈페이지</td>
                          <td align="right">{{kindergarten.hpaaddr}}</td>
                        </tr>
                        <tr>
                          <td>운영시간</td>
                          <td align="right">{{kindergarten.opertime}}</td>
                        </tr>
                        <tr>
                          <td>만 3세 학급수</td>
                          <td align="right">{{kindergarten.clcnt3}}</td>
                        </tr>
                        <tr>
                          <td>만 4세 학급수</td>
                          <td align="right">{{kindergarten.clcnt4}}</td>
                        </tr>
                        <tr>
                          <td>민 5세 학급수</td>
                          <td align="right">{{kindergarten.clcnt5}}</td>
                        </tr>
                        <tr>
                          <td>혼합학급수</td>
                          <td align="right">{{kindergarten.mixclcnt}}</td>
                        </tr>
                        <tr>
                          <td>특수학급수</td>
                          <td align="right">{{kindergarten.shclcnt}}</td>
                        </tr>
                        <tr>
                          <td>만3세유아수</td>
                          <td align="right">{{kindergarten.ppcnt3}}</td>
                        </tr>
                        <tr>
                          <td>만4세유아수</td>
                          <td align="right">{{kindergarten.ppcnt4}}</td>
                        </tr>
                        <tr>
                          <td>만5세유아수</td>
                          <td align="right">{{kindergarten.ppcnt5}}</td>
                        </tr>
                        <tr>
                          <td>혼합유아수</td>
                          <td align="right">{{kindergarten.mixppcnt}}</td>
                        </tr>
                        <tr>
                          <td>특수유아수</td>
                          <td align="right">{{kindergarten.shppcnt}}</td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>
          </div>
      </div>
      <div class="col-6">
        <div id="mapContainer" style="width:100%;height:600px;"/>
      </div>
    </div>
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
      totalList: [],
      rowIndex: 0
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
        center: new daum.maps.LatLng(this.totalList[this.rowIndex].lat, this.totalList[this.rowIndex].lng), //지도의 중심좌표.
        level: 5 //지도의 레벨(확대, 축소 정도)
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
      for (var j = 0; j < positions.length; j++) {
        var marker = new daum.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[j].latlng, // 마커를 표시할 위치
          title: positions[j].title // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        })
      }
    },
    rowClickEvent: function (index) {
      if ($('.detailDiv').eq(index).hasClass('on')) {
        $('.detailDiv').eq(index).removeClass('on')
        $('.detailDiv').eq(index).slideUp('slow')
      } else {
        this.rowIndex = index
        $('.detailDiv').each(function (i) {
          if (i === index) {
            $('.detailDiv').eq(i).slideDown('slow')
            $('.detailDiv').eq(i).addClass('on')
          } else {
            $('.detailDiv').eq(i).css('display', 'none')
            $('.detailDiv').eq(i).removeClass('on')
          }
        })
        this.loadMap()
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
.table-wrapper-scroll-y {
  display: block;
  max-height: 520px;
  overflow-y: auto;
  -ms-overflow-style: -ms-autohiding-scrollbar;
}
</style>
