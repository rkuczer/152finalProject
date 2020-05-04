package bsu.comp152;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ryanController implements Initializable {
    @FXML
    private ListView<ryanDataHandler.category> ListControl;
    private ryanDataHandler Model;
    private String jokeInput;

    public void loadData(){
        var site = "http://api.chucknorris.io/";
        var params = getQueryParameters();
        var query = site+params;

        Model = new ryanDataHandler(query);
        var jokeList = Model.getData();
        ObservableList<ryanDataHandler.category> dataToShow = FXCollections.observableArrayList(jokeList);
        ListControl.setItems(dataToShow);
    }

    public String getQueryParameters(){
        var category = getCategory();
        return "/jokes/"+category;
    }
    private String getJokeInput(){
        return jokeInput;
    }


    private String getCategory(){
        TextInputDialog answer = new TextInputDialog("joke category");
        answer.setHeaderText("Joke Categories: animal, career, celebrity, dev, fashion, food, history, money, movie, music.");
        answer.setContentText("Type your joke category.");
        Optional<String> result = answer.showAndWait();
        if (result.isPresent())
            return result.get();
        else
            return "";
    }

    //initialize method runs when fxml loads
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadData();
        jokeInput = "";
        ListControl.getSelectionModel().selectedItemProperty().addListener( //gets selected item from the list
                new ChangeListener<ryanDataHandler.category>() {
                    @Override
                    public void changed(ObservableValue<? extends ryanDataHandler.category> observable, ryanDataHandler.category oldValue, ryanDataHandler.category newValue) {
                        var jokeCategory = ListControl.getSelectionModel().getSelectedItem(); //creates a new alert dialog
                        Alert jokeInfo = new Alert(Alert.AlertType.INFORMATION);        //sets dialog to info type
                        jokeInfo.setTitle("Joke and Category");                         //sets title for window
                        jokeInfo.setContentText("Joke: "+jokeCategory.value);           //presents the joke category
                        jokeInfo.showAndWait();                                         //lets the user exit when they choose.
                    }
                }
        );
    }
    @FXML
    public void selectMenuItem(javafx.event.ActionEvent actionEvent) {
        var item =(MenuItem)actionEvent.getSource();
        jokeInput = item.getText();
    }





}
