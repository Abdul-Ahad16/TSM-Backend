package com.stg.tsm.utils;

import java.util.regex.*;

import org.springframework.stereotype.Service;

@Service
public class Checkpassword {
    public static boolean isValidPassword(String password){
        if (password.length() != 8) {
            return false;
        }
        // regex for at least one uppercase letter
        Pattern uppercase = Pattern.compile("[A-Z]");
        Matcher hasUpper = uppercase.matcher(password);
        // regex for at least one lowercase letter
        Pattern lowercase = Pattern.compile("[a-z]");
        Matcher hasLower = lowercase.matcher(password);
        // regex for at least one special character
        Pattern special = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Matcher hasSpecial = special.matcher(password);
        // regex for at least one numeric character
        Pattern numeric = Pattern.compile("[0-9]");
        Matcher hasNumeric = numeric.matcher(password);
        return hasUpper.find() && hasLower.find() && hasSpecial.find() && hasNumeric.find();
    }
}