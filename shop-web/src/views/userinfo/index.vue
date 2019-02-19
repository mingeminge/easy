<template>
  <section style="padding: 20px">
    <el-row :gutter="10">
      <el-col :span="6">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <el-form label-position="left" label-width="80px">
            <div style="text-align: center">
              <p>
                <img src="../../image/headPortrait/user.png" style="clear: right"/>
              </p>
              <p>
                <el-button type="text">修改头像</el-button>
              </p>
              <p>
                {{userInfo.nickName}}
              </p>
            </div>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs type="border-card">
            <el-tab-pane label="基本资料">
              <el-form label-position="left" label-width="80px">
                <el-form-item label="昵称">
                  <el-input v-model="userInfo.nickName"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                  <el-input v-model="userInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                  <el-radio-group v-model="userInfo.sex">
                    <el-radio :label="1">男</el-radio>
                    <el-radio :label="0">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号码">
                  <el-input v-model="userInfo.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                  <el-input v-model="userInfo.email"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary">提交</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="修改密码">
              <el-form label-position="left" label-width="100px">
                <el-form-item label="旧密码">
                  <el-input placeholder="请输入旧密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                  <el-input placeholder="请输入新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码">
                  <el-input placeholder="请再次输入新密码"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary">提交</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </section>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        userInfo: {
          name: 'yzm',
          loginArea: '广州',
          loginIp: '192.167.88.99',
          loginTime: '2019-02-13',
          nickName: '大佬',
          sex: 1,
          phone: '12345678911',
          email: '278@qq.com',
          birthday: '1998-12-11'
        }
      }
    }
  }
</script>

<style scoped>
  .clearfix {
    text-align: center;
  }

  .boxItem {
    text-align: center;
  }

  el-card__header {
    background-color: cadetblue !important;
  }

</style>
