package nl.rudie.rest_client;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //async task uitvoeren met juiste url
        new RestClient().execute("http://192.168.0.111:8080/rest/service/items");
    }

    public class RestClient extends AsyncTask<String, Void, List<HashMap<String, String>>>{

        @Override
        protected List<HashMap<String, String>> doInBackground(String... uri) {
            try {
                //client aanmaken
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(new HttpGet(uri[0]));

                //response code controleren
                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){

                    //response body (json) omzetten naar list
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(response.getEntity().getContent(), List.class);
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> items){
            System.out.println(items.toString());
        }
    }
}
