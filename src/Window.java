import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JComboBox;

import java.util.Arrays;

import java.lang.String;

public class Window extends JFrame {

    
    private String[] queryConsultation= {"Liste des colocations avec leur gestionnaire",
					 "Ensemble des membres d'une colocation donnée",
					 "Liste des achats effectués par une colocation et pour un mois donné",
					 "Liste des colocations pour lesquels aucun achat n'a été enregistré au cous des 6 derniers mois"};
    
    private String[] queryStat ={"Liste des colocations avec le nombre de leurs membres à une date donnée"};
				 
				 

    private String[] queryMaj =  {"Ajouter une personne",
				  "Ajouter une colocation",
				  "Ajouter un personne a une colocation",
				  "Ajouter un abondement",
				  "Ajouter un achat colocation",
				  "Ajouter un versement"};

    private String[] queryGest ={};

    private String[] queryMbrOpen = {};
    
    private String[] queryMbrClosed = {};
    
    private JToolBar toolBar = new JToolBar();

    private JButton run = new JButton(new ImageIcon("../img/img.png"));

    private JSplitPane split;

    private JPanel result = new JPanel();
      
    private JComboBox combo = new JComboBox();

    String profil;

    String mode;

    
    public Window(String profilUser, String modeUser){
	setSize(900, 600);
	setTitle("SGBD Colocation");
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	profil = profilUser;
	modeUser = mode;
	
	initToolbar();
	initContent();
    }
	
   
    private void initToolbar(){

	combo.addItem("Selectionner une requete");
	for (int i = 0; i < queryConsultation.length; i++){ 
	    combo.addItem(queryConsultation[i]);
	}

	for (int i = 0; i < queryStat.length; i++){ 
	    combo.addItem(queryStat[i]);
	}

	for (int i = 0; i < queryMaj.length; i++){ 
	    combo.addItem(queryMaj[i]);
	}
	
	
	/*
	if (profil == "Gestionnaire"){
	    for (int i = 0; i< queryGest.length; i++){
		combo.addItem(queryGest[i]);
	    }
	}
	else{
	    if (mode == "Ouvert"){
		for (int i = 0; i < queryGest.length; i++){
		    combo.addItem(queryMbrOpen[i]);
		}
	    }
	    else {
		for (int i = 0; i < queryGest.length; i++){
		    combo.addItem(queryMbrClosed[i]);
		}
	    }
	    }*/
	    
	run.setPreferredSize(new Dimension(30, 35));
	run.setBorder(null);
	run.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
		    if (combo.getSelectedItem() == queryMaj[3]){
			updateQuery3();
			return;
		    }
		    if (combo.getSelectedItem() == queryMaj[4]){
			updateQuery4();
			return;
		    }
		    if (combo.getSelectedItem() == queryMaj[5]){
			updateQuery5();
			return;
		    }
		    
		    String query = QuerySelected(combo.getSelectedItem());
		    int number = numberParameter(query);
		    if ( number  == 0)
			initTable(query);
		    else { //Générer fenêtre de dialogue
			Object value[] = new Object[number];
			displayPane(combo.getSelectedItem(),value);
			
			//test si toutes les valeurs ont été remplies
			for (int i = 0; i < value.length ; i++){
			    if (value[i] == null){
				return;
			    }
			}
			for (int i = 0; i < queryMaj.length ; i++)
			    if ( queryMaj[i].compareTo((String)combo.getSelectedItem()) == 0 ){
				updateQuery(query,value,number);
				return;
			    }
			
			initTable(query, value, number);
		    }
		}
	    });
	
	toolBar.add(run);
	toolBar.addSeparator();
	toolBar.add(combo);
	getContentPane().add(toolBar, BorderLayout.NORTH);
	
	
	
    }
    
    public int numberParameter(String query){
	int n = 0;
	for (int i = 0; i < query.length(); i++){
	    if (query.charAt(i) == '?')
		n++;
	}
	return n;
    }
    
    
    public void initContent(){
	//Vous connaissez ça...
	result.setLayout(new BorderLayout());
	split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(), result);
	split.setDividerLocation(150);
	getContentPane().add(split, BorderLayout.CENTER);
	
    }



    
    public void initTable(String query){
	try {
	    //On crée un statement
	    //long start = System.currentTimeMillis();
	    Statement state = DBConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    
	    //On exécute la requête
	    ResultSet res = state.executeQuery(query);

	    displayQuery(res);

	    res.close();
	    state.close();
	    
	} catch (SQLException e) {
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    result.removeAll();
	    result.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
	    result.revalidate();
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	}	
    }

    

    public void initTable(String query, Object[] param, int length ){
	try {
	    //On crée un statement
	    PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    for (int i = 1; i<= length ; i++){
		pstmt.setObject(i,param[i-1]);
	    }
	    
	    //On exécute la requête
	    ResultSet res = pstmt.executeQuery();

	    //On affiche la requete
	    displayQuery(res);

	    res.close();
	    pstmt.close();
	} catch (SQLException e) {
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    result.removeAll();
	    result.add(new JScrollPane(new JTable()), BorderLayout.CENTER);
	    result.revalidate();
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	}	
    }

    
    public void updateQuery(String query, Object[] param, int length){
	try {
	    //On crée un statement
	    PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    for (int i = 1; i<= length ; i++){
		pstmt.setObject(i,param[i-1]);
	    }
	    
	    //On exécute la requête
	   
	    pstmt.executeUpdate();
	   
	    pstmt.close();
	    
	    JOptionPane.showMessageDialog(null, "Mise a jour faite", "Validation", JOptionPane.INFORMATION_MESSAGE);

	} catch (SQLException e) {
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	}
    }


    //Methode pour comparer les dates de java.sql.Date
    
   
    
    //Methode pour evaluer sur la date est entre date entrée et date sortie pour un membre d'une colocation
    
    public Object[] validDate(String query) throws SQLException{
	
	    
	
	PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
	String[] field = {"Identifiant du membre", "Date de l'operation(jj-mmm-aaaa)"};
	DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	int idMembre ;
	Object dateVersement = new Object(); 
	
	if (dQ.getValue()[0] != null)
	    if (dQ.getValue()[1] != null){
		dateVersement = dQ.getValue()[1]; 
		idMembre = Integer.parseInt((String)dQ.getValue()[0]);
	    }
	    else return null;
	else return null;


	
	pstmt.setObject(1, idMembre);
	pstmt.setObject(2, dateVersement);
	pstmt.setObject(3, dateVersement);
	pstmt.setObject(4, dateVersement);

	
	ResultSet res = pstmt.executeQuery();

	ResultSetMetaData meta = res.getMetaData();

	if (res.next() == false){
	    JOptionPane.showMessageDialog(null, "Le membre n'existe pas à cette date", "Erreur", JOptionPane.ERROR_MESSAGE);
	    return null;
	}
	else{
	    
	}
	Object[] obj ={idMembre,dateVersement};
	return obj;
		
	/*	Date dateEntree = (Date) res.getObject(1);
	Date dateSortie = (Date) res.getObject(2);
	//Date dateActuelle = (Date) res.getObject(3);
	    
	    
	// test si la date de l'abondement n'est pas entre date entrée et date sortie

	if (dateSortie != null){
	    if  (lowerThanDate(dateSortie,dateActuelle) || ( lowerThanDate(dateActuelle, dateEntree) )){
		JOptionPane.showMessageDialog(null, "Le membre n'est plus dans la colocation", "Erreur", JOptionPane.ERROR_MESSAGE);
		return -1;
	    }}
	else 
	    if ( lowerThanDate(dateActuelle, dateEntree)){ // ERREUR : DAte sortie NULL
		JOptionPane.showMessageDialog(null, "Le membre n'est plus dans la colocation", "Erreur", JOptionPane.ERROR_MESSAGE);
		return -1;
	    }

	    return idMembre;*/

	
	
    }



    
    //Methode pour la requete de mise a jour : Ajouter un abondement qui a un comportement non générique (Se fait sur plusieurs requêtes)
    
    public void updateQuery3(){

	try {

		
	    String query ="select ID_CONTRAT_MEMBRE  from CONTRAT_MEMBRE  where ID_CONTRAT_MEMBRE = ? and ((DATE_ENTREE <= ? and DATE_SORTIE is null) or (DATE_ENTREE <= ? and DATE_SORTIE >= ?)) ";
	    Object[] value = null;
	    	    
	    value = validDate(query);
	    
	    if (value  == null){
		return;
		}

	    int idMembre = (int) value[0];
	    Object dateAbondement = value[1];
	   
	    query ="select A_UNE_CAGNOTTE from COLOCATION, CONTRAT_MEMBRE where CONTRAT_MEMBRE.ID_COLOCATION = COLOCATION.ID_COLOCATION and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = ?";
	    
	    PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
	    pstmt.setObject(1, idMembre);
	
	    ResultSet res = pstmt.executeQuery();

	    ResultSetMetaData meta = res.getMetaData();

	    
	    //Petite manipulation pour obtenir le nombre de lignes
	    res.last();
	    int rowCount = res.getRow();
	    Object[][] data2 = new Object[res.getRow()][meta.getColumnCount()];

	    //On revient au départ
	    res.beforeFirst();
	    int j = 1;

	    //On remplit le tableau d'Object[][]
	    while(res.next()){
		for(int i = 1 ; i <= meta.getColumnCount(); i++)
		    data2[j-1][i-1] = res.getObject(i);
	    
		j++;
	    }
	    // Test si la colocation a laquelle il appartient a une cagnotte
	    if ( ((String)data2[0][0]).compareTo("Y") != 0){
		JOptionPane.showMessageDialog(null, "La colocation n'a pas de cagnotte", "Erreur", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    query = "insert into ABONDEMENT values (seq_abondement.nextval, ?, ? , ? )";

	    
	    String[] field2 ={"Valeur de l'abondement"};
	    Object param[] = new Object[3];
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field2);
	    param[0] = dateAbondement;
	    param[1] = dQ.getValue()[0];
	    param[2] = idMembre;
	    updateQuery(query, param,3);
	    
	} catch (SQLException e) {
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	}
	
	
					    
    }

    //Ajouter un achat colocation
    
     public void updateQuery4()  {
	try{
	    
	    
	    String[] typeAchat = {"A partir de la cagnotte","Par un membre"};
	    String type = (String)JOptionPane.showInputDialog(null, "Veuillez indiquer le type d'achat colocation !", "Type de l'achat",JOptionPane.QUESTION_MESSAGE,null,typeAchat,typeAchat[0]);

	    
	    if (type == "Par un membre"){
		
		String query ="select ID_CONTRAT_MEMBRE  from CONTRAT_MEMBRE  where ID_CONTRAT_MEMBRE = ? and ((DATE_ENTREE <= ? and DATE_SORTIE is null) or (DATE_ENTREE <= ? and DATE_SORTIE >= ?)) ";
		Object[] value = null;
		
		value = validDate(query);
		
		if (value  == null){
		    return;
		}
		
		int idMembre = (int) value[0];
		Object dateVersement = value[1];
		
		// Insère l'achat
		
		query = "insert into ACHAT_COLOCATION values (seq_colocation.nextval, ?, ? , ? , ? , ?)";

		String[] field ={"Intitule","Montant de l'achat","Id de la colocation" };
		Object param[] = new Object[5];
		DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);

		param[0] = dQ.getValue()[0];
		param[1] = dateVersement;
		param[2] = dQ.getValue()[1];
		param[3] = dQ.getValue()[2];
		param[4] = idMembre;
		
		updateQuery(query, param, param.length);
	    } else if (type == "A partir de la cagnotte") { 	    //Avec Cagnotte


		String query = "select A_UNE_CAGNOTTE from COLOCATION where ID_COLOCATION = ?";

		
		PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		String[] field ={"Id de la colocation" };
		Object id = new Object();
		DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
		
		id = dQ.getValue()[0];

		pstmt.setObject(1, id);
		
		ResultSet res = pstmt.executeQuery();

		ResultSetMetaData meta = res.getMetaData();

		if (res.next() == false){
		     JOptionPane.showMessageDialog(null, "Id invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
		    return;
		}
	    
		//Petite manipulation pour obtenir le nombre de ligne
		res.last();
		int rowCount = res.getRow();
		Object[][] data2 = new Object[res.getRow()][meta.getColumnCount()];
		
		//On revient au départ
		res.beforeFirst();
		int j = 1;

		//On remplit le tableau d'Object[][]
		while(res.next()){
		    for(int i = 1 ; i <= meta.getColumnCount(); i++)
			data2[j-1][i-1] = res.getObject(i);
	    
		    j++;
		}

		// Test si la colocation a laquelle il appartient a un cagnotte
		if ( ((String)data2[0][0]).compareTo("Y") != 0){
		    JOptionPane.showMessageDialog(null, "La colocation n'a pas de cagnotte", "Erreur", JOptionPane.ERROR_MESSAGE);
		    return;
		}


		// Insère l'achat
	    
		query = "insert into ACHAT_COLOCATION values (seq_colocation.nextval, ?, ? , ? , ? , null)";

		String[] field2 ={"Intitule","Date(jj-mmm-aaaa)","Montant de l'achat" };
		Object param[] = new Object[field2.length + 1];
		dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field2);

		for (int i = 0 ; i < field2.length ; i++){
		    param[i] = dQ.getValue()[i];
		}
		param[param.length -1 ] = id;
	    
		updateQuery(query, param, param.length);
	    }

	    
	} catch (SQLException e) {
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	} 
	
	
    }

    public void updateQuery5(){
	try{
	    String query ="select ID_CONTRAT_MEMBRE  from CONTRAT_MEMBRE  where ID_CONTRAT_MEMBRE = ? and ((DATE_ENTREE <= ? and DATE_SORTIE is null) or (DATE_ENTREE <= ? and DATE_SORTIE >= ?)) ";
	    Object[] value = null;
	    	    
	    value = validDate(query);
	    
	    if (value  == null){
		return;
	    }

	    Object idMembre =  value[0];
	    Object dateVers = value[1];

	    Object[] value2 = null;

	    String dateVersement = dateVers.toString();

	    	    
	    query ="select ID_CONTRAT_MEMBRE  from CONTRAT_MEMBRE  where ID_CONTRAT_MEMBRE = ? and ((DATE_ENTREE <= ? and DATE_SORTIE is null) or (DATE_ENTREE <= ? and DATE_SORTIE >= ?))" ;
	    
	    PreparedStatement pstm2 = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    String[] field = {"Id du receveur"};
	    
	    pstm2.setObject(1, (new DialogQuery (null, "Veuillez entrer les parametres", true,  field).getValue()[0]));
	    pstm2.setObject(2, dateVers);
	    pstm2.setObject(3,dateVers);
	    pstm2.setObject(4,dateVers);
	    
	    ResultSet res2 = pstm2.executeQuery();
	    
	    if (res2.next() == false){
		JOptionPane.showMessageDialog(null, "Le membre n'existe pas a cette date !", "Erreur", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    
	    Object idMembre2 = res2.getObject(1);
	    
	    
	    query = "select ID_COLOCATION from CONTRAT_MEMBRE where ID_CONTRAT_MEMBRE = ? or ID_CONTRAT_MEMBRE = ?";

	    PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	    pstmt.setObject(1,idMembre);
	    pstmt.setObject(2,idMembre2);
		
	    ResultSet res = pstmt.executeQuery();

	    if (res.next() == false){
		JOptionPane.showMessageDialog(null, "Pas de la même colocation !", "Erreur", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    query = "insert into VERSEMENT values (seq_versement.nextval, ? , ?, ?, ?)";

	    String[] field2 ={"Montant" };
	    Object param[] = new Object[field2.length + 3];
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field2);

	    param[0]= dateVersement;
	    param[1] = dQ.getValue()[0];
	    param[2] = idMembre;
	    param[3] = idMembre2;
	    
	    updateQuery(query, param, param.length);
		
	} catch (SQLException e){
	    //Dans le cas d'une exception, on affiche une pop-up et on efface le contenu		
	    JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
	}
    }
    
    public void displayQuery(ResultSet res) throws SQLException{
	
	//On récupère les meta afin de récupérer le nom des colonnes
	ResultSetMetaData meta = res.getMetaData();
	//On initialise un tableau d'Object pour les en-têtes du tableau
	Object[] column = new Object[meta.getColumnCount()];

	for(int i = 1 ; i <= meta.getColumnCount(); i++)
	    column[i-1] = meta.getColumnName(i);

	//Petite manipulation pour obtenir le nombre de lignes
	res.last();
	int rowCount = res.getRow();
	Object[][] data = new Object[res.getRow()][meta.getColumnCount()];

	//On revient au départ
	res.beforeFirst();
	int j = 1;

	//On remplit le tableau d'Object[][]
	while(res.next()){
	    for(int i = 1 ; i <= meta.getColumnCount(); i++)
		data[j-1][i-1] = res.getObject(i);
				
	    j++;
	}
	

	//On enlève le contenu de notre conteneur
	result.removeAll();
	//On y ajoute un JTable
	result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);

	//On force la mise à jour de l'affichage
	result.revalidate();
	    
    }


    private String QuerySelected (Object c){
	
	if ( c == queryConsultation[0])
	    return ScriptRunner.getQuery("../sql/consultations/listColocGest.sql");
	if ( c == queryConsultation[1])
	    return ScriptRunner.getQuery("../sql/consultations/membColoc.sql");
	if ( c == queryConsultation[2])
	    return ScriptRunner.getQuery("../sql/consultations/listAchatColoc.sql");
	if ( c == queryConsultation[3])
	    return ScriptRunner.getQuery("../sql/consultations/listColocSixMois.sql");
	if ( c == queryStat[0])
	    return ScriptRunner.getQuery("../sql/stats/listColocNbrMemb.sql");
	if ( c == queryMaj[0])
	    return ScriptRunner.getQuery("../sql/miseAJour/ajoutPers.sql");
	if ( c == queryMaj[1])
	    return ScriptRunner.getQuery("../sql/miseAJour/ajoutColoc.sql");
	if ( c == queryMaj[2])
	    return ScriptRunner.getQuery("../sql/miseAJour/ajoutMbr.sql");
	    
	return "";
    }
    
    private void displayPane( Object query, Object[] value){
	String q = (String) query;
	int n = 0;

	if (q == queryConsultation[1]) {
	    String[] field = {"ID de la colocation"};
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	    for (int i = 0 ; i < value.length ; i++){
		value[i] = dQ.getValue()[i];
	    }
	}

	if (q == queryConsultation[2]){
	    String[] field = {"Id de la colocation", "Mois (entre 1 et 12)"};
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	    for (int i = 0 ; i < value.length ; i++){
		value[i] = dQ.getValue()[i];
	    }
	}

	if (q == queryStat[0]){
	    String[] field = {"Date (jj-mmm-aaaa)"};
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	    value[0] = dQ.getValue()[0];
	    value[1] = value[0];
	    
	}
		
		
	
	 if (q == queryMaj[0]){
	     String[] field = {"Nom","Prenom","Mail"};
	     DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	     for (int i = 0 ; i < value.length  ; i++){
		 value[i] = dQ.getValue()[i];
	     }
	 }

	 if (q == queryMaj[1]){
	     String[] field = {"Adresse","Nom","A une cagnotte?","ID du gerant"};
	     DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	     for (int i = 0 ; i < value.length ; i++){
		 value[i] = dQ.getValue()[i];
	     }
	 }

	 if (q == queryMaj[2]){
	     String[] field = {"Date d'entree", "Date de sortie", "ID de la colocation", "ID de la personne"};
	     DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	     for (int i = 0 ; i < value.length ; i++){
		 value[i] = dQ.getValue()[i];
	     }
	 }


	 
    }

	


 
    public static void main(String[] args){

	String[] profilArray = {"Gestionnaire","Membre"};
	String[] modeArray = {"Ouvert", "Fermé"};
	JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
	String profil = (String)jop.showInputDialog(null, "Veuillez indiquer votre Profil !", "Profil",JOptionPane.QUESTION_MESSAGE,null,profilArray,profilArray[0]);
	if (profil != null){
	    String mode = (String)jop.showInputDialog(null, "Veuillez indiquer un mode d'utilisation !", "Mode",JOptionPane.QUESTION_MESSAGE,null,modeArray,modeArray[0]);
	    if  (mode != null){
		Window fen = new Window(profil,mode);
		fen.setVisible(true);
	    }
	}
    }
    


}

    
    

