package tdm1.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tdm1.model.tri.TriFacture;

public class TestModele 
{

	public static void main(String[] args) 
    {
    	TestModele model=new TestModele();
    	//model.ex4();
    	//model.ex5();
    	model.ex7();
    }
    
    void ex4(){
    	TriFacture trifact = new TriFacture();
    	
    	List<Appel> appels = DataSource.getDataSource().getAppels();
    	HashMap<Client, Facture> totaux= new HashMap();
    	for(Appel appel: appels) {
    		Facture tmp = totaux.get(appel.client);
    		if(tmp==null) {
    			tmp=new Facture(appel.client);
    			totaux.put(appel.client, tmp);
    		}
    		tmp.add(appel.cout);
    	}
    	
    	List<Facture> factures = new ArrayList(totaux.values());
    	Collections.sort(factures,trifact);
    	for (Facture facture : factures) {
    		  System.out.println(facture.client+" => "+facture.value);
    	}
    }
    
    void ex5(){
    	HashMap<Integer, Integer> totaux= new HashMap();
    	
    	List<Appel> appels = DataSource.getDataSource().getAppels();
    	for(Appel appel: appels) {
    		Integer tmp = totaux.get(appel.client.departement.numero);
    		if(tmp==null) {
    			tmp=0;
    		}
    		tmp+=appel.cout;
    		totaux.put(appel.client.departement.numero, tmp);
    	}
    	

    	List<Integer> departements=new ArrayList(totaux.keySet());
    	Collections.sort(departements);
    	for (Integer dep: departements) {
    		  System.out.println(dep+"=>"+totaux.get(dep));
    	}
    }


    void ex7(){
    	HashMap<RegionOperateur, Integer> totaux= new HashMap();
    	
    	List<Appel> appels = DataSource.getDataSource().getAppels();
    	for(Appel appel: appels) {
    		RegionOperateur tmp_key = new RegionOperateur(appel);
    		Integer tmp_val = totaux.get(tmp_key);
    		if(tmp_val==null) {
    			tmp_val=0;
    		}
    		tmp_val+=appel.cout;
			totaux.put(tmp_key, tmp_val);
    	}
    	
    	for (Entry<RegionOperateur, Integer> tmp: totaux.entrySet()) {
  		  System.out.println(tmp.getKey()+"=>"+tmp.getValue());
    	}
    }
}
