package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;

//import java.sql.Date;

public class Controller implements Initializable {

    private static final Logger log = Logger.getLogger(Controller.class.getName() );
    private static ObservableList<DriveSet> set;
    private static DBC db;

    @FXML
    Parent root;

    @FXML
    Button disableBtn;

    @FXML
    DatePicker datePicker;

    @FXML
    TextField number;

    @FXML
    TableView<DriveSet> tableView;

    @FXML
    TableColumn<DriveSet,Integer> colorColumn;

    @FXML
    TableColumn<DriveSet,Date> dateColumn;

    @FXML
    TableColumn<DriveSet,Integer> personColumn;

    //region Information Labels
    @FXML
    Label info1;
    @FXML
    Label info2;
    @FXML
    Label info3;
    @FXML
    Label info4;
    @FXML
    Label info5;
    //endregion

    @FXML
    public void addButtonClick() {
        log.fine("Add Button Clicked...");
        db.insertData(datePicker.getValue().toString(),Integer.parseInt(number.getText()));
        updateTableData();
    }
    @FXML
    public void deleteButtonClick() {
        log.fine("Delete Button Clicked...");
        DriveSet tempSet = tableView.getSelectionModel().getSelectedItem();

        if (db.deleteData(tempSet.getId())) {
            set.remove(tempSet);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableBtn.setDisable(false);
        db = new DBC();
        setupTable();
        updateTableData();
        //System.out.println(set.toString());
    }

    private void setupTable() {
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        personColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        colorColumn.setVisible(false);
    }

    public void colorIt() {
        int i = 0;
        for (Node n: tableView.lookupAll("TableRow")) {
            if (n instanceof TableRow) {
                TableRow row = (TableRow) n;
                if (tableView.getItems().get(i).getNumber()>2) {
                    row.getStyleClass().add("good");
                } else {
                    row.getStyleClass().add("bad");
                }
                i++;
                if (i == tableView.getItems().size())
                    break;
            }
        }
    }

    private void updateTableData() {
        setData();
        tableView.setItems(set);
    }
    private void setData() {
        set = db.getData("drives");
    }
}

