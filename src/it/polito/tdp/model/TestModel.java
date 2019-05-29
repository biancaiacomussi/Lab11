package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		Simulatore sim = new Simulatore();
		sim.init();
		sim.run();
		
		System.out.println("Clienti totali:"+sim.getClienti_tot());
		System.out.println("Clienti soddisfatti:"+sim.getClienti_sodd());
		System.out.println("Clienti insoddisfatti:"+sim.getClienti_ins());
		System.out.println("Eventi out: "+sim.getConta());
		

	}

}
