import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ApiReader {

    private static final String URL_BASE = "https://statsapi.web.nhl.com";
    private static final String URL_TEAMS = "/api/v1/teams";

    public ApiReader() {
    }

    public ArrayList<Team> getTeams() {
        try {
            String str = readFromApi(URL_TEAMS);
            JSONObject json = new JSONObject(str);
            JSONArray arrayTeams = (JSONArray) json.get("teams");
            ArrayList<Team> teamsList = new ArrayList<>();

            for (int i = 0; i < arrayTeams.length(); i++) {
                JSONObject teamJson = (JSONObject) arrayTeams.get(i);
                teamsList.add(new Team(teamJson));
            }

            return teamsList;

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromApi(String urlLastPart) throws MalformedURLException {
        String urlString = URL_BASE + urlLastPart;
        URL url = new URL(urlString);

        try {
            InputStream input = url.openStream();
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;

            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }

            return json.toString();
        }
        catch (IOException e) {
            System.out.println("Something went wrong -> readFromApi(" + urlString + ")");
            throw new RuntimeException(e);
        }
    }
}
