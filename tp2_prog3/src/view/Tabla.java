package view;

import java.util.List;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.*;

import model.Arista;
import model.Grafo;
import controller.*;

public class Tabla extends JFrame {

	private static final long serialVersionUID = 1L;

	public Tabla(Grafo grafo, List<Arista> agm) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Tabla de Regiones del AGM");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(400, 300);
			frame.setLocationRelativeTo(null);
			Region reg = new Region(grafo, agm);
			List<Set<String>> regiones = reg.generarRegiones();
			String[] columnas = { "Región", "Vértices" };
			DefaultTableModel tableModel = new DefaultTableModel(columnas, 0);
			for (int i = 0; i < regiones.size(); i++) {
				Set<String> region = regiones.get(i);
				String verticesTexto = String.join(", ", region);
				tableModel.addRow(new Object[] { i + 1, verticesTexto });
			}
			JTable table = new JTable(tableModel);
			JScrollPane scrollPane = new JScrollPane(table);
			frame.add(scrollPane);
			frame.setVisible(true);

		});
	}

}
