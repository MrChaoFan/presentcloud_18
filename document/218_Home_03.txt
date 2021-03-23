<template>
  <el-container>
    <div class="wrapper">
      <y-header></y-header>
      <y-sidebar></y-sidebar>
      <div class="content-box">
        <div class="content">
          <div class="router-info">
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div v-if="this.$router.currentRoute.name !== 'ManageBoard'" style="margin-top: 15px;width:40%">
            <el-input placeholder="请输入内容" v-model="input" class="input-with-select">
              <el-select v-model="select" slot="prepend" placeholder="请选择">
                <el-option label="学号" value="1"></el-option>
                <el-option label="教工号" value="2"></el-option>
                <el-option label="手机号" value="3"></el-option>
              </el-select>
              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
          </div>
              <router-view></router-view>
        </div>
      </div>
    </div>
  </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import ySidebar from "@/components/Sidebar";
import yHeader from "@/components/Header";
export default {
  name: 'Home',
  components: {
    ySidebar,
    yHeader
  },
  data() {
    return {
      currentUserName: 'Admin',
      collapse: false,
      select: '学号',
      input: ''
    }
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
  @import url("//unpkg.com/element-ui@2.15.1/lib/theme-chalk/index.css");
  .home-container {
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
  }
  .content-box {
    margin-top: 10px;
    margin-left: 10px;
  }
  .wrapper {
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .router-info {
    width: 100%;
    padding-left: 8px;
    padding-top: 10px;
    padding-right: 10px;
    padding-bottom: 10px;
    background: #fff;
  }
  .content-box {
    position: absolute;
    left: 250px;
    right: 0;
    top: 70px;
    bottom: 0;
    padding-bottom: 30px;
    -webkit-transition: left .3s ease-in-out;
    transition: left .3s ease-in-out;
    background: #fafafa;
  }
  .el-select .el-input {
    width: 130px;
  }
  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }
</style>
