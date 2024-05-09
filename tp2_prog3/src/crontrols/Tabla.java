package crontrols;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import model.Grafo;

public class Tabla extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;


	public Tabla(Grafo grafo) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 819, 544);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(339, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		setTitle("Tabla de AGM y Regiones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		// Crear una tabla con las columnas necesarias (por ejemplo, "Nodo", "Región", etc.)
		String[] columnNames = {"Nodo", "Región"};
		Object[][] data = {
				{"A", "Región 1"},
				{"B", "Región 2"},
				// Agrega más filas de datos aquí...
		};

		//JTable table = new JTable(data, columnNames);
		//table.setFillsViewportHeight(true);

		//JScrollPane scrollPane = new JScrollPane(table);
		//add(scrollPane);

		pack();



	}

}


