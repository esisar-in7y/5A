package tdm.tdm1.exo8;

import java.util.ArrayList;
import java.util.List;

public class Test 
{         
    public static void main(String[] args)     
    {
    	// Integer == Integer => compare only -128 and 127 (1st byte)
        List<Client> cs = new ArrayList<>();
        cs.add(new Client(50, "CLIENT AAAA"));
        cs.add(new Client(100, "CLIENT BBBB"));
        cs.add(new Client(150, "CLIENT CCCC"));
        cs.add(new Client(300000000, "CLIENT DDDD"));

        Client c = Client.findClientIn(cs, 150);

        if (c==null)
        {
            System.out.println("Le client n'est pas trouvé");
        }
        else
        {
            System.out.println("Le client trouvé est "+c.getNumClient()+" "+c.getNom());
        }
    }
}
