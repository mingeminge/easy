<template>
  <div class="dashboard-container">
    <!-- <div class="dashboard-text">name:{{ name }}</div>
     <div class="dashboard-text">roles:<span v-for="role in roles" :key="role">{{ role }}</span></div>-->
    <div>
      <div style="height: 650px;width: 100%" id="plane"></div>
    </div>
  </div>

</template>

<script>
  import {mapGetters} from 'vuex'
  import {convertData, planePath, BJData, GZData, SHData, geoCoordMap} from "../../utils/plane";

  export default {
    name: 'Dashboard',
    data() {
      return {
        planeChart: null
      }
    },
    computed: {
      ...mapGetters([
        'name',
        'roles'
      ]),
      getOpened() {
        return this.$store.getters.sidebar.opened
      }
    },
    watch: {
      getOpened: {
        handler(newValue, oldValue) {
          this.planeChart.resize();
        },
        deep: true
      }
    },
    mounted() {
      this.initChart();
      console.log(this.$router.options)
    },
    methods: {
      initChart() {
        this.planeChart = this.$echarts.init(document.getElementById('plane'));
        let color = ['#a6c84c', '#ffa022', '#46bee9'];
        let series = [];
        [['北京', BJData], ['上海', SHData], ['广州', GZData]].forEach(function (item, i) {
          //console.log(item,i);
          series.push({
              name: item[0] + ' Top10',
              type: 'lines',
              zlevel: 1,
              effect: {
                show: true,
                period: 6,
                trailLength: 0.7,
                color: '#fff',
                symbolSize: 3
              },
              lineStyle: {
                normal: {
                  color: color[i],
                  width: 0,
                  curveness: 0.2
                }
              },
              data: convertData(item[1])
            },
            {
              name: item[0] + ' Top10',
              type: 'lines',
              zlevel: 2,
              symbol: ['none', 'arrow'],
              symbolSize: 10,
              effect: {
                show: true,
                period: 6,
                trailLength: 0,
                symbol: planePath,
                symbolSize: 15
              },
              lineStyle: {
                normal: {
                  color: color[i],
                  width: 1,
                  opacity: 0.6,
                  curveness: 0.2
                }
              },
              data: convertData(item[1])
            },
            {
              name: item[0] + ' Top10',
              type: 'effectScatter',
              coordinateSystem: 'geo',
              zlevel: 2,
              rippleEffect: {
                brushType: 'stroke'
              },
              label: {
                normal: {
                  show: true,
                  position: 'right',
                  formatter: '{b}'
                }
              },
              symbolSize: function (val) {
                return val[2] / 8;
              },
              itemStyle: {
                normal: {
                  color: color[i]
                }
              },
              data: item[1].map(function (dataItem) {
                return {
                  name: dataItem[1].name,
                  value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value])
                };
              })
            });
        });
        let option = {
          title: {
            text: '模拟迁徙',
            subtext: '',
            left: 'center',
            textStyle: {
              color: '#fff'
            }
          },
          tooltip: {
            trigger: 'item',
            formatter: function (params, ticket, callback) {
              console.log(params);
              if (params.seriesType == "effectScatter") {
                return "线路：" + params.data.name + "" + params.data.value[2];
              } else if (params.seriesType == "lines") {
                return params.data.fromName + ">" + params.data.toName + "<br />" + params.data.value;
              } else {
                return params.name;
              }
            }
          },
          legend: {
            orient: 'vertical',
            top: 'bottom',
            left: 'right',
            data: ['北京 Top10', '上海 Top10', '广州 Top10'],
            textStyle: {
              color: '#323c48'
            },
            selectedMode: 'multiple',
          },
          geo: {
            map: 'china',
            label: {
              emphasis: {
                show: true,
                color: '#fff'
              }
            },
            roam: true,
            itemStyle: {
              normal: {
                /*areaColor: '#323c48',*/
                borderColor: '#404a59'
              },
              emphasis: {
                areaColor: '#2a333d'
              }
            }
          },
          series: series
        };
        this.planeChart.setOption(option);
        window.addEventListener('resize', () => {
          this.planeChart.resize()
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 0px;
    }
    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
