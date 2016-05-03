package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	public static boolean debug = false;
	UndirectedGraph<String, DefaultEdge> graph;
	int numeroLettere = 0;

	public List<String> createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		WordDAO wordDAO = new WordDAO();
		this.numeroLettere = numeroLettere;

		 List<String> parole = wordDAO.getAllWordsFixedLength(numeroLettere);

		// Aggiungo tutti i vertici del grafo
		for (String parola : parole) {
			graph.addVertex(parola);
		}

		// Per ogni parola aggiungo un arco di collegamento con le parole
		// che differiscono per una sola lettera (stessa lunghezza)
		for (String parola : parole) {
			
			// Alternativa 1: uso il Database
			// List<String> paroleSimili = wordDAO.getAllSimilarWords(parola, numeroLettere);

			// Alternativa 2: uso il mio algoritmo in Java
			List<String> paroleSimili = Utils.getAllSimilarWords(parole, parola, numeroLettere);

			if (debug) {
				System.out.println("parola: " + parola);
				System.out.println(paroleSimili);
			}

			// Creo l'arco
			for (String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
		}

		// Ritorno la lista dei vertici
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> parole = new ArrayList<String>();
		
		// Ottengo la lista dei vicini di un vertice
		parole.addAll(Graphs.neighborListOf(graph, parolaInserita));

		if (debug) {
			System.out.println(graph.degreeOf(parolaInserita));
			System.out.println(parole);
		}

		// Ritorno la lista dei vicini
		return parole;
	}

	/*
	 * VERSIONE ITERATIVA
	 */
	public List<String> displayAllNeighbours(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		// Creo due liste: quella dei noti visitati ..
		List<String> visited = new LinkedList<String>();
		// .. e quella dei nodi da visitare
		List<String> toBeVisited = new LinkedList<String>();

		// Aggiungo alla lista dei vertici visitati il nodo di partenza.
		visited.add(parolaInserita);
		// Aggiungo ai vertici da visitare tutti i vertici collegati a quello inserito
		toBeVisited.addAll(Graphs.neighborListOf(graph, parolaInserita));
		
		while (!toBeVisited.isEmpty()) {
			
			// Rimuovi il vertice in testa alla coda
			String temp = toBeVisited.remove(0);
			
			// Aggiungi il nodo alla lista di quelli visitati
			visited.add(temp);

			if (debug) {
				System.out.println("Vertex " + temp);
				System.out.println("Degree: " + graph.degreeOf(temp));
				System.out.println("Degree: " + Graphs.neighborListOf(graph, temp));
			}

			// Ottieni tutti i vicini di un nodo
			List<String> listaDeiVicini = Graphs.neighborListOf(graph, temp);

			// Rimuovi da questa lista tutti quelli che hai già visitato..
			listaDeiVicini.removeAll(visited);
			// .. e quelli che sai già che devi visitare.
			listaDeiVicini.removeAll(toBeVisited);

			if (debug) {
				System.out.println("Added: " + listaDeiVicini);
			}

			// Aggiungi i rimanenenti alla coda di quelli che devi visitare.
			toBeVisited.addAll(listaDeiVicini);
		}

		// Ritorna la lista di tutti i nodi raggiungibili
		return visited;
	}
	
	/*
	 * VERSIONE LIBRERIA JGRAPHT
	 */
	public List<String> displayAllNeighboursOne(String parolaInserita) {
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		// Creo due liste: quella dei noti visitati ..
		List<String> visited = new LinkedList<String>();
		
		GraphIterator<String, DefaultEdge> dfv = new DepthFirstIterator<String, DefaultEdge>(graph, parolaInserita);
		
		while (dfv.hasNext()) {
			String t = dfv.next();
			visited.add(t);
			if (debug) {
				System.out.print(" " + t);
			}
		}
		if (debug) {
			System.out.println("");
		}
		
		return visited;
	}
	
	/*
	 * VERSIONE RICORSIVA
	 */
	public List<String> displayAllNeighboursTwo(String parolaInserita) {
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		// Creo due liste: quella dei noti visitati ..
		List<String> recVisited = new LinkedList<String>();
		
		recursiveVisit(parolaInserita, recVisited);
		
		return recVisited;
	}
	
	protected void recursiveVisit(String n, List<String> recVisited) {
		
		if (debug) {
			System.out.print(" " + n);
		}
		if (recVisited.contains(n))
			return;
		recVisited.add(n);
		for (String c : Graphs.neighborListOf(graph, n)) {
			recursiveVisit(c, recVisited);
		}
	}
		
}
