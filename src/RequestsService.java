import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RequestsService {
    public void postColor(ColorEntity color) throws Exception  {

        String url = "http://localhost:8080/colors";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");

        String postJsonData = convertColorToJson(color);


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());
    }

    public void postColors(ColorEntity[] colors) throws Exception {

        String url = "http://localhost:8080/colors";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");
    Gson gson = new Gson() ;
        String postJsonData = gson.toJson(colors) ;


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //printing result from response
        System.out.println(response.toString());
    }

//     HTTP GET request
            public ColorEntity[] getColors() throws Exception {

                String urlString = "http://localhost:8080/colors";

                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // By default it is GET request
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                System.out.println("Sending get request : "+ url);
                System.out.println("Response code : "+ responseCode);

                // Reading response from input Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String output;
                StringBuffer response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
                Gson gson = new Gson() ;
                ColorEntity[] colors = gson.fromJson(response.toString(), ColorEntity[].class) ;
                return colors ;

            }


    // HTTP Post request

    public String convertColorToJson(ColorEntity color){
        String postJsonData = "{\"red\":\""+color.getRed()+"\",\"green\":\""+color.getGreen()+"\",\"blue\":\""+color.getBlue()+"\",\"rgbVal\":\""+color.getRgbVal()+"\"}";
        return postJsonData ;
    }
}
