package it.polito.tdp.newufosightings.model;

public class StatoDefcon {

	private State stato;
	private double defcon;

	public StatoDefcon(State stato) {
		this.stato = stato;
		this.defcon = 5;
	}

	public State getStato() {
		return stato;
	}

	public void setStato(State stato) {
		this.stato = stato;
	}

	public double getDefcon() {
		return defcon;
	}

	public void setDefcon(double defcon) {
		this.defcon = defcon;
	}

	public void removeDefcon(double quantita) {
		this.defcon += quantita;
	}

	public void addDefcon(double quantita) {
		this.defcon -= quantita;
	}
}
