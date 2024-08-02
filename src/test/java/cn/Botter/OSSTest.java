//package cn.Botter;
//
//import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
//import com.qiniu.http.Response;
//import com.qiniu.storage.Configuration;
//import com.qiniu.storage.Region;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.storage.model.DefaultPutRet;
//import com.qiniu.util.Auth;
//import lombok.Data;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Component;
//
//import java.io.ByteArrayInputStream;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//
//@Component //注解将该类标记为Spring的组件
////测试类所在包名 test 和 启动类所在包名java 一致(都在同一包src下)，不需要加配置。如果不一致要加配置（加属性）
////@SpringBootTest(classes = SanGengBlogApplication.class)//指定启动类的字节码
//@SpringBootTest
////@ConfigurationProperties 的作用: 让JavaBean中属性值要和配置文件application.xml进行映射
////@ConfigurationProperties(prefix = "oss") //从application.xml 配置文件中读取 内容
//public class OSSTest {
//
//    //    读取的时候，要创建成员变量（变量名要和application.xml文件中的名字一致）
////    并且 成员变量必须创建setter方法，进行自动赋值  （自动读取配置文件赋值）
//    private String accessKey;
//    private String secretKey;
//    private String bucket;
//
//    public void setAccessKey(String accessKey) {
//        this.accessKey = accessKey;
//    }
//    public void setSecretKey(String secretKey) {
//        this.secretKey = secretKey;
//    }
//    public void setBucket(String bucket) {
//        this.bucket = bucket;
//    }
//
//    @Test
//    public void testOss(){
//
////        注：用七牛云的oss，所以导包的时候 要导入七牛云的包。     用人家的代码，导人家的包 com.qiniu.storage
//
//        //构造一个带指定 Region 对象的配置类
////修改1.Region指定数据存储区域，autoRegion()自动根据七牛云账号找到选的区域（我选的是 华北）
//        Configuration cfg = new Configuration(Region.autoRegion());
//        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
////...其他参数参考类注释
//
//        UploadManager uploadManager = new UploadManager(cfg);
////...生成上传凭证，然后准备上传
//
////        注：为了安全起见，AK,SK,bucket存储空间名，都是从application.xml 配置文件中读取到的
////        修改2.复制七牛云官网-个人中心-密钥管理-  AK和SK
////        String accessKey = "";
////        String secretKey = "";
////        修改3.创建存储空间的名字 pk-sg-blog
////        String bucket = "";
//
////默认不指定key的情况下，以文件内容的hash值作为文件名,  比如上传一张图片，名字问hash值生成的名字
////修改4.指定上传文件到oss时，文件的存储名
//        String key = null;
//
//        try {
////修改5 注释掉，默认上传 字符串，写我们自己的上传格式（上传图片）
////            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
////            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
////上传文件——图片格式（本机绝对路径）
//            InputStream inputStream = new FileInputStream("D:\\Java\\JAVA_CODE\\Studay\\ActualCombat\\Big-Event\\src\\main\\java\\cn\\Botter\\files\\R-C.jpg");
//
//            Auth auth = Auth.create(accessKey, secretKey);//创建凭证
//            String upToken = auth.uploadToken(bucket); //上传凭证
//
//            try {
////修改6 put方法 第一个参数 要放上面 自己定义的 inputStream对象
//                Response response = uploadManager.put(inputStream,key,upToken,null, null);
//                //解析上传成功的结果
//                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key); //Fo2AVLRHugoNbek6XZ8Uy-DCnuSL
//                System.out.println(putRet.hash); //Fo2AVLRHugoNbek6XZ8Uy-DCnuSL hash值就是上传后的图片名字
//            } catch (QiniuException ex) {
//                Response r = ex.response;
//                System.err.println(r.toString());
//                try {
//                    System.err.println(r.bodyString());
//                } catch (QiniuException ex2) {
//                    //ignore
//                }
//            }
//        } catch (Exception ex) {
//            //ignore  异常类型 改大一点
//        }
//
//    }
//}
