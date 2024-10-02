package tdm1.model.tri;

import java.util.Comparator;

import tdm1.model.Departement;

public class TriDep implements Comparator<Departement>{

	@Override
	public int compare(Departement o1, Departement o2) {
		return o1.numero - o2.numero;
	}
	
}