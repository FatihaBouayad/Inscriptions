/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Inscriptions;

import com.jfoenix.controls.JFXComboBox;
import dgsescuela.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
//import javafx.collections.ObservableListBase;
import javafx.collections.FXCollections;
/**
 *
 * @author Fatiha Bouayed
 */
public class Methodes {
    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stm=null;
    ObservableList<String> items;
    List<String> ListeIdF= new ArrayList<>();
    ObservableList<String> items2;
    List<String> ListeIdEt= new ArrayList<>();
    
   public void MethodeIdFormation(JFXComboBox cmbF1) throws ClassNotFoundException {
  
       try{
               conn= DBConnection.EtablirConnection();
               stm=conn.createStatement();
               rs= stm.executeQuery("SELECT IDFormation FROM formation");
               while(rs.next()){
                   String idf = rs.getString("IDFormation");
                   ListeIdF.add(idf);
               }
               
               items = FXCollections.observableArrayList(ListeIdF);
               cmbF1.setItems(items);
       } 
       catch(SQLException s){
           System.out.println("errrrreuuuurrrr");
       }    
       
   } 
   public void MethodeIdEtudiant(JFXComboBox cmbF1) throws ClassNotFoundException {
  
       try{
               conn= DBConnection.EtablirConnection();
               stm=conn.createStatement();
               rs= stm.executeQuery("SELECT idEtud FROM etudiant");
               while(rs.next()){
                   String idE = rs.getString("idEtud");
                   ListeIdEt.add(idE);
               }
               
               items2 = FXCollections.observableArrayList(ListeIdEt);
               cmbF1.setItems(items2);
       } 
       catch(SQLException s){
           System.out.println("errrrreuuuurrrr");
       }    
       
   } 
}
