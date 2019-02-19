<template>
  <section style="padding: 20px">
    <el-form label-position="left" inline v-model="queryForm">
      <el-form-item label="时间">
        <el-date-picker
          v-model="queryForm.time"
          type="date"
          style="width: 160px"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          placeholder="订单创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="厂家">
        <el-input placeholder="请输入厂家" v-model="queryForm.buyerName"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryForm.status" placeholder="请选择订单状态" style="width: 160px">
          <el-option
            v-for="item in status"
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
    <!--<el-button type="primary" @click="addToDayOrder">排单</el-button>-->
    <el-button type="primary" @click="addOrderVisible=true;isAdd=true;">
      <i class="el-icon-circle-plus-outline"></i>创建订单
    </el-button>
    <!--<el-button type="danger" @click="handleSelectionChange">取消选择</el-button>-->
    <el-table
      :data="tableData"
      ref="orderTable"
      v-loading="tableLoading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
      @select-all="selectAll"
      @selection-change="selectChange"
      @select="selectCheckBox"
      style="width: 100%">
      <el-table-column
        label="选择"
        type="selection">
      </el-table-column>
      <el-table-column type="expand" label="展开">
        <template slot-scope="props">
          <el-form label-position="left" inline>
            <div v-for="tread in props.row.orderInfo">
              <el-form-item label="鞋底编号">
                <el-tag>{{tread.treadNum}}</el-tag>
              </el-form-item>
              <el-form-item label="颜色">
                <el-tag>{{tread.color}}</el-tag>
              </el-form-item>
              <el-form-item label="类型">
                <div v-if="tread.materialType===1">
                  <el-tag>普通</el-tag>
                </div>
                <div v-else-if="tread.materialType===2">
                  <el-tag>高弹</el-tag>
                </div>
                <div v-else-if="tread.materialType===3">
                  <el-tag>耐磨</el-tag>
                </div>
              </el-form-item>
              <el-form-item label="底类型">
                <div v-if="tread.treadType===1">
                  <el-tag type="danger">鞋底</el-tag>
                </div>
                <div v-else-if="tread.treadType===2">
                  <el-tag type="danger">天皮</el-tag>
                </div>
                <div v-else-if="tread.treadType===3">
                  <el-tag type="danger">板</el-tag>
                </div>
              </el-form-item>
              <el-form-item label="总共数量">
                <el-tag>{{tread.total}}</el-tag>
              </el-form-item>
              <el-form-item label="剩余数量">
                <el-tag v-if="tread.surplus<50" type="warning">{{tread.surplus}}</el-tag>
                <el-tag v-else-if="tread.surplus<20" type="danger">{{tread.surplus}}</el-tag>
                <el-tag v-else type="primary">{{tread.surplus}}</el-tag>
              </el-form-item>
              <el-form-item>
                <el-button v-if="tread.surplus>0" type="primary" @click="addDailyClick(tread,props.row.orderId)">添加产量</el-button>
                <el-button v-else type="primary" @click="addDailyClick(tread,props.row.orderId)" disabled>添加产量</el-button>
              </el-form-item>
            </div>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="订单号"
        :show-overflow-tooltip=true
        prop="orderId">
      </el-table-column>
      <el-table-column
        label="订单状态"
        sortable
        prop="status">
        <template slot-scope="scope">
          <div v-if="scope.row.status===1">
            <el-tag type="warning">未完成</el-tag>
          </div>
          <div v-else-if="scope.row.status===2">
            <el-tag type="success">已完成</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="buyerName"
        sortable
        label="厂家">
        <template slot-scope="scope">
          <el-badge :value="scope.row.orderInfo.length" class="item">
            <el-tag>
              {{scope.row.buyerName}}
            </el-tag>
          </el-badge>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        :formatter="formatDate"
        :show-overflow-tooltip=true
        label="创建时间">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        :formatter="formatDate"
        :show-overflow-tooltip=true
        label="更新时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        width="170"
        label="操作">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="editOrder(scope.row)"><i class="el-icon-edit"></i>修改</el-button>
          <el-button type="danger" size="mini" @click="deleteOrder(scope.row,scope.$index)"><i
            class="el-icon-delete"></i>删除
          </el-button>
        </template>
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
    <!------------------------------添加---------------------->
    <el-dialog
      title="订单"
      :visible.sync="addOrderVisible"
      width="60%"
      :before-close="closeAddOrder"
      center>
      <label style="color: #F56C6C">注：带*号为必填项</label>
      <el-form label-position="left" label-width="80px" :model="addOrderForm" ref="addOrderForm" inline>
        <el-form-item label="厂家" prop="buyerName" :rules="{
      required: true, message: '请输入厂家', trigger: 'blur'}">
          <el-input v-model="addOrderForm.buyerName"></el-input>
        </el-form-item>
        <el-form-item v-if="!isAdd" label="订单编号">
          <el-input v-model="addOrderForm.orderId" readonly="readonly"></el-input>
        </el-form-item>
        <div v-for="(item,index) in addOrderForm.orderInfo">
          <HR/>
          <label>订单{{index+1}}：</label><br>
          <el-form-item :key="item.key" label="鞋底编号"
                        :prop="'orderInfo.' + index + '.treadNum'"
                        :rules="{
      required: true, message: '请输入鞋底编号', trigger: 'blur'}">
            <el-input v-model="item.treadNum" placeholder="请输入鞋底编号"></el-input>
          </el-form-item>
          <el-form-item :key="item.key" label="数量"
                        :prop="'orderInfo.' + index + '.total'"
                        :rules="[{
      required: true, message: '请输入数量', trigger: 'blur'},{type: 'number', message: '数量必须为数字'}]">
            <el-input v-model.number="item.total"></el-input>
          </el-form-item>

          <el-form-item label="类型" :key="item.key" :prop="'orderInfo.' + index + '.materialType'"
                        :rules="{
      required: true, message: '请选择材料类型', trigger: 'change'}">
            <el-select v-model="item.materialType" placeholder="请选择材料类型" style="width: 160px">
              <el-option
                v-for="item in treadType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="颜色" :key="item.key" :prop="'orderInfo.' + index + '.color'"
                        :rules="{
      required: true, message: '请填写颜色', trigger: 'blur'}">
            <el-input v-model="item.color"></el-input>
          </el-form-item>

          <el-form-item label="底类型" :key="item.key" :prop="'orderInfo.' + index + '.treadType'"
                        :rules="{
      required: true, message: '请选择类型', trigger: 'change'}">
            <el-select v-model="item.treadType" placeholder="请选择底类型" style="width: 160px">
              <el-option
                v-for="item in materialType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" :key="item.key">
            <el-input type="textarea" v-model="item.remark"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="deleteAddInfo(index)" v-if="addOrderForm.orderInfo.length>1"
                       class="el-icon-remove-outline" type="danger">删除
            </el-button>
          </el-form-item>
        </div>
        <el-row justify="center" v-if="isAdd">
          <el-button @click="addInfo" class="el-icon-circle-plus-outline" style="color: #409EFF;flex: 1">添加</el-button>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeAddOrder">取 消</el-button>
    <el-button type="primary" @click="addOrder">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog width="40%" center title="添加产量" :visible.sync="addDailyVisible">
      <el-form :model="addDailyForm" label-position="left" label-width="80px" ref="addDailyForms" inline>
        <el-form-item label="鞋底编号">
          <el-input v-model="addDailyForm.treadNum" readonly></el-input>
        </el-form-item>
        <el-form-item label="总数">
          <el-input v-model="addDailyForm.total" readonly="readonly"></el-input>
        </el-form-item>
        <el-form-item label="剩余">
          <el-input v-model="addDailyForm.surplus" readonly="readonly"></el-input>
        </el-form-item>
        <el-form-item label="产量" prop="output" :rules="[
      { required: true, message: '产量不能为空',trigger:'blur'},
      { type: 'number', message: '产量必须为数字值',trigger:'blur'}
    ]">
          <el-input v-model.number="addDailyForm.output"></el-input>
        </el-form-item>
        <el-form-item label="工作班次" prop="workShift" :rules="{
      required: true, message: '请选择工作班次', trigger: 'change'}">
          <el-select v-model="addDailyForm.workShift" placeholder="请选择工作班次">
            <el-option label="白班" value="1"></el-option>
            <el-option label="夜班" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDailyVisible = false">取 消</el-button>
        <el-button type="primary" @click="addDaily">确 定</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
  import {add, deleteOrderByOrderId, getOrderList, update, addDaily} from "../../api/order";

  export default {
    name: "Order2",
    data() {
      return {
        addDailyVisible: false,
        addDailyForm: {
          id: '',
          surplus: 0,
          total: '',
          treadNum: '',
          workShift: '',
          output: '',
          orderId: ''
        },
        status: [{value: 1, label: '未完成'}, {value: 2, label: '已完成'}],
        materialType: [{value: 1, label: '鞋底'}, {value: 2, label: '天皮'}],
        treadType: [{value: 1, label: '普通料'}, {value: 2, label: '高弹料'}, {value: 3, label: '耐磨料'}],
        addOrderVisible: false,//添加订单弹框不可见
        tableLoading: true,//表格loading
        isAdd: false,//是否是添加
        selectOrders: [],//选中的数据
        queryForm: {//查询条件
          time: '',
          buyerName: '',
          status: '',
          page: 1,
          limit: 10
        },
        tableData: [],//表格数据
        tableTotal: 0,//表格总数
        addOrderForm: {//添加订单的表格
          buyerName: '',
          orderId: '',
          orderInfo: [{
            total: '',
            treadNum: '',
            materialType: '',
            treadType: '',
            color: '',
            remark: ''
          }]
        }
      }
    },
    mounted() {//页面加载完成执行的方法
      this.getTableData(this.queryForm);
    },
    methods: {
      editOrder(row) {//点击编辑数据
        this.addOrderForm = row;
        this.addOrderVisible = true;
        this.isAdd = false;
      },
      deleteOrder(row, index) {
        this.$confirm('此操作将永久删除该条订单信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: 'loading....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.5)'
          });
          deleteOrderByOrderId(row.orderId).then(resp => {
            if (resp.code === 20000) {
              this.$message({type: 'success', message: '删除成功'});
              loading.close();
              this.tableData.splice(index, 1);
            }
          }).catch(e => {
            loading.close();
          });
        });
      },
      addOrder() {//向后台添加或修改订单（如果有订单id说明点击的是编辑）
        console.log(this.addOrderForm);
        if (this.addOrderForm.orderId) {
          const loading = this.$loading({
            lock: true,
            text: 'loading....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.5)'
          });
          update(this.addOrderForm).then(resp => {
            if (resp.code === 20000) {
              this.addOrderVisible = false;
              loading.close();
              this.$message({type: 'success', message: '修改成功'});
              this.resetAddForm();
              this.getTableData(this.queryForm);
            }
          }).catch(e => {
            loading.close();
          })
        } else {
          this.$refs['addOrderForm'].validate((valid) => {
            if (valid) {
              const loading = this.$loading({
                lock: true,
                text: 'loading....',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.5)'
              });
              add(this.addOrderForm).then(resp => {
                if (resp.code === 20000) {
                  this.addOrderVisible = false;
                  loading.close();
                  this.$message({type: 'success', message: '添加成功'});
                  this.resetAddForm();
                  this.getTableData(this.queryForm);
                  /*this.tableData.push(this.addOrderForm)*/
                }
              }).catch(e => {
                loading.close();
              })
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
      },
      deleteAddInfo(index) {//删除动态添加的订单信息
        let _this = this;
        _this.$confirm('删除该条订单信息？, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.addOrderForm.orderInfo.splice(index, 1)
        });
      },
      addToDayOrder() {//添加到排单
        let _this = this;
        console.log(this.selectOrders);
        if (_this.selectOrders.length > 0) {
          const loading = this.$loading({
            lock: true,
            text: '排单中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.5)'
          });
          setTimeout(function () {
            loading.close();
            _this.$message({type: 'success', message: '排单成功'});
          }, 2000)
          /*addDayOrders(this.selectOrders).then(resp => {
            console.log(resp)
            if (resp.code === 20000) {


            }
          })*/
          console.log(_this.selectOrders)
        } else {
          _this.$message({type: 'warning', message: '未选中任何订单'})
        }

      },
      addDailyClick(row, orderId) {//添加产量
        console.log(row,orderId)
        this.addDailyForm = row;
        this.addDailyVisible = true;
        this.addDailyForm.orderId = orderId;
      },
      addDaily() {
        this.$refs['addDailyForms'].validate((valid) => {
          if (valid) {
            addDaily(this.addDailyForm).then(resp => {
              console.log(resp);
              if (resp.code === 20000) {
                this.$message({type: 'success', message: '添加产量成功'});
                this.addDailyVisible = false;
                this.getTableData(this.queryForm);
              }
            }).catch(() => {
              this.$message({type: 'error', message: '添加失败'});
            });
            console.log(this.addDailyForm)
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      selectAll(selection) {//表格全选
        let _this = this;
        let selected = [];
        selection.forEach(row => {
          if (row.status === 1) {
            _this.$refs.orderTable.toggleRowSelection(row, true)
            selected.push(row.orderId);
          }
        });
        _this.selectOrders = selected;
        console.log(_this.selectOrders);
      },
      selectChange(selection) {//选中切换
        let _this = this;
        let selected = [];
        selection.forEach(function (item, index) {
          selected.push(item.orderId);
        })
        _this.selectOrders = selected;
      },
      selectCheckBox(selection, row) {//点击勾选多选框
        if (row.status === 2) {
          this.$message({type: 'warning', message: '已在排单列表'});
          this.$refs.orderTable.clearSelection();
          return false;
        } else if (row.status === 3) {
          this.$message({type: 'warning', message: '该订单已完成'});
          this.$refs.orderTable.clearSelection();
          return false;
        }
      },
      getTableData(param) {//获取表格数据
        let _this = this;
        _this.tableLoading = true;
        getOrderList(param).then(resp => {
          if (resp.code === 20000) {
            _this.tableLoading = false;
            _this.tableData = resp.data.data;
            _this.tableTotal = resp.data.count;
          }
        }).catch((e) => {
          _this.tableLoading = false;
          _this.$message({message: e.message, type: 'danger'})
        })
      },
      limitChange(val) {//每页多少条数据
        this.queryForm.limit = val;
        this.getTableData(this.queryForm);
      },
      pageChange(val) {//第几页
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
      resetAddForm() {
        this.addOrderForm = {
          buyerName: '',
          orderInfo: [{total: '', treadNum: '', materialType: '', treadType: '', color: '', remark: ''}]
        }
      },
      addInfo() {//动态添加
        this.addOrderForm.orderInfo.push({
          total: '',
          treadNum: '',
          materialType: '',
          treadType: '',
          color: '',
          remark: ''
        })
      },
      search() {//查询
        this.getTableData(this.queryForm);
      },
      closeAddOrder() {//关闭添加窗口
        this.resetAddForm();
        this.addOrderVisible = false;
      },
    }
  }
</script>

<style scoped>
  .item {
    margin-top: 10px;
    margin-right: 40px;
  }
</style>
