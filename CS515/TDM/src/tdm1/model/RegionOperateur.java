package tdm1.model;

public class RegionOperateur {
    public Region region;
    public Operateur operateur;

    public RegionOperateur(Region region,Operateur operateur) {
		super();
		this.operateur=operateur;
		this.region=region;
    }
    public RegionOperateur(Appel appel) {
		super();
		this.operateur=appel.operateurDestinataire;
		this.region=appel.client.departement.region;
    }
    
    public boolean equals(RegionOperateur other) {
    	return other.region==this.region && other.operateur==this.operateur;
    }

	public String toString() {
		return "Region "+this.region.nom+" "+this.operateur.nom;
	}
}
