public class Main {

    public static void main(String[] args) {
        NhlTeams nhlTeams = new NhlTeams();
        nhlTeams.verifyNumberOfTeams(32);
        nhlTeams.verifyOldestTeam("Montréal Canadiens");
        nhlTeams.verifyNumberOfTeamsInLocation();
        nhlTeams.verifyNumberOfTeamsDivision("Metropolitan", 8);
        nhlTeams.verifyNationalityOfOldestTeam(nhlTeams.getOfficialSiteUrlOfOldestTeam());
    }
}