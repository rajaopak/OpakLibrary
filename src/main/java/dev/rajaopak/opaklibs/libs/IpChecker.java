package dev.rajaopak.opaklibs.libs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IpChecker {

    public static String getIpLocation(String ip) {
        String url = "http://ip-api.com/json/" + ip;
        JSONObject json = readJsonFromUrl(url);

        StringBuilder builder = new StringBuilder();

        if (json == null) {
            return "unknown";
        }

        if (!json.getString("status").equalsIgnoreCase("fail")) {
            if (json.getString("continent") != null &&
                    json.getString("country") != null &&
                    json.getString("city") != null) {
                builder.append(json.get("continent")).append(", ")
                        .append(json.get("country")).append(", ")
                        .append(json.get("city"));
            } else if (json.getString("continent") != null){
                builder.append(json.get("continent"));
            } else if (json.getString("country") != null) {
                builder.append(json.get("country"));
            } else if (json.getString("city") != null) {
                builder.append(json.get("city"));
            } else {
                return "unknown";
            }
        } else {
            return "unknown";
        }

        return builder.toString();
    }

    private static JSONObject readJsonFromUrl(String link) throws JSONException {
        try (InputStream input = new URL(link).openStream()) {
            BufferedReader re = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            String Text = Read(re);
            JSONObject json = new JSONObject(Text);
            if (json.get("status") == "fail") {
                return null;
            }
            return json;
        } catch (Exception e) {
            return null;
        }
    }
    private static String Read(Reader re) throws IOException {
        StringBuilder str = new StringBuilder();
        int temp;
        do {
            temp = re.read();
            str.append((char) temp);
        } while (temp != -1);

        return str.toString();
    }

}
