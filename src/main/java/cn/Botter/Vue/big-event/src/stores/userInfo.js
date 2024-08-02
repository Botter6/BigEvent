import {defineStore} from "pinia";
import {ref} from "vue";

const useUserInfoStore = defineStore('userInfo',()=>{
    const info = ref({})
    const setData=(newData)=>{
        info.value = newData;
    }
    const removeData=()=>{
        info.value = {};
    }
    return {info,setData,removeData}
},{persist:true})

export {useUserInfoStore}