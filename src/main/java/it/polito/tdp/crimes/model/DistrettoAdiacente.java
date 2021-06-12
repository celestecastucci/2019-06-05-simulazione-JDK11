package it.polito.tdp.crimes.model;

public class DistrettoAdiacente {

	private int id;
	private Double distanza;
	
	public DistrettoAdiacente(int id, Double distanza) {
		super();
		this.id = id;
		this.distanza = distanza;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getDistanza() {
		return distanza;
	}
	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}
	@Override
	public String toString() {
		return id + " - "+ distanza ;
	}
	
	
	
}
