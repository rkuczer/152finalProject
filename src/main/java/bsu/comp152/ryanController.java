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

public class ryanController implements Initializable {
    @FXML
    private ListView<ryanDataHandler.category> ListControl;
    private ryanDataHandler Model;
    private String category;

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

    private String getCategory2(){
        return category;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loadData();
        ListControl.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ryanDataHandler.category>() {
                    @Override
                    public void changed(ObservableValue<? extends ryanDataHandler.category> observable, ryanDataHandler.category oldValue, ryanDataHandler.category newValue) {
                        var joke = ListControl.getSelectionModel().getSelectedItem();
                        Alert recipeInfo = new Alert(Alert.AlertType.INFORMATION);
                        recipeInfo.setTitle("Joke and Category");
                        recipeInfo.setHeaderText("Ingredients: "+ joke.ingredients);
                        recipeInfo.setContentText("Here: "+joke.icon);
                        recipeInfo.showAndWait();
                    }
                }
        );
    }

    @FXML
    public void selectMenuItem(javafx.event.ActionEvent actionEvent) {
        var item =(MenuItem)actionEvent.getSource();
        category = item.getText();
    }


}
