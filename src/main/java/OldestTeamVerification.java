import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class OldestTeamVerification {

    private final static String CANADA = "CAN";
    private final static String USA = "USA";
    private final Playwright playwright;
    private final Browser browser;
    private final Page page;

    public OldestTeamVerification(String urlOldestTeam) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        page = browser.newPage();
        page.navigate(urlOldestTeam);
    }

    public void verifyNationality() {
        rejectCookies();
        openTeamPlayersRoster();
        getAndVerifyNationality();
    }

    private void rejectCookies() {
        getRejectCookiesButton().click();
    }

    private void openTeamPlayersRoster() {
        getTeamMenu().click();
        getTeamRosterMenu().click();
    }

    private void getAndVerifyNationality() {
        Locator birthplaces = getBirtPlacesColumn();

        birthplaces.last().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(20000));

        int numberOfCanPlayers = 0;
        int numberOfUsaPlayers = 0;

        for (int i = 0; i < birthplaces.count(); i++) {
            String birthplace = birthplaces.nth(i).innerText();
            if (birthplace.contains(CANADA)) numberOfCanPlayers++;
            if (birthplace.contains(USA)) numberOfUsaPlayers++;
        }

        String message;

        if (numberOfCanPlayers > numberOfUsaPlayers) {
            message = String.format("SUCCESS - Number of CAN players - '%d' (expected is 18) is bigger then USA players - '%d' (expected is 5)", numberOfCanPlayers, numberOfUsaPlayers);
        } else {
            message = String.format("FAILURE - Number of CAN players - '%d' (expected is 18) is not bigger then USA players - '%d' (expected is 5)", numberOfCanPlayers, numberOfUsaPlayers);
        }

        printMessage("-----> open web browser and scrape roster of the oldest team and verify there's more Canadian players than players from USA");
        printMessage(message);
    }

    public void closeBrowser() {
        browser.close();
        playwright.close();
    }

    private void printMessage(String message) {
        System.out.println(message);
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
