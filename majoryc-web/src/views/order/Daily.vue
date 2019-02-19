<template>
  <section style="padding: 20px">
    <el-form label-position="left" inline v-model="queryForm">
      <el-form-item label="编号">
        <el-input placeholder="鞋底编号" v-model="queryForm.treadNum"></el-input>
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="queryForm.time"
          type="date"
          style="width: 160px"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工作班次">
        <el-select v-model="queryForm.workShift" placeholder="请选择订单状态" style="width: 160px">
          <el-option
            v-for="item in workShift"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search"><i class="el-icon-search"></i>查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      v-loading="tableLoading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      style="width: 100%">
      <el-table-column
        prop="treadNum"
        label="编号">
        <template slot-scope="scope">
          <el-tag>{{scope.row.treadNum}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="workShift"
        label="工作班次"
        width="180">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.workShift===1" type="success">白班</el-tag>
          <el-tag v-else type="danger">晚班</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="surplus"
        label="剩余">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.surplus>70" type="success">{{scope.row.surplus}}</el-tag>
          <el-tag v-else type="danger">{{scope.row.surplus}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="output"
        label="产量">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.output>70" type="danger">{{scope.row.output}}</el-tag>
          <el-tag v-else type="success">{{scope.row.output}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="time"
        :formatter="formatDate"
        label="日期">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="limitChange"
      @current-change="pageChange"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="100"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableTotal">
    </el-pagination>
  </section>
</template>

<script>
  import {dailyList} from "../../api/order";

  export default {
    name: "Daily",
    data() {
      return {
        queryForm: {treadNum: '', workShift: '', time: ''},
        workShift: [{label: '白班', value: '1'}, {label: '夜班', value: '2'}],
        tableData: [],
        tableTotal: 0,
        tableLoading: true
      }
    },
    mounted() {
      this.getTableData();
    },
    methods: {
      search() {
        this.getTableData(this.queryForm);
      },
      getTableData(param) {
        this.tableLoading = true;
        let _this=this;
        dailyList(param).then(resp => {
          if (resp.code === 20000) {
            _this.tableData = resp.data.data;
            _this.tableTotal = resp.data.count;
            _this.tableLoading = false
          }
        }).catch(() => {
          _this.tableLoading = false;
        })
      },
      limitChange(val) {
        this.queryForm.limit = val;
        this.getTableData(this.queryForm);
      },
      pageChange(val) {
        this.queryForm.page = val;
        this.getTableData(this.queryForm);
      },
      formatDate(row, column, cellValue, index) {
        let date = new Date(cellValue);
        let year = date.getFullYear();
        let month = date.getMonth() < 10 ? 0 + '' + (date.getMonth() + 1) : date.getMonth();
        let day = date.getDate() < 10 ? 0 + '' + date.getDate() : date.getDate();
        let hh = date.getHours() < 10 ? 0 + '' + date.getHours() : date.getHours();
        let mm = date.getMinutes() < 10 ? 0 + '' + date.getMinutes() : date.getMinutes();
        let ss = date.getSeconds() < 10 ? 0 + '' + date.getSeconds() : date.getSeconds();
        return year + "/" + month + "/" + day + " " + hh + ":" + mm + ":" + ss;
      },
    },

  }
</script>

<style scoped>

</style>
