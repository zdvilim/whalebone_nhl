import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.ArrayList;
import java.util.List;

public class BrowserPage {

    private final Playwright playwright;
    private final Browser browser;
    private final Page page;

    public BrowserPage() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        page = browser.newPage();
    }

    public void openUrl(String url) {
        page.navigate(url);
    }

    public void rejectCookies() {
        getRejectCookiesButton().click();
    }

    public void openTeamPlayersRoster() {
        getTeamMenu().click();
        getTeamRosterMenu().click();
    }

    public List<String> getBirthplaces() {
        List<String> birthplaces = new ArrayList<>();
        Locator locatorBirthplaces = getBirtPlacesColumn();

        locatorBirthplaces.last().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(20000));

        for (int i = 0; i < locatorBirthplaces.count(); i++) {
            birthplaces.add(locatorBirthplaces.nth(i).innerText());
        }

        return birthplaces;
    }

    public void closeBrowser() {
        browser.close();
        playwright.close();
    }

    private Locator getRejectCookiesButton() {
        return page.locator("//button[@id='onetrust-reject-all-handler']");
    }

    private Locator getBirtPlacesColumn() {
        return page.locator("//div[@id='root']//div[@class='rt-tbody']//div[contains(@class, 'birthplace')]/div");
    }

    private Locator getTeamMenu() {
        return page.locator("//button[contains(@class, 'nhl-o-menu__link')]/span[text()='Équipe']");
    }

    private Locator getTeamRosterMenu() {
        return page.locator("//ul[contains(@class, 'nhl-o-dropdown__menu')]//span[text()='Formation']");
    }
}
