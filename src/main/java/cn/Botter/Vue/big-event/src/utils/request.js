//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import {ElMessage} from "element-plus";
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
const baseURL = '/api'

const instance = axios.create({baseURL})

//导入token状态
import { useTokenStore } from '@/stores/token.js';
//添加请求拦截器
instance.interceptors.request.use(
    (config)=>{
        //在发送请求之前做什么
        let tokenStore = useTokenStore()
        //如果token中有值，在携带
        if(tokenStore.token){
            config.headers.Authorization=tokenStore.token
        }
        return config
    },
    (err)=>{
        //如果请求错误做什么
        Promise.reject(err)
    }
)
//添加响应拦截器
import router from '@/router'
instance.interceptors.response.use(
    result=>{
        if (result.data.code === 0){
            return result.data;
        }
        // alert(result.data.message ?result.data.message:"服务异常" );
        ElMessage({
            message: result.data.message ?result.data.message:"服务异常",
            type: "error",
            plain:true,
        })
        //异步状态的操作转换为失败
        return Promise.reject(result.data);
    },
    err=>{
        console.log(err.response.status)
        if (err.response.status === 401){
            ElMessage({
                message: "请先登录",
                type: "error",
                plain:true,
            });
           router.push('/login');
        }
        else {
            ElMessage({
                message: "服务异常",
                type: "error",
                plain:true,
            })
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;