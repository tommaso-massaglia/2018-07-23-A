package it.polito.tdp.newufosightings.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Simulatore {

	private int alfa;
	private int T1;
	private Graph<State, DefaultWeightedEdge> grafo;
	private Map<String, StatoDefcon> statiDefcon;

	private PriorityQueue<Evento> queue;

	public String getDefconStati() {
		String result = "";
		for (StatoDefcon sd : this.statiDefcon.values()) {
			result += "Lo stato " + sd.getStato().getName() + " ha livello DEFCON:" + sd.getDefcon() + "\n";
		}
		return result;
	}

	public void init(int alfa, int T1, Graph<State, DefaultWeightedEdge> grafo, int year, String shape) {
		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();
		this.grafo = grafo;
		this.alfa = alfa;
		this.T1 = T1;
		this.queue = new PriorityQueue<Evento>();
		this.statiDefcon = new HashMap<>();

		for (Sighting s : dao.loadYearShapeSighting(shape, year)) {
			queue.add(new Evento(s.getDatetime(), s.getState().toUpperCase(), Evento.TIPO.ALLERTA));
		}

		for (State s : dao.loadAllStates()) {
			this.statiDefcon.put(s.getId(), new StatoDefcon(s));
		}
	}

	public void run() {
		Evento e;
		while ((e = queue.poll()) != null) {
			switch (e.getTipo()) {
			case ALLERTA:
				this.aumentaDefcon(1, e.getState());
				queue.add(new Evento(e.getData().plusDays(T1), e.getState(), Evento.TIPO.CESSATA_ALLERTA));
				if (alfa > Math.random() * 100) {
					for (State s : Graphs.neighborListOf(grafo, this.statiDefcon.get(e.getState()).getStato())) {
						this.aumentaDefcon(0.5, s.getId());
						queue.add(new Evento(e.getData().plusDays(T1), e.getState(), Evento.TIPO.CESSATA_ALLERTA_ALFA));
					}
				}

				break;

			case CESSATA_ALLERTA:
				this.riduciDefcon(1, e.getState());
				break;

			case CESSATA_ALLERTA_ALFA:
				this.riduciDefcon(0.5, e.getState());
				break;

			}
		}
	}

	private boolean aumentaDefcon(double quantita, String stato) {
		if (this.statiDefcon.get(stato).getDefcon() - quantita >= 1) {
			this.statiDefcon.get(stato).addDefcon(quantita);
		}
		else {
			this.statiDefcon.get(stato).setDefcon(1);
		}
		return false;
	}

	private void riduciDefcon(double quantita, String stato) {
		if (this.statiDefcon.get(stato).getDefcon() + quantita <= 5) {
			this.statiDefcon.get(stato).removeDefcon(quantita);
		}
		else {
			this.statiDefcon.get(stato).setDefcon(5);
		}
		return;
	}

}
