public class QueryCreation {
    
    static String CreateColocation() {
	
	return "create table COLOCATION" +
	    "("+
	    "ID_COLOCATION NUMBER(3) not null,"+
	    "ADRESSE_COLOCATION CHAR(20) not null,"+
	"NOM_COLOCATION CHAR(20) not null,"+
	"A_UNE_CAGNOTTE CHAR(1) not null,"+
	"ID_PERSONNE NUMBER(3)  not null,"+
	"constraint pk_colocation primary key (ID_COLOCATION)"+
	" );";
	
}

static String CreateSeqColocation(){ return " " ; }
}
