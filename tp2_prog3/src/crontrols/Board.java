package crontrols;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.Label;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;

public class Board {

	private JFrame frame;
	private JTextField peso;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Board window = new Board();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Board() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		// Agregar las provincias argentinas al JComboBox
        String[] provinciasArgentinas = {"Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes",
                "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén",
                "Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero",
                "Tierra del Fuego", "Tucumán"};
        
		JComboBox provincia1 = new JComboBox();
        for (String provincia : provinciasArgentinas) {
            provincia1.addItem(provincia);
        }
		provincia1.setAutoscrolls(true);
		provincia1.setBounds(10, 52, 414, 32);
		frame.getContentPane().add(provincia1);
		
		JComboBox provincia2 = new JComboBox();
        for (String provincia : provinciasArgentinas) {
            provincia2.addItem(provincia);
        }
		provincia2.setBounds(10, 95, 414, 32);
		frame.getContentPane().add(provincia2);
		
		peso = new JTextField();
		peso.setBounds(10, 138, 414, 32);
		frame.getContentPane().add(peso);
		peso.setColumns(10);
		
		JButton nuevaArista = new JButton("Agregar Nueva Arista");
		nuevaArista.setBounds(45, 227, 176, 23);
		frame.getContentPane().add(nuevaArista);
		
		JButton Comenzar = new JButton("Comenzar");
		Comenzar.setBounds(231, 227, 176, 23);
		frame.getContentPane().add(Comenzar);
	}
}
