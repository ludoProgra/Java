package serie18;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;



public class RechercheSec extends JPanel{

	private FenetrePrincipale f;
	private JComboBox<Object> liste;
	private JButton ok;
	private JTable listeSec;
	private Object[] tabSec;
	private Container cont;
	private Ecouteur ecouteur;
	private PreparedStatement prepa;
	private TableModelGen tb;
	
	public RechercheSec (FenetrePrincipale f) {
		
		
		this.f = f;
		
		ecouteur = new Ecouteur();
		
		setLayout(new GridLayout(5,7,20,20));  // ligne puis colonnes
		
		initialiserTableaux();
		
		add(new JLabel("Liste section"));
		liste = new JComboBox<Object>(tabSec);
		add(liste);
		
		ok = new JButton("OK");
		add(ok);
		ok.addActionListener(ecouteur);
		
		listeSec = new JTable();
		JScrollPane scrollIS = new JScrollPane(listeSec);
		add(scrollIS);
		
		
		
		
		
		
	}
	
		private void initialiserTableaux(){
		
			try {
			
				prepa = f.getCo().prepareStatement("SELECT libelle FROM section");
				tabSec = AccessBDGen.creerListe1Colonne(prepa); // obtenir la liste des pcs
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	
	private class Ecouteur implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(ok)) {
				try {
					prepa = f.getCo().prepareStatement("SELECT codesection FROM section WHERE libelle = ?");
					
					String sectionchoisi;
					sectionchoisi = liste.getSelectedItem().toString(); // on récupere l'element de l user a la coimbobox et on caste en string pour pas qu'il rale	
					prepa.setString(1, sectionchoisi);
					
					String codeSection = (String)AccessBDGen.creerListe1Colonne(prepa)[0]; // on prend le premeire element du tableau qui est renvoyé (façon racourci de prendre la premiere cellule du tableau et de la metre dans NEWID
							
					prepa = f.getCo().prepareStatement("SELECT dateinstallation, commentaires, dureeinstallation FROM installation " + 
							"LEFT JOIN software ON software.CodeSoftware = installation.CodeSoftware " + 
							"LEFT JOIN utilisationsoftware ON utilisationsoftware.CodeSoftware = software.CodeSoftware " + 
							"LEFT JOIN anneeetude ON anneeetude.IdAnneeEtude = utilisationsoftware.IdAnneeEtude " + 
							"LEFT JOIN section ON section.CodeSection = anneeetude.CodeSection " + 
							"WHERE section.CodeSection = ? ");
					
					prepa.setString(1, codeSection);
					tb = AccessBDGen.creerTableModel(prepa); 
					listeSec.setModel(tb);
					listeSec.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
					listeSec.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
					listeSec.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
					listeSec.setVisible(true);
					
					RechercheSec.this.repaint();
					f.repaint();
					f.setVisible(true);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	
	


	
}
