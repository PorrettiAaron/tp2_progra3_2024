package crontrols;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.Bfs;
import controller.Dfs;
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
        JLabel titleLabel = new JLabel("Agregue sus vértices...");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        titlePanel.add(titleLabel);


        JPanel mapPanel = new JPanel(new BorderLayout());
        mapViewer = new JMapViewer();
        mapViewer.setDisplayPosition(new Coordinate(0, 0), 3);
        mapPanel.add(mapViewer, BorderLayout.CENTER);

        // Panel para el botón
        JPanel buttonPanel = new JPanel();

        JButton similarityButton = new JButton("Agregar Índice de Similaridad");
        buttonPanel.add(similarityButton);

        JButton btnCrearRegiones = new JButton("Crear regiones");
        buttonPanel.add(btnCrearRegiones);

        JButton btnEliminarArista = new JButton("Eliminar vertice");
        buttonPanel.add(btnEliminarArista);


        // Agregar un MouseAdapter para detectar clics del mouse en el mapa
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pedir al usuario que ingrese el nombre del punto
                String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del punto:");

                // Verificar si el nombre ya existe en el mapa
                if (points.containsKey(name)) {
                    JOptionPane.showMessageDialog(frame, "Ya existe un punto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                // Obtener las coordenadas del punto donde se hizo clic
                Point point = e.getPoint();
                ICoordinate iCoord = mapViewer.getPosition(point.x, point.y);
                Coordinate coord = new Coordinate(iCoord.getLat(), iCoord.getLon());
                
                points.put(name, coord);
                g.agregarVertice(name);

                // Crear un marcador con etiqueta para mostrar en el mapa
                MapMarkerDotWithLabel marker = new MapMarkerDotWithLabel(coord, name);
                mapViewer.addMapMarker(marker);
            }
        });

        // Acción del botón
        similarityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y configurar el diálogo
                JDialog dialog = new JDialog(frame, "Seleccionar puntos", true);
                dialog.getContentPane().setLayout(new GridLayout(4, 2));

                // Obtener nombres de puntos disponibles
                List<String> pointNames = new ArrayList<>(points.keySet());

                // ComboBox para el primer punto
                JComboBox<String> pointComboBox1 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.getContentPane().add(new JLabel("Punto 1:"));
                dialog.getContentPane().add(pointComboBox1);

                // ComboBox para el segundo punto
                JComboBox<String> pointComboBox2 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.getContentPane().add(new JLabel("Punto 2:"));
                dialog.getContentPane().add(pointComboBox2);

                // Campo de texto para el peso
                JTextField pesoField = new JTextField();
                dialog.getContentPane().add(new JLabel("Peso:"));
                dialog.getContentPane().add(pesoField);

                // Botón de confirmación
                JButton confirmButton = new JButton("Confirmar");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener los puntos seleccionados y el peso
                        String punto1 = (String) pointComboBox1.getSelectedItem();
                        String punto2 = (String) pointComboBox2.getSelectedItem();
                        String textoPeso = pesoField.getText();

                        try {
                            g.agregarArista(punto1, punto2, Integer.parseInt(textoPeso));
                            JOptionPane.showMessageDialog(frame, "Punto 1: " + punto1 + "\n" + "Punto 2: " + punto2 + "\n" + "Peso: " + textoPeso);
                            dibujarAristas();
                            dialog.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Por favor ingrese un valor numérico para el peso.");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocurrió un error al agregar la arista.");
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

        btnEliminarArista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y configurar el diálogo
                JDialog dialog = new JDialog(frame, "Seleccione su punto", true);
                dialog.getContentPane().setLayout(new GridLayout(4, 2));

                // Obtener nombres de puntos disponibles
                List<String> pointNames = new ArrayList<>(points.keySet());

                // ComboBox para el primer punto
                JComboBox<String> pointComboBox1 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.getContentPane().add(new JLabel("Punto 1:"));
                dialog.getContentPane().add(pointComboBox1);

                // ComboBox para el segundo punto
                JComboBox<String> pointComboBox2 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.getContentPane().add(new JLabel("Punto 2:"));
                dialog.getContentPane().add(pointComboBox2);

                // Botón de confirmación
                JButton confirmButton = new JButton("Confirmar");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener los puntos seleccionados y el peso
                        String punto1 = (String) pointComboBox1.getSelectedItem();
                        String punto2 = (String) pointComboBox2.getSelectedItem();

                        try {
                        	g.imprimirGrafo();
                            g.eliminarAristaEntreVertices(punto1, punto2);
                            g.eliminarAristaEntreVertices(punto2, punto1);
                            g.imprimirGrafo();
                            JOptionPane.showMessageDialog(frame, "Se eliminó la arista entre punto 1: " + punto1 + "\n" + " y punto 2: " + punto2);
                            dibujarAristas();
                            dialog.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame, "Ocurrió un error al eliminar la arista.");
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
	                AgmPrim agm = new AgmPrim(g);
	                Tabla tabla = new Tabla(g, agm.procesoAgm(2));
	                tabla.setVisible(true);
	                
            	} catch (Exception e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }

        });
        
        // Agregar los paneles al JFrame
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
        
        // Obtener todas las aristas del grafo
        List<Arista> todasAristas = g.obtenerTodasAristas();

        // Crear las líneas para cada arista
        for (Arista arista : todasAristas) {
            String punto1 = arista.getOrigen();
            String punto2 = arista.getDestino();

            // Obtener las coordenadas de los puntos
            Coordinate coordenada1 = points.get(punto1);
            Coordinate coordenada2 = points.get(punto2);

            // Verificar si las coordenadas son nulas
            if (coordenada1 != null && coordenada2 != null) {
                // Crear las coordenadas de la línea
                List<Coordinate> coordinates = new ArrayList<>();
                coordinates.add(coordenada1);
                coordinates.add(coordenada2);
                coordinates.add(coordenada1);
                
                // Agregar la línea al mapa
                MapPolygonImpl line = new MapPolygonImpl(coordinates);
                mapViewer.addMapPolygon(line);
                mapLines.add(line); // Agregar la línea a la lista de líneas
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mapa::new);
    }
}
