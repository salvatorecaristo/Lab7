package it.polito.tdp.dizionario.controller;

import java.net.URL;
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
	private TextArea txtResult;

	@FXML
	private TextField inputNumeroLettere;

	@FXML
	private TextField inputParola;

	@FXML
	private Button btnGeneraGrafo;

	@FXML
	private Button btnTrovaVicini;

	@FXML
	private Button btnTrovaTutti;

	@FXML
	void doReset(ActionEvent event) {
		txtResult.clear();
		inputNumeroLettere.clear();
		inputParola.clear();
		inputNumeroLettere.setEditable(true);
		btnGeneraGrafo.setDisable(false);
		btnTrovaVicini.setDisable(true);
		btnTrovaTutti.setDisable(true);
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		// Reset
		txtResult.clear();
		inputParola.clear();
		btnTrovaVicini.setDisable(false);
		btnTrovaTutti.setDisable(false);

		try {
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			System.out.println("numero di Lettere: " + numeroLettere);

			inputNumeroLettere.setEditable(false);

			List<String> parole = model.createGraph(numeroLettere);

			if (parole != null) {
				txtResult.setText("Trovate " + parole.size() + " parole di lunghezza " + numeroLettere);

			} else {
				txtResult.setText("Trovate 0 parole di lunghezza: " + numeroLettere);
			}

			btnGeneraGrafo.setDisable(true);

		} catch (NumberFormatException nfe) {
			txtResult.setText("Inserire un numero corretto di lettere!");

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}

	}

	@FXML
	void doTrovaTutti(ActionEvent event) {
		try {

			String parolaInserita = inputParola.getText();
			if (parolaInserita == null || parolaInserita.length() == 0) {
				txtResult.setText("Inserire una parola da cercare");
				return;
			}

			// Sono tutte alternative. Basta un solo metodo.
			// I risultati dovrebbero essere identici (anche se in ordine diverso).
			List<String> parole = model.displayAllNeighbours(parolaInserita);
			List<String> paroleOne = model.displayAllNeighboursOne(parolaInserita);
			List<String> paroleTwo = model.displayAllNeighboursTwo(parolaInserita);
			if (parole != null) {
				txtResult.setText(parole.toString());
				txtResult.appendText("\n" + parole.size());
				txtResult.appendText("\n" + paroleOne.toString());
				txtResult.appendText("\n" + paroleOne.size());
				txtResult.appendText("\n" + paroleTwo.toString());
				txtResult.appendText("\n" + paroleTwo.size());

			} else {
				txtResult.setText("Non è stato trovato nessun risultato");
			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {
		try {

			String parolaInserita = inputParola.getText();
			if (parolaInserita == null || parolaInserita.length() == 0) {
				txtResult.setText("Inserire una parola da cercare");
				return;
			}

			List<String> parole = model.displayNeighbours(parolaInserita);
			if (parole != null) {
				txtResult.setText(parole.toString());

			} else {
				txtResult.setText("Non è stato trovato nessun risultato");
			}

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaTutti != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";

		btnTrovaVicini.setDisable(true);
		btnTrovaTutti.setDisable(true);
	}
}
