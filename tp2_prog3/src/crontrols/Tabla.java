package crontrols;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.*;

import model.Arista;
import model.Grafo;
import controller.*;


public class Tabla extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;


	public Tabla(Grafo grafo, List<Arista> agm ) {
		SwingUtilities.invokeLater(() -> {		
			JFrame frame = new JFrame("Tabla de Regiones del AGM");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 300);
			
			Region reg = new Region(grafo, agm);
			List<Set<String>> regiones = reg.generarRegiones();

			//genero el modelo de la tabla
			String[] columnas = {"Región", "Vértices"};
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
			//lleno el modelo
			for (int i = 0; i < regiones.size(); i++) {
				Set<String> region = regiones.get(i);
				String verticesTexto = String.join(", ", region);
				tableModel.addRow(new Object[]{i + 1, verticesTexto});
			}
			// Crear la tabla y asignarle el modelo
			JTable table = new JTable(tableModel);

			// Crear un JScrollPane y agregar la tabla a él
			JScrollPane scrollPane = new JScrollPane(table);

			// Agregar el JScrollPane al JFrame
			frame.add(scrollPane);

			frame.setVisible(true);

		});
	}


}


