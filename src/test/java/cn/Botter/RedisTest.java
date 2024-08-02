package cn.Botter;

import net.sf.jsqlparser.statement.select.KSQLWindow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author Botter
 * @date 2024/7/18
 * @Description
 */
@SpringBootTest
//在测试类添加这个注解，那么将来单元测试方法执行之前，会先初始化Spring容器
public class RedisTest {
//    private SringRedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testSer(){
        //存储一个键值对
        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();
        op.set("username", "Botter");
        op.set("id","1",15, TimeUnit.SECONDS);
    }
    @Test
    public void testGet(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        System.out.println(ops.get("username"));
    }
}
