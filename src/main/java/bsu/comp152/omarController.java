package bsu.comp152;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class omarController implements Initializable {
    @FXML
    private omarDataHandler Model;
    @FXML
    private TextArea DataDisplay;
    @FXML
    private ListView DataList;
    private ListView<omarDataHandler.responseDataType> responseDataTypeListView;
    private String ISOCode;
    private String query;
    private String params;

    public void loadData() { // method that gathers the data to display
        var site1 = "http://country.io/phone.json ";
        var site2 = "http://country.io/names.json";
//        var params = getQueryParameters();

        var query = site1 + params;
//        var query2 = site2 + params;

       Model = new omarDataHandler(query);
       Model.getData();
//       ObservableList<omarDataHandler.responseDataType> dataToShow = FXCollections.observableArrayList();
//        DataList.setItems(dataToShow);

        var requestBuilder = HttpRequest.newBuilder();
        var dataGrabber = HttpClient.newHttpClient();
        var dataRequest = requestBuilder.uri(URI.create(site1)).build();
        HttpResponse<String> response = null;

        try {
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("Error connecting to network or site");
        } catch (InterruptedException e) {
            System.out.println("Connection to site broken");
        }
        if (response == null) {
            System.out.println("Something went terribly wrong, ending program");
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

    }



//    private String getQueryParameters() {
//        var isoCodes = getISOCode();
//        return "/country/"+isoCodes;
//    }
//
//
//    private String getISOCode(){
//
//        TextInputDialog answer = new TextInputDialog("International Phone Numbers");
//        answer.setHeaderText("Choose country ISO code.");
//        answer.setContentText("Choose country.");
//        answer.setWidth(400);
//        answer.setResizable(true);
//        Optional<String> result = answer.showAndWait();
//        if (result.isPresent())
//            return result.get();
//        else
//            return "";
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // a method that loads the data and displays it
        loadData();
        ISOCode = "";
        responseDataTypeListView.getSelectionModel().selectedItemProperty().addListener( // gets selected item from the list of countries
                new ChangeListener<omarDataHandler.responseDataType>(){
                    @Override
                    public void changed(ObservableValue<? extends omarDataHandler.responseDataType> observableValue, omarDataHandler.responseDataType oldValue, omarDataHandler.responseDataType  newValue) {
                        var phoneCode = responseDataTypeListView.getSelectionModel().getSelectedItem(); //creates a new alert dialog
                        Alert phoneCodeInfo = new Alert(Alert.AlertType.INFORMATION);        //sets dialog to info type
                        phoneCodeInfo.setTitle("Info for"+phoneCode.title);                         //sets title for window
                        phoneCodeInfo.setContentText("Phone code:"+phoneCode.value);           //
                        phoneCodeInfo.showAndWait();                                         //lets the user exit when they choose.

                    }
                }
        );

    }





    @FXML
    public void selectMenuItem(javafx.event.ActionEvent actionEvent) {
        var item =(MenuItem)actionEvent.getSource();
        ISOCode = item.getText();
    }
}
