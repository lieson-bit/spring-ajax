package com.shopme;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "alex";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("password I created: " + encodedPassword);
    }
}
