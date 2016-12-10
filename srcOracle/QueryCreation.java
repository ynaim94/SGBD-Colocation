public class QueryCreation {
    
    static String CreateColocation() {
	
	return "create table COLOCATION" +
	    "("+
	    "ID_COLOCATION NUMBER(3) NOT NULL,"+
	    "ADRESSE_COLOCATION CHAR(20) NOT NULL,"+
	"NOM_COLOCATION CHAR(20) NOT NULL,"+
	"A_UNE_CAGNOTTE CHAR(1) NOT NULL,"+
	"ID_PERSONNE NUMBER(3)  NOT NULL,"+
	    //"constraint pk_colocation primary key (ID_COLOCATION)"+
	    " );";
	
    }
}
    

