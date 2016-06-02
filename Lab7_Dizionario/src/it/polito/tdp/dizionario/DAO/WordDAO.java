package it.polito.tdp.dizionario.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//import it.polito.tdp.dizionario.model.Parola;

public class WordDAO {

	
	
public List<String> getAllSimilarWords(String parola,int lunghezza){
		
		Connection conn= DBConnect.getConnection();
		String sql= "SELECT nome FROM parola WHERE LENGTH(nome)=?;";
		
		try {
			
			List <String> listaTutteLeParole = new LinkedList<String>();
			List <String> listaParoleSimili = new LinkedList<String>();

			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, lunghezza);
			ResultSet res= st.executeQuery();
			
			while (res.next()){
				String p= res.getString("nome");
				listaTutteLeParole.add(p);
			}
			
			for(String sTemp:listaTutteLeParole){
				int delta=0;
				for(int i=0;i<parola.length();i++){
					if (sTemp.charAt(i)==parola.charAt(i))
						delta++;
					
					if (delta==lunghezza-1)
						listaParoleSimili.add(sTemp);
				}
			}
			return listaParoleSimili;
			
			
		} catch (SQLException e) {
			throw new RuntimeException("Errore DB");

		}
		
	}
	


public List<String> getAllWords(int lunghezza){
	
	List <String> listaTutteLeParole = new LinkedList<String>();
	Connection conn= DBConnect.getConnection();
	String sql= "SELECT nome FROM parola WHERE LENGTH(nome)=?;";
	
	try {
		
		//List <String> listaTutteLeParole = new LinkedList<String>();
		//List <String> listaParoleSimili = new LinkedList<String>();

		PreparedStatement st= conn.prepareStatement(sql);
		st.setInt(1, lunghezza);
		ResultSet res= st.executeQuery();
		
		while (res.next()){
			String p= res.getString("nome");
			listaTutteLeParole.add(p);
		}
			return listaTutteLeParole;
		
		
	} catch (SQLException e) {
		throw new RuntimeException("Errore DB");

	}
	
}








}
