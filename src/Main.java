import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;


class Main {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException, JSONException {
        HashMap<String, Integer> universities = new HashMap<>();
        HashMap<String, Integer> forms = new HashMap<>();
        String api = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=iritrtf_urfu&fields=education&access_token=" +
                "f5493d1ef5493d1ef5493d1e03f53ddc23ff549f5493d1eaae58a467485251565e27d2a&v=5.126";
        URL url = new URL(api);
        URLConnection connection = url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = readAll(buffer);
        buffer.close();

        JSONObject members = new JSONObject(line);
        JSONArray arrayOfMembers = members.getJSONObject("response").getJSONArray("items");

        for (int i = 0; i < arrayOfMembers.length(); i++) {
            JSONObject info = arrayOfMembers.getJSONObject(i);
            try {
                String educationForm = info.getString("education_form");
                forms.put(educationForm, forms.containsKey(educationForm) ? forms.get(educationForm) + 1 : 1);
                String universityName = info.getString("university_name");
                universities.put(universityName, universities.containsKey(universityName) ? universities.get(universityName) + 1 : 1);
            } catch (Exception ignored) {}
        }

        for (Map.Entry<String, Integer> item1 : universities.entrySet()) {
            for (Map.Entry<String, Integer> item2 : forms.entrySet())
            System.out.println(item1.getKey() + ": " + item1.getValue() +
                    "; " + item2.getKey() + ": " + item2.getValue());
        }
    }
}