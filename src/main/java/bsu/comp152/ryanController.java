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

public class ryanController implements Initializable {
    @FXML
    private ListView<ryanDataHandler.jokeDataType> ListControl;
    private ryanDataHandler Model;
    private String category;

    public void loadData(){
        var site = "https://api.chucknorris.io/jokes/categories/";
        var params = getQueryParameters();
        var query = site+params;

        Model = new ryanDataHandler(query);
        var jokeList = Model.getData();
        ObservableList<ryanDataHandler.jokeDataType> dataToShow = FXCollections.observableArrayList(jokeList);
        ListControl.setItems(dataToShow);
    }

    public String getQueryParameters(){
        var category = getCategory();
        var jokeCategory = getJokeCategory();
        return "i="+jokeCategory+"&q="+category;
    }

    private String getJokeCategory(){
        TextInputDialog answer = new TextInputDialog("joke category");
        answer.setHeaderText("Gathering Information");
        answer.setContentText("What joke category do you want.");
        Optional<String> result = answer.showAndWait();
        if (result.isPresent())
            return result.get();
        else
            return "";
    }

    private String getCategory(){
        return category;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();

    }


}
