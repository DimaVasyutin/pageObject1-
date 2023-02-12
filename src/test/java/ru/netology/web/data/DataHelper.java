package ru.netology.web.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class AccountNumbers {
        private String accountNumber;
        private String idCard;

    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AccountNumbers getFirstCard() {
        return new AccountNumbers("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static AccountNumbers getSecondCard() {
        return new AccountNumbers("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static AccountNumbers getInvalidCard() {
        return new AccountNumbers("5559 0000 0000 0003", "");
    }

    public static int getValidSum(int balance) {
        return new Random().nextInt(balance) + 1;
    }

    public static int getInvalidSum(int balance) {
        return Math.abs(balance) + new Random().nextInt(10000);
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }
}
