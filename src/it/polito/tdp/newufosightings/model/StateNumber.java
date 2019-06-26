package it.polito.tdp.newufosightings.model;

public class StateNumber {

	private String state_id;
	private int num_sighting;

	public StateNumber(String state_id, int num_sighting) {
		this.state_id = state_id;
		this.num_sighting = num_sighting;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public int getNum_sighting() {
		return num_sighting;
	}

	public void setNum_sighting(int num_sighting) {
		this.num_sighting = num_sighting;
	}

}
