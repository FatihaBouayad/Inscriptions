/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dgsescuela.DBConnection;
//import static dgsescuela.Inscriptions.FXMLFormationsController.StageFor;
/*import static dgsescuela.Inscriptions.FXMLInscriptionsController.EtudStage;
import static dgsescuela.Inscriptions.FXMLInscriptionsController.FormStage;*/
import static dgsescuela.LoginPackage.loginController.newStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEtudiantsStatic;
import dgsescuela.Modele.ModeleFormationsStatic;
import dgsescuela.Modele.ModeleInscriptions;
import dgsescuela.Modele.ModeleInscriptionsStatic;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Fatiha Bouayed
 */
public class FXMLAjouterController implements Initializable  {
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ModeleInscriptionsStatic inscr= new ModeleInscriptionsStatic();
   
    @FXML
    private DatePicker DateI;
  @FXML
    private TextField   idetud;
  @FXML
    private TextField   idform;
  @FXML
    private TextField   Nometud;
  @FXML
    private TextField   Titreform;
  
      @FXML
    private Label isEmpty;
       public static Stage EtudStage= new Stage();
    public static Stage FormStage= new Stage();
      
       ModeleEtudiantsStatic CurrentObjetStatic;
       ModeleFormationsStatic CurrentObjetStatic2;
     
      /**
     * Initializes the controller class.
     */
      
      @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        try {
            // TODO
             
            conn= DBConnection.EtablirConnection();
            DateI.setValue(LocalDate.now());
            System.out.println("999999999999999999999999999999999");
         CurrentObjetStatic =new ModeleEtudiantsStatic();
         CurrentObjetStatic2 =new ModeleFormationsStatic();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dgsescuela.Inscriptions.FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("00000000000000000000000");
        }
        

    } 
  
@FXML
    private void AjouterInscription() {
       
        try {
            if (isValidCondition()) {
               if(isnewData()){
                    
                    String sql = "insert into inscription(IDformation,TitreF,IdEtud,NomE,DateInscription) values(?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    

                   
                    pst.setString(1, idform.getText());
                    pst.setString(2, Titreform.getText());
                    pst.setString(3, idetud.getText());
                    pst.setString(4, Nometud.getText());
                    pst.setString(5, DateI.getValue().toString());
                 
                    pst.executeUpdate();
                    pst.close();
                    
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucess");
                    alert.setHeaderText("Sucess :   ");
                    alert.setContentText("l'inscription : a été ajouté avec succès");
                    alert.showAndWait();
                    
                    //newStage.close();
                    FenetreInscription();
                    
                
            }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur :   ");
                    alert.setContentText("cette données existe deja !!!");
                    alert.showAndWait();
                }
            }
           else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Vérifiez les données d'Article!!!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        catch (ParseException ex) {
            Logger.getLogger(dgsescuela.Inscriptions.FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(dgsescuela.Inscriptions.FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void Clavier(KeyEvent event) throws ParseException, SQLException, IOException {
         if (event.getCode() == KeyCode.ENTER)
         {
             AjouterInscription();

         }
    }
    
        //////////////////////  validé les condition du remplir les champ  //////////////

    private boolean isValidCondition() throws SQLException {
        isEmpty.setText("");
        boolean registration = false;
        if (isEmpty()) {
            System.out.println("Condition valid");
            registration = true;
            isEmpty.setText("done !!!");
            isEmpty.setStyle("-fx-text-fill:green;");

        } else {
            isEmpty.setText("SVP entrer tous les champs !!!");
            isEmpty.setStyle("-fx-text-fill:red;");

            System.out.println("Condition Invalid");
            registration = false;
        }

        return registration;

    }
    
///////////////    pour verifier les champ vide /////////////////
    private boolean isEmpty() {
        boolean isEmpty = false;
        if (idform.getText().isEmpty()
                || idetud.getText().isEmpty()
                || DateI.getValue().toString().trim().isEmpty()
               )
                  {

            //System.out.println("il y a un ou plusieur champs vide");
            isEmpty = false;
        } else {
                    
            System.out.println("il y a un ou plusieur champs vide");

            isEmpty = true;
        }
        return isEmpty;
    }

    /////////////////////   verifier si cette condidat est nouveau ou deja inscrit //////////
    private boolean isnewData() throws SQLException {

        String sql = "select * from `inscription` where IDInscription = '" + inscr.getIdInscription()+ "'";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            pst.close();
            rs.close();
            System.out.println("isn't new data");
            return false;

        }
        pst.close();
        rs.close();
        System.out.println("is new data");
        return true;
    }
    public void SelectImage (){
        
    }  
    @FXML
   public void FenetreEtudiant() throws IOException {

      
        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLEtudiants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            EtudStage.setScene(sceneAccueil);
            EtudStage.showAndWait();
          
            idetud.setText(CurrentObjetStatic.getIdEtudiant());
            Nometud.setText(CurrentObjetStatic.getNomEtudiant());

    }
   @FXML
   public void FenetreFormation() throws IOException {

      
        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLFormations.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            FormStage.setScene(sceneAccueil);
            FormStage.showAndWait();
          
            idform.setText(CurrentObjetStatic2.getIdFormation());
            Titreform.setText(CurrentObjetStatic2.getTitreFormation());

    }
   public void FenetreInscription() throws ParseException, IOException {

            rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLInscriptions.fxml"));
            sceneAccueil = new Scene(rootAccueil);
         


    }

    




}
