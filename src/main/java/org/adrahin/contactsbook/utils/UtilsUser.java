package org.adrahin.contactsbook.utils;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UtilsUser {
    public String generateToken(String login, String password) {

        Random random = new Random();

        //генерация первых 5 случайных цифр
        String randomFiveDigits = IntStream.range(0, 5)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        return randomFiveDigits + "{" + login + "|" + password + "}";
    }

    public boolean checkCorrectPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasSpecialChar;
        boolean hasDigit;
        boolean hasUpper;
        String specialChars = "!@#$%^&*()-+=<>?";

        hasSpecialChar = password.chars()
                .mapToObj(c -> (char) c)
                .anyMatch(c -> specialChars.contains(String.valueOf(c)));

        hasDigit = password.chars()
                .anyMatch(Character::isDigit);

        hasUpper = password.chars()
                .anyMatch(Character::isUpperCase);

        // Если нет цифры и спец символа и большой буквы -> false:
        return hasDigit && hasSpecialChar && hasUpper;
    }

    public String extractLoginFromToken(String token) {
        int startLoginIndex = token.indexOf("{");
        int endLoginIndex = token.indexOf("|");
        return token.substring(startLoginIndex + 1, endLoginIndex);
    }

    // Метод для хеширования пароля
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для конвертации массива байтов в шестнадцатеричную строку
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
