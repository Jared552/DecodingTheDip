package Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataScraper {

    public double getOpeningPrice(String code){
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String html = "";

        try {
            url = new URL("https://finance.yahoo.com/quote/" + code + "/history/");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                html += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                System.out.println("Error");
            }
        }

        String now = html.split("Ta\\(start\\)")[2];
        String openPrice = now.split("Py\\(10px\\) Pstart\\(10px\\)")[1];
        return Double.parseDouble(openPrice.substring(openPrice.indexOf("span")+5, openPrice.indexOf("/span")-1));
    }

    public double getCurrentPrice(String code) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String html = "";

        try {
            url = new URL("https://finance.yahoo.com/quote/" + code + "/");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                html += line;
            }
        } catch (MalformedURLException mue) {
            //mue.printStackTrace();
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            return -1.0;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                System.out.println("Error");
            }
        }
        html = html.substring(html.indexOf("regularMarketPrice"));
        html = html.substring(html.indexOf(">")+1, html.indexOf("<"));

        double result = -1.0;

        try {
            result = Double.parseDouble(html);
        } catch(NumberFormatException e){}

        return result;
    }

    public static List<double[]> getHistoricalData(String code) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String html = "";

        try {
            String urlString = "https://query2.finance.yahoo.com/v8/finance/chart/" + code;
            urlString += "?formatted=true&crumb=6iPfwrHM.4i&lang=en-IN&region=IN&period1=1337662800&period2=1684731600&interval=1d&events=div|split&corsDomain=in.finance.yahoo.com";
            url = new URL(urlString);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                html += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                System.out.println("Error");
            }
        }
        String lows = html.substring(html.indexOf("low\":[") + 6);
        lows = lows.substring(0, lows.indexOf("]"));
        String[] lowsList = lows.split(",");

        String closes = html.substring(html.indexOf("close\":[") + 8);
        closes = closes.substring(0, closes.indexOf("]"));
        String[] closesList = closes.split(",");

        String highs = html.substring(html.indexOf("high\":[") + 7);
        highs = highs.substring(0, highs.indexOf("]"));
        String[] highsList = highs.split(",");

        String opens = html.substring(html.indexOf("open\":[") + 7);
        opens = opens.substring(0, opens.indexOf("]"));
        String[] opensList = opens.split(",");

        List<double[]> toReturn = new ArrayList<>();

        for(int i = 0 ; i < opensList.length ; i++){
            toReturn.add(0, new double[]{Double.parseDouble(opensList[i]), Double.parseDouble(highsList[i]),
                    Double.parseDouble(lowsList[i]), Double.parseDouble(closesList[i])});
        }
        return toReturn;
    }
}
