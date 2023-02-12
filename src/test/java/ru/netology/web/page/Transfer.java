package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class Transfer {
    private SelenideElement transferSum = $("[data-test-id=amount] input");
    private SelenideElement transferFrom = $("[data-test-id=from] input");
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]");
    private SelenideElement headTranfer = $(byText("Пополнение карты"));
    private SelenideElement errorTransfer = $("[data-test-id='error-notification'] .notification__content");

    public Transfer() {
        headTranfer.shouldBe(visible);
    }

    public void makeTransfer(DataHelper.AccountNumbers cardsInfo, String sum) {
        transferSum.setValue(sum);
        transferFrom.setValue(cardsInfo.getAccountNumber());
        actionTransfer.click();
    }

    public DashboardPage transferValid(DataHelper.AccountNumbers accountNumbers, String sum) {
        makeTransfer(accountNumbers, sum);
        return new DashboardPage();
    }

    public void errorMessage(String expectedText) {
        errorTransfer.shouldHave(exactText(expectedText), Duration.ofSeconds(5)).shouldBe(visible);
    }


}
