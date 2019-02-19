<template>
  <section>
    <el-container>
      <div class="head-wrapper">
        <el-header>
          <el-row>
            <el-col :span="3" :offset="1">
              <div style="height: 60px;line-height: 60px;color: #ededed;">
                <label>YC 远昌</label>
              </div>
            </el-col>
            <el-col :span="16">
              <el-col :offset="6" :span="12">
                <el-menu :default-active="activeIndex"
                         active-text-color="#FFFFFF"
                         background-color="#333333"
                         text-color="#ededed"
                         mode="horizontal" @select="handleSelect">
                  <el-menu-item index="1">首页</el-menu-item>
                  <el-submenu index="2">
                    <template slot="title">我的工作台</template>
                    <el-menu-item index="2-1">选项1</el-menu-item>
                    <el-menu-item index="2-2">选项2</el-menu-item>
                    <el-menu-item index="2-3">选项3</el-menu-item>
                    <el-submenu index="2-4">
                      <template slot="title">选项4</template>
                      <el-menu-item index="2-4-1">选项1</el-menu-item>
                      <el-menu-item index="2-4-2">选项2</el-menu-item>
                      <el-menu-item index="2-4-3">选项3</el-menu-item>
                    </el-submenu>
                  </el-submenu>
                  <el-menu-item index="3">联系我们</el-menu-item>
                  <el-menu-item index="4"><a href="/dashboard" target="_blank">后台管理</a></el-menu-item>
                </el-menu>
              </el-col>
            </el-col>
            <el-col :span="2" :offset="2" style="height: 60px;line-height: 60px">
              <el-col :span="8">
                <div @click="find">
                  <svg-icon icon-class="find" class="page-find"></svg-icon>
                </div>
              </el-col>
              <el-col :span="8" :offset="3">
                <div @click="login">
                  <svg-icon icon-class="login" class="page-login"></svg-icon>
                </div>
              </el-col>
              <!--<el-button type="success" round>管理员登录</el-button>-->
              <!--<el-button type="success" round>注册</el-button>-->
            </el-col>
          </el-row>
        </el-header>
      </div>
      <div class="page-component">

        <el-row>
          <el-carousel :interval="4000" height="600px">
            <el-carousel-item v-for="item in imgUrl" :key="item.name">
              <div class="pic-font">
                <label>{{item.name}}</label>
              </div>
              <img :src="item.path" style="height: 100%;width: 100%" @click="clickPic(item.id)"/>
            </el-carousel-item>
          </el-carousel>
          <!--<line-chart chart-id="line-1" height="400px" width="50%"></line-chart>-->
        </el-row>

      </div>
    </el-container>
  </section>
</template>

<script>
  import LineChart from "../../components/Echarts/LineChart";

  export default {
    name: "index",
    components: {LineChart},
    data() {
      return {
        activeIndex: '1',
        imgUrl: [
          {id: 1, name: '啊啊啊啊啊', path: require('../../icons/img/1.jpg')},
          {id: 2, name: '不不不不不', path: require('../../icons/img/2.jpg')},
          {id: 3, name: '错错错错错', path: require('../../icons/img/3.jpg')},
          {id: 4, name: '等等等等等', path: require('../../icons/img/4.jpg')}
        ]
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      find() {
        this.$prompt('请输入编号', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(({value}) => {
          this.$message({
            type: 'success',
            message: '你输入的编号是: ' + value
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
      login() {

      },
      clickPic(id) {
        alert(id)
      }
    }
  }
</script>

<style scoped>
  .head-wrapper {
    position: fixed;
    width: 100%;
    left: 0;
    top: 0;
    z-index: 1500;
    background-color: #333333;
  }

  .page-component {
    height: calc(100% - 80px);
    margin-top: 60px;
    width: 100%;
  }

  .pic-font {
    position: absolute;
    transform: translate(-50%, -50%);
    left: 50%;
    top: 95%;
    color: red;
    font-size: 2em;
    z-index: 9999;
  }

  .el-menu--horizontal {
    border-bottom: none;
  }

  .el-menu--horizontal > .el-menu-item.is-active {
    border-bottom: none;
  }

  .page-find {
    height: 30px;
    width: 30px;
    vertical-align: middle;
    cursor: pointer;
  }

  .page-login {
    height: 40px;
    width: 40px;
    vertical-align: middle;
    cursor: pointer;
  }
</style>
