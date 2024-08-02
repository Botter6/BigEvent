import request from  '@/utils/request.js'

export  const userRegisterService= (registerData) =>{
    const params = new URLSearchParams();
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    // console.log('userRegisterService函数进来了');
    return request.post('/user/register', params);
}

export const userLoginService= (loginData) =>{
    const params = new URLSearchParams();
    for (let key in loginData) {
        params.append(key, loginData[key]);
    }
    return request.post('/user/login', params);
}


//详细信息
export const userInfoService = () =>{
    return request.get('/user/userInfo');
}

//修改用户信息
export const userInfoUpdateService = (userInfoData) =>{
    return request.put('/user/update',userInfoData);
}


export const userAvatarUpdateService = (avatarData) =>{
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarData);
    return request.patch('/user/updateAvatar',params);
}


//更新密码
export const userUpdatePwdService= (userPwd) =>{
    const params = new URLSearchParams();
    console.log(userPwd)

    return request.patch('/user/updatePwd',userPwd);
}