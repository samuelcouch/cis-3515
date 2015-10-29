package edu.temple.lab_7;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by samcouch on 10/28/15.
 */
public class WebDataHandler extends AsyncTask<String, Void, String>{
    private WebView browser;
    String inputLine;

    public WebDataHandler(WebView browser){
        this.browser = browser;
    }

    @Override
    protected String doInBackground(String... params){
        try {
            String input = params[0];
            input = cleanUrl(input);

            URL url = new URL(input);
            InputStreamReader isr = new InputStreamReader(url.openStream());

            BufferedReader reader = new BufferedReader(isr);

            StringBuilder response = new StringBuilder();

            while((inputLine = reader.readLine()) != null){
                response.append(inputLine);
            }

            reader.close();

            return response.toString();
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if(this.browser != null && result != null){
            this.browser.loadData(result, "text/html", "UTF-8");
        }
    }

    public String cleanUrl(String url){
        url = url.toLowerCase();

        if(url.startsWith("http://") || url.startsWith("https://")){
            return url;
        }

        return "http://" + url;
    }
}
