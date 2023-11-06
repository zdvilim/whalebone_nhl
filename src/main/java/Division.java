import org.json.JSONException;
import org.json.JSONObject;

public class Division {

    private final String name;

    public Division(JSONObject teamJson) throws JSONException {
        this.name = teamJson.getString("name");
    }

    public String getName() {
        return name;
    }
}
