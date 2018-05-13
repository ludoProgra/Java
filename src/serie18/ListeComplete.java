package serie18;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;



public class ListeComplete extends JFrame{

	private FenetrePrincipale f;
	private Container cont;
	private JTable tableIS, tablePR, tableS, tableAE, tableE, tableF; //composant swing qui représente une table bien pratique pour afficher des elements d un tableau // facile puisque tablemodelgen converti les info en Array qu'on peut recup avec JTABLE
	private PreparedStatement prepa;

	public ListeComplete(FenetrePrincipale f){ //la classe formulaire recoit en argument "connection" pour se connecter a la BD une fois que le formulaire se lance
		
		super("Liste complète");
		
		this.f = f;	// sauvegarde de la varibale fenetre du coup
		
		setBounds(250,250,800,800);
		
		cont = getContentPane(); // méthode jframe qui récupere le containeur courant afin d y ajouter des elements apres
		cont.setLayout(new GridLayout(5,1)); 
		
		
		try {
			
			
			//affichage liste INSTALLATION
			
			prepa = f.getCo().prepareStatement("SELECT * FROM installation");
			TableModelGen listeIS = AccessBDGen.creerTableModel(prepa); 
			tableIS = new JTable(listeIS);
			
			tableIS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
			tableIS.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
			tableIS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
			
			JScrollPane scrollIS = new JScrollPane(tableIS);
			cont.add(scrollIS);
			
			//affichage liste PROFESSEUR
			
			prepa = f.getCo().prepareStatement("SELECT * FROM professeur");
			TableModelGen listePR = AccessBDGen.creerTableModel(prepa); 
			tablePR = new JTable(listePR);
			
			tablePR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
			tablePR.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
			tablePR.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
			
			JScrollPane scrollPR = new JScrollPane(tablePR);
			cont.add(scrollPR);
			
			//affichage liste SECTION
			
			prepa = f.getCo().prepareStatement("SELECT * FROM section");
			TableModelGen listeS = AccessBDGen.creerTableModel(prepa); 
			tableS = new JTable(listeS);
			
			tableS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
			tableS.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
			tableS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
			
			JScrollPane scrollS = new JScrollPane(tableS);
			cont.add(scrollS);
			
			//affichage liste
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setVisible(true);
	
		
	
	}
}
