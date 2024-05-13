package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.*;
import model.Grafo;

public class TablaGrafo extends JFrame {

	private static final long serialVersionUID = 1L;

	public TablaGrafo(Grafo grafo) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Tabla del Grafo");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(600, 300);
			frame.setLocationRelativeTo(null);
			Grafo g = grafo;
			String[] columnas = { "Vertice", "Adyacencias" };
		
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
			for (Entry<String, String> entry : g.imprimirGrafo().entrySet()) {
				tableModel.addRow(new Object[] { entry.getKey(), entry.getValue() });
			}
			JTable table = new JTable(tableModel);
	        // Añadir un MouseListener para detectar clics en las filas
	        table.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getClickCount() == 1) {
	                    JTable target = (JTable) e.getSource();
	                    int row = target.getSelectedRow();
	                    String vertice = (String) target.getValueAt(row, 0);
	                    String col = (String) target.getValueAt(row, 1);
	                    JOptionPane.showMessageDialog(frame, col, "Adyacencias de " + vertice ,JOptionPane.INFORMATION_MESSAGE);
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
