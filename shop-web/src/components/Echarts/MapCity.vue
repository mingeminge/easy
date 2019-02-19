<template>
  <div style="height: 98%;width: 97%;padding: 10px">
    <div style="width: 100%;border-radius: 10px;box-shadow: 0 0 10px #a6e3e9;height: 100%;">
      <div style="height: 90%;width: 98%;padding: 20px">
        <el-button @click="back">返回</el-button>
        <div id="mapCity" style="height: 100%;width: 100%"></div>
      </div>
    </div>
  </div>
</template>

<script>
  import {data} from "../../utils/mapData";

  export default {
    name: "MapCity",
    data() {
      return {
        myChart: null,
        mapType: 'china',
        cityMap: [
          ['上海', 'shanghai'],
          ['河北', 'hebei'],
          ['山西', 'shangxi'],
          ['内蒙古', 'neimenggu'],
          ['辽宁', 'liaoning'],
          ['吉林', 'jilin'],
          ['黑龙江', 'heilongjiang'],
          ['江苏', 'jiangsu'],
          ['浙江', 'zhejiang'],
          ['安徽', 'anhui'],
          ['福建', 'fujian'],
          ['江西', 'jiangxi'],
          ['山东', 'shangdong'],
          ['河南', 'henan'],
          ['湖北', 'hubei'],
          ['湖南', 'hunan'],
          ['广东', 'guangdong'],
          ['广西', 'guangxi'],
          ['海南', 'hainan'],
          ['四川', 'sichuan'],
          ['贵州', 'guizhou'],
          ['云南', 'yunnan'],
          ['西藏', 'xizang'],
          ['陕西', 'shanxi1'],
          ['甘肃', 'gansu'],
          ['青海', 'qinghai'],
          ['宁夏', 'ningxia'],
          ['新疆', 'xinjiang'],
          ['北京', 'beijing'],
          ['天津', 'tianjin'],
          ['重庆', 'chongqing'],
          ['香港', 'xianggang'],
          ['澳门', 'aomen']
        ]
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      back() {
        this.mapType = 'china';
        this.init();
      },
      init() {
        let _this = this;
        _this.myChart = _this.$echarts.init(document.getElementById('mapCity'));
        let option = {
          visualMap: {
            min: 0,
            max: 1000,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],
            calculable: false,
            orient: 'horizontal',
            inRange: {
              color: ['#e0ffff', '#006edd'],
              symbolSize: [30, 100]
            }
          },
          tooltip: {
            padding: 0,
            enterable: true,
            transitionDuration: 1,
            textStyle: {
              color: '#000',
              decoration: 'none',
            },
          },
          series: [{
            name: 'iphone4',
            type: 'map',
            mapType: _this.mapType,
            aspectScale: 1,
            itemStyle: {
              normal: {
                label: {
                  show: true
                }
              },
              emphasis: {
                label: {
                  show: true
                }
              }
            },
            data: data
          }]
        }
        _this.myChart.setOption(option);
        window.addEventListener('resize', function () {
          _this.myChart.resize();
        });
        _this.myChart.on('click', function (param) {
          console.log(param);
          _this.mapType = param.name;
          _this.init()
        })
      }
    }
  }
</script>

<style scoped>

</style>
