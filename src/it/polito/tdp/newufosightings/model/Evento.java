package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento> {

	public enum TIPO {
		ALLERTA, CESSATA_ALLERTA, CESSATA_ALLERTA_ALFA, 
	}

	private LocalDateTime data;
	private String state;
	private TIPO tipo;

	public Evento(LocalDateTime data, String state, TIPO tipo) {
		this.data = data;
		this.state = state;
		this.tipo = tipo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TIPO getTipo() {
		return tipo;
	}

	public void setTipo(TIPO tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compareTo(Evento arg0) {
		return this.data.compareTo(arg0.getData());
	}

}
