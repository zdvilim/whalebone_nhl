import org.json.JSONException;
import org.json.JSONObject;

public class Team {

    private final String teamName;
    private final String name;
    private final int firstYearOfPlay;
    private final String locationName;
    private final Division division;
    private final String officialSiteUrl;

    public Team(JSONObject teamJson) throws JSONException {
        this.teamName = teamJson.getString("teamName");
        this.name = teamJson.getString("name");
        this.firstYearOfPlay = Integer.parseInt(teamJson.getString("firstYearOfPlay"));
        this.locationName = teamJson.getString("locationName");
        this.division = new Division(teamJson.getJSONObject("division"));
        this.officialSiteUrl = teamJson.getString("officialSiteUrl");
    }

    public String getTeamName() {
        return teamName;
    }

    public String getName() {
        return name;
    }

    public int getFirstYearOfPlay() {
        return firstYearOfPlay;
    }

    public String getLocationName() {
        return locationName;
    }

    public Division getDivision() {
        return division;
    }

    public String getOfficialSiteUrl() {
        return officialSiteUrl;
    }
}
