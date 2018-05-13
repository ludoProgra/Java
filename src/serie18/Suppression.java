package serie18;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;



public class Suppression extends JPanel{

	private FenetrePrincipale f;
	private JButton valider, supprimer, annuler;
	private PreparedStatement prepa;
	private JTable table;
	private Ecouteur ecouteur;
	private PanneauDate pdate;
	private Container cont;
	private TableModelGen liste;
	
	
	public Suppression(FenetrePrincipale f){
		
		this.f = f; // save pour utiliser partout dans la classe
		
		
		
		ecouteur = new Ecouteur();  // si on initialise pas l'écouteur est null
		setLayout(new GridLayout(8,7));
		
		
		add(new JLabel("Choisir date"));
		pdate = new PanneauDate();
		add(pdate);
		
		
		valider = new JButton("valider");
		add(valider);
		valider.addActionListener(ecouteur);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
		table.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
		JScrollPane scroll = new JScrollPane(table);
		add(scroll);
	
		supprimer = new JButton("supprimer");
		add(supprimer);
		supprimer.addActionListener(ecouteur);
		
	}
	
	private class Ecouteur implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource().equals(valider)){
				if(pdate.dateValide()) {
					String dateString = pdate.getText();
					try {
						prepa = f.getCo().prepareStatement("SELECT * FROM installation WHERE refprocedureinstallation is null AND dateinstallation > ?");
						prepa.setDate(1, Date.valueOf(dateString));
						liste = AccessBDGen.creerTableModel(prepa); 
											
						table.setModel(liste);
						table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);   // éviter les problemes au niveau des lignes pour les user pas de multiple select
						table.setRowHeight(50);   // hauteur de la ligne 50 pixel c'est bien
						table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // taille
						table.setVisible(true);
						
						Suppression.this.repaint();
						f.repaint();
						f.setVisible(true);
						
											
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Date invalide","Erreur d'encodage", JOptionPane.ERROR_MESSAGE);
					
				}
				// transforme la string en date (voir precedent)
				// requete prepa qui va selectionnner toute les installation SELEC * FROM installation WHERE dateInstallation > ? 
			}
			
			if(e.getSource().equals(supprimer)) {
				int indexChoisi;
				indexChoisi = table.getSelectedRow();
				if(indexChoisi == -1)
					return;
				
				int idSupprimer = (int) table.getValueAt(indexChoisi, 0);
				System.out.println(idSupprimer);
				try {
					prepa = f.getCo().prepareStatement("DELETE FROM installation WHERE idinstallation = ?");
					prepa.setInt(1, idSupprimer);	
					prepa.executeUpdate();
					liste.contenu.remove(indexChoisi);
					Suppression.this.repaint();
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
