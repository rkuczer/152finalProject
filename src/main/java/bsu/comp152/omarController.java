package bsu.comp152;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class omarController implements Initializable {
    @FXML
    private ListView<omarDataHandler.ISOCode> ListControl;
    private omarDataHandler Model;
    private String ISOCode;

    public void loadData(){
        var site1 = "http://country.io/phone.json";
        var site2 = "http://country.io/names.json";
        var params = getQueryParameters();
        var query = site+params;

        Model = new omarDataHandler(query);
        var CountryCode = Model.getData();
        ObservableList<omarDataHandler.ISOCode> dataToShow = FXCollections.observableArrayList(CountryCode);
        ListControl.setItems(dataToShow);
    }

    private String getQueryParameters() {
        var isoCodes = getISOCode();
        return "/jokes/"+isoCodes;
    }
    private String getJokeInput(){
        return ISOCode;


    }


    private String getISOCode(){

        TextInputDialog answer = new TextInputDialog("International Phone Numbers");
        answer.setHeaderText("Choose country ISO code.");
        answer.setContentText("Choose country.");
        Optional<String> result = answer.showAndWait();
        if (result.isPresent())
            return result.get();
        else
            return "";
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
