package view;

import java.util.Map.Entry;
import javax.swing.JFrame;
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
			frame.setSize(400, 300);
			frame.setLocationRelativeTo(null);
			Grafo g = grafo;
			String[] columnas = { "Vertice", "Adyacencias" };
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
			for (Entry<String, String> entry : g.imprimirGrafo().entrySet()) {
				tableModel.addRow(new Object[] { entry.getKey(), entry.getValue() });
			}
			JTable table = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane);

			frame.setVisible(true);

		});
	}

}
