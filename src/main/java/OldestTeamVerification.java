import java.util.List;

public class OldestTeamVerification {

    private final static String CANADA = "CAN";
    private final static String USA = "USA";
    private final BrowserPage browserPage;

    public OldestTeamVerification() {
        browserPage = new BrowserPage();
    }

    public void verifyNationality(String url) {
        browserPage.openUrl(url);
        browserPage.rejectCookies();
        browserPage.openTeamPlayersRoster();
        verificationOfNationality();
        browserPage.closeBrowser();
    }

    private void verificationOfNationality() {
        int numberOfCanPlayers = 0;
        int numberOfUsaPlayers = 0;
        List<String> birthplaces = browserPage.getBirthplaces();

        for (String birthplace: birthplaces) {
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

    private void printMessage(String message) {
        System.out.println(message);
    }
}
