package serie18;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Formulaire extends JPanel{

	
	private JTextField txtInstallation;
	private JCheckBox typeInstallation;  // oui non ? qu'attendez vous ?
	private JTextField commentaires, dureeInstallation, reference, dateInstallation;
	private JRadioButton aPrevoir, termine, enCours;
	private ButtonGroup validation;
	private JComboBox<Object> codeSW, matricule, codeOS; 
	private Object[] tabCodeSW, tabMatricule, tabCodeOS;
	private JButton insertion, reset;
	private PreparedStatement preparation;
	private Ecouteur ecouteur;
	private Container cont;
	private PanneauDate p;
	private PanneauDate pdate;
	private FenetrePrincipale f;
	
	public Formulaire(FenetrePrincipale f){ //la classe formulaire recoit en argument "connection" pour se connecter a la BD une fois que le formulaire se lance
		
		this.f = f;
		
		
		setLayout(new GridLayout(15,2));
		ecouteur = new Ecouteur();
		
		add(new JLabel("Veuilliez choisir une date"));
		/*txtInstallation = new JTextField();
		add(txtInstallation);*/
		
		
		pdate = new PanneauDate();
		add(pdate);
			

		add(new JLabel("type d'installation standard?"));
		typeInstallation = new JCheckBox();
		add(typeInstallation); // j'ajoute au panel typeInstallation qui est de type CheckBox
		
		add(new JLabel("Commentaires :"));
		commentaires = new JTextField();
		add(commentaires);
		
		add(new JLabel("Quel est la durée de l'installation ? (Obligatoire):"));
		dureeInstallation = new JTextField();
		add(dureeInstallation);
		
		add(new JLabel("Quel est la référence de l'installation ? :"));
		reference = new JTextField();
		add(reference);
		
		add(new JLabel("Le statut de l'installation est :"));
		validation = new ButtonGroup();
		aPrevoir = new JRadioButton("A prévoir");
		validation.add(aPrevoir);
		add(aPrevoir);
		aPrevoir.addActionListener(ecouteur);
		termine = new JRadioButton("Terminé");
		validation.add(termine);
		add(termine);
		termine.addActionListener(ecouteur);
		enCours = new JRadioButton("En cours");
		validation.add(enCours);
		add(enCours);
		enCours.addActionListener(ecouteur);
		enCours.setSelected(true);
		
		add(new JLabel("Date d'installation"));
		/*dateInstallation = new JTextField();
		add(dateInstallation);*/
		
				
		p = new PanneauDate(); // désactiver par défaut
		add(p);
		p.setEnabled(false);
		initialiserTableaux(); //les jcombobox on besoin  de tableau pour afficher les contenu de la DB
		
		add(new JLabel("Code Software"));
		codeSW = new JComboBox<Object>(tabCodeSW);
		add(codeSW);
		
		add(new JLabel("Matricule"));
		matricule = new JComboBox<Object>(tabMatricule);
		add(matricule);
		
		add(new JLabel("Code Operating System"));
		codeOS = new JComboBox<Object>(tabCodeOS);
		add(codeOS);
		
		insertion = new JButton("Insérer");
		add(insertion);
		insertion.addActionListener(ecouteur);
		
		reset = new JButton("Réinitialiser");
		add(reset);
		reset.addActionListener(ecouteur);
		
		
		
	
		
		
		
	}
	
	private void initialiserTableaux(){
		
		try {
			
			preparation = f.getCo().prepareStatement("SELECT CodeSoftware FROM Installation");
			tabCodeSW = AccessBDGen.creerListe1Colonne(preparation); // obtenir la liste des pcs
			
			preparation = f.getCo().prepareStatement("SELECT Matricule FROM Installation");
			tabMatricule = AccessBDGen.creerListe1Colonne(preparation); // obtenir la liste des types
			
			preparation = f.getCo().prepareStatement("SELECT CodeOS FROM Installation");
			tabCodeOS = AccessBDGen.creerListe1Colonne(preparation); //obtenir la liste des fourni interv
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	private class Ecouteur implements ActionListener{
		// objet evenement creer par java quand on appuie sur un boutton
		public void actionPerformed(ActionEvent e){  
	
			if (e.getSource().equals(insertion)){
				
				if(valide()) {
					try {
						preparation = f.getCo().prepareStatement("SELECT max(IdInstallation) FROM Installation");
						int newID = (int) AccessBDGen.creerListe1Colonne(preparation)[0]; // on prend le premeire element du tableau qui est renvoyé (façon racourci de prendre la premiere cellule du tableau et de la metre dans NEWID
						newID++; // incrémenter l ID sinon erreur (2x un enregistrement avec la même clé sa pète !!!
						String dateAinserer = pdate.getText();// on récupere la date que l'utlisateur a mit
						preparation = f.getCo().prepareStatement("INSERT INTO Installation ("
								+ "IdInstallation, "
								+ "DateInstallation, "
								+ "TypeInstallation, "
								+ "Commentaires, "
								+ "DureeInstallation, "
								+ "RefProcedureInstallation, "
								+ "Validation, "
								+ "DateValidation, "
								+ "CodeSoftware, "
								+ "Matricule, "
								+ "CodeOS)VALUES (?,?,?,?,?,?,?,?,?,?,?)");
						preparation.setInt(1, newID);
						
						preparation.setDate(2, Date.valueOf(dateAinserer));
						
						boolean type;
						type = typeInstallation.isSelected(); //true if selected donc case coché or false if not coché)
						preparation.setBoolean(3, type); // on récupère le type
						
						String com;
						com = commentaires.getText(); // on va rechercher ce que l user a inserer dans commantaires
						if(com.equals("")){
							preparation.setString(4, null); // soit on recupere rien
						}else{
							preparation.setString(4, com); // soit on récupere le commantaire com
						}
						
						int temps;
						temps = Integer.parseInt(dureeInstallation.getText()); // fonction spéciale qui caste le string en int car dureeinstalle c'est un jtextfield et je lui donne en argument la séquence
						preparation.setInt(5, temps);
						
						String ref;
						ref = reference.getText();
						if(ref.equals("")){
							preparation.setString(6, null);
						}else{
							preparation.setString(6, ref);
						}
						
						if(aPrevoir.isSelected()){  // test dans un buttungroup pas besoin de else puisque on ne peut selectionner qu'un seul champ
							preparation.setString(7, "à prévoir");
							String dateIns;
							dateIns = p.getText(); // on récupere la date de l'user
							Date dateInsFor;
							dateInsFor = Date.valueOf(dateIns); // on converti la date de l'user au format de la méthode que propose date.valueof
							preparation.setDate(8, dateInsFor); // on envoie la date
						}if(termine.isSelected()){
							preparation.setString(7, "terminé");
							preparation.setDate(8, null); // aucune date a prévoir pour ce champ et l'autre
						}if(enCours.isSelected()){
							preparation.setString(7, "en cours");
							preparation.setDate(8, null);
						}
						
						String codeSWchoisi;
						codeSWchoisi = codeSW.getSelectedItem().toString(); // on récupere l'element de l user a la coimbobox et on caste en string pour pas qu'il rale	
						preparation.setString(9, codeSWchoisi);
						
						String mat;
						mat = matricule.getSelectedItem().toString(); // on récupere l'element de l user a la coimbobox et on caste en string pour pas qu'il rale	
						preparation.setString(10, mat);
						
						String oscode;
						oscode = codeOS.getSelectedItem().toString(); // on récupere l'element de l user a la coimbobox et on caste en string pour pas qu'il rale	
						preparation.setString(11, oscode);
						
						preparation.executeUpdate(); // exécute les preparation
						preparation.close();
						JOptionPane.showMessageDialog(null, "Vous venez d'encoder les informations nécessaires", "Félicitation", JOptionPane.INFORMATION_MESSAGE);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas rempli correctement les champs demandés, vérifiez les dates, et autres contenus","Erreur", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
			if (e.getSource().equals(reset)){
				for(Component control : Formulaire.this.getComponents())  // remet tout les jtext du pannel a rien et pareilp our comboBox
				{//fonction qui prend tout les element dans le pannel courant et qui les renvoie sous la forme d un tableau et le for en racourci pour boucler sur tout les elements
				    if(control instanceof JTextField) // control représente l'element sur lequel on est
				    {									//https://stackoverflow.com/questions/21524117/how-to-clear-all-input-fields-within-a-jpanel
				        JTextField ctrl = (JTextField) control;
				        ctrl.setText("");
				    }
				    else if (control instanceof JComboBox)
				    {
				        JComboBox ctr = (JComboBox) control;
				        ctr.setSelectedIndex(0);
				    }
				    else if (control instanceof JCheckBox)
				    {
				    	JCheckBox ctr = (JCheckBox) control;
				        ctr.setSelected(false);
				    }
				}
				enCours.setSelected(true);
				p.reinitialiser();
				pdate.reinitialiser();
			}
			if (e.getSource().equals(aPrevoir)){ // si l user a cliquer sur a prévoir
				p.setEnabled(true);	
			}if (e.getSource().equals(termine)){
				p.setEnabled(false);
			}if (e.getSource().equals(enCours)){
				p.setEnabled(false);
			}
		}

		private boolean valide() {
			if(dureeInstallation.getText().equals("")) {
				return false;
			}
			if(isNumber(dureeInstallation.getText()) == false) {
				return false;
			}
					
			if(pdate.dateValide() == false) {
				return false;
			}
			return p.dateValide();
			}
		
		private boolean isNumber(String string) {
			try {
				Integer.parseInt(string);
				return true; // l user a bien mit un integer dans dureeinstallation
			}catch(NumberFormatException e) {
				return false;  // si on est dans le catch c'est bien un string qui est placé a la place d un int
			}
		}
	}
}
