<template>
  <body id="paper">
    <div>
        <!--  这里绑定定义的数据-->
        <el-form v-if="this.login" :rules="rules" class="login-container" label-position="left"
                label-width="0px" v-loading="loading">
            <h1 class="login_title">到云后台系统登录</h1>
            <el-form-item prop="account">
            <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="手机号"></el-input>
            </el-form-item>
            <el-form-item prop="checkPass">
            <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码" show-password></el-input>
            </el-form-item>
            <el-checkbox class="login_remember" v-model="checked" label-position="left">记住密码</el-checkbox>
            <el-form-item style="width: 100%">
            <el-button type="info" @click.native.prevent="submitClick" style="width: 100%">登录</el-button>
            </el-form-item>
          <div>
            <el-link @click="onChangeMode">注册账号</el-link>
          </div>
        </el-form>
      <el-form v-else :rules="rules" class="login-container" label-position="left"
               label-width="0px" v-loading="loading">
        <h1 class="login_title">注册</h1>
        <el-form-item prop="account">
          <el-input type="text" v-model="registerForm.phone" auto-complete="off" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" v-model="registerForm.password" auto-complete="off" placeholder="密码" show-password></el-input>
        </el-form-item>

        <el-form-item  prop="smsCode">
          <el-row :gutter="10">
            <el-col :span="18">
              <el-input type="string" v-model="registerForm.smsCode" autocomplete="off" placeholder="验证码"></el-input>
            </el-col>
            <el-col :span="6">
              <el-button type="info" @click="getMsgCode()">获取</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item style="width: 100%">
          <el-button type="info" @click.native.prevent="onRegister" style="width: 100%">注册</el-button>
        </el-form-item>
        <div>
          <el-link @click="onChangeMode">前往登录</el-link>
        </div>
      </el-form>

    </div>
  </body>
</template>
<script>
  import {postRequest} from '../utils/api'
  export default{
    name: 'Login',
    data(){
      return {
        // 这里定义数据
        rules: {
          account: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          checkPass: [{required: true, message: '请输入密码', trigger: 'blur'}],
          smsCode: [{ required: true, message: '验证码不能为空'},
                    {pattern: '/[a-zA-Z0-9}$/',message: '验证码只能为数字或字母',trigger: 'blur' }]
        },
        checked: true,
        msgValidate: false,
        login: true,
        loginForm: {
          username: 'admin',
          password: '123456'
        },
        registerForm: {
          phone: '',
          password: '',
          smsCode: ''
        },
        loading: false
      }
    },
    methods: {
      onChangeMode: function () {
        this.login = !this.login
      },
      onRegister: function () {
        return
      },
      // 这里使用定义的数据
      submitClick: function () { // 提交表单
        let _this = this;
        this.loading = true;
        postRequest('/login', { // 被封装了的axios方法
          // 向服务器发送请求
          username: this.loginForm.username,
          password: this.loginForm.password
        }).then(resp => {
          _this.loading = false;
          if (resp.status == 200) { // 200 表示成功 300 表示重定向
            //成功
            let json = resp.data;
            if (json.status == 'success') { // 返回成功便跳转到home
              _this.$store.commit('login', _this.loginForm)
              _this.$router.replace({path: '/home'});
            } else {
              _this.$alert('登录失败!', '💩失败!');
            }
          } else {
            //失败
            _this.$alert('登录失败!', '💩失败!');
          }
        }, resp => {
          console.log(resp.status);
          _this.loading = false;
          _this.$alert('哎呀！找不到服务器⊙﹏⊙||!', '💩真尴尬!');
        });
      },
      getMsgCode() {
        let _this = this;
        this.loading = false;
        postRequest('/getcode',{
          code: this.loginForm.smsCode
        }).then(resp => {
          _this.$alert('验证码发送成功');
        })
      }
    }
  }
</script>
<style>
#paper {
  background:url("../assets/19.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  top: 0;
}
body {
  margin: 0;
}

.login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background-color: rgba(255,255,255,0.7);
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
</style>
