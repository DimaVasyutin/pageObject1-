package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.Transfer;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    LoginPageV2 loginPage;

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        loginPage = open("http://localhost:9999/", LoginPageV2.class);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var sum = DataHelper.getValidSum(firstCardBalance);
        var expectedFirstBalanceCars = firstCardBalance - sum;
        var expectedSecondBalanceCars = secondCardBalance + sum;
        var transfer = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transfer.transferValid(firstCardInfo, String.valueOf(sum));
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstBalanceCars, actualFirstCardBalance);
        assertEquals(expectedSecondBalanceCars, actualSecondCardBalance);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var sum = DataHelper.getValidSum(secondCardBalance);
        var expectedFirstBalanceCars = firstCardBalance + sum;
        var expectedSecondBalanceCars = secondCardBalance - sum;
        var transfer = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transfer.transferValid(secondCardInfo, String.valueOf(sum));
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstBalanceCars, actualFirstCardBalance);
        assertEquals(expectedSecondBalanceCars, actualSecondCardBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstToFirst() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var sum = DataHelper.getValidSum(firstCardBalance);
        var expectedFirstBalanceCars = firstCardBalance;
        var transfer = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transfer.transferValid(firstCardInfo, String.valueOf(sum));
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        assertEquals(expectedFirstBalanceCars, actualFirstCardBalance);
    }

    @Test
    void shouldTransferMoneyFromSecondToSecond() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var sum = DataHelper.getValidSum(firstCardBalance);
        var expectedFirstBalanceCars = firstCardBalance - sum;
        var expectedSecondBalanceCars = secondCardBalance + sum;
        var transfer = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transfer.transferValid(firstCardInfo, String.valueOf(sum));
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstBalanceCars, actualFirstCardBalance);
        assertEquals(expectedSecondBalanceCars, actualSecondCardBalance);
    }

    @Test
    void shouldTransferMoneyFromInvalidToFirst() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var invalidCardInfo = DataHelper.getInvalidCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var sum = DataHelper.getValidSum(firstCardBalance);
        var transfer = dashboardPage.selectCardToTransfer(firstCardInfo);
        transfer.makeTransfer(invalidCardInfo, String.valueOf(sum));
        transfer.errorMessage("Ошибка! Произошла ошибка");
    }

    @Test
    void shouldTransferMoneyFromInvalidToSecond() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var secondCardInfo = DataHelper.getSecondCard();
        var invalidCardInfo = DataHelper.getInvalidCard();
        var firstCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var sum = DataHelper.getValidSum(firstCardBalance);
        var transfer = dashboardPage.selectCardToTransfer(secondCardInfo);
        transfer.makeTransfer(invalidCardInfo, String.valueOf(sum));
        transfer.errorMessage("Ошибка! Произошла ошибка");
    }

    @Test
    void shouldTransferFromFirstToSecondMoreThanThereIsMoney() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var sum = DataHelper.getInvalidSum(firstCardBalance);
        var transfer = dashboardPage.selectCardToTransfer(secondCardInfo);
        transfer.makeTransfer(firstCardInfo, String.valueOf(sum));
        transfer.errorMessage("Ошибка! Произошла ошибка");
    }

    @Test
    void shouldTransferFromSecondToFirstMoreThanThereIsMoney() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var sum = DataHelper.getInvalidSum(secondCardBalance);
        var transfer = dashboardPage.selectCardToTransfer(firstCardInfo);
        transfer.makeTransfer(secondCardInfo, String.valueOf(sum));
        transfer.errorMessage("Ошибка! Произошла ошибка");
    }

}

