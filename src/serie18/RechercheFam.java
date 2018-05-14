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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;




public class RechercheFam extends JPanel{

	private FenetrePrincipale f;
	private JComboBox<Object> liste;
	private JButton ok;
	private JTable listeFam;
	private Object[] tabFam;
	private Container cont;
	private Ecouteur ecouteur;
	private PreparedStatement prepa;
	private TableModelGen tb;
	
	public RechercheFam (FenetrePrincipale f) {
		
		
		this.f = f;
		
		ecouteur = new Ecouteur();
		
		setLayout(new GridLayout(5,7,20,20));  // ligne puis colonnes
		
		initialiserTableaux();
		
		add(new JLabel("Liste famille softwares"));
		liste = new JComboBox<Object>(tabFam);
		add(liste);
		
		ok = new JButton("OK");
		add(ok);
		ok.addActionListener(ecouteur);
		
		listeFam = new JTable();
		JScrollPane scrollIS = new JScrollPane(listeFam);
		add(scrollIS);
		
		
		
		
		
		
	}
	
		private void initialiserTableaux(){
		
			try {
			
				prepa = f.getCo().prepareStatement("SELECT libelle FROM famillesoftware");
				tabFam = AccessBDGen.creerListe1Colonne(prepa); // obtenir la liste des pcs
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	
	private class Ecouteur implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(ok)) {
				try {
					prepa = f.getCo().prepareStatement("SELECT IdFamSoft FROM famillesoftware WHERE libelle = ?");
					
					String famillechoisi;
					famillechoisi = liste.getSelectedItem().toString(); // on récupere l'element de l user a la coimbobox et on caste en string pour pas qu'il rale	
					prepa.setString(1, famillechoisi);
					
					int IDfamsoft = (int) AccessBDGen.creerListe1Colonne(prepa)[0]; // on prend le premeire element du tableau qui est renvoyé (façon racourci de prendre la premiere cellule du tableau et de la metre dans NEWID
							
					prepa = f.getCo().prepareStatement("SELECT software.nom, software.version, software.DateAcquisition FROM software " + 
							"RIGHT JOIN fournisseur " + 
							"ON software.CodeFourn = fournisseur.CodeFourn " + 
							"WHERE fournisseur.NomContactComm is null AND software.IdFamSoft = ?");
					
					prepa.setInt(1, IDfamsoft);
					tb = AccessBDGen.creerTableModel(prepa); 
					listeFam.setModel(tb);
					listeFam.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
					listeFam.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
					listeFam.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
					listeFam.setVisible(true);
					
					RechercheFam.this.repaint();
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
