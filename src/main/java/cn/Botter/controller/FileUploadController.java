package cn.Botter.controller;

import cn.Botter.pojo.Result;
import cn.Botter.utils.QiNiuOssUtil;
import cn.Botter.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Botter
 * @date 2024/7/15
 * @Description
 */
@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload( MultipartFile file) throws IOException {
        //把文件的内容存储到本地磁盘中
        String name = file.getOriginalFilename();
        //保证文件的名字唯一
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        String s = "uid"+id.toString()+"uuid"+UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
//        file.transferTo(new File("D:\\Java\\JAVA_CODE\\Studay\\ActualCombat\\Big-Event\\src\\main\\java\\cn\\Botter\\files\\"+s ));
        String url = QiNiuOssUtil.update("UserAvatar/"+s, file.getInputStream());
//        System.out.println(url);
        return Result.success(url);
    }
}
