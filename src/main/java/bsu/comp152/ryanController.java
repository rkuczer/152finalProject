package bsu.comp152;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ryanController implements Initializable {
    @FXML
    private ListView<DataHandler.recipeDataType> ListControl;
    private DataHandler Model;

    public void loadData(){
        var site = "https://api.chucknorris.io/";
        var params = getQueryParameters();
        var query = site+params;
        Model = new DataHandler(query);
        var jokeList = Model.getData();
        ObservableList<DataHandler.recipeDataType> dataToShow =
                FXCollections.observableArrayList(jokeList);
        ListControl.setItems(dataToShow);
    }

}
