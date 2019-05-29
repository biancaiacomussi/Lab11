package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
	//stato del mondo
	private PriorityQueue<Evento> queue ;
	private List<Tavolo> tavoli;
	
	
	//parametri di simulazione
	private final int NUMERO_EVENTI=2000;
	
	//variabili interne
	Random rand = new Random() ;
	int conta=0;
	
	

	//statistiche raccolte
	private int clienti_sodd;
	private int clienti_ins;
	private int clienti_tot;
	
	
	public void init() {
		
		queue = new PriorityQueue<>();
		tavoli = new ArrayList<>();
		
		for(int i=1; i<3; i++) {
			Tavolo t = new Tavolo(i, 10);
			tavoli.add(t);
		}
		for(int i=3; i<7;i++) {
			Tavolo t = new Tavolo(i, 8);
			tavoli.add(t);
		}
		for(int i=7; i<11;++i) {
			Tavolo t = new Tavolo(i, 6);
			tavoli.add(t);
		}
		for(int i=11; i<16;i++) {
			Tavolo t = new Tavolo(i, 4);
			tavoli.add(t);
		}
		int t=0;
		int numPersone = rand.nextInt(10)+1;
		int durata = rand.nextInt(60)+60;
		float tolleranza = rand.nextFloat();
		queue.add(new Evento(t, TipoEvento.ARRIVO,numPersone,durata,tolleranza));
		
		for(int i=1; i<this.NUMERO_EVENTI;i++) {
			numPersone = rand.nextInt(10)+1;
			durata = rand.nextInt(61)+60;
			tolleranza = rand.nextFloat();
			t = t+rand.nextInt(10)+1;
			queue.add(new Evento(t, TipoEvento.ARRIVO,numPersone,durata,tolleranza));
		}
		
		this.clienti_ins=0;
		this.clienti_sodd=0;
		this.clienti_tot=0;
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Evento ev = queue.poll();
			System.out.println(ev);
			
			switch(ev.getTipo()) {
			case ARRIVO:
				
				clienti_tot += ev.getNumPersone();
				Tavolo ottimo=new Tavolo(0,Integer.MAX_VALUE);
				for(Tavolo t:tavoli) {
					
					if(ev.getNumPersone()<=t.getPosti()) {
						if(!t.isOccupato() && t.getPosti()<ottimo.getPosti() && ev.getNumPersone()>=t.getPosti()/2)
							ottimo = t;
					}
				}
				if(ottimo.getId()!=0) {
					//faccio accomodare al tavolo
					ev.setTavolo(ottimo);
					ottimo.setOccupato(true);
					//genero evento di out
					queue.add(new Evento(ev.getMinutoArrivo()+ev.getDurata(),TipoEvento.OUT,ev.getNumPersone(),0,ev.getTolleranza()));
				}else {
					//valuto la tolleranza
					if(ev.getTolleranza()>=rand.nextFloat()) {
						this.clienti_sodd+=ev.getNumPersone();
					}else {
						this.clienti_ins+=ev.getNumPersone();
					}
				}
				break;
				
			
				
			case OUT:
				conta++;
				Tavolo t = ev.getTavolo();
				if(t!=null)
				t.setOccupato(false);
				break;
			}
		}
	}

	public PriorityQueue<Evento> getQueue() {
		return queue;
	}

	public void setQueue(PriorityQueue<Evento> queue) {
		this.queue = queue;
	}

	public int getClienti_sodd() {
		return clienti_sodd;
	}

	public void setClienti_sodd(int clienti_sodd) {
		this.clienti_sodd = clienti_sodd;
	}

	public int getClienti_ins() {
		return clienti_ins;
	}

	public void setClienti_ins(int clienti_ins) {
		this.clienti_ins = clienti_ins;
	}

	public int getClienti_tot() {
		return clienti_tot;
	}

	public void setClienti_tot(int clienti_tot) {
		this.clienti_tot = clienti_tot;
	}
	
	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}
}
