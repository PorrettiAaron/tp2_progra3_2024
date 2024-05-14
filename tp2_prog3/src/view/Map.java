package view;

import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Map {

	private JFrame frame;
	
public static void main (String[] args) {	
	JFrame frame = new JFrame("Mapa");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JMapViewer mapViewer = new JMapViewer();

    Coordinate coordenadaArgentina = new Coordinate(-38.4161, -63.6167);
    mapViewer.setDisplayPosition(coordenadaArgentina, 4);

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
