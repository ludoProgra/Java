package serie18;

import javax.swing.table.*;
import java.util.*;



/* Tout objet de cette classe est un mod?le de donn?es,
 * ? savoir un ensemble de lignes et de colonnes sous forme d'un tableau ? deux dimensions.
 * Un mod?le de donn?es contient ?galement le nom des colonnes.
 * Un mod?le de donn?es peut ?tre affich? dans une JTable.
 */
public class TableModelGen extends AbstractTableModel
    {// Liste des noms de colonnes
     ArrayList <String> nomCol = new ArrayList <String>();
     // Donn?es sous forme d'une liste ? deux dimensions qui est une liste d'Objects
     ArrayList <Object> contenu = new ArrayList<Object>();
     // Liste d'objets types (pour retrouver le type des colonnes)
     ArrayList <Object> objetTypes = new ArrayList <Object>();

     TableModelGen(ArrayList <String> col, ArrayList <Object> lig, ArrayList <Object> types)
     {nomCol = col;
      contenu = lig;
      objetTypes = types;
     }

     // Retourne le nombre de colonnes
     public int getColumnCount()
     {return nomCol.size();
     }

     // Retourne le nombre de lignes
     public int getRowCount()
     {return contenu.size();
     }

     // Retourne le nom de la colonne num?ro "col"
     public String getColumnName(int col)
     {return  nomCol.get(col);
     }

     // Retourne la valeur de la cellule ? l'intersection de la ligne num?ro "row" et de la colonne num?ro "col"
     public Object getValueAt(int row, int col)
     { ArrayList vect = (ArrayList)(contenu.get(row));
       return vect.get(col);
     }

     /* Retourne la classe correspondant aux valeurs de la colonne num?ro "c".
      * Cette m?thode est appel?e entre autres lors de l'affichage des colonnes dans la JTable 
      * afin d'afficher le bon type des colonnes (num?riques align?s ? droite, 
      * cha?nes de caract?res align?es ? gauche, bool?ens sous forme de cases ? cocher, date avec format de date, ...)
      */
     public Class getColumnClass (int c)
     {return (objetTypes.get(c)).getClass();
     }

    }

