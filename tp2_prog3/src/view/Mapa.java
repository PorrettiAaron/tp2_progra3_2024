package view;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.AgmPrim;
import exceptions.AristaInexistenteException;

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

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import model.Arista;
import model.Grafo;

public class Mapa implements Serializable {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JMapViewer mapViewer;
	private Grafo g;
	private Map<String, Map<String, Double>> points;
	private List<MapPolygonImpl> mapLines = new ArrayList<>();

	/**
	 * @wbp.parser.entryPoint
	 */
    public Mapa() {
        g = new Grafo();
        points = new HashMap<>();
        frame = new JFrame("Creando Regiones");
        mapViewer = new JMapViewer();
        
        this.leerSerializacion();
        
        initialize();
    }

	private void initialize() {
		
		mostrarMensajeDeBienvenida();
		inicializarFrame();
		dibujarAristas();
		
		// ----------- Agregar botones y mapa --------------
		
		JPanel mapPanel = new JPanel(new BorderLayout());
		mapViewer.setDisplayPosition(new Coordinate(-38.4161, -63.6167), 4);
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

		
		// ----------- Manejo de clicks en el mapa para agregar vertices --------------
		
		mapViewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del punto:");
				validarPunto(name);

				try {
					g.agregarVertice(name);
					agregarPuntoAlMapa(e, name);
				}
				
				catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}

		});
		
		
		// ----------- Manejo de boton para agregar indice de similaridad --------------

		similarityButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				// ----------- Agregar botones a la ventana de dialogo --------------	
				
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
				
				
				// ----------- Funcionalidad de agregar la arista  --------------
				
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							agregarAristaAlGrafoYMapa(pointComboBox1, pointComboBox2, pesoField);
							dialog.dispose();
						} 
						
						catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(frame, "Por favor ingrese un valor numérico para el peso.");
						} 
						
						catch (Exception ex) {
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

		
		// ----------- Manejo de boton para eliminar indice de similaridad --------------
		
		btnEliminarArista.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// ----------- Agregar botones a la ventana de dialogo --------------	
				
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
				
				
				// ----------- Funcionalidad de eliminar la arista  --------------
				
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						try {
							eliminarAristaDelGrafoYMapa(pointComboBox1, pointComboBox2);
							dialog.dispose();
						} 
						
						catch (Exception ex) {
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

		
		// ----------- Manejo de boton para crear regiones --------------
		
		btnCrearRegiones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String regiones = JOptionPane.showInputDialog(frame, "Ingrese la cantidad de regiones");
					AgmPrim agm = new AgmPrim(g);
					Tabla tabla = new Tabla(g, agm.procesoAgm(Integer.parseInt(regiones)));
					tabla.setVisible(true);
				} 
				
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Por favor ingrese un valor numérico la cantidad de regiones");

				} 
				
				catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});

		
		// ----------- Manejo de boton para ver aristas y pesos  --------------
		
		btnVerGrafo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					TablaGrafo tablaGrafo = new TablaGrafo(g);
					tablaGrafo.setVisible(true);
				} 
				
				catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}

		});

		
		frame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}



	private void inicializarFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		
		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Agregue sus vértices... ");
		titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		titlePanel.add(titleLabel);		
		frame.getContentPane().add(titlePanel, BorderLayout.NORTH);

	}

	
	private void mostrarMensajeDeBienvenida() {
        String mensajeBienvenida = "Bienvenido al programa Disenando regiones.\n\n" +
                "Para marcar un punto en el mapa, simplemente haz clic en la ubicación deseada.\n" +
                "Luego, se te pedirá que ingreses un nombre para el punto.\n" +
                "Puedes marcar varios puntos de esta manera.\n\n" +
                "Para generar regiones, todos los puntos deben estar conectados \npor al menos un índice de similitud.\n" +
                "Asegúrate de haber marcado todos los puntos que deseas incluir en las regiones.\n\n" +
                "Haz clic en \"Comenzar\" para empezar.";

        JOptionPane.showMessageDialog(null, mensajeBienvenida, "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	private void validarPunto(String name) {
		if (points.containsKey(name)) {
			JOptionPane.showMessageDialog(frame, "Ya existe un punto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	
	private void agregarPuntoAlMapa(MouseEvent e, String name) {
		Point point = e.getPoint();
		ICoordinate iCoord = mapViewer.getPosition(point.x, point.y);
		double lat = iCoord.getLat();
        double lon = iCoord.getLon();

        Map<String, Double> coord = new HashMap<>();
        coord.put("lat", lat);
        coord.put("lon", lon);

        points.put(name, coord);
        
        MapMarkerDotWithLabel marker = new MapMarkerDotWithLabel(new Coordinate(lat, lon), name);
		mapViewer.addMapMarker(marker);
		
	}
	
	
	private void agregarAristaAlGrafoYMapa(JComboBox<String> pointComboBox1, JComboBox<String> pointComboBox2, JTextField pesoField) {
		String punto1 = (String) pointComboBox1.getSelectedItem();
		String punto2 = (String) pointComboBox2.getSelectedItem();
		String textoPeso = pesoField.getText();
		
		g.agregarArista(punto1, punto2, Integer.parseInt(textoPeso));
		JOptionPane.showMessageDialog(frame, "Punto 1: " + punto1 + "\n" + "Punto 2: " + punto2 + "\n" + "Peso: " + textoPeso);
		dibujarAristas();
	}

	
	private void eliminarAristaDelGrafoYMapa(JComboBox<String> pointComboBox1, JComboBox<String> pointComboBox2) throws AristaInexistenteException {
		String punto1 = (String) pointComboBox1.getSelectedItem();
		String punto2 = (String) pointComboBox2.getSelectedItem();
		g.eliminarAristaEntreVertices(punto1, punto2);
		g.eliminarAristaEntreVertices(punto2, punto1);
		JOptionPane.showMessageDialog(frame, "Se eliminó la arista entre punto 1: " + punto1 + "\n" + " y punto 2: " + punto2);
		dibujarAristas();
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

            Map<String, Double> coord1 = points.get(punto1);
            Map<String, Double> coord2 = points.get(punto2);

            if (coord1 != null && coord2 != null) {
                List<Coordinate> coordinates = new ArrayList<>();
                coordinates.add(new Coordinate(coord1.get("lat"), coord1.get("lon")));
                coordinates.add(new Coordinate(coord2.get("lat"), coord2.get("lon")));
                coordinates.add(new Coordinate(coord1.get("lat"), coord1.get("lon")));

                MapPolygonImpl line = new MapPolygonImpl(coordinates);
                mapViewer.addMapPolygon(line);
                mapLines.add(line);
            }
        }
    }
	
	
	private void dibujarPuntos() {
	    for (Map.Entry<String, Map<String, Double>> entry : points.entrySet()) {
	        String name = entry.getKey();
	        Map<String, Double> coord = entry.getValue();
	        double lat = coord.get("lat");
	        double lon = coord.get("lon");
	        
	        MapMarkerDotWithLabel marker = new MapMarkerDotWithLabel(new Coordinate(lat, lon), name);
	        mapViewer.addMapMarker(marker);
	    }
	}
	
	
	public void leerSerializacion() {
    	Mapa ms = null;
  	
        try {
        	FileInputStream fis = new FileInputStream("mapa.txt");
        	ObjectInputStream in = new ObjectInputStream(fis);
        	ms = (Mapa) in.readObject();
        	this.g = ms.g;
        	this.points = ms.points;
        	this.dibujarPuntos();
        	in.close();
            System.out.println("Mapa serializado correctamente en mapa.txt");
        } catch (Exception ex) {
            System.err.println("Error al serializar el mapa: " + ex.getMessage());
        }
        
    }
    
	
	public void serializar() {
        try {
        	FileOutputStream fos = new FileOutputStream("mapa.txt");
        	ObjectOutputStream out = new ObjectOutputStream(fos);
        	out.writeObject(this);
        	out.close();
            System.out.println("Mapa serializado correctamente en mapa.txt");
        } catch (Exception ex) {
            System.err.println("Error al serializar el mapa: " + ex.getMessage());
        }
    } 


	public static void main(String[] args) {
		SwingUtilities.invokeLater(Mapa::new);
	}
}
