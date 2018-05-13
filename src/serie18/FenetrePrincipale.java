package serie18;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class FenetrePrincipale extends JFrame{

	private JMenuBar menuB;
	private JMenu accueil, modification, listing;
	private JMenuItem ajouter, supprimer, listingComplet, listingFamille, listingSection;
	private Accueil JPaccueil;
	private Container cont;
	private Ecouteur ecouteur;
	private Connection connection;
	
	public FenetrePrincipale(Connection connection){
	
		super("Gestion installations");
		
		this.connection = connection;
		ecouteur = new Ecouteur();  // si on initialise pas l'écouteur est null
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); // est une méthode héritée de JFrame
		
		menuB = new JMenuBar();
		setJMenuBar(menuB);
		
		
		
		accueil = new JMenu("Accueil");
		menuB.add(accueil);
		//accueil.addActionListener(ecouteur);
				
		modification = new JMenu("Modification");
		menuB.add(modification);
		
		listing = new JMenu("Listing");
		menuB.add(listing);
		
		ajouter = new JMenuItem("Ajouter");
		modification.add(ajouter);
		ajouter.addActionListener(ecouteur);
		
		supprimer = new JMenuItem("Supprimer");
		modification.add(supprimer);
		supprimer.addActionListener(ecouteur);
		
		listingComplet = new JMenuItem("Liste Complète");
		listing.add(listingComplet);
		listingComplet.addActionListener(ecouteur);
		
		listingFamille = new JMenuItem("Liste par Famille");
		listing.add(listingFamille);
		
		listingSection = new JMenuItem("Liste par Section");
		listing.add(listingSection);
		
		JPaccueil = new Accueil();
		cont = getContentPane(); // je récupere le conteneur de la fenetre dans laquel je suis
		cont.setLayout(new BorderLayout());
		cont.add(JPaccueil, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public Connection getCo() {
		return connection;
	}
	

	private class Ecouteur implements ActionListener{
		// objet evenement creer par java quand on appuie sur un boutton
		public void actionPerformed(ActionEvent e){  
	
			if (e.getSource().equals(ajouter)){
				cont.removeAll();
				Formulaire f = new Formulaire(FenetrePrincipale.this);  // on change le panneau courant pour lui en mettre un nouveau
				cont.add(f, BorderLayout.CENTER);
				cont.repaint();
				FenetrePrincipale.this.setVisible(true);
			}
			if (e.getSource().equals(listingComplet)){
				new ListeComplete(FenetrePrincipale.this); // creer une nouvelle fenetre qui a juste besoin de connection comme objet
			}
			if (e.getSource().equals(supprimer)){
				cont.removeAll();
				Suppression supprimer = new Suppression(FenetrePrincipale.this);
				cont.add(supprimer, BorderLayout.CENTER);
				cont.repaint();
				FenetrePrincipale.this.setVisible(true);
			}
			/* if (e.getSource().equals(accueil)){
				cont.removeAll();    // effacer les anciens affichages (cont = endroit ou on stock l'ensemble des panneau et autres éléments graphiques)
				Accueil accueil = new Accueil();// initialise la variable av le nouveau panneau a afficher
				cont.add(accueil, BorderLayout.CENTER);// le réajouter au panneau
				cont.repaint(); // force la visualisation graphique du panneau (repeindre le containaire)
				FenetrePrincipale.this.setVisible(true);
			}
			*/
			
			
			
		}	
	}		
}

