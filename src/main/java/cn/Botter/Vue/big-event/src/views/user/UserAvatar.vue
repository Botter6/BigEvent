<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import {ref} from 'vue'
import avatar from '@/assets/default.png'
import {useUserInfoStore} from "@/stores/userInfo.js";
import {useTokenStore} from "@/stores/token.js";
import {userAvatarUpdateService} from "@/api/user.js"
const tokenStore = useTokenStore();
const UserInfoStore = useUserInfoStore();
const uploadRef = ref()
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

//用户头像地址
const imgUrl= ref(UserInfoStore.info.userPic?UserInfoStore.info.userPic:avatar)


//图片上次成功的回调
const uploadSuccess = (result)=>{
  imgUrl.value = result.data;

}
const returndata = ref();
//完成头像修改
const updataAvater = async ()=>{
  //调用接口

  let result = await userAvatarUpdateService(imgUrl.value);
  returndata.value = result.data;
   ElMessage.success("上传成功");
  UserInfoStore.info.userPic=result.data;

}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>更换头像</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-upload
            ref="uploadRef"
            class="avatar-uploader"
            :show-file-list="false"
            :auto-upload = true
            action="/api/upload"
            name = "file"
            :headers="{'Authorization': tokenStore.token}"
            :on-success="uploadSuccess"
        >
          <img v-if="imgUrl" :src="imgUrl" class="avatar" />
          <img v-else :src="avatar" width="278" />
        </el-upload>
        <br />
        <el-button type="primary" :icon="Plus" size="large"  @click="uploadRef.$el.querySelector('input').click()">
          选择图片
        </el-button>
        <el-button type="success" :icon="Upload" size="large" @click = "updataAvater">
          上传头像
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
  :deep {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>