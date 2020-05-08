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

public class alexController implements Initializable {
    @FXML
    private ListView<alexDataHandler.category> ListControl;
    private alexDataHandler Model;
    private String category;

    public void loadData(){
        var site1 = " https://searchly.asuarez.dev/docs/v1#tag/song";
        var site2 = "https://searchly.asuarez.dev/api/v1/similarity/by_song"; //2nd step
        var params = getQueryParameters();
        var query = site1 +params;

        Model = new alexDataHandler(query);
        var songList = Model.getData();
        ObservableList<alexDataHandler.category> dataToShow = FXCollections.observableArrayList(songList);
        ListControl.setItems(dataToShow);
    }

    public String getQueryParameters(){
        var category = getCategory();
        return "/songs/"+category;
    }

    private String getCategory(){
        TextInputDialog answer = new TextInputDialog("song finder");
        answer.setContentText("Type the name of your artist.");
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
                new ChangeListener<alexDataHandler.category>() {
                    @Override
                    public void changed(ObservableValue<? extends alexDataHandler.category> observable,
                                        alexDataHandler.category oldValue, alexDataHandler.category newValue) {
                        var song = ListControl.getSelectionModel().getSelectedItem();
                        Alert artistInfo = new Alert(Alert.AlertType.INFORMATION);
                        artistInfo.setTitle("Song Finder");
                        artistInfo.setContentText("song: "+song.value);
                        artistInfo.showAndWait();
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

