/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import com.jfoenix.controls.JFXComboBox;
import dgsescuela.DBConnection;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static dgsescuela.Inscriptions.FXMLInscriptionsController.StageIns;

/**
 * FXML Controller class
 *
 * @author Fatiha Bouayed
 */
public class FXMLModifierController implements Initializable {
Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    @FXML
    private Label isEmpty;
    @FXML
    private JFXComboBox<String> cmb1;
    @FXML
    private JFXComboBox<String> cmb2;
    @FXML
    private DatePicker DateI;
     Methodes mtd= new Methodes();
      Methodes mtd2= new Methodes();

    /**
     * Initializes the controller class.
     */
    ModeleInscriptionsStatic mIns = new ModeleInscriptionsStatic();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            // TODO
            conn= DBConnection.EtablirConnection();
            DateI.setValue(LocalDate.now());
            mtd.MethodeIdFormation(cmb1);
            mtd2.MethodeIdEtudiant(cmb2);
            Init();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dgsescuela.Etudiants.FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 @FXML
    void Clavier(KeyEvent event) throws ParseException, SQLException, IOException {
         if (event.getCode() == KeyCode.ENTER)
         {
             ModifierInscription();

         }
    }
    
    @FXML
    public void SelectImage( )
    {
        
    } 
      private void Init(){
        
        //pour récupérer les informations qui existe déja dans la bdd
        
        if(mIns==null || mIns.getIdInscription().equals("")){
            
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner une inscription d'abord!!!");
                alert.showAndWait();
                
        }else{
        cmb1.setValue(mIns.getIdFormat());
      //  fxDate.setText(CurrentObjetStatic.getDate());
         cmb2.setValue(mIns.getIdEtud());
      //  DateI.setValue(mIns.getDateIns());
        }
      }

    @FXML
    private void ModifierInscription() {
        String v=null;
         try {
            if (isValidCondition()) {
             
                  String sql = " update inscription set IDformation=?, IdEtud=?, DateInscription=? where IDInscription='"+mIns.getIdInscription()+"'";
                   //String sql = "";
                   pst = conn.prepareStatement(sql);

                    //pst.setString(1, mIns.getIdInscription().toString());
                    pst.setString(1, cmb1.getValue().toString());
                    pst.setString(2, cmb2.getValue().toString());
                    pst.setString(3, DateI.getValue().toString());
                     
                   
                    
     

                    pst.executeUpdate();
                    pst.close();
                    
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucess");
                    alert.setHeaderText("Sucess :   ");
                    alert.setContentText("l'inscription a été Bien Modifier");
                    alert.showAndWait();
                    
                    StageIns.close();
                    FenetreInscription();
                   
                
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

        } catch (ParseException ex) {
            Logger.getLogger(FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
        if (cmb1.getValue().trim().isEmpty()
                || cmb2.getValue().trim().isEmpty()
               
          
                ) {

            //System.out.println("il y a un ou plusieur champs vide");
            isEmpty = false;
        } else {
                    
            System.out.println("il y a un ou plusieur champs vide");

            isEmpty = true;
        }
        return isEmpty;
    }

    /////////////////////   verifier si cette condidat est nouveau ou deja inscrit //////////
   
    
          public void FenetreInscription() throws ParseException, IOException {

            rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLInscriptions.fxml"));
            sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);
         


    }

    
    
}
