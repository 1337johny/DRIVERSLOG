package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    ///////////////////////////////
    //Totals
    @FXML
    Label daysDriven;
    @FXML
    Label kmDriven;
    @FXML
    Label costTotal;
    ///////////////////////////////
    //a day
    @FXML
    Label costAday;
    @FXML
    Label kmAday;
    //endregion

    @FXML
    public void addButtonClick() throws Exception {
        log.fine("Add Button Clicked...");
        if (datePicker.getValue() != null){
            String tmp = number.getText();
            if (!(number.getText().isEmpty())) {
                if (db.insertData(datePicker.getValue().toString(),Integer.parseInt(number.getText()))) {
                    setupTable();
                    updateTableData();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            colorIt();
                        }
                    });

                }
            } else {
                basicAlert("INPUT ERROR","Please enter a valid Value for the number of Persons");
                throw new Exception("No Number of Persons where input.");
            }
        } else {
            basicAlert("INPUT ERROR","Please enter a valid Date");
            throw new Exception("No Date was picked");
        }
    }

    @FXML
    public void deleteButtonClick() throws Exception{
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DELETE");
            alert.setHeaderText("ARE YOU SURE THAT YOU WANT TO DELETE THIS DATE?");
            alert.setContentText("If you click OK, you cant restore the data...");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    log.fine("Delete Button Clicked...");
                    DriveSet tempSet = tableView.getSelectionModel().getSelectedItem();
                    if (db.deleteData(tempSet.getId())) {
                        set.remove(tempSet);
                        tableView.refresh();
                        setupTable();
                        updateTableData();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                colorIt();
                            }
                        });

                    }
                }
            });
        } else {
            basicAlert("SELECTION ERROR", "Please select a row to delete!");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableBtn.setDisable(false);
        // SETUP DATABASE
        ////////////////////////////////////////////////////////////////////////////////////////////
        db = new DBC();

        // SETUP TABLE
        ////////////////////////////////////////////////////////////////////////////////////////////
        setupTable();
        updateTableData();

        // SETUP LABELS
        ////////////////////////////////////////////////////////////////////////////////////////////
        updateLabel();
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
                if (tableView.getItems().get(i).getNumber()>= 2) {
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

    private int getTotalDays () {
        return set.size();
    }

    private int getTotalKm() {
        return (set.size())*70;
    }

    private int getTotalCash() {
        double tmp = set.size()*6.125;
        return  (int)tmp;
    }

    private void updateLabel(){
        daysDriven.setText("Total Days:\n"+getTotalDays());
        kmDriven.setText("Total KM:\n"+getTotalKm());
        costTotal.setText("Total Costs:\n"+getTotalCash()+" €");
        kmAday.setText("KM a day:\n70");
        costAday.setText("Cost a day:\n6,125");
    }

    private void basicAlert(String head, String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("ERROR");
        a.setHeaderText(head);
        a.setContentText(text);
        a.show();
    }
}

