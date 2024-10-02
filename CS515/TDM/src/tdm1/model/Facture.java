package tdm1.model;

public class Facture {
	public int value;
	public Client client;
	public Facture(Client client) {
		super();
		this.client = client;
		this.value = 0;
	}
	public void add(int val) {
		value+=val;
	}
}
