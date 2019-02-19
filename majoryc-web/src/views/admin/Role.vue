<template>
  <div style="padding: 20px">
    <el-row>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="角色名">
          <el-input v-model="roleName" placeholder="角色名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
        <el-button type="primary" @click="addRoleVisible=true">添加</el-button>
      </el-form>
    </el-row>
    <el-table
      :data="tableData"
      ref="multipleTable"
      select-all="selectAll"
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column
        type="selection">
      </el-table-column>
      <el-table-column
        prop="id"
        label="序号">
      </el-table-column>
      <el-table-column
        prop="roleName"
        label="角色名称">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="permission(scope.row)" type="warning" size="mini">
            <i class="el-icon-edit"></i>权限
          </el-button>
          <el-button @click="deleteRole(scope.row)" type="danger" size="mini"><i
            class="el-icon-delete"></i>删除
          </el-button>
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
      title="添加角色"
      :visible.sync="addRoleVisible"
      width="30%"
      ref="addRoleForm"
      :before-close="handleClose">
      <span>
        <el-form :inline="true" :model="roleForm" ref="userForm" :rules="rules" class="demo-form-inline"
                 label-width="100px">
        <el-form-item label="角色名" prop="roleName">
          <el-input v-model="roleForm.roleName" placeholder="角色名"></el-input>
        </el-form-item>
      </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="addRoleVisible = false">取 消</el-button>
    <el-button type="primary" @click="addRole">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
      title="分配权限"
      :visible.sync="permissionVisible"
      width="30%"
      :before-close="handleClose">
      <span>
        <el-form :inline="true" ref="permissionForm" class="demo-form-inline"
                 label-width="100px">
        <el-form-item label="权限" prop="permission.js">
          <el-checkbox-group v-model="hasPermissions">
           <el-checkbox v-for="(item,index) in permissions" :label="item.label" :key="item.id"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="permissionVisible = false">取 消</el-button>
    <el-button type="primary" @click="permissionVisible=false">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
  import {getRoles, addRole as addRoles, deleteRole, getPermissionByRoleId} from "../../api/management";

  export default {
    name: "Role",
    data() {
      return {
        roleName: '',
        dialogVisible: false,
        tableData: [],
        tableLoading: false,
        currentPage: 1,
        total: 0,
        addRoleVisible: false,
        permissionVisible: false,
        permissions: [{label: '增加', id: 1}, {label: '删除', id: 2}, {label: '修改', id: 3}, {label: '查询', id: 4}],
        hasPermissions: [],
        roleForm: {
          roleName: ''
        },
        permissionForm: {
          id: []
        },
        rules: {
          roleName: [
            {required: true, message: '请输入角色名', trigger: 'blur'},
            {min: 2, max: 6, message: '长度在 2 到 6 个字符', trigger: 'blur'}
          ],
        }
      }
    },
    mounted() {
      this.getRoleTab();
    },
    methods: {
      addRole() {
        let _this = this;
        _this.$refs['userForm'].validate((valid) => {
          if (valid) {
            addRoles(_this.roleForm).then(resp => {
              if (resp.code === 20000) {
                _this.$message({
                  type: 'success', message: '添加成功'
                });
                _this.addRoleVisible = false;
                _this.roleForm = {};
                _this.getRoleTab();
              }
            })
          }
        });
      },
      permission(row) {
        let _this = this;
        _this.permissionVisible = true;
        _this.hasPermissions = [];
        getPermissionByRoleId(row.id).then(res => {
          if (res.code === 20000) {
            res.data.forEach((item, index) => {
              _this.hasPermissions.push(item.name)
            })
            console.log(_this.hasPermissions)
          }
        })

      },
      deleteRole(row) {
        let _this = this;
        _this.$confirm('此操作将永久删除该条信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRole(row.id).then(resp => {
            if (resp.code === 20000) {
              _this.$message({
                type: 'success', message: '删除'
              });
              _this.addRoleVisible = false;
              _this.getRoleTab();
            }
          })
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleClose() {
        this.addRoleVisible = false
        this.permissionVisible = false
      },
      onSubmit() {
        let params = {name: this.roleName};
        this.getRoleTab(params);
      },
      getRoleTab(params) {
        let _this = this;
        _this.tableLoading = true;
        getRoles(params).then(resp => {
          if (resp.code == 20000) {
            _this.tableData = resp.data.data;
            _this.total = resp.data.count;
            _this.tableLoading = false
          }
        });
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      },
    }
  }
</script>

<style scoped>

</style>
