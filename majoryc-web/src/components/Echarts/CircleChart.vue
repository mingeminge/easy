<template>
  <div>
    <div id="circle" style="height: 100%;width: 100%"></div>
  </div>
</template>

<script>
  export default {
    name: "CircleChart",
    data() {
      return {
        CircleChart: null
      }
    },
    mounted() {
      let dom = document.getElementById('circle');
      let height = window.innerHeight - 50;
      dom.style.height = height + 'px'
      this.initChart();
    },
    methods: {
      initChart() {
        let _this = this;
        _this.CircleChart = _this.$echarts.init(document.getElementById('circle'))
        let option = {
          tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            x : 'center',
            y : 'bottom',
            data:['rose1','rose2','rose3','rose4','rose5','rose6','rose7','rose8']
          },
          toolbox: {
            show : true,
            feature : {
              mark : {show: true},
              dataView : {show: true, readOnly: false},
              magicType : {
                show: true,
                type: ['pie', 'funnel']
              },
              restore : {show: true},
              saveAsImage : {show: true}
            }
          },
          calculable : true,
          series : [
            {
              name:'半径模式',
              type:'pie',
              radius : [20, 110],
              center : ['25%', '50%'],
              roseType : 'radius',
              label: {
                normal: {
                  show: false
                },
                emphasis: {
                  show: true
                }
              },
              lableLine: {
                normal: {
                  show: false
                },
                emphasis: {
                  show: true
                }
              },
              data:[
                {value:10, name:'rose1'},
                {value:5, name:'rose2'},
                {value:15, name:'rose3'},
                {value:25, name:'rose4'},
                {value:20, name:'rose5'},
                {value:35, name:'rose6'},
                {value:30, name:'rose7'},
                {value:40, name:'rose8'}
              ]
            }
          ]
        };
        _this.CircleChart.setOption(option)
      }
    }
  }
</script>

<style scoped>

</style>
