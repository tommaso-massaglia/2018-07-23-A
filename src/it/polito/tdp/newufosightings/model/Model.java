package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {

	private Graph<State, DefaultWeightedEdge> grafo;
	private Map<String, State> stateIdMap;

	public String getShapes() {
		String result = "Elenco delle forme:\n";
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		for (String s : dao.loadShapes()) {
			result += s + "\n";
		}
		return result;
	}

	public void creaGrafo(String forma, int year) {
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		this.stateIdMap = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Map<String, Integer> stateshapesighting = new HashMap<>();

		for (State s : dao.loadAllStates()) {
			grafo.addVertex(s);
			this.stateIdMap.put(s.getId(), s);
		}

		for (StateNumber sn : dao.loadCountShape(forma, year)) {
			stateshapesighting.put(sn.getState_id(), sn.getNum_sighting());
		}

		for (StateLink sl : dao.loadAllStatesLinks()) {
			grafo.addEdge(this.stateIdMap.get(sl.getS1()), this.stateIdMap.get(sl.getS2()));
			if (stateshapesighting.containsKey(sl.getS1()) && stateshapesighting.containsKey(sl.getS2())) {
				grafo.setEdgeWeight(grafo.getEdge(this.stateIdMap.get(sl.getS1()), this.stateIdMap.get(sl.getS2())),
						stateshapesighting.get(sl.getS1()) + stateshapesighting.get(sl.getS2()));
			} else {
				grafo.setEdgeWeight(grafo.getEdge(this.stateIdMap.get(sl.getS1()), this.stateIdMap.get(sl.getS2())), 0);
			}
		}

	}

	public String getSommaPesi() {
		String result = "Elenco stati con somma dei pesi degli archi adiacenti:\n";
		int sommapesi = 0;
		for (State s1 : grafo.vertexSet()) {
			for (State s2 : Graphs.neighborListOf(grafo, s1)) {
				sommapesi += grafo.getEdgeWeight(grafo.getEdge(s1, s2));
			}
			result += "Lo stato " + s1.getId() + " ha peso complessivo " + sommapesi + "\n";
			sommapesi = 0;
		}
		return result;
	}
	
	public String simulaDefcon(int year, int T1, int alfa, String shape) {
		Simulatore sim = new Simulatore();
		sim.init(alfa, T1, this.grafo, year, shape);
		sim.run();
		return sim.getDefconStati();
	}

}
