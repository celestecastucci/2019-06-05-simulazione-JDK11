package it.polito.tdp.crimes.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private SimpleWeightedGraph<Integer, DefaultWeightedEdge>grafo;
	
	public Model() {
		dao= new EventsDao();
		
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//vertici 
		Graphs.addAllVertices(grafo, dao.getVertici());
		
		//archi --> non creo classe adiacenza
		  //creo due metodi nel dao che ricevono anno e vertice e calcolano avglat e avglon
			//per ogni coppia devo aggiungere l'arco ( doppio ciclo for )
				//se non c'è arco lo aggiungo richiamando i metodi dao per entrambi i vertici
					//calcolo la distanza media ( il peso) con questi metodi presi dal sito
		for(Integer v1 : this.grafo.vertexSet()) {
			for(Integer v2 : this.grafo.vertexSet()) {
				if(!v1.equals(v2)) {
					if(this.grafo.getEdge(v1, v2) == null) {
						Double latMediaV1 = dao.getLatMedia(anno, v1);
						Double latMediaV2 = dao.getLatMedia(anno, v2);
						
						Double lonMediaV1 = dao.getLonMedia(anno, v1);
						Double lonMediaV2 = dao.getLonMedia(anno, v2);
						
						Double distanzaMedia = LatLngTool.distance(new LatLng(latMediaV1,lonMediaV1), 
																	new LatLng(latMediaV2, lonMediaV2), 
																	LengthUnit.KILOMETER);
						//aggiungo gli archi
						Graphs.addEdgeWithVertices(this.grafo, v1, v2, distanzaMedia);
						
					}
				}
			}
		}
	}
	
	//metodo distretti adiacenti di ogni vertice
	public List<DistrettoAdiacente> getDistrettiAdiacenti(Integer distretto){
		List<DistrettoAdiacente> result = new LinkedList<DistrettoAdiacente>();
		for(Integer d: Graphs.neighborListOf(grafo, distretto)) {
			if(!d.equals(distretto)) {
				
				//recupero arco
				DefaultWeightedEdge e= this.grafo.getEdge(d, distretto);
				
				 //recupero peso
				double peso = this.grafo.getEdgeWeight(e);
				//aggiungo l'oggetto alla clsse
				DistrettoAdiacente da = new DistrettoAdiacente(d, peso);
				//aggiungo alla lista
				result.add(da);
			}
			
		}
		Collections.sort(result, new Comparator<DistrettoAdiacente>() {

			@Override
			public int compare(DistrettoAdiacente o1, DistrettoAdiacente o2) {
				
				return o1.getDistanza().compareTo(o2.getDistanza());
			}
			
		});
		return result;
		
	}
	
	//metodo per tutti i vertici del grafo
	public Set<Integer> getVerticiGrafo(){
		return this.grafo.vertexSet();
	}
	
	
	//metodo per num vertici e num archi
	public int numArchi() {
		if(this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	public int numVertici() {
		if(this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	//metodo per gli anni che fa da passacarte con il controller
	public List<Integer> getAnni(){
		return dao.getAnni();
		
	
	}
}
