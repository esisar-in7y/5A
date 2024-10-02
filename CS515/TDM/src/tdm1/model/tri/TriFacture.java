package tdm1.model.tri;

import java.util.Comparator;
import tdm1.model.Facture;

public class TriFacture implements Comparator<Facture>{

	@Override
	public int compare(Facture o1, Facture o2) {
		return o1.value - o2.value;
	}
	
}