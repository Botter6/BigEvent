<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//文章分类数据模型
const categorys = ref([
  {
    "id": 3,
    "categoryName": "美食",
    "categoryAlias": "my",
    "createTime": "2023-09-02 12:06:59",
    "updateTime": "2023-09-02 12:06:59"
  },
  {
    "id": 4,
    "categoryName": "娱乐",
    "categoryAlias": "yl",
    "createTime": "2023-09-02 12:08:16",
    "updateTime": "2023-09-02 12:08:16"
  },
  {
    "id": 5,
    "categoryName": "军事",
    "categoryAlias": "js",
    "createTime": "2023-09-02 12:08:33",
    "updateTime": "2023-09-02 12:08:33"
  }
])

//用户搜索时选中的分类id
const categoryId=ref('')

//用户搜索时选中的发布状态
const state=ref('')

//文章列表数据模型
const articles = ref([
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
])
import {
  articleCategoryListService,
  articleListService,
  articleAddService,
  articleCategoryDeleteService
} from '@/api/article.js'

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数


const articleCategoryList = async ()=>{
  let data = (await articleCategoryListService()).data;
  categorys.value = data;
}
//文章列表查询
const getArticles = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null
  }
  let result = await articleListService(params);
  //渲染列表数据
  articles.value = result.data.items
  //为列表中添加categoryName属性
  for(let i=0;i<articles.value.length;i++){
    let article = articles.value[i];
    for(let j=0;j<categorys.value.length;j++){
      if(article.categoryId===categorys.value[j].id){
        article.categoryName=categorys.value[j].categoryName
      }
    }
  }
  //渲染总条数
  total.value=result.data.total
}
articleCategoryList();
getArticles();

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getArticles()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getArticles()
}



import {Plus} from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {useTokenStore} from "@/stores/token.js";
import {ElMessage, ElMessageBox} from "element-plus";
const tokenStore = useTokenStore();
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
  title: '',
  categoryId: '',
  coverImg: '',
  content:'',
  state:''
})


//上传成功的回调

//上传图片成功回调
const uploadSuccess = (img) => {
  //img就是后台响应的数据，格式为：{code:状态码，message：提示信息，data: 图片的存储地址}
  articleModel.value.coverImg=img.data
}


const addArticle = async (clickState)=>{
  articleModel.value.state = clickState;
  let result = await articleAddService(articleModel.value);
  ElMessage.success(result.message?result.message:"添加成功");
  getArticles()
}
const title = ref('')
//修改文章时数据回显
const updateArticleEcho = (row) => {
  // title.value = '修改分类'
  // dialogVisible.value = true
  // //将row中的数据赋值给categoryModel
  // categoryModel.value.categoryName=row.categoryName
  // categoryModel.value.categoryAlias=row.categoryAlias
  // //修改的时候必须传递分类的id，所以扩展一个id属性
  // categoryModel.value.id=row.id
  title.value = '修改分类';
  articleModel.value.coverImg = row.coverImg
  articleModel.value.title = row.title
  articleModel.value.categoryId = row.categoryId
  articleModel.value.content = row.content
  articleModel.value.state = row.state
  articleModel.value.id = row.id;
}
const clearArticleModel = ()=>{
  articleModel.value.coverImg = '';
  articleModel.value.title = '';
  // articleModel.value.categoryId = '';
  articleModel.value.content = '';
  articleModel.value.state = '';
}
import {articleUpdateService,articleDeleteService} from '@/api/article.js'
//更新文章的接口调用
const updateArticleService =async (state)=>{
  articleModel.value.state = state;
  // console.log(articleModel.value.id)
  let result = await articleUpdateService(articleModel.value);
  getArticles()
  ElMessage.success(result.message?result.message:"更新成功");
}
const deleteArticle = async (row)=>{
        // let result = await articleDeleteService(row.id);
        // getArticles()
  ElMessageBox.confirm(
      '你确认要删除这条信息吗?',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center:true,
        draggable: true,
        overflow: true,
      }
  )
      .then(async () => {
        let result = await articleDeleteService(row.id);
        getArticles()
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除',
        })
      })

}

</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章管理</span>
        <div class="extra">
          <el-button type="primary" @click="visibleDrawer = true;title='添加文章'; clearArticleModel();articleModel.content=''">添加文章</el-button>
        </div>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="文章分类：">
        <el-select placeholder="请选择" v-model="categoryId" fit-input-width="true" style="width: 240px">
          <el-option
              v-for="c in categorys"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="发布状态：" style="width: 240px">
        <el-select placeholder="请选择" v-model="state">
          <el-option label="已发布" value="已发布"></el-option>
          <el-option label="草稿" value="草稿"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click = "getArticles();">搜索</el-button>
        <el-button @click = "categoryId = ''; state = ''; ">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 文章列表 -->
    <div>
    <el-table :data="articles.slice((pageNum-1)*pageSize , pageSize*pageNum)" style="width: 100%">
      <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
      <el-table-column label="分类" prop="categoryName"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="visibleDrawer=true;updateArticleEcho(row);"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteArticle(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    </div>
    <!-- 分页条 -->
    <div>
      <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[5 ,10, 15,20]"
                     layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
                     @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </div>


    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
      <!-- 添加文章表单 -->
      <el-form :model="articleModel" label-width="100px" >
        <el-form-item label="文章标题" >
          <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select placeholder="请选择" v-model="articleModel.categoryId">
            <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章封面">
<!--        auto-upload:是否自动上传
            action: 服务器接口路径
            name: 上传的文件字段名
            headers: 设置上传的请求头
            on-success: 上传成功的回调函数-->
          <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                     action = '/api/upload'
                     name = "file"
                     :headers = "{'Authorization': tokenStore.token}"
                     :on-success = "uploadSuccess"
          >
            <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章内容">
          <div class="editor">
            <quill-editor
                theme="snow"
                v-model:content="articleModel.content"
                contentType="html"
            >
            </quill-editor>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="title=='添加文章'?addArticle('已发布'):updateArticleService('已发布') ;visibleDrawer=false; ">发布</el-button>
          <el-button type="info" @click="title=='添加文章'?addArticle('草稿'):updateArticleService('草稿') ;visibleDrawer=false;">草稿</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>
<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}



/* 抽屉样式 */
.avatar-uploader {
  :deep{
    .avatar {
      width: 178px;
      height: 178px;
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
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}

</style>