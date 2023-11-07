import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NhlTeams {

    private static final ApiReader apiReader = new ApiReader();
    private final ArrayList<Team> teamsList;

    public NhlTeams() {
        teamsList = apiReader.getTeams();
    }

    public void verifyNumberOfTeams(int expectedNumberOfTeams) {
        int numberOfTeams = teamsList.size();
        String message;

        if (numberOfTeams == expectedNumberOfTeams) {
            message = String.format("SUCCESS - Number of teams is '%d', it is expected number of teams.", numberOfTeams);
        } else {
            message = String.format("FAILURE - Found number of teams is '%d', but expected number of teams is '%d'.", numberOfTeams, expectedNumberOfTeams);
        }

        printMessage("-----> verify the response returned expected count of teams (32 in total)");
        printMessage(message);
    }

    public void verifyOldestTeam(String expectedName) {
        Team oldestTeam = getOldestTeam();
        String message;

        if (oldestTeam.getName().equals(expectedName)) {
            message = String.format("SUCCESS - Oldest teams is '%s', it is expected oldest team.", oldestTeam.getName());
        } else {
            message = String.format("FAILURE - Found oldest teams is '%s', but expected oldest team is '%s'.", oldestTeam.getName(), expectedName);
        }

        printMessage("-----> verify the oldest team is Montréal Canadiens");
        printMessage(message);
    }

    public void verifyNumberOfTeamsInLocation() {
        // Map of City and number of teams in the city
        Map<String, Integer> cities = new HashMap<>();

        for (Team team : teamsList) {
            if (cities.containsKey(team.getLocationName())) {
                // city record exists
                cities.computeIfPresent(team.getLocationName(), (k, v) -> v + 1);
            } else {
                // city found for the first time
                cities.put(team.getLocationName(), 1);
            }
        }

        List<Map.Entry<String, Integer>> citiesWithMoreTeams = cities.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .toList();

        printMessage("-----> verify there's a city with more than 1 team and verify names of these teams");

        if (citiesWithMoreTeams.isEmpty()) {
            // no city with two or more teams
            printMessage("FAILURE - There is no city with more than 1 team in NHL.");
        } else {
            // city/cities with more teams found
            for (Map.Entry<String, Integer> cityWithMoreTeam : citiesWithMoreTeams) {
                String city = cityWithMoreTeam.getKey();
                int teamNumber = 1;
                List<Team> teams = teamsList.stream().filter(team -> team.getLocationName().equals(city)).toList();

                for (Team team : teams) {
                    printMessage(String.format("SUCCESS - City '%s' - team no '%d' is '%s' - full name '%s'", city, teamNumber, team.getTeamName(), team.getName()));
                    teamNumber++;
                }
            }
        }
    }

    public void verifyNumberOfTeamsDivision(String expectedDivisionName, int expectedNumberOfTeams) {
        int numberOfDivisionTeams = teamsList.stream().filter(team -> team.getDivision().getName().equals(expectedDivisionName)).toList().size();

        String message;

        if (numberOfDivisionTeams == expectedNumberOfTeams) {
            message = String.format("SUCCESS - Number of teams in division '%s' is '%d', it is expected number of teams.", expectedDivisionName, numberOfDivisionTeams);
        } else {
            message = String.format("FAILURE - Found number of teams in division '%s' is '%d', but expected number of teams is '%d'.", expectedDivisionName, numberOfDivisionTeams, expectedNumberOfTeams);
        }

        printMessage("-----> verify there are 8 specific teams in the Metropolitan division");
        printMessage(message);
    }

    public String getOfficialSiteUrlOfOldestTeam() {
        return getOldestTeam().getOfficialSiteUrl();
    }

    public void verifyNationalityOfOldestTeam(String urlOldestTeam) {
        OldestTeamVerification oldestTeamVerification = new OldestTeamVerification();
        oldestTeamVerification.verifyNationality(urlOldestTeam);
    }

    private Team getOldestTeam() {
        Team oldestTeam = null;

        for (Team team : teamsList) {
            if (oldestTeam == null) oldestTeam = team;

            if (team.getFirstYearOfPlay() < oldestTeam.getFirstYearOfPlay()) {
                oldestTeam = team;
            }
        }

        return oldestTeam;
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
