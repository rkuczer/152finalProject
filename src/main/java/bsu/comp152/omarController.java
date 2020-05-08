package bsu.comp152;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
        var query = site1+params;

        Model = new omarDataHandler(query);
        var CountryCode = Model.getData();
        ObservableList<omarDataHandler.ISOCode> dataToShow = FXCollections.observableArrayList(CountryCode);
        ListControl.setItems(dataToShow);
    }

    private String getQueryParameters() {
        var isoCodes = getISOCode();
        return "/country/"+isoCodes;
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

        ISOCode = "";
        ListControl.getSelectionModel().selectedItemProperty().addListener( //gets selected item from the list
                new ChangeListener<omarDataHandler.ISOCode>() {
                    @Override
                    public void changed(ObservableValue<? extends omarDataHandler.ISOCode> observable, omarDataHandler.ISOCode oldValue, omarDataHandler.ISOCode newValue) {
                        var phoneCode = ListControl.getSelectionModel().getSelectedItem(); //creates a new alert dialog
                        Alert phoneCodeInfo = new Alert(Alert.AlertType.INFORMATION);        //sets dialog to info type
                        phoneCodeInfo.setTitle("International Phone Numbers");                         //sets title for window
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
