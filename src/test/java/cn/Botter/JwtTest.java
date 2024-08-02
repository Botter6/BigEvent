package cn.Botter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Botter
 * @date 2024/7/13
 * @Description
 */
public class JwtTest {
    String file = "D:\\Java\\JAVA_CODE\\Studay\\ActualCombat\\Big-Event\\src\\test\\java\\cn\\Botter\\Jwttast";
//        System.out.println(file);
    String fileValues;

    {
        try {
            fileValues = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGen() throws IOException {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id",1);
        claims.put("usrname" , "王五");
        String Token = JWT.create()
                .withClaim("user" , claims) //添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() * 1000*60*60*12)) //添加过期时间
                .sign(Algorithm.HMAC256(fileValues));
//        System.out.println(Token);
    }

    @Test
    public void testParse(){
        String Token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c3JuYW1lIjoi546L5LqUIn0sImV4cCI6NTU0OTY5NDMyNDk5MzkzfQ." +
                "uhwkYxEeLiYVKPkMkF8NmH3OFF7eKa_G2c3Z0MFCeHA";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(fileValues)).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(Token);

        Map<String, Claim> claims = decodedJWT.getClaims();
//        System.out.println(claims.get("user"));
    }

    // 如果篡改头部和载荷部分的内容，验证失败
    // 如果密钥该了，验证失败
    // 如果Tokne失效，验证失败
}
