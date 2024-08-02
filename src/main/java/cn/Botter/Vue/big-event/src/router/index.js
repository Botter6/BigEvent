import {createRouter,createWebHistory}from 'vue-router'

import LoginView from '@/views/Login.vue'
import LayOutVIew from "@/views/LayOut.vue";
import ArticleCategoryView from "@/views/article/ArticleCategory.vue";
import ArticleManageView from "@/views/article/ArticleManage.vue";
import UserAvatarView from "@/views/user/UserAvatar.vue";
import UserInfoView from "@/views/user/UserInfo.vue";
import UserResetPasswordView from "@/views/user/UserResetPassword.vue";
const routes = [

    {
        path: '/login',
        ame: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path:'/',component: LayOutVIew,redirect:'/article/manage',
        children:[
            {path:'/article/category',component: ArticleCategoryView},
            {path:'/article/manage',component: ArticleManageView},
            {path:'/user/avatar',component: UserAvatarView },
            {path:'/user/info',component: UserInfoView},
            {path:'/user/resetPassword',component: UserResetPasswordView},
        ]
    }
]

const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

export default router;