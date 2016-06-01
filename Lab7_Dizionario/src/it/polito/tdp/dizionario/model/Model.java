package it.polito.tdp.dizionario.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionario.DAO.WordDAO;

public class Model {

	WordDAO w= new WordDAO();
	UndirectedGraph<String, DefaultEdge> graph;
	List <String> nodi = new LinkedList<String>();
	List <String> nodi2 = new LinkedList<String>();
	List<String> paroleSimili = new LinkedList<String>();


	// devo aggiungere tutti i nodi (che sono tutte le parole 
	//									di una certa lunghezza) al grafo
	
	
	
	public List<String> createGraph (int lun){
		nodi.clear();
		paroleSimili.clear();
		nodi = w.getAllWords(lun);
		graph = new SimpleGraph<>(DefaultEdge.class);
		for(String s : nodi){
			graph.addVertex(s);
		}
		 // per ogni nodo devo anche creare l'arco:
		for(String s : nodi){
			paroleSimili = w.getAllSimilarWords(s, lun);
			for(String parolaSimile :paroleSimili){
				if (!s.equalsIgnoreCase(parolaSimile))
				graph.addEdge(s, parolaSimile);
			}
		}
		return nodi;
	}
	
	public List<String> getNodi() {
		return nodi;
	}

	public List<String> displayVicini(String stringa){
		List<String> vicini = new LinkedList<String>();
		vicini.addAll(Graphs.neighborListOf(graph, stringa));
		return vicini;
	}
	
	public List<String> raggiungibiliDataPartenza(String stringa){
		//USO L'ITERATORE PER L'ESPLORAZIONE PROFONDA [profondita' iteratore]
		List<String> raggiungibili = new LinkedList<String>();
		GraphIterator<String,DefaultEdge> visit = new DepthFirstIterator<String,DefaultEdge>(graph,stringa);
		while (visit.hasNext()){
			String c = visit.next();  // visit.next fornisce l'elemento corrente e si posiziona sul successivo
			raggiungibili.add(c);
		}
		return raggiungibili;
	}
}
