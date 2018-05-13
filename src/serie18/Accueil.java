package serie18;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class Accueil extends JPanel{

	private JLabel accueil0, accueil1, accueil2, accueil3, accueil4;
	
	public Accueil(){
		
		
		
		// question a poser cumuler JLabel ou /n ??  // possibilité de mettre tout dans le add ??
		accueil0 = new JLabel("Bienvenue sur cette application.");
		accueil1 = new JLabel("Je vous propose de réaliser certaines tâches comme:");
		accueil2 = new JLabel("Lister différents contenus selon certains critères");	
		accueil3 = new JLabel("Ajouter ou supprimer différentes informations");
		accueil4 = new JLabel("Bonne visite !");	
		setLayout(new GridLayout(15,1));
		add(accueil0);
		add(accueil1);
		add(accueil2);
		add(accueil3);
		add(accueil4);
		
		
		
	}
}
