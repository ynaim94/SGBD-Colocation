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


import java.lang.String;

public class Fenetre2 extends JFrame {

    /**
     * ToolBar pour le lancement des requêtes
     */
    private JToolBar tool = new JToolBar();

    /**
     * Le bouton
     */
    private JButton load = new JButton(new ImageIcon("../img/img.png"));

    /**
     * Le délimiteur
     */
    private JSplitPane split;

    /**
     * Le conteneur de résultat
     */
    private JPanel result = new JPanel();

    /**
     * Requête par défaut pour le démarrage
     */
    private String requete = "select * from BENEFICIAIRE";

    /**
     * Le composant dans lequel taper la requête
     */
    private JTextArea text = new JTextArea(requete);

    /**
     * Liste déroulante des requêtes
     */
    private JComboBox combo = new JComboBox();
		
    /**
     * Constructeur
     */
    public Fenetre2(){
	setSize(900, 600);
	setTitle("TP JDBC");
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		
	initToolbar();
	initContent();
	initTable(requete);
    }
	
    /**
     * Initialise la toolbar
     */
    private void initToolbar(){
	combo.addItem("Selectionner une requete");
	combo.addItem("Liste des colocations avec leur gestionnaire");
	combo.addItem("Ensemble des membres d'une colocation donnée");
	combo.addItem("Liste des achats efefctués par une colocation et pour un mois donné");
	combo.addItem("Liste des colocations pour lesquels aucun achat n'a été enregistré au cous des 6 derniers mois");
	combo.addItem("Liste des colocations avec le nombre de leurs membres à une date donnée");
	combo.addItem("Pour chaque achat, le nombre de personnes concernées");
	combo.addItem("Pour une personne donnée, la liste des débits et des crédits avec leur montant");
	combo.addItem("Pour une colocation, la liste de ses membres avec leur solde");
	load.setPreferredSize(new Dimension(30, 35));
	//combo.setPreferredSize(new Dimension(100,20));
	load.setBorder(null);
	load.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
		    int number = numberParameter(text.getText());
		    if ( number  == 0)
			initTable(text.getText());
		    //Générer fenêtrede dialogue
		    else {
			JOptionPane jop = new JOptionPane();
			Object value[] = new Object[number];
			for (int i=0; i < number; i++){
			    value[i] = jop.showInputDialog(null, "Donnez la valeur du parametre" + (i+1), "Param" + (i+1) , JOptionPane.QUESTION_MESSAGE);

			}
			initTable(text.getText(), value, number);
		    }
		}
	    });
	
	tool.add(load);
	tool.addSeparator();
	tool.add(combo);
	getContentPane().add(tool, BorderLayout.NORTH);
	
    }

    public int numberParameter(String query){
	int n = 0;
	for (int i = 0; i < query.length(); i++){
	    if (query.charAt(i) == '?')
		n++;
	}
	return n;
    }
    
    /**
     * Initialise le contenu de la fenêtre
     */
    public void initContent(){
	//Vous connaissez ça...
	result.setLayout(new BorderLayout());
	split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(text), result);
	split.setDividerLocation(100);
	getContentPane().add(split, BorderLayout.CENTER);
	
    }
	
    /**
     * Initialise le visuel avec la requête saisie dans l'éditeur
     * @param query
     */
    public void initTable(String query){
	try {
	    //On crée un statement
	    //long start = System.currentTimeMillis();
	    Statement state = SdzConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    
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
	    //long start = System.currentTimeMillis();
	    PreparedStatement pstmt = SdzConnection.getInstance().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

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

	//On ferme le tout                                     
	    

	//long totalTime = System.currentTimeMillis() - start;

	//On enlève le contenu de notre conteneur
	result.removeAll();
	//On y ajoute un JTable
	result.add(new JScrollPane(new JTable(data, column)), BorderLayout.CENTER);
	//result.add(new JLabel("La requête à été exécuter en " + totalTime + " ms et a retourné " + rowCount + " ligne(s)"), BorderLayout.SOUTH);
	//On force la mise à jour de l'affichage
	result.revalidate();
	    
    }
    
    /**
     * Point de départ du programme
     * @param args
     */
    public static void main(String[] args){
	Fenetre2 fen = new Fenetre2();
	fen.setVisible(true);
    }
}