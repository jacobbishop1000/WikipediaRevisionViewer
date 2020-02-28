package utils;

import exceptions.NetworkConnectionFailedException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;

public class RequestUtils {

    public static String establishConnection(String articleName) throws IOException, NetworkConnectionFailedException {
        URL url = new URL("https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles="
                + articleName
                +"&rvprop=timestamp|user&rvlimit=30&redirects");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            connection.getInputStream();
        } catch(IOException e){
            throw new NetworkConnectionFailedException();
        }
        InputStream in = connection.getInputStream();
        Scanner scanner = new Scanner(in);
        String result = scanner.nextLine();
        return result;
    }

}
