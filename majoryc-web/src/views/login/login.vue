<template>
  <div class="bg">
    <div class="login-wrap animated flipInY" :title="'Auto Vue'">
      <h3>喵喵喵</h3>
      <h3></h3>
      <el-form ref="form" :model="form" :rules="rules" label-width="0px">
        <el-form-item prop="username">
          <el-input placeholder="账号" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="密码" v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="inputCode">
          <div class="join_formitem">
            <div class='captcha'>
              <el-input type="text" placeholder="请输入验证码" class="yanzhengma_input"
                        v-model="form.inputCode"></el-input>
              <input type="button" @click="createCode" class="verification" v-model="form.checkCode"/>
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-row type="flex" justify="space-between">
            <el-checkbox v-model="isMemery" style="color:#eee">记住密码</el-checkbox>
            <!--<a href="" @click.prevent="openMsg" style="color:#eee">忘记密码</a>-->
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Login('form')" :loading="logining">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 粒子漂浮物 -->
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="star"
      :particleSize="5"
      linesColor="#fff"
      :linesWidth="2"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push">
    </vue-particles>
  </div>
</template>
<script>
  import {isvalidUsername} from '@/utils/validate'
  import lazyLoading from "../../utils/lazyLoading";

  export default {
    name: 'signin',
    components: {},
    data() {
      const validateCode = (rule, value, callback) => {
        if (this.form.checkCode !== value.toUpperCase()) {
          this.createCode();
          callback(new Error("请输入正确的验证码"));
          this.createCode();
        } else {
          callback();
        }
      };
      return {
        logining: false,
        form: {
          username: localStorage.username || '',
          password: localStorage.passwordInfo || '',
          checkCode: '',
          inputCode: '',
        },
        isMemery: false,
        rules: {
          username: [
            {
              required: true,
              message: '请输入用户名',
              trigger: 'blur',
              // validator: checkone
            }
          ],
          password: [
            {
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            }
          ],
          inputCode: [
            {required: true, message: "请输入验证码", trigger: "blur"},
            {min: 4, max: 4, message: "验证码长度为4位", trigger: "blur"},
            {required: true, trigger: "blur", validator: validateCode}
          ]
        }
      };
    },
    created() {
      this.createCode();
    },
    mounted() {
      let _this = this;
      document.onkeydown = function (e) {
        let key = window.event.keyCode;
        if (key === 13) {
          _this.Login('form');
        }
      }
    },
    methods: {
      aa(e) {
        console.log(e)
      },
      Login(formName) {
        let _this = this;
        _this.logining = true;
        _this.$refs[formName].validate(valid => {
          if (valid) {
            this.logining = true
            this.$store.dispatch('Login', this.form).then(() => {
              this.$router.push({path: this.redirect || '/'})
              this.logining = false
            }).catch(e => {
              _this.logining = false;
            });
          } else {
            _this.logining = false;
            return false;
          }
        });
      },
      createCode() {
        //先清空验证码的输入
        this.code = '';
        this.form.checkCode = '';
        this.form.inputCode = '';
        //验证码的长度
        let codeLength = 4;
        //随机数
        let random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        for (let i = 0; i < codeLength; i++) {
          //取得随机数的索引（0~35）
          let index = Math.floor(Math.random() * 36);
          //根据索引取得随机数加到code上
          this.code += random[index];
        }
        //把code值赋给验证码
        this.form.checkCode = this.code;
      }
    },
    watch: {
      isMemery(n, o) {
        if (n) {
          localStorage.username = this.form.username;
          localStorage.passwordInfo = this.form.password;
        } else {
          localStorage.removeItem('username');
          localStorage.removeItem('passwordInfo');
        }
      }
    }
  };
</script>
<style scoped lang="less">
  .bg {
    position: relative;
    overflow: hidden;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url('../../image/bg.jpg');
    background-position: -20% 10%;
    background-size: contain;
    #particles-js {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
    }
  }

  .login-wrap {
    width: 330px;
    border-radius: 5px;
    padding: 20px;
    z-index: 3;
    margin-left: 60%;
    background: rgba(216, 220, 229, 0.5);
    .el-form-item {
      margin-bottom: 25px !important;
    }
    h3 {
      text-align: center;
      color: #ebedef;
      margin-top: 0px;
      margin-bottom: 5px;
      span {
        color: #20a0ff;
      }
    }
    form {
      margin-top: 25px;
      .el-form-item {
        margin-bottom: 15px;
      }
    }
    a {
      text-decoration: none;
      color: #1f2d3d;
    }
    button {
      width: 100%;
      font-weight: 600;
    }
  }

  .yanzhengma_input {
    font-family: 'Exo 2', sans-serif;
    outline: none;
    letter-spacing: 1px;
    font-size: 17px;
    font-weight: normal;
    height: 30px;
    width: 150px;
  }

  .verification {
    border-radius: 12px;
    letter-spacing: 5px;
    margin-left: 50px;
    height: 25px;
    transform: translate(-15px, 0);
  }

  .captcha {
    height: 50px;
    text-align: justify;
  }
</style>
