<template>
  <div :id="chartId" :style="{height:height,width:width}"></div>
</template>

<script>
  export default {
    name: "PieChart",
    props: {
      chartId: '',
      height: '',
      width: '',
      data: Array,
      nameData: Array
    },
    data() {
      return {
        pieChart: null
      }
    },
    watch: {
      data() {
        this.initPieChart();
      }
    },
    mounted() {
      this.initPieChart();
    },
    methods: {
      initPieChart() {//客户对比图
        this.pieChart = this.$echarts.init(this.chartId);
        let option = {
          color: ['#2b85e4', '#2db7f5', '#19be6b', '#ff9900', '#ed4014'],
          tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
          },
          legend: {
            left: 'center',
            data: this.nameData
          },
          series: [
            {
              name: '订单数量',
              type: 'pie',
              radius: ['50%', '70%'],
              avoidLabelOverlap: false,
              label: {
                normal: {
                  show: false,
                  position: 'center'
                },
                emphasis: {
                  show: true,
                  textStyle: {
                    fontSize: '20',
                    fontWeight: 'bold'
                  }
                }
              },
              labelLine: {
                normal: {
                  show: false
                }
              },
              data: this.data
            }
          ]
        };
        this.pieChart.setOption(option);
      }
    }
  }
</script>

<style scoped>

</style>
