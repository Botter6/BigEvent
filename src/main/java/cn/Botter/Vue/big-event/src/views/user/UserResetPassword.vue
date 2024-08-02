<script setup >
import {Lock, User} from "@element-plus/icons-vue";

import {ref} from "vue";
import {ElMessage} from "element-plus";

import {userUpdatePwdService} from '@/api/user'

import {useUserInfoStore} from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore();
const passwordModel = ref({
  old_pwd:'',
  new_pwd:'',
  re_pwd:''
})
const checkPassword = (rule, value, callback) => {
  if (value === null) {
    callback(new Error("请再次输入密码"));
  } else if (value != passwordModel.value.password) {
    callback(new Error("两次输入的密码不一致"));

  } else {
    callback();
  }

}


//定义校验规则
const rules = {
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
    {min: 5, max: 15, message: "用户名长度必须在5到15", trigger: "blur"},
  ],

  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {min: 6, max: 15, message: "密码长度必须在6到15", trigger: "blur"}
  ],
  rePassword: [{validator: checkPassword, trigger: "blur"}]
}

const updatePassword =   async () =>{
  let result = await userUpdatePwdService(passwordModel.value);
  // if (result.code == 0) {
  //   alert(result.message ? result.message : "注册成功");
  // } else {
  //   alert("注册失败");
  // alert(result.message ? result.message : "注册成功");
  console.log(result)
  ElMessage({
    message: "修改密码成功",
    type: "success",
    plain:true,
  })
}

</script>
<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <!-- 修改密码 -->
      <el-form ref="form" size="large" autocomplete="off" :model="passwordModel" :rules="rules">
        <el-form-item>
          <h1>修改密码</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="Lock"  placeholder="请输入原密码" v-model="passwordModel.old_pwd">

          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                    v-model="passwordModel.new_pwd"></el-input>
        </el-form-item>
        <el-form-item prop="rePassword">
          <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码 "
                    v-model="passwordModel.re_pwd"></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="updatePassword">
            修改密码
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
        </el-form-item>
      </el-form></el-col>
  </el-row>

</template>


<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo.png') no-repeat 60% center / 240px auto,
    url('@/assets/backgrond.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>