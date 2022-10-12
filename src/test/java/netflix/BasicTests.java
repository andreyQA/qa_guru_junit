package netflix;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import netflix.data.Locals;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static netflix.data.Locals.NL;
import static netflix.data.Locals.NL_EN;

public class BasicTests {
    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://www.netflix.com";
        Configuration.holdBrowserOpen = true;
    }
    @Disabled
    @DisplayName("Check that page is successfully loaded")
    @Test
    void titleTest() {
        open("/");
        $(".footer-country").shouldHave(text("Netflix"));
    }

    @ValueSource(strings = {"Unlimited films, TV programmes and more.", "Enjoy on your TV.", "Download your programmes to watch offline.", "Watch everywhere.", "Create profiles for children."})
    @ParameterizedTest(name = "Check all titles on main page")
    void netflixTitlesOnMainPage(String title) {
        open("/");
        $$(".our-story-card-title").find(text(title));
    }

    @CsvSource(value = {
            "fff, Email is required.",
            "invalidEmail, Please enter a valid email address.",
            "emailfdhfhdjf@gmail.com, Finish setting up your account"
    })
    @ParameterizedTest(name = "Check that email field has different messages")
    void checkEmailField(String input, String output) {
        open("/");
        $("#id_email_hero_fuji").setValue(input);
        $(".cta-btn-txt").click();
        $(byText(output)).should(exist);
    }
    static Stream<Arguments> footerLinksDataProvider() {
        return Stream.of(
                Arguments.of(List.of("FAQ", "Help Centre", "Account", "Media Centre", "Investor Relations", "Jobs", "Redeem gift cards", "Buy gift cards", "Ways to Watch", "Terms of Use", "Privacy", "Cookie Preferences", "Corporate Information", "Contact Us", "Speed Test", "Legal Guarantee", "Legal Notices", "Only on Netflix"),
                        NL_EN),
                Arguments.of(List.of("Veelgestelde vragen", "Helpcentrum", "Account", "Mediacenter", "Relaties met investeerders", "Vacatures", "Cadeaubonnen gebruiken", "Cadeaubonnen kopen", "Hoe kun je Netflix kijken?", "Gebruiksvoorwaarden", "Privacy", "Cookievoorkeuren", "Bedrijfsgegevens", "Contact opnemen", "Snelheidstest", "Wettelijke garantie", "Wettelijke bepalingen", "Alleen op Netflix"),
                        NL)
        );
    }
    @MethodSource("footerLinksDataProvider")
    @ParameterizedTest(name = "Check footer links")
    void footerLinksCheck(List<String> linksTexts, Locals locals) {
        open("/"+locals.asValue());
        $$(".footer-link-item").shouldHave(texts(linksTexts));
    }


}
