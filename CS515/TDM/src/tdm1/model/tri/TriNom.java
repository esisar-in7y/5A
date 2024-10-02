package tdm1.model.tri;

import java.util.Comparator;

import tdm1.model.Client;

public class TriNom implements Comparator<Client>{

	@Override
	public int compare(Client o1, Client o2) {
		return o1.nom.compareTo(o2.nom);
	}
	
}