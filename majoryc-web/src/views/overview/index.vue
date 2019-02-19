<template>
  <section>
    <el-row :gutter="20">
      <el-col :md="24" :sm="24">
        <!--<div id="line" style="width: 100%;height: 300px"></div>-->
        <line-chart chart-id="line1" style="height: 400px;width: 100%" :day-data="lineData.dayData"
                    :night-data="lineData.nightData" :time-data="lineData.time"></line-chart>
      </el-col>
      <el-col :md="24" :sm="24">
        <bar-chart chart-id="bar1" style="height: 400px;width: 100%;" :data="barData.data"
                   :time-data="barData.time"></bar-chart>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :md="12" :sm="24">
       <!-- <pie-chart chart-id="pie1" style="height: 300px;width: 100%" :data="pieData.data"
                   :name-data="pieData.nameData"></pie-chart>-->
      </el-col>
    </el-row>
  </section>
</template>

<script>
  import {outputDay, shiftOutput} from "../../api/overview";
  import LineChart from "../../components/Echarts/LineChart";
  import BarChart from "../../components/Echarts/BarChart";
  import PieChart from "../../components/Echarts/PieChart";

  export default {
    name: "index",
    components: {PieChart, BarChart, LineChart},
    data() {
      return {
        lineData: {
          time: [],
          dayData: [],
          nightData: []
        },
        barData: {
          time: [],
          data: []
        },
        pieData: {
          nameData: [],
          data: []
        }
      }
    },
    computed: {
      getCollapsed() {
        console.log(this.$store.state.app.open)
        return this.$store.state.app.open;
      }
    },
    watch: {
      getCollapsed() {
        console.log(this.$store.state.app.open, 12131)
      }
    },
    created() {
      this.getShiftOutput();
      this.getOutputDay();
    },
    methods: {
      getOutputDay() {
        outputDay().then(resp => {
          console.log(resp);
          if (resp.code == 20000) {
            let timeArr = [];
            let data = [];
            resp.data.forEach(item => {
              timeArr.push(item.time);
              data.push(item.output);
            });
            this.barData.data = data;
            this.barData.time = timeArr;
          }
        })
      },
      getShiftOutput() {
        let _this = this;
        shiftOutput().then(resp => {
          console.log(resp);
          if (resp.code == 20000) {
            let dayData = [];
            let nightData = [];
            let timeArr = [];
            resp.data.forEach(item => {
              if (item.workShift === 1) {
                dayData.push(item.output);
              } else {
                nightData.push(item.output)
              }
              if (timeArr.indexOf(item.time) === -1) {
                timeArr.push(item.time)
              }
            });
            _this.lineData.dayData = dayData;
            _this.lineData.nightData = nightData;
            _this.lineData.time = timeArr;
          }
          console.log(_this.lineData)
        })
      },

    }
  }
</script>

<style scoped>

</style>
