/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dgsescuela.DBConnection;
import dgsescuela.Formations.*;
//import static dgsescuela.Formations.FXMLFormationsController.StageEtudiant;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleFormations;
import dgsescuela.Modele.ModeleInscriptions;
import dgsescuela.Modele.ModeleInscriptionsStatic;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLInscriptionsController implements Initializable {
 @FXML
    private ObservableList<ModeleInscriptions> ListeInscription;
     @FXML
    private TableView<ModeleInscriptions> fxTableInscription;
    @FXML
    private TableColumn<ModeleInscriptions, String>  fxIdInscription;
    @FXML
    private TableColumn<ModeleInscriptions, String>  fxIdFormation;
     @FXML
    private TableColumn<ModeleInscriptions, String>  fxIdEtudiant;
      @FXML
    private TableColumn<ModeleInscriptions, String>  fxDateI;
     @FXML
    private Label isEmpty;
    private Label fxRechercher;

    /**
     * Initializes the controller class.
     */
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public static Stage StageIns= new Stage();
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     initialisationData();
    } 
    public void initialisationData(){
     try {
               //initialisation de la liste d'objets etudiants
               ListeInscription = FXCollections.observableArrayList();
               
               //Connection
               conn= DBConnection.EtablirConnection();
               
               // initialisation de la table fx des etudiants
               initTable();
               
               // récupérer les données depuis la bdd
               uploadTableInscription();
               
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FXMLInscriptionsController.class.getName()).log(Level.SEVERE, null, ex);
           }
    }    
        private void initTable() {

        fxIdInscription.setCellValueFactory(new PropertyValueFactory<>("idInscription"));
         fxIdFormation.setCellValueFactory(new PropertyValueFactory<>("idFormat"));
        fxIdEtudiant.setCellValueFactory(new PropertyValueFactory<>("idEtud"));
        fxDateI.setCellValueFactory(new PropertyValueFactory<>("DateIns"));
        }
     
             public void uploadTableInscription(){
           
           try {
               ListeInscription.clear();
               
              /// String sql = " " ;
               
               fxTableInscription.getItems().clear();
               pst = conn.prepareStatement("SELECT IDInscription,IDformation,IdEtud,DateInscription FROM `inscription`");
               rs = pst.executeQuery();
               
               while(rs.next()){
                   ModeleInscriptions newInscription = new ModeleInscriptions();
                   newInscription.setIdInscription(rs.getString(1));
                   newInscription.setIdFormat(rs.getString(2));
                   newInscription.setIdEtud(rs.getString(3));
                   newInscription.setDateIns(rs.getString(4));
                   ListeInscription.add(newInscription);
                   System.out.println("*******************Inscription");
               }
               fxTableInscription.setItems(ListeInscription);
               pst.close();
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(FXMLInscriptionsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           }
              ModeleInscriptions CurrentObjet= new ModeleInscriptions();
    ModeleInscriptionsStatic CurrentObjetStatic = new ModeleInscriptionsStatic();
  
    private void selectCondidat(){
          CurrentObjet=(ModeleInscriptions)fxTableInscription.getSelectionModel().getSelectedItem();

          
          if(CurrentObjet==null || CurrentObjet.getIdInscription().equals(""))
          {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner une ligne d'abord!!!");
                alert.showAndWait();
          }else{
           CurrentObjetStatic.setIdInscription(CurrentObjet.getIdInscription());
               CurrentObjetStatic.setIdFormat(CurrentObjet.getIdFormat());
           CurrentObjetStatic.setIdEtud(CurrentObjet.getIdEtud());
            CurrentObjetStatic.setDateIns(CurrentObjet.getDateIns());
           
          }

   
    }
    
    

/*------------------------------------------------Fenetre----------------------------------------*/

   public void FenetreEnseignant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLEnseignants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   
   public void FenetreEtudiant() throws IOException {

        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Etudiants/FXMLEtudiants.fxml"));
        sceneAccueil = new Scene(rootAccueil);

            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }

   
   public void FenetreFormation() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Formations/FXMLFormations.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);


    }
   
   
   public void FenetreFrais() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Frais/FXMLFais.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);
    }

   public void FenetreInscription() throws IOException  {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLInscriptions.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }
   
   
   public void FenetreStatistiques() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Statistiques/FXMLStatistiques.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);



    }

   
   
   public void FenetreEmploisDuTemps() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Emplois/FXMLEmplois.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
            adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);

    }

   public void FenetreAccueil() throws IOException {


        rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/AccueilPackage/FXMLAccueil.fxml"));
        sceneAccueil = new Scene(rootAccueil);

      
           adminStage.setScene(sceneAccueil);
            adminStage.show();
            adminStage.setMaximized(false);
            adminStage.setMaximized(true);
    }
   
   /*------------------------------------------------Fenetre----------------------------------------*/

   

    
    @FXML
    public void AjouterInscription()throws IOException  {
        
       
            Parent newRoot;
            Scene newScene;
            newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLAjouter.fxml"));
            newScene = new Scene(newRoot);
            
            StageIns= new Stage();;
            StageIns.setTitle("Ajouter Inscription");
            StageIns.setScene(newScene);
            StageIns.showAndWait();
      
    }
    
    @FXML
    public void SupprimerInscription()throws IOException  {
        
     selectCondidat();
            Parent newRoot;
            Scene newScene;
            newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLConfermationSuppression.fxml"));
            newScene = new Scene(newRoot);
            
            StageIns= new Stage();;
            StageIns.setTitle("Supprimer Inscription");
            StageIns.setScene(newScene);
            StageIns.showAndWait();
        
    }
        @FXML
    public void ModifierInscription()throws IOException  {
        
     selectCondidat();
            Parent newRoot;
            Scene newScene;
            newRoot = FXMLLoader.load(getClass().getResource("/dgsescuela/Inscriptions/FXMLModifier.fxml"));
            newScene = new Scene(newRoot);
            
            StageIns= new Stage();;
            StageIns.setTitle("Modifier Inscription");
            StageIns.setScene(newScene);
            StageIns.showAndWait();
        
    }


 
    
     public void Recherche() throws SQLException{

        
        if(fxRechercher==null || fxRechercher.getText().equals(""))
        {
            System.out.println("hhhhh");
            uploadTableInscription();
            
        }else{
        ListeInscription.clear();
            RechercheDonnee();
    
  
        }
    }


     public void RechercheDonnee() throws SQLException{       
          String sql="select IDInscription,IDformation,IdEtud,DateInscription from inscription where IDInscription ='"+fxRechercher.getText().toLowerCase()+"' OR DateInscription= '"+fxRechercher.getText().toLowerCase()+"'";           
        try {
            
       fxTableInscription.getItems().clear();
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            ModeleInscriptions ins = new ModeleInscriptions();
            
            
            ins.setIdInscription(rs.getString(1));
            ins.setIdFormat(rs.getString(2));
            ins.setIdEtud(rs.getString(3));
            ins.setDateIns(rs.getString(4));
            
            ListeInscription.add(ins);
            fxTableInscription.setItems(ListeInscription);
        }
            System.out.println("Cette inscription est faite ");
            pst.close();
           rs.close();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLInscriptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
