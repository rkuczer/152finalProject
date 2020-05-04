package bsu.comp152;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


public class alexDataHandler {
    private HttpClient dataGrabber;//
    private String webLocation;

    public alexDataHandler(String webLocation) {
        dataGrabber = HttpClient.newHttpClient();
        this.webLocation = webLocation;
    }

    public ArrayList<category> getData(){ //creates from API
        var requestBuilder = HttpRequest.newBuilder();
        var dataRequest = requestBuilder.uri(URI.create(webLocation)).build();
        HttpResponse<String> response = null;
        try {
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            System.out.println("We could not connect to the network or the site is broken.");
        }
        catch (InterruptedException e){ //catches an interrupted connection to network
            System.out.println("connection to the site is broken");
        }
        if (response == null){
            System.out.println("Something went very wrong, ending the program");
            System.exit(-1);
        }
        var usefulData = response.body(); //assigns data to the gson interpreter to convert the data from json into parsed readable info.
        var jsonInterpreter = new Gson(); //creats the gson object
        var songData = jsonInterpreter.fromJson(usefulData, responseDataType.class); //creates data from a class responseDataType so the info can be parsed into an object.
        System.out.println(songData.categories);
        return songData.categories;

    }

    class responseDataType { // json information song finder  api response
        ArrayList<category> categories;
        String icon_url;
        String id;
        String url;
        String value;
    }

    class category{ //stores data for the song
        String value;
        @Override
        public String toString() {
            return "Joke: " + value;
        }
    }
}
