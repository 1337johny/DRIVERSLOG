package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by JOHNY on 30.04.2016.
 */
public class DBC {

    /*
     * "conn" Your main Connection
     * "query" Your main query Variable
     */
    Connection conn = null;
    Statement query;

    /**
     * Connects...
     * */
    public DBC() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://server.fritz.box/data?" + "user=johny&password=18021984");
            query = conn.createStatement();
            System.out.println("DBC success...");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    /**
     * @param table     The Table name you want to get.
     * @return          a list of all your items (DriveSet)
     * */
    public ObservableList<DriveSet> getData(String table) {

        ObservableList<DriveSet> list = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM "+table;
            ResultSet result = query.executeQuery(sql);

            while(result.next()) {
                int id = result.getInt("id");
                String date = result.getString("date");
                int number = result.getInt("persons");
                list.add(new DriveSet(id,date,number));
            }

            return list;
        } catch (Exception ex) {
            System.out.println("Query failed. Check SQL code in 'getTable'\n"+ex);
            return null;
        }
    }

    /**
     * @param mydate    The Date you want to insert
     * @param mynumber  The Number of persons you want to insert
     * @return          returns true, if inserting was successfull
     *                  otherwise false will be returned
     * */
    public boolean insertData(String mydate, int mynumber) {

        mydate = mydate.replaceAll("-","");

        String sql = "INSERT INTO drives(date,persons) VALUES ("+mydate+","+mynumber+")";
        try {
            query.executeUpdate(sql);
            return true;
        }catch (Exception ex) {
            System.out.println("Insert Query failed. Check SQL code in 'getTable'\n"+ex);
            System.out.println("date :"+mydate);
            System.out.println("number: "+mynumber);
            return false;
        }
    }

    /**
     * @param id   The Date you want to insert
     * @return          returns true, if deleting was successful
     *                  otherwise false will be returned
     * */
    public boolean deleteData(int id){
        String sql = "DELETE FROM drives WHERE id="+id;
        try {
            query.executeUpdate(sql);
            return true;
        }catch (Exception ex) {
            System.out.println("Delete Query failed. Check SQL code in 'getTable'\n"+ex);
            return false;
        }
    }

}
