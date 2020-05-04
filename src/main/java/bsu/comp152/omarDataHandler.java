package bsu.comp152;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class omarDataHandler {

    private HttpClient dataGrabber;// HTTPClient will be created as well as the String to get the data and store it.
    private String webLocation;

    public omarDataHandler(String webLocation) { //constructor for data handler to create a new object once called
        dataGrabber = HttpClient.newHttpClient();
        this.webLocation = webLocation;
    }

    public ArrayList<omarDataHandler.category> getData() { //This method creates a data request from the API

        var requestBuilder = HttpRequest.newBuilder();
        var dataRequest = requestBuilder.uri(URI.create(webLocation)).build();
        HttpResponse<String> response = null;

        try { // start a try/catch that attempts to get data with data grabber and send a request and use the body as a string
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("Failed to connect to Network!!");
        } catch (InterruptedException e) { //catches and handles an interrupted or corrupt network
            System.out.println("Connection Failed!!");
        }
        if (response == null) { //catches an error when the connection fails
            System.out.println("Oops, an issue happened, exiting program");
            System.exit(-1);
        }

        var usefulData = response.body(); //assigns data to a Gson interpreter to convert the data from json into parsed readable info.
        var jsonInterpreter = new Gson(); //Gson will be created
        var intPhoneNum = jsonInterpreter.fromJson(usefulData, omarDataHandler.responseDataType.class); //creates data from a class responseDataType so the info can be parsed into an object.
        System.out.println(intPhoneNum.categories);
        return intPhoneNum.categories;

    }

    class responseDataType { //response data type for json information from International Phone Numbers API.
        ArrayList<omarDataHandler.category> categories;
        String icon_url;
        String id;
        String url;
        String value;
    }

    class category{ //stores data for the joke named as value in json
        String value;
        @Override
        public String toString() {
            return "Joke: " + value;
        }
    }
}

