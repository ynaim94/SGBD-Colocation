


public class Query{
    
    public String ConsultationColocGestio(){
	return "Select COLOCATION_NOM,PERSONNE_NOM" +
	    "from COLOCATION,PERSONNE" +
	    "where COLOCATION_ID_PERSONNE=PERSONNE_ID_PERSONNE;";
    }
    
    public String ConsultationMembreColoc(){
	return	"Select PERSONNE_NOM"+
	    "from COLOCATION,CONTRAT_MEMBRE,PERSONNE"+
	    "where COLOCATION_NOM= ? || COLOCATION_ID_COLOCATION= ?"+
	    "and COLOCATION_ID_COLOCATION=CONTRAT_MEMBRE_ID_COLOCATION"+
	    "and CONTRAT_MEMBRE_ID_PERSONNE=PERSONNE_ID_PERSONNE;";
    }
    
}
