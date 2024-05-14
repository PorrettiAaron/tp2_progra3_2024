package crontrols;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate; 

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


public class Mapa {
    private JFrame frame;
    private JMapViewer mapViewer;
    private Map<String, Coordinate> points;


    /**
     * @wbp.parser.entryPoint
     */
    public Mapa() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Map Marker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // Panel para el título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Agregue sus vértices...");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        titlePanel.add(titleLabel);

        // Panel para el mapa
        JPanel mapPanel = new JPanel(new BorderLayout());
        mapViewer = new JMapViewer();
        mapViewer.setDisplayPosition(new Coordinate(0, 0), 3);
        mapPanel.add(mapViewer, BorderLayout.CENTER);

        // Panel para el botón
        JPanel buttonPanel = new JPanel();
        JButton similarityButton = new JButton("Agregar Índice de Similaridad");
        buttonPanel.add(similarityButton);

        points = new HashMap<>();

        // Agregar un MouseAdapter para detectar clics del mouse en el mapa
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pedir al usuario que ingrese el nombre del punto
                String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del punto:");

                // Verificar si el nombre ya existe en el mapa
                if (points.containsKey(name)) {
                    JOptionPane.showMessageDialog(frame, "Ya existe un punto con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Salir del método si ya existe el punto
                }

                // Obtener las coordenadas del punto donde se hizo clic
                Point point = e.getPoint();
                ICoordinate iCoord = mapViewer.getPosition(point.x, point.y);

                // Convertir ICoordinate a Coordinate
                Coordinate coord = new Coordinate(iCoord.getLat(), iCoord.getLon());

                // Almacenar el nombre y las coordenadas del punto en el mapa
                points.put(name, coord);

                // Crear un marcador con etiqueta para mostrar en el mapa
                MapMarkerDotWithLabel marker = new MapMarkerDotWithLabel(coord, name);
                mapViewer.addMapMarker(marker);

                System.out.println(points);
            }
        });
        
        // Acción del botón
        similarityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                               ArrayList <String> pointNames = new ArrayList<>(points.keySet());
            	Board board = new Board(pointNames);
            	
            	board.setVisible(true);  
            }           
            	/*
                // Crear y configurar el diálogo
                JDialog dialog = new JDialog(frame, "Seleccionar puntos", true);
                dialog.setLayout(new GridLayout(4, 2));

                // Obtener nombres de puntos disponibles


                // ComboBox para el primer punto
                JComboBox<String> pointComboBox1 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.add(new JLabel("Punto 1:"));
                dialog.add(pointComboBox1);

                // ComboBox para el segundo punto
                JComboBox<String> pointComboBox2 = new JComboBox<>(pointNames.toArray(new String[0]));
                dialog.add(new JLabel("Punto 2:"));
                dialog.add(pointComboBox2);

                // Campo de texto para el peso
                JTextField pesoField = new JTextField();
                dialog.add(new JLabel("Peso:"));
                dialog.add(pesoField);

                // Botón de confirmación
                JButton confirmButton = new JButton("Confirmar");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obtener los puntos seleccionados y el peso
                        String punto1 = (String) pointComboBox1.getSelectedItem();
                        String punto2 = (String) pointComboBox2.getSelectedItem();
                        String peso = pesoField.getText();

                        // Mostrar mensaje con los puntos seleccionados
                        JOptionPane.showMessageDialog(frame, "Punto 1: " + punto1 + "\n" + "Punto 2: " + punto2 + "\n" + "Peso: " + peso);

                        // Cerrar el diálogo
                        dialog.dispose();
                    }
                });
                dialog.add(confirmButton);

                // Mostrar el diálogo
                dialog.setSize(300, 150);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
            */
        });

        // Agregar los paneles al JFrame
        frame.getContentPane().add(titlePanel, BorderLayout.NORTH);
        frame.getContentPane().add(mapPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Mapa::new);
    }
}