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
    
    private String[] queryStat ={"Liste des colocations avec le nombre de leurs membres à une date donnée",
				  "Pour chaque achat, le nombre de personnes concernées",
				  "Pour une personne donnée, la liste des débits et des crédits avec leur montant",
				  "Pour une colocation, la liste de ses membres avec leur solde"};

    private String[] queryMaj =  {"Ajouter une personne",
				  "Ajouter une colocation",
				  "Ajouter un personne a une colocation",
				  "Ajouter un abondement"};

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


    public boolean lowerThanDate (Date date1, Date date2){

	String d1Inter = date1.toString();
	String d2Inter = date2.toString();

	char[] d1 = d1Inter.toCharArray();
	char[] d2 = d2Inter.toCharArray();
	//Format yyyy-mm-dd

	char[] year1 = new char[4];
	char[] year2 = new char[4];

	for (int i = 0 ; i < 4; i++){
	    year1[i] = d1[i];
	}

	for (int i = 0 ; i < 4; i++){
	    year2[i] = d2[i];
	}

	if ( Integer.parseInt( new String(year1)) < Integer.parseInt(new String(year2) ) )
	    return true;
	else if (Integer.parseInt (new String(year1))  == Integer.parseInt(new String(year2))){
	    char[] month1 = new char[2];
	    char[] month2 = new char[2];

	    for (int i = 0 ; i < 2; i++){
		month1[i] = d1[i];
	    }
	    
	    for (int i = 0 ; i < 2; i++){
		month2[i] = d2[i];
	    }

	    if ( Integer.parseInt(new String(month1) ) < Integer.parseInt(new String(month2)))
		return true;
	    else if ( Integer.parseInt (new String(month1) ) == Integer.parseInt(new String(month2))){
		char[] day1 = new char[2];
		char[] day2 = new char[2];

		for (int i = 0 ; i < 2; i++){
		    day1[i] = d1[i];
		}
	    
		for (int i = 0 ; i < 2; i++){
		    day2[i] = d2[i];
		}

		if ( Integer.parseInt(new String(day1) ) <= Integer.parseInt(new String(day2)))
		    return true;
		
		
	    }
	}
    
	return false;
    }
    
    public void updateQuery3(){

	

	try {
	    String query ="select DATE_ENTREE, DATE_SORTIE, sysdate from CONTRAT_MEMBRE  where ID_CONTRAT_MEMBRE = ?";
	    
	    PreparedStatement pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
	    String[] field = {"Identifiant du membre(contrat membre)"};
	    DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	    Object value = dQ.getValue()[0];

	    pstmt.setObject(1, value);
	
	    ResultSet res = pstmt.executeQuery();

	    ResultSetMetaData meta = res.getMetaData();
	    
	    
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
	    // test si la date de l'abondement n'est pas entre date entrée et date sortie
	    if (( lowerThanDate((Date)data[0][2], (Date)data[0][0]) || lowerThanDate((Date)data[0][2],(Date)data[0][1]))){ // ERREUR : DAte sortie NULL
		JOptionPane.showMessageDialog(null, "Mise a jour faite", "Validation", JOptionPane.ERROR_MESSAGE);
		return;
	    }
	    
	    query ="select A_UNE_CAGNOTTE from COLOCATION, CONTRAT_MEMBRE where CONTRAT_MEMBRE.ID_COLOCATION = COLOCATION.ID_COLOCATION and CONTRAT_MEMBRE.ID_CONTRAT_MEMBRE = ?";
	    
	    pstmt = DBConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
	    pstmt.setObject(1, value);
	
	    res = pstmt.executeQuery(query);

	    meta = res.getMetaData();

	    
	    //Petite manipulation pour obtenir le nombre de lignes
	    res.last();
	    rowCount = res.getRow();
	    Object[][] data2 = new Object[res.getRow()][meta.getColumnCount()];

	    //On revient au départ
	    res.beforeFirst();
	    j = 1;

	    //On remplit le tableau d'Object[][]
	    while(res.next()){
		for(int i = 1 ; i <= meta.getColumnCount(); i++)
		    data2[j-1][i-1] = res.getObject(i);
	    
		j++;
	    }
	    // Test si la colocation a laquelle il appartient a un abondement
	    if ( (char)data2[0][0] == 'Y'){
		JOptionPane.showMessageDialog(null, "La colocation n'a pas de cagnotte", "Erreur", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    query = "insert into ABONDEMENT values (seq_abondement.nextval, sysdate, ? ,"+ value+ " )";

	    
	    String[] field2 ={"Valeur de l'abondement"};
	    Object param[] = new Object[1];
	    dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field2);
	    param[0] = dQ.getValue();
	    updateQuery(query, param, 1);
	    
	} catch (SQLException e) {
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
	if ( c == queryStat[1])
	    return ScriptRunner.getQuery("../sql/stats/nbrPersAchat.sql");
	if ( c == queryStat[2])
	    return ScriptRunner.getQuery("../sql/stats/persDebCred.sql");
	if ( c == queryStat[3])
	    return ScriptRunner.getQuery("../sql/stats/colocMbrSold.sql");
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
	    String[] field = {"Nom de la colocation", "ID de la colocation"};
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

	
	
	 if (q == queryMaj[0]){
	     String[] field = {"Nom","Prenom","Mail"};
	     DialogQuery dQ= new DialogQuery(null, "Veuillez entrer les parametres", true, field);
	     for (int i = 0 ; i < value.length ; i++){
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

    
    

