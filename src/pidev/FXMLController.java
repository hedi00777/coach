/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author OMEN
 */
public class FXMLController implements Initializable {

    @FXML
    private TableView<Coachs> tvcoachs;
    @FXML
    private TableColumn<Coachs, Integer> colid;
    @FXML
    private TableColumn<Coachs, String> colnom;
    @FXML
    private TableColumn<Coachs, String> colprenom;
    @FXML
    private TableColumn<Coachs, Integer> colprix;
    @FXML
    private TableColumn<Coachs, Integer> coltel;
    @FXML
    private TableColumn<Coachs, String> colmail;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_del;
    @FXML
    private Button btn_mod;
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfprix;
    @FXML
    private TextField tftel;
    @FXML
    private TextField tfmail;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showCoachs();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btn_add) {
            addInfo();
        }else if (event.getSource() == btn_mod){
            modifyInfo ();
        }else if (event.getSource() == btn_del){
            delmode() ;
        }
    }
    
    private void delmode(){
        String query = "DELETE FROM coachs WHERE id =" + tfid.getText() + "";
        executeQuery(query);
        showCoachs();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("JDBC:mysql://localhost:3306/sporify", "root", "");
            return conn;
        } catch (Exception ex) {
            System.out.println("error" + ex.getMessage());
            return null;
        }

    }

    public ObservableList<Coachs> getCoachList() {
        ObservableList<Coachs> coachList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM Coachs";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Coachs coachs;
            while (rs.next()) {
                coachs = new Coachs(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("prix"), rs.getInt("tel"), rs.getString("mail"));
                coachList.add(coachs);
            }
        } catch (Exception ex) {
        }
        return coachList;
    }

    public void showCoachs() {
        ObservableList<Coachs> list = getCoachList();
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("mail"));

        tvcoachs.setItems(list);
    }
   
    private void addInfo() {
        String query = "INSERT INTO coachs VALUES (" + tfid.getText() + ",'" + tfnom.getText()
                + "','" + tfprenom.getText() + "'," + tfprix.getText() + "," + tftel.getText()
                + "," + tftel.getText() + ",'" + tfmail.getText() + "')";
        executeQuery(query);
        showCoachs();
           
    }
  
    private void modifyInfo (){
     String query = "UPDATE coachs SET nom = '" + tfnom.getText() + "', preom = '" + tfprenom.getText() + "' prix = '" + tfprix.getText() + ", tel = " +
             tftel.getText() +"', mail = " + tfmail.getText() + "WHERE id = " + tfid.getText() + "";
        executeQuery(query);
        showCoachs();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;

        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
        }
    }
}
