package crontrols;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Label;

import model.*;
import controller.AgmPrim;

public class Board extends JFrame{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField peso;
	private Grafo grafo;
	private Arista arista;
	private ArrayList<String> vertices;


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
	public Board(ArrayList<String> vertices) {

		this.vertices = vertices;
		initialize();
		this.grafo = new Grafo();



	}
	
	public Board() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SwingUtilities.invokeLater(() -> {	
			JFrame frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);


			// Agregar las provincias argentinas al JComboBox
			String[] provinciasArgentinas = {"Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba", "Corrientes",
					"Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquén",
					"Río Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero",
					"Tierra del Fuego", "Tucumán"};

			JComboBox<String> provincia1 = new JComboBox<>();
			for (String provincia : provinciasArgentinas) {
				provincia1.addItem(provincia);
			}
			provincia1.setAutoscrolls(true);
			provincia1.setBounds(10, 52, 414, 32);
			frame.getContentPane().add(provincia1);

			JComboBox<String> provincia2 = new JComboBox<>();
			for (String provinci : provinciasArgentinas) {
				provincia2.addItem(provinci);
			}
			provincia2.setBounds(10, 95, 414, 32);
			frame.getContentPane().add(provincia2);

			peso = new JTextField();
			peso.setBounds(10, 138, 414, 32);
			frame.getContentPane().add(peso);
			peso.setColumns(10);

			JLabel log = new JLabel("");
			log.setHorizontalAlignment(SwingConstants.CENTER);
			log.setBounds(20, 188, 387, 14);
			frame.getContentPane().add(log);

			JButton nuevaArista = new JButton("Agregar Nueva Arista");
			nuevaArista.setBounds(45, 227, 176, 23);
			frame.getContentPane().add(nuevaArista);
			nuevaArista.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						log.setText("");
						grafo.agregarArista(provincia1.getSelectedItem().toString(), provincia2.getSelectedItem().toString(), Integer.parseInt(peso.getText()));
						log.setText("arista agregada correctamente");
					}
					catch(Exception e){
						log.setText("por favor ingrese los datos correctamente");
					}
				}
			});

			JButton Comenzar = new JButton("Comenzar");
			Comenzar.setBounds(231, 227, 176, 23);
			frame.getContentPane().add(Comenzar);
			Comenzar.addActionListener((ActionListener) new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					grafo.imprimirGrafo();
					AgmPrim agm = new AgmPrim(grafo);
					Tabla tabla = new Tabla(grafo, agm.procesoAgm(2));
					tabla.setVisible(true);
				}
			});
		});

	}
}
