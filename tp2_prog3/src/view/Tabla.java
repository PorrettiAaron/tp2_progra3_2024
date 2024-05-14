package view;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.*;

import model.Arista;
import model.Grafo;
import controller.*;

public class Tabla extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JFrame frame;
	
	public Tabla(Grafo grafo, List<Arista> agm) {
		SwingUtilities.invokeLater(() -> {
			frame = new JFrame("Tabla de Regiones del AGM");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(600, 300);
			frame.setLocationRelativeTo(null);
			Region reg = new Region(grafo, agm);
			List<Set<String>> regiones = reg.generarRegiones();
			String[] columnas = {"Region", "Vertices"};
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
			for (int i = 0; i < regiones.size(); i++) {
				Set<String> region = regiones.get(i);
				String verticesTexto = String.join(",\n ", region);
				tableModel.addRow(new Object[] { i + 1, verticesTexto });
			}
			table = new JTable(tableModel);
			
	        // Añadir un MouseListener para detectar clics en las filas
	        table.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 1) {
	                    JTable target = (JTable) e.getSource();
	                    int row = target.getSelectedRow();
	                    String col = (String) target.getValueAt(row, 1);
	                    JOptionPane.showMessageDialog(frame, col, "Region " + (row + 1) ,JOptionPane.INFORMATION_MESSAGE);
	                }
	            }
	        });
	    	table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    	table.getColumnModel().getColumn(1).setPreferredWidth(500);
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane);
			frame.setVisible(true);
		});
		

	}

}

