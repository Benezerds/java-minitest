
/*
 * This task is to display weather forecasting in Jakarta for 5 days forward
 */

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SecondTask {

    public static void main(String[] args) {
        String LAT = "-6.21462";
        String LON = "106.845131";
        String EXCLUDE = "current,minutely,hourly,alerts";
        String API_KEY = "10d287a09267adfdc0c8d6abbddb3072";

        // Create a SimpleDateFormat for the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");

        // API
        String baseApiUrl = String.format(
                "https://api.openweathermap.org/data/3.0/onecall?lat=%s&lon=%s&exclude=%s&units=metric&appid=%s",
                LAT, LON, EXCLUDE, API_KEY);

        System.out.println(baseApiUrl);

        try {
            URL url = new URL(baseApiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            // Debugging Purpose
            System.out.println(responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                // Close scanner
                scanner.close();

                System.out.println("Starting to parse...");
                // Getting Json response
                JSONParser parse = new JSONParser();
                JSONObject data_object = (JSONObject) parse.parse(inline.toString());

                // Accessing the "list" array which contains the weather forecast
                JSONArray list = (JSONArray) data_object.get("daily");

                /*
                 * Loop from the second index (The next day) to a total of 5 days from the
                 * current date.
                 */
                for (int i = 1; i <= 5; i++) {
                    JSONObject forecast = (JSONObject) list.get(i);

                    // Access date and time of the forecase
                    Long timestamp = (Long) forecast.get("dt");
                    Date datetime = new Date(timestamp * 1000);

                    String formattedDate = dateFormat.format(datetime);

                    // Getting Min and Max Temp
                    JSONObject tempData = (JSONObject) forecast.get("temp");
                    String tempMin = String.valueOf((Double) tempData.get("min"));
                    String tempMax = String.valueOf((Double) tempData.get("max"));

                    // Getting expected weather condition
                    JSONArray weatherData = (JSONArray) forecast.get("weather");
                    JSONObject weather = (JSONObject) weatherData.get(0);
                    String weatherDescription = (String) weather.get("description");

                    // Print the output
                    System.out.println(formattedDate + ":\n" +
                            "temp-min: " + tempMin + "°C" + "\n" +
                            "temp-max: " + tempMax + "°C" + "\n" +
                            "description: " + weatherDescription + "\n--------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
