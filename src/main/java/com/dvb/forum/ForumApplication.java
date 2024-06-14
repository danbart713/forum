package com.dvb.forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumApplication {

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            SecretKey key = Jwts.SIG.HS512.key().build();
//            String secretString = Encoders.BASE64.encode(key.getEncoded());
//            System.out.println(secretString);
//        }
        SpringApplication.run(ForumApplication.class, args);
    }

}