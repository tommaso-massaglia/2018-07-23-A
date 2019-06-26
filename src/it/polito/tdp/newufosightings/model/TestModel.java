package it.polito.tdp.newufosightings.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		System.out.println(model.getShapes());
		model.creaGrafo("cylinder", 2000);
		System.out.println(model.getSommaPesi());
		System.out.println(model.simulaDefcon(2000, 20, 10, "cylinder"));
	}

}
