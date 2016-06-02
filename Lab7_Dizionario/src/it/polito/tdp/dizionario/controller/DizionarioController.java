package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {
	
	Model model;
  
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnGenera;

    @FXML
    private Button btnVicini;

    @FXML
    private Button btnTutti;

    @FXML
    private TextArea txtGrafo;

    @FXML
    private Button btnReset;

	

    @FXML
    void doGenera(ActionEvent event) {
    	String S=txtLettere.getText();
    	int l = Integer.parseInt(S);
    	model.createGraph(l);
    	
    	
    	//System.out.println(model.getGraph().toString());
    	
    	txtGrafo.setText("grafo creato, ci sono "+model.getNodi().size()+" nodi");
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtGrafo.clear();
    	txtLettere.clear();
    	txtParola.clear();

    }

    @FXML
    void doTutti(ActionEvent event) {
    	String pippo = txtParola.getText();
    	String daStampare="";
    	List<String> raggiungibili = new LinkedList<String>();
    	raggiungibili = model.raggiungibiliDataPartenza(pippo);
    	
    	for(String s :raggiungibili){
    		daStampare+=s+"\n";
    	}
    	txtGrafo.setText("Questi sono tutti i nodi raggiungibili dalla partenza che hai inserito"+daStampare);
    	
    	
    	/*String stringhetta="";
    	List <String> stampaTutti = new LinkedList <String>();
    	stampaTutti =model.getNodi();
    	for(String s :stampaTutti){
    		stringhetta+=s+"\n";
    	}
    	txtGrafo.setText(stringhetta);
*/
    }

    @FXML
    void doVicini(ActionEvent event) {
    	txtGrafo.clear();
    	List <String > nodiVicini = new LinkedList<String>();
    	String parolaRichiesta= txtParola.getText();
    	
    	nodiVicini=model.displayVicini(parolaRichiesta);
    	String stampa ="";
    	for(String vicino :nodiVicini){
    		stampa+=vicino+"\n";
    	}
    	txtGrafo.setText("I nodi vicini sono i seguenti:\n"+stampa);
    	

    }

    @FXML
    void initialize() {
        assert txtLettere != null : "fx:id=\"txtLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnGenera != null : "fx:id=\"btnGenera\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnTutti != null : "fx:id=\"btnTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert txtGrafo != null : "fx:id=\"txtGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Dizionario.fxml'.";

    }

	public void setModel(Model model) {

		this.model=model;
		
	}
}
