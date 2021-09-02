/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import dgsescuela.DBConnection;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import static dgsescuela.Inscriptions.FXMLAjouterController.FormStage;
import dgsescuela.Modele.ModeleFormations;
import dgsescuela.Modele.ModeleFormationsStatic;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Fatiha Bouayed
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import dgsescuela.DBConnection;

import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;

import dgsescuela.Modele.ModeleFormations;
import dgsescuela.Modele.ModeleFormationsStatic;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLFormationsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ObservableList<ModeleFormations> ListeFormation;
    
   @FXML
    private TableView<ModeleFormations> fxTableFormation;
     @FXML
    private TableColumn<ModeleFormations, String> fxIdFormation;
      @FXML
    private TableColumn<ModeleFormations, String> fxDateColumn; 

    @FXML
    private TableColumn<ModeleFormations, String> fxTitreFormation;

    @FXML
    private TableColumn<ModeleFormations, String> fxPrixFormation;

    @FXML
    private TableColumn<ModeleFormations, String> fxSession;

    @FXML
    private TableColumn<ModeleFormations, String> fxDateD;
    @FXML
    private TableColumn<ModeleFormations, String> fxDateF;
    @FXML
    private TableColumn<ModeleFormations, String> fxIdEns;
    @FXML
    private TableColumn<ModeleFormations, String> fxNiveau;
    @FXML
    private TableColumn<ModeleFormations, String> fxDesc;
/*@FXML
    private JFXButton fxRechercherButton;*/
    @FXML
    private TextField fxRechercher;
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    //public static Stage StageFor= new Stage();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        initialisationData();
    } 
    public void initialisationData(){
     try {
               //initialisation de la liste d'objets etudiants
               ListeFormation = FXCollections.observableArrayList();
               
               //Connection
               conn= DBConnection.EtablirConnection();
               
               // initialisation de la table fx des etudiants
               initTable();
               
               // récupérer les données depuis la bdd
               uploadTableFormation();
               
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FXMLFormationsController.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    private void initTable() {

        fxIdFormation.setCellValueFactory(new PropertyValueFactory<>("idFormation"));
       
         fxDateColumn.setCellValueFactory(new PropertyValueFactory<>("DateAjout"));
        fxTitreFormation.setCellValueFactory(new PropertyValueFactory<>("TitreFormation"));
        fxPrixFormation.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        fxSession.setCellValueFactory(new PropertyValueFactory<>("Session"));
        fxDateD.setCellValueFactory(new PropertyValueFactory<>("DateD"));
       fxDateF.setCellValueFactory(new PropertyValueFactory<>("DateF"));
        fxIdEns.setCellValueFactory(new PropertyValueFactory<>("IdEnseignant"));
        fxNiveau.setCellValueFactory(new PropertyValueFactory<>("Niveau"));
        fxDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
    }
         public void uploadTableFormation(){
           
           try {
               ListeFormation.clear();
               //f,enseignant e WHERE f.idEnseignant=e.idEnseignant
               String sql = "select IDFormation,Date_Ajout,TitreFormation,Prix,Session,DateDebut,DateFin,idEnseignant,Niveau,Description from `formation` " ;
               
               fxTableFormation.getItems().clear();
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               while(rs.next()){
                   ModeleFormations newFormation = new ModeleFormations();
                   newFormation.setIdFormation(rs.getString(1));
                   newFormation.setDateAjout(rs.getString(2));
                   newFormation.setTitreFormation(rs.getString(3));
                   newFormation.setPrix(rs.getString(4));
                   newFormation.setSession(rs.getString(5));
                   newFormation.setDateD(rs.getString(6));
                   newFormation.setDateF(rs.getString(7));
                   newFormation.setIdEnseignant(rs.getString(8));
                   newFormation.setNiveau(rs.getString(9));
                   newFormation.setDescription(rs.getString(10));
                   ListeFormation.add(newFormation);
                  // System.out.println("********************* date: "+newFormation.getDateAjout());
               }
               fxTableFormation.setItems(ListeFormation);
               pst.close();
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(FXMLFormationsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
          ModeleFormations CurrentObjet= new ModeleFormations();
    ModeleFormationsStatic CurrentObjetStatic = new ModeleFormationsStatic();
 
    @FXML
    private void selectCondidat(){
          CurrentObjet=(ModeleFormations)fxTableFormation.getSelectionModel().getSelectedItem();

          
          if(CurrentObjet==null || CurrentObjet.getIdFormation().equals(""))
          {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner une ligne d'abord!!!");
                alert.showAndWait();
          }else{
           CurrentObjetStatic.setIdFormation(CurrentObjet.getIdFormation());
               CurrentObjetStatic.setDateAjout(CurrentObjet.getDateAjout());
           CurrentObjetStatic.setTitreFormation(CurrentObjet.getTitreFormation());
            CurrentObjetStatic.setPrix(CurrentObjet.getPrix());
           CurrentObjetStatic.setSession(CurrentObjet.getSession());
           CurrentObjetStatic.setDateD(CurrentObjet.getDateD());
           CurrentObjetStatic.setDateF(CurrentObjet.getDateF());
           CurrentObjetStatic.setIdEnseignant(CurrentObjet.getIdEnseignant());
           CurrentObjetStatic.setNiveau(CurrentObjet.getNiveau());
           CurrentObjetStatic.setDescription(CurrentObjet.getDescription());
          }

   FormStage.close();
    }
    
    


/*------------------------------------------------Fenetre----------------------------------------*/

     @FXML
     public void Recherche() throws SQLException{

        
        if(fxRechercher==null || fxRechercher.getText().equals(""))
        {
            System.out.println("hhhhh");
            uploadTableFormation();
            
        }else{
        ListeFormation.clear();
            RechercheDonnee();
    
  
        }
    }


 @FXML
     public void RechercheDonnee() throws SQLException{       
          String sql="select IDFormation,Date_Ajout,TitreFormation,Prix,Session,DateDebut,DateFin,idEnseignant from formation where IDFormation ='"+fxRechercher.getText().toLowerCase()+"' OR TitreFormation= '"+fxRechercher.getText().toLowerCase()+"' OR Session= '"+fxRechercher.getText().toLowerCase()+"' OR Prix= '"+fxRechercher.getText().toLowerCase()+"' OR Date_Ajout = '"+fxRechercher.getText()+"' OR DateDebut = '"+fxRechercher.getText()+"'OR DateFin='"+fxRechercher.getText()+"'OR idEnseignant='"+fxRechercher.getText()+"'";
            
        try {
            
       fxTableFormation.getItems().clear();
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            ModeleFormations Formation = new ModeleFormations();
            
            
            Formation.setIdFormation(rs.getString(1));
            Formation.setDateAjout(rs.getString(2));
            Formation.setTitreFormation(rs.getString(3));
            Formation.setPrix(rs.getString(4));
            Formation.setSession(rs.getString(5));
            Formation.setDateD(rs.getString(6));
            Formation.setDateF(rs.getString(7));
            Formation.setIdEnseignant(rs.getString(8));
            Formation.setNiveau(rs.getString(9));
            Formation.setDescription(rs.getString(10));
            
            ListeFormation.add(Formation);
            fxTableFormation.setItems(ListeFormation);
        }
            System.out.println("la formation est la ");
            pst.close();
           rs.close();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLFormationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

}
