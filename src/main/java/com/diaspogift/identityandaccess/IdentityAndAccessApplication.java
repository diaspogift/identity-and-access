package com.diaspogift.identityandaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
public class IdentityAndAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityAndAccessApplication.class, args);


        System.out.println(Pattern.matches("\\d{9}", "669262656"));
    }


}
