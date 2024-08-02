import request from '@/utils/request.js'
import {useTokenStore} from '@/stores/token.js'
export const articleCategoryListService = () =>{
    // const tokenStore = useTokenStore();
    // //在pinia中定义的响应式数据不需要.value更新值
    // return request.get('/category' , {headers:{'Authorization':tokenStore.token}});
    return request.get('/category' );
}


//添加文章分类
export const articleCategoryAddService = (categoryModel) => {
    return request.post('/category', categoryModel);
}

export const articleCategoryUpdateService = (categoryModel) => {
    return request.put('/category', categoryModel);
}

export const articleCategoryDeleteService = (id) => {
    return request.delete('/category?id=' + id);
}

export const articleListService = (params) =>{
    return request.get('/article',{params:params} );
}

export const articleAddService = (articleData) =>{
    return request.post('/article',articleData);

}

export const articleUpdateService = (articleData) =>{
    return request.put('/article/update',articleData);
}

//删除文章
export const articleDeleteService = (id) =>{
    return request.delete('/article?id='+id );
}