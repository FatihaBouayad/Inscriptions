/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import dgsescuela.Formations.*;
import dgsescuela.Etudiants.*;
import dgsescuela.DBConnection;
//import dgsescuela.Etudiants.FXMLEtudiantsController;
import static dgsescuela.Etudiants.FXMLEtudiantsController.StageEtudiant;
import static dgsescuela.Inscriptions.FXMLAjouterController.EtudStage;
//import static dgsescuela.Formations.FXMLAjouterController.EnseignantStage;
import static dgsescuela.LoginPackage.loginController.adminStage;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEnseignants;
import dgsescuela.Modele.ModeleEnseignantsStatic;
import dgsescuela.Modele.ModeleEtudiants;
import dgsescuela.Modele.ModeleEtudiantsStatic;
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
public class FXMLEtudiantsController implements Initializable {

    /**
     * Initializes the controller class.
     */
       @FXML
    private ObservableList<ModeleEtudiants> ListeEtudiants;
    
   @FXML
    private TableView<ModeleEtudiants> fxTableEtudiants;
     @FXML
    private TableColumn<ModeleEtudiants, String> fxIdEtud;
      @FXML
    private TableColumn<ModeleEtudiants, String> fxDateColumn;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxNomEtud;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxPrenomEtud;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxTele;

    @FXML
    private TableColumn<ModeleEtudiants, String> fxEmail;
   
  
    
    @FXML
        private TextField fxRechercher;
     
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    // public static Stage StageEtud= new Stage();
    
    
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initialisationData();
    }    
    
    public void initialisationData(){
         
           try {
               //initialisation de la liste d'objets etudiants
               ListeEtudiants = FXCollections.observableArrayList();
               
               //Connection
               conn= DBConnection.EtablirConnection();
               
               // initialisation de la table fx des etudiants
               initTable();
               
               // récupérer les données depuis la bdd
               uploadTableEtud();
               
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
     }
     private void initTable() {

        fxIdEtud.setCellValueFactory(new PropertyValueFactory<>("idEtudiant"));
        fxDateColumn.setCellValueFactory(new PropertyValueFactory<>("DateAjout"));
        fxNomEtud.setCellValueFactory(new PropertyValueFactory<>("NomEtudiant"));
        fxPrenomEtud.setCellValueFactory(new PropertyValueFactory<>("PrenomEtudiant"));
        fxTele.setCellValueFactory(new PropertyValueFactory<>("TelEtudiant"));
        fxEmail.setCellValueFactory(new PropertyValueFactory<>("EmailEtudiant"));
        
    
    }
       
       public void uploadTableEtud(){
           
           try {
               ListeEtudiants.clear();
               

               String sql = "select IdEtud,Date_Ajout,NomEtud,PrenomEtud,Tel,Email from `etudiant`" ;
               
               fxTableEtudiants.getItems().clear();
               pst = conn.prepareStatement(sql);
               rs = pst.executeQuery();
               
               while(rs.next()){
                   ModeleEtudiants newEtud = new ModeleEtudiants();
                   newEtud.setIdEtudiant(rs.getString(1));
                   newEtud.setDateAjout(rs.getString(2));
                   newEtud.setNomEtudiant(rs.getString(3));
                   newEtud.setPrenomEtudiant(rs.getString(4));
                   newEtud.setTelEtudiant(rs.getString(5));
                   newEtud.setEmailEtudiant(rs.getString(6));
                  
                   ListeEtudiants.add(newEtud);
                   //System.out.println("********************* date: "+newEtudiant.getDateAjout());
               }
               fxTableEtudiants.setItems(ListeEtudiants);
               pst.close();
               rs.close();
           } catch (SQLException ex) {
               Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
           ModeleEtudiants CurrentObjet= new ModeleEtudiants();
    ModeleEtudiantsStatic CurrentObjetStatic = new ModeleEtudiantsStatic();
  
    
   @FXML
   public void selectCondidat(){
          CurrentObjet=(ModeleEtudiants)fxTableEtudiants.getSelectionModel().getSelectedItem();

          
          if(CurrentObjet==null || CurrentObjet.getIdEtudiant().equals(""))
          {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner un etudiant d'abord!!!");
                alert.showAndWait();
          }else{
           CurrentObjetStatic.setIdEtudiant(CurrentObjet.getIdEtudiant());
           CurrentObjetStatic.setDate(CurrentObjet.getDateAjout());
           CurrentObjetStatic.setNomEtudiant(CurrentObjet.getNomEtudiant());
            CurrentObjetStatic.setPrenomEtudiant(CurrentObjet.getPrenomEtudiant());
           CurrentObjetStatic.setTelEtudiant(CurrentObjet.getTelEtudiant());
           CurrentObjetStatic.setEmailEtudiant(CurrentObjet.getEmailEtudiant());
          
          }
          EtudStage.close();

   
    }

    

/*------------------------------------------------Fenetre----------------------------------------*/


     @FXML
     public void Recherche() throws SQLException{

        
        if(fxRechercher==null || fxRechercher.getText().equals(""))
        {
            System.out.println("hhhhh");
            uploadTableEtud();
            
        }else{
        ListeEtudiants.clear();
            RechercheDonnee();
    
  
        }
    }


 @FXML
     public void RechercheDonnee() throws SQLException{       
          String sql="select IdEtud,Date_Ajout,NomEtud,PrenomEtud,Tel,Email from etudiant where IdEns ='"+fxRechercher.getText().toLowerCase()+"' OR NomEns= '"+fxRechercher.getText().toLowerCase()+"' OR PrenomEns= '"+fxRechercher.getText().toLowerCase()+"' OR Tel= '"+fxRechercher.getText().toLowerCase()+"' OR Date_Ajout = '"+fxRechercher.getText()+"' OR Email = '"+fxRechercher.getText()+"' OR adresse = '"+fxRechercher.getText()+"'";
            
        try {
            
       fxTableEtudiants.getItems().clear();
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            ModeleEtudiants Etud = new ModeleEtudiants();
            
            
            Etud.setIdEtudiant(rs.getString(1));
            Etud.setDateAjout(rs.getString(2));
            Etud.setNomEtudiant(rs.getString(3));
            Etud.setPrenomEtudiant(rs.getString(4));
            Etud.setTelEtudiant(rs.getString(5));
            Etud.setEmailEtudiant(rs.getString(6));
           
            
            
            
            ListeEtudiants.add(Etud);
            fxTableEtudiants.setItems(ListeEtudiants);
        }
            System.out.println("l'etudiant est la ");
            pst.close();
           rs.close();
          
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEtudiantsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}