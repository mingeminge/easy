<template>
  <div :id="chartId" :style="{height:height,width:width}"></div>
</template>

<script>
  export default {
    name: "LineChart",
    props: {
      chartId: '',
      height: '',
      width: '',
      dayData: Array,
      nightData: Array,
      timeData: Array
    },
    mounted() {
      this.initChart();
    },
    data() {
      return {
        Chart: null
      }
    },
    watch: {
      nightData() {
        this.initChart();
      },
      dayData() {
        this.initChart();
      }
    },
    methods: {
      initChart() {
        this.Chart = this.$echarts.init(document.getElementById(this.chartId));
        let option = {
          title: {text: '班次产量对比',textStyle:{color: "#00c7ff"}},
          backgroundColor: '#00265f',
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: ['订单', '产量'],
            align: 'right',
            right: 10,
            textStyle: {
              color: "#fff"
            },
            itemWidth: 10,
            itemHeight: 10,
            itemGap: 35
          },
          grid: {
            left: '3%',
            right: '10%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            data: this.timeData /*['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']*/,
            axisLine: {
              show: false,
              lineStyle: {
                color: "#063374",
                width: 1,
                type: "solid"
              }
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: "#00c7ff",
              }
            },
          }],
          yAxis: [{
            type: 'value',
            axisLabel: {
              formatter: '{value}'
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: "#00c7ff",
                width: 1,
                type: "solid"
              },
            },
            splitLine: {
              lineStyle: {
                color: "#063374",
              }
            }
          }],
          series: [{
            name: '白班',
            type: 'bar',
            data: this.dayData /*[20, 50, 80, 58, 83, 68, 57, 80, 42, 66,55,66]*/,
            barWidth: 10, //柱子宽度
            barGap: 1, //柱子之间间距
            itemStyle: {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#008cff'
                }, {
                  offset: 1,
                  color: '#005193'
                }]),
                opacity: 1,
              }
            }
          }, {
            name: '夜班',
            type: 'bar',
            data: this.nightData /*[50, 70, 60, 61, 75, 87, 60, 62, 86, 46,88,21]*/,
            barWidth: 10,
            barGap: 1,
            itemStyle: {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: '#00da9c'
                }, {
                  offset: 1,
                  color: '#007a55'
                }]),
                opacity: 1,
              }
            }
          }]
        };
        this.Chart.setOption(option)
      }
    }
  }
</script>

<style scoped>

</style>
