<template>
  <div :id="chartId" :style="{height:height,width:width}"></div>
</template>

<script>
  export default {
    name: "BarChart",
    props: {
      chartId: '',
      height: '',
      width: '',
      data: Array,
      timeData: Array
    },
    data() {
      return {
        barChart: null
      }
    },
    watch: {
      data() {
        this.initChart();
      }
    },
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        this.barChart = this.$echarts.init(document.getElementById(this.chartId));
        let option = {
          title: {text: '日生产统计',textStyle:{color: "#00c7ff"}},
          backgroundColor: '#00265f',
          color: ['#3398DB'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              scale: true,
              data: this.timeData,
              axisTick: {
                alignWithLabel: true
              },
              axisLabel: {
                show: true,
                textStyle: {
                  color: "#00c7ff",
                }
              },
            }
          ],
          yAxis: [
            {
              type: 'value',
              splitLine: {show: false},
              axisLine: {
                show: false,
                lineStyle: {
                  color: "#00c7ff",
                  width: 1,
                  type: "solid"
                },
              },

            }
          ],
          series: [
            {
              name: '产量',
              type: 'bar',
              barWidth: '30%',
              data: this.data,
              itemStyle: {
                //柱形图圆角，鼠标移上去效果
                emphasis: {
                  barBorderRadius: [5, 5, 0, 0]
                },

                normal: {
                  //柱形图圆角，初始化效果
                  barBorderRadius: [5, 5, 0, 0],
                }
              }
            }
          ]
        };
        this.barChart.setOption(option);
      }
    }
  }
</script>

<style scoped>

</style>
