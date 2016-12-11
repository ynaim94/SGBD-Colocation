import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


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
				  "Liste des colocations pour lesquels aucun achat n'a été enregistré au cous des 6 derniers mois",
				  "Liste des colocations avec le nombre de leurs membres à une date donnée",
				  "Pour chaque achat, le nombre de personnes concernées",
				  "Pour une personne donnée, la liste des débits et des crédits avec leur montant",
				  "Pour une colocation, la liste de ses membres avec leur solde"};

    private String[] queryMaj =  {"Ajouter une personne",
				  "Ajouter une colocation",
				  "Ajouter un membre a une colocation"};

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
		    String query = QuerySelected(combo.getSelectedItem());
		    //		    int 
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
	    System.out.println(res.rowInserted());
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
	if ( c == queryConsultation[4])
	    return ScriptRunner.getQuery("../sql/consultations/listColocNbrMemb.sql");
	if ( c == queryConsultation[5])
	    return ScriptRunner.getQuery("../sql/consultations/nbrPersArchat.sql");
	if ( c == queryConsultation[6])
	    return ScriptRunner.getQuery("../sql/consultations/persDebCred.sql");
	if ( c == queryConsultation[7])
	    return ScriptRunner.getQuery("../sql/consultations/colocMbrSold.sql");
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

	


    /**
     * Point de départ du programme
     * @param args
     */
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

    
    

