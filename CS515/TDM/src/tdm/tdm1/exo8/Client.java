package tdm.tdm1.exo8;

import java.util.List;

public class Client {
	private Integer numClient; // MODIFICATION ICI
	private String nom;

	public Client(int numClient, String nom) {
		super();
		this.numClient = numClient;
		this.nom = nom;
	}

	public Integer getNumClient() // MODIFICATION ICI
	{
		return numClient;
	}

	public void setNumClient(Integer numClient) // MODIFICATION ICI
	{
		this.numClient = numClient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	static public Client findClientIn(List<Client> clients, Integer numClient) // MODIFICATION ICI
	{
		for (Client client : clients) {
			if (client.getNumClient().equals(numClient)) {
				return client;
			}
		}
		return null;
	}
}
