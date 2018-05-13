package serie18;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

;

public class PanneauDate extends JPanel{

	private JComboBox<Object> jour, mois, annee;
	private Object[] tabJour, tabMois, tabAnnee;
	private Ecouteur ecouteur;
	
	public PanneauDate() {
		
		
		setLayout(new GridLayout(3,1));
		ecouteur = new Ecouteur();
		
		tabJour = new Object[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		tabMois = new Object[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		tabAnnee = new Object[]{2016,2017,2018,2019,2020,2021,2022};
		
		add(new JLabel("jour"));
		jour = new JComboBox<Object>(tabJour);
		add(jour);
		
		add(new JLabel("mois"));
		mois = new JComboBox<Object>(tabMois);
		add(mois);
		
		add(new JLabel("annee"));
		annee = new JComboBox<Object>(tabAnnee);
		add(annee);
		
		
		
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		mois.setEnabled(enabled);
		jour.setEnabled(enabled);
		annee.setEnabled(enabled);
	}
	
	public boolean dateValide() { // parse lance une exeption si date pas valide genre 31 fevrier
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf.setLenient(false);
		try {
			String dateToValidate;
			dateToValidate = jour.getSelectedItem().toString() + "-" + mois.getSelectedItem().toString() + "-" + annee.getSelectedItem().toString();
			Date date = sdf.parse(dateToValidate);
			return true;
		}catch(ParseException e){
			return false;
		}
	}
	
	private class Ecouteur implements ActionListener{
		// objet evenement creer par java quand on appuie sur un boutton
		public void actionPerformed(ActionEvent e){  
			
		}
		
		
		
		
		}

	public String getText() {
		// TODO Auto-generated method stub
		return annee.getSelectedItem().toString() + "-" + mois.getSelectedItem().toString() + "-" + jour.getSelectedItem().toString();
	}
	
	
	
	
	
	
	
}
