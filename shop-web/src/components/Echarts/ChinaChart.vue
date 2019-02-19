<template>
  <div style="width: 97%;height: 100%;padding: 10px">
    <div style="width: 100%;border-radius: 10px;box-shadow: 0 0 10px #a6e3e9;height: 100%;">
      <div id="china" style="height: 650px;width: 100%"></div>
    </div>
  </div>

</template>

<script>
  import {data} from "../../utils/mapData";

  export default {
    name: "ChinaChart",
    data() {
      return {
        chinaChart: null
      }
    },
    mounted() {
      this.initChart();
    },
    methods: {
      tooltipCharts() {
        let myChart = this.$echarts.init(document.getElementById('tooltipBarId'));
        let option = {
          tooltip: {},
          dataset: {
            source: [
              ['xAxis', '201701', '201702', '201703', '201704', '201705', '201706', '201707', '201708', '201709', '20170', '201710', '20170', '201801'],
              ['amount', 41.1, 30.4, 65.1, 53.3, 83.8, 98.7, 65.1, 53.3, 41.1, 30.4, 53.3, 41.1, 53.3, 83.8]
            ]
          },
          xAxis: {
            type: 'category',
            interval: true,
            axisLabel: {
              rotate: 45
            },
            axisTick: {
              show: false
            }
          },
          yAxis: {},
          color: ['#4FA8F9', '#F5A623'],
          grid: {
            show: true,
            backgroundColor: '#FAFAFA',
            left: 30,
            right: 20,
            top: 20
          },
          series: [{
            type: 'bar',
            smooth: true,
            seriesLayoutBy: 'row',
            barWidth: 10
          }]
        };
        myChart.setOption(option);
      },
      initChart() {
        let _this = this
        this.chinaChart = this.$echarts.init(document.getElementById('china'));
        let option = option = {
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
            // position: function (point, params, dom, rect, size) {
            //   return [point[0], point[1]];
            // },
            formatter: function (params) {
              // console.log(params)
              var tipHtml = '';
              tipHtml = '<div style="height:360px;width:400px;border-radius:5px;background:#fff;box-shadow:0 0 10px 5px #aaa">' +
                '    <div style="height:50px;width:100%;border-radius:5px;background:#F8F9F9;border-bottom:1px solid #F0F0F0">' +
                '        <span style="line-height:50px;margin-left:18px">' + params.name + '</span>' +
                '        <span style="float:right;line-height:50px;margin-right:18px;color:#5396E3;cursor:pointer" onclick="mapTooltipClick(this);">点击查看详情</span>' +
                '    </div>' +
                '    <div style="height:110px;width:100%;background:#fff">' +
                '        <div style="padding-left:18px;padding-top:22px">' +
                '            <span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:rgba(92,169,235,1)"></span> ' +
                '            <span>上传表格数量</span>' +
                '            <span style="float:right;margin-right:18px">' + params.data.tipData[0] + '万</span>' +
                '        </div>' +
                '        <div style="padding-left:18px;padding-top:14px">' +
                '            <span style="display:inline-block;margin-right:5px;width:10px;height:10px;background-color:rgba(92,169,235,1)"></span> ' +
                '            <span>上传数据条数</span>' +
                '            <span style="float:right;margin-right:18px">' + params.data.tipData[1] + '条</span>' +
                '        </div>' +
                '    </div>' +
                '    <div id="tooltipBarId" style="height:200px;width:100%;border-radius:0 0 5px 0;background:#fff"></div>' +
                '</div>';
              return tipHtml;
            }
          },
          series: [{
            name: 'iphone4',
            type: 'map',
            mapType: 'china',
            itemStyle: {
              normal: {
                label: {
                  show: false
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
        this.chinaChart.setOption(option);
        window.addEventListener('resize', () => {
          this.chinaChart.resize();
        });
        let count = 0;
        let timeTicket = null;
        let dataLength = option.series[0].data.length;
        /*timeTicket && clearInterval(timeTicket);
        timeTicket = setInterval(function () {
          _this.chinaChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
          });
          _this.chinaChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: (count) % dataLength
          });
          _this.chinaChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: (count) % dataLength
          });
          count++;
        }, 1000);*/
        /*_this.chinaChart.on('mouseover', function (params) {
          console.log(params)
          clearInterval(timeTicket);
          _this.chinaChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0
          });
          _this.chinaChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: params.dataIndex
          });
          _this.chinaChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: params.dataIndex,
          });
        });*/
        /*_this.chinaChart.on('mouseout', function (params) {
          timeTicket && clearInterval(timeTicket);
          timeTicket = setInterval(function () {
            _this.chinaChart.dispatchAction({
              type: 'downplay',
              seriesIndex: 0,
            });
            _this.chinaChart.dispatchAction({
              type: 'highlight',
              seriesIndex: 0,
              dataIndex: (count) % dataLength
            });
            _this.chinaChart.dispatchAction({
              type: 'showTip',
              seriesIndex: 0,
              dataIndex: (count) % dataLength
            });
            count++;
          }, 1000);
        });*/
      }
    }
  }
</script>

<style scoped>

</style>
