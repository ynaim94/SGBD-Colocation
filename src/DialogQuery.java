import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;


class DialogQuery extends JDialog {

    private Object[] value;
    
    //  private boolean sendData;

    // private JLabel[] labelArray;
    
    private JTextField[] text;
    

    public DialogQuery(JFrame parent, String title, boolean modal, String[] field){

	super(parent, title, modal);

	this.setSize(600, 600);

	this.setLocationRelativeTo(null);

	this.setResizable(true);

	this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

	this.initComponent(field);

	this.setVisible(true);
	
    }
    


    private void initComponent (String[] field){

	value = new Object[field.length];

	text = new JTextField[field.length];

	JPanel content = new JPanel();
	
	JPanel[] pan = new JPanel[field.length];

	for (int i = 0; i < field.length; i++){

	    pan[i] = new JPanel();

	    text[i] = new JTextField();
	    
	    //pan[i].setBackground(Color.white);

	    pan[i].setPreferredSize(new Dimension(300, 60));

	    text[i].setPreferredSize(new Dimension(100, 25));

	    pan[i].setBorder(BorderFactory.createTitledBorder(field[i]));

	    //textLabel = new JLabel("Saisir un text :");

	    //pan[i].add(textLabel);

	    pan[i].add(text[i]);

	    content.add(pan[i]);

	}

	JPanel control = new JPanel();
	JButton okBouton = new JButton("OK");

	okBouton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {        
		    
		    for (int i = 0; i < value.length; i++){
			if ((text[i].getText()).isEmpty())
			    value[i] = null;
			else
			    value[i] = text[i].getText();
		    }
		    
		    setVisible(false);
		    
		}
		
	    });

	JButton cancelBouton = new JButton("Annuler");

	cancelBouton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {

		    setVisible(false);

		}      

	    });


	control.add(okBouton);

	control.add(cancelBouton);

	this.getContentPane().add(content, BorderLayout.CENTER);

	this.getContentPane().add(control, BorderLayout.SOUTH);
    }

    public Object[] getValue (){
	return value;
    }
    

    
}
	    
