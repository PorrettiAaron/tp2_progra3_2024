package view;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.AgmPrim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.Integer;
import java.lang.NumberFormatException;

import model.Arista;
import model.Grafo;

public class Mapa {
	private JFrame frame;
	private JMapViewer mapViewer;
	private Grafo g;
	private Map<String, Coordinate> points;
	private List<MapPolygonImpl> mapLines = new ArrayList<>();

	/**
	 * @wbp.parser.entryPoint
	 */
	public Mapa() {
		initialize();
	}

	private void initialize() {
		g = new Grafo();
		points = new HashMap<>();
		frame = new JFrame("Map Marker");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);

		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Agregue sus vértices... ");
		titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		titlePanel.add(titleLabel);

		JLabel titleLabel2 = new JLabel("Para agregar un vertice haz click en el mapa ");
		titleLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		titlePanel.add(titleLabel2);

		JPanel mapPanel = new JPanel(new BorderLayout());
		mapViewer = new JMapViewer();
		mapViewer.setDisplayPosition(new Coordinate(0, 0), 3);
		mapPanel.add(mapViewer, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();

		JButton similarityButton = new JButton("Agregar Índice de Similaridad");
		buttonPanel.add(similarityButton);

		JButton btnCrearRegiones = new JButton("Crear regiones");
		buttonPanel.add(btnCrearRegiones);

		JButton btnVerGrafo = new JButton("Ver Grafo");
		buttonPanel.add(btnVerGrafo);

		JButton btnEliminarArista = new JButton("Eliminar Índice de Similaridad");
		buttonPanel.add(btnEliminarArista);

		mapViewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del punto:");

				if (points.containsKey(name)) {
					JOptionPane.showMessageDialog(frame, "Ya existe un punto con ese nombre.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					g.agregarVertice(name);
					// Obtener las coordenadas del punto donde se hizo clic
					Point point = e.getPoint();
					ICoordinate iCoord = mapViewer.getPosition(point.x, point.y);
					Coordinate coord = new Coordinate(iCoord.getLat(), iCoord.getLon());

					points.put(name, coord);
					// Crear un marcador con etiqueta para mostrar en el mapa
					MapMarkerDotWithLabel marker = new MapMarkerDotWithLabel(coord, name);
					mapViewer.addMapMarker(marker);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}
		});

		similarityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JDialog dialog = new JDialog(frame, "Seleccionar puntos", true);
				dialog.getContentPane().setLayout(new GridLayout(4, 2));
				
				List<String> pointNames = new ArrayList<>(points.keySet());
				
				JComboBox<String> pointComboBox1 = new JComboBox<>(pointNames.toArray(new String[0]));
				dialog.getContentPane().add(new JLabel("Punto 1:"));
				dialog.getContentPane().add(pointComboBox1);
				
				JComboBox<String> pointComboBox2 = new JComboBox<>(pointNames.toArray(new String[0]));
				dialog.getContentPane().add(new JLabel("Punto 2:"));
				dialog.getContentPane().add(pointComboBox2);
				
				JTextField pesoField = new JTextField();
				dialog.getContentPane().add(new JLabel("Peso:"));
				dialog.getContentPane().add(pesoField);
				
				JButton confirmButton = new JButton("Confirmar");
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String punto1 = (String) pointComboBox1.getSelectedItem();
						String punto2 = (String) pointComboBox2.getSelectedItem();
						String textoPeso = pesoField.getText();
						try {
							g.agregarArista(punto1, punto2, Integer.parseInt(textoPeso));
							JOptionPane.showMessageDialog(frame,
									"Punto 1: " + punto1 + "\n" + "Punto 2: " + punto2 + "\n" + "Peso: " + textoPeso);
							dibujarAristas();
							dialog.dispose();
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, "Por favor ingrese un valor numérico para el peso.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, ex.getMessage());
						}
					}
				});
				dialog.getContentPane().add(confirmButton);
				dialog.setSize(300, 150);
				dialog.setLocationRelativeTo(frame);
				dialog.setVisible(true);
			}
		});

		btnEliminarArista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(frame, "Seleccione su punto", true);
				dialog.getContentPane().setLayout(new GridLayout(4, 2));
				List<String> pointNames = new ArrayList<>(points.keySet());
				JComboBox<String> pointComboBox1 = new JComboBox<>(pointNames.toArray(new String[0]));
				dialog.getContentPane().add(new JLabel("Punto 1:"));
				dialog.getContentPane().add(pointComboBox1);
				JComboBox<String> pointComboBox2 = new JComboBox<>(pointNames.toArray(new String[0]));
				dialog.getContentPane().add(new JLabel("Punto 2:"));
				dialog.getContentPane().add(pointComboBox2);
				JButton confirmButton = new JButton("Confirmar");
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String punto1 = (String) pointComboBox1.getSelectedItem();
						String punto2 = (String) pointComboBox2.getSelectedItem();

						try {
							g.eliminarAristaEntreVertices(punto1, punto2);
							g.eliminarAristaEntreVertices(punto2, punto1);
							JOptionPane.showMessageDialog(frame,
									"Se eliminó la arista entre punto 1: " + punto1 + "\n" + " y punto 2: " + punto2);
							dibujarAristas();
							dialog.dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, ex.getMessage());
						}
					}
				});
				dialog.getContentPane().add(confirmButton);

				// Mostrar el diálogo
				dialog.setSize(300, 150);
				dialog.setLocationRelativeTo(frame);
				dialog.setVisible(true);
			}
		});

		btnCrearRegiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String regiones = JOptionPane.showInputDialog(frame, "Ingrese la cantidad de regiones");
					AgmPrim agm = new AgmPrim(g);
					Tabla tabla = new Tabla(g, agm.procesoAgm(Integer.parseInt(regiones)));
					tabla.setVisible(true);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Por favor ingrese un valor numérico la cantidad de regiones");

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});

		btnVerGrafo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					TablaGrafo tablaGrafo = new TablaGrafo(g);
					tablaGrafo.setVisible(true);

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}

		});
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
		frame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	private void dibujarAristas() {
		for (MapPolygonImpl line : mapLines) {
			mapViewer.removeMapPolygon(line);
		}
		mapLines.clear();
		List<Arista> todasAristas = g.obtenerTodasAristas();
		for (Arista arista : todasAristas) {
			String punto1 = arista.getOrigen();
			String punto2 = arista.getDestino();
			Coordinate coordenada1 = points.get(punto1);
			Coordinate coordenada2 = points.get(punto2);
			if (coordenada1 != null && coordenada2 != null) {
				List<Coordinate> coordinates = new ArrayList<>();
				coordinates.add(coordenada1);
				coordinates.add(coordenada2);
				coordinates.add(coordenada1);
				MapPolygonImpl line = new MapPolygonImpl(coordinates);
				mapViewer.addMapPolygon(line);
				mapLines.add(line);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Mapa::new);
	}
}
