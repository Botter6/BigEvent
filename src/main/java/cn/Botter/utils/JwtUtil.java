package cn.Botter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    String file = "D:\\Java\\JAVA_CODE\\Studay\\ActualCombat\\Big-Event\\src\\test\\java\\cn\\Botter\\Jwttast";
    //        System.out.println(file);
    String fileValues = "Botter";
    {
        try {
            fileValues = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static String KEY = "Botter";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(KEY));
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
