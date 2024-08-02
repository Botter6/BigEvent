import {defineStore} from 'pinia'
import {ref} from 'vue'
/*
参数1：
名字，且具有唯一性
参数2：函数，函数的内部可以定义所有的状态
 */
export const useTokenStore=defineStore('token',()=>{
    const token = ref('');

    const setToken = (newToken)=>{
        token.value = newToken;
    }

    const removeToken = ()=>{
        token.value = ''
    }
    return {token,setToken,removeToken};

},
    {
        persist: true,
    });

