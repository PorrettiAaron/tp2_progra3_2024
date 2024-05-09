package crontrols;

import java.awt.EventQueue;
import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Map {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Map window = new Map();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
public static void main (String[] args) {	
	JFrame frame = new JFrame("Mapa");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Crear un objeto JMapViewer
    JMapViewer mapViewer = new JMapViewer();

    
    // Establecer la posición del centro del mapa en Argentina
    Coordinate coordenadaArgentina = new Coordinate(-38.4161, -63.6167);
    mapViewer.setDisplayPosition(coordenadaArgentina, 4); // Zoom nivel 6

    // Añadir el JMapViewer al JFrame
    frame.getContentPane().add(mapViewer);
    frame.setVisible(true);
}

	/**
	 * Create the application.
	 */
	public Map() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
