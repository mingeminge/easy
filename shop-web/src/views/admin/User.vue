<template>
  <div style="padding: 20px">
    <el-row>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="formInline.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="formInline.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-button type="primary" @click="openAddUser">添加</el-button>

      </el-form>
    </el-row>
    <el-table
      :data="tableData"
      stripe
      ref="multipleTable"
      select-all="selectAll"
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column
        type="selection">
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名">
      </el-table-column>
      <!--<el-table-column label="图片" prop="img">
        <template slot-scope="scope">
          <img src="../static/img/timg.jpg" min-width="40" height="40"/>
        </template>
      </el-table-column>-->
      <el-table-column
        prop="sex"
        label="性别">
        <template slot-scope="scope">
          <div v-if="scope.row.sex===1">
            <el-tag>男</el-tag>
          </div>
          <div v-else>
            <el-tag type="danger">女</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号码">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱">
      </el-table-column>
      <el-table-column
        prop="created"
        label="创建日期"
        :formatter="formateDate">
      </el-table-column>
      <el-table-column
        prop="updated"
        label="修改日期"
        :formatter="formateDate">
      </el-table-column>
      <el-table-column
        fixed="right"
        width="190"
        label="操作">
        <template slot-scope="scope">
          <!--<el-dropdown>
            <el-button>设置
              <i class="el-icon-setting"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click="handleClick(scope.row)">编辑</el-dropdown-item>
              <el-dropdown-item @click="deleteUserById(scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>-->
          <el-button @click="handleClick(scope.row)" type="warning" size="mini"><i class="el-icon-edit"></i>编辑</el-button>
          <el-button @click="deleteUserById(scope.row,scope.$index)" type="danger" size="mini"><i
            class="el-icon-delete"></i>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <el-dialog
      title="新增用户"
      :visible.sync="dialogVisible"
      width="25%"
      @close="dialogVisible=false"
      :before-close="handleClose">
      <el-form :inline="true" :model="addUserForm" ref="userForm" :rules="rules" class="demo-form-inline"
               label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addUserForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUserForm.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="addUserForm.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="resource">
          <el-radio-group v-model="addUserForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input type="email" v-model="addUserForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="分配角色">
          <!--<el-checkbox-group v-model="hasRoles">
            <div v-for="role in roles">
              <el-checkbox :label="role.roleName" :true-label="role.id"></el-checkbox>
            </div>
          </el-checkbox-group>-->
          <!-- <el-checkbox-group v-model="hasRoles">
             <el-checkbox v-for="role in roles" :label="role.id" :key="role.id">{{role.roleName}}</el-checkbox>
           </el-checkbox-group>-->
          <el-checkbox-group v-model="hasRoles">
            <div v-for="role in roles">
              <el-checkbox :label="role.id">{{role.roleName}}</el-checkbox>
            </div>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser(addUserForm)">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改用户"
      :visible.sync="updateVisible"
      width="30%"
      @close="updateVisible=false"
      :before-close="handleClose">
      <el-form :inline="true" :model="updateUserForm" ref="userForm" :rules="rules" class="demo-form-inline"
               label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="updateUserForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <!--<el-form-item label="密码" prop="password">
          <el-input v-model="updateUserForm.password" type="password" placeholder="密码"></el-input>
        </el-form-item>-->
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="updateUserForm.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="resource">
          <el-radio-group v-model="updateUserForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input type="email" v-model="updateUserForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="分配角色">
          <el-checkbox-group v-model="hasRoles">
            <div v-for="role in roles">
              <el-checkbox :label="role.id">{{role.roleName}}</el-checkbox>
            </div>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUser(updateUserForm)">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
  import {addUser, deleteUser, getUsers, getRolesByUserId, getRoles} from "../../api/management";

  export default {
    name: "User",
    data() {
      return {
        hasRoles: [],
        roles: [],
        tableLoading: true,
        formInline: {
          username: '',
          phone: ''
        },
        addUserForm: {
          username: '',
          password: '',
          sex: '',
          phone: '',
          email: ''
        },
        updateUserForm: {
          username: '',
          password: '',
          sex: '',
          phone: '',
          email: ''
        },
        dialogVisible: false,
        updateVisible: false,
        currentPage: 1,
        total: 0,
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 15, message: '密码长度在6到15位', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入手机号码', trigger: 'blur'},
            {min: 11, max: 11, message: '请输入正确的手机号码', trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请输入邮箱地址', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}]
        },
        tableData: []
      }
    },
    mounted() {
      this.getUserTab();

    },
    methods: {
      onSubmit() {//点击查询窗口
        let _this = this;
        let params = {username: _this.formInline.username, phone: _this.formInline.phone}
        _this.getUserTab(params)
      },
      openAddUser() {//打开添加用户窗口
        this.dialogVisible = true;
        let _this = this;
        getRoles().then(resp => {
          console.log(resp);
          _this.roles = resp.data;
        })
      },
      getUserTab(params) {
        let _this = this;
        _this.tableLoading = true;
        getUsers(params).then(resp => {
          if (resp.code === 20000) {
            _this.tableData = resp.data.data;
            _this.total = resp.data.count;
            _this.tableLoading = false
          }
        })
      },
      handleClose(done) {
        this.dialogVisible = false;
        this.updateVisible = false;
        this.hasRoles = [];
      },
      updateUser(row) {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            let _this = this;
            addUser(row).then(resp => {
              if (resp.code === 20000) {
                if (_this.tableData.indexOf(row) == -1) {
                  _this.getUserTab();
                }
                _this.updateVisible = false;
                _this.$message({
                  type: 'success',
                  message: '成功'
                });
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      addUser(row) {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {
            let _this = this;
            let param = {user: row, roleId: _this.hasRoles}
            addUser(param).then(resp => {
              if (resp.code === 20000) {
                if (_this.tableData.indexOf(row) == -1) {
                  _this.getUserTab();
                }
                _this.dialogVisible = false;
                _this.$message({
                  type: 'success',
                  message: '成功'
                });
                _this.$refs['userForm'].resetFields();
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      formateDate(row, column, cellValue, index) {
        let date = new Date(cellValue);
        let year = date.getFullYear();
        let month = date.getMonth() < 10 ? 0 + '' + (date.getMonth() + 1) : date.getMonth();
        let day = date.getDate() < 10 ? 0 + '' + date.getDate() : date.getDate();
        let hh = date.getHours() < 10 ? 0 + '' + date.getHours() : date.getHours();
        let mm = date.getMinutes() < 10 ? 0 + '' + date.getMinutes() : date.getMinutes();
        let ss = date.getSeconds() < 10 ? 0 + '' + date.getSeconds() : date.getSeconds();
        return year + "/" + month + "/" + day + " " + hh + ":" + mm + ":" + ss;
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        let params = {username: this.formInline.username, phone: this.formInline.phone, limit: val}
        this.getUserTab(params)
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        let params = {username: this.formInline.username, phone: this.formInline.phone, page: val}
        this.getUserTab(params)
      },
      handleClick(row) {//打开编辑谈框
        console.log(row);
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        getRolesByUserId(row.id).then(resp => {
          this.updateVisible = true;
          loading.close();
          let hasRoleArr = [];
          resp.data.hasRoles.forEach(item => {
            hasRoleArr.push(item.id);
          })
          this.hasRoles = hasRoleArr;
          this.roles = resp.data.roles;
        }).catch(() => {
          loading.close();
        });
        this.updateUserForm = row;
      },
      deleteUserById(row, index) {
        let _this = this;
        _this.$confirm('此操作将永久删除该条信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUser(row.id).then(resp => {
            if (resp.code == 20000) {
              _this.$message({
                type: 'success',
                message: '删除成功'
              });
              _this.tableData.splice(index, 1);
            }
          })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      }
    }
  }
</script>

<style scoped>

</style>
