package bsu.comp152;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class omarController implements Initializable {
    @FXML
    private ListView<omarDataHandler.category> ListControl;
    private omarDataHandler Model;
    private String ISOCode;

    public void loadData(){
        var site1 = "http://country.io/phone.json";
        var site2 = "http://country.io/names.json";
        var params = getQueryParameters();
        var query = site+params;

        Model = new omarDataHandler(query);
        var country = Model.getData();
        ObservableList<omarDataHandler.category> dataToShow = FXCollections.observableArrayList(country);
        ListControl.setItems(dataToShow);
    }

    private String getQueryParameters() {


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
