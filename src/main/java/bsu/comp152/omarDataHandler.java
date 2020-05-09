package bsu.comp152;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

public class omarDataHandler {

    private HttpClient dataGrabber;// HTTPClient will be created as well as the String to get the data and store it.
    private String webLocation;
    @FXML
    private ListView DataList;

    public omarDataHandler(String webLocation) { //constructor for data handler to create a new object once called
        dataGrabber = HttpClient.newHttpClient();
        this.webLocation = webLocation;
    }

    public ListView<responseDataType> getData() { //This method creates a data request from the API

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
        var usefulData = response.body();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        var gson = new Gson();
        Map<String, String> myMap = gson.fromJson(usefulData, type);
        var dataList = new ArrayList<String>(myMap.keySet());
        ObservableList<String> countryName = FXCollections.observableArrayList(dataList);
        DataList.setItems(countryName);

        var jsonInterpreter = new Gson(); //Gson will be created
        var intPhoneNum = jsonInterpreter.fromJson(usefulData, omarDataHandler.responseDataType.class); //creates data from a class responseDataType so the info can be parsed into an object.
        System.out.println(intPhoneNum.responseDataTypes);
        return intPhoneNum.responseDataTypes;

    }

    class responseDataType { //response data type for json information from International Phone Numbers API.

        ListView<responseDataType> responseDataTypes;
//        String icon_url;
        String title;
        String id;
        String url;
        String value;
    }

    class ISOCode{ //stores data for the country name as value in json
        String value;
        @Override
        public String toString() {
            return "Country: " + value;
        }
    }
}

