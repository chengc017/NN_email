package com.elka.nn.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
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
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Neural Network Mail Analyzer");
		frame.setBounds(100, 100, 687, 536);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 150));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 386, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(414, 11, 386, 20);
		panel.add(textField_1);
		
		JButton btnWybierzLokalizacj = new JButton("Wybierz lokalizacj\u0119");
		btnWybierzLokalizacj.setBounds(10, 114, 155, 21);
		panel.add(btnWybierzLokalizacj);
		
		textField_2 = new JTextField();
		textField_2.setBounds(176, 115, 220, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnWczytajSowaZ = new JButton("Wczytaj s\u0142owa z pliku");
		btnWczytajSowaZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWczytajSowaZ.setBounds(10, 42, 188, 23);
		panel.add(btnWczytajSowaZ);
		
		JButton btnZapiszSowaZ = new JButton("Zapisz s\u0142owa z analizy");
		btnZapiszSowaZ.setBounds(208, 42, 188, 23);
		panel.add(btnZapiszSowaZ);
		
		JButton button = new JButton("Wczytaj s\u0142owa z pliku");
		button.setBounds(414, 42, 188, 23);
		panel.add(button);
		
		JButton btnZapiszSowaDo = new JButton("Zapisz s\u0142owa do pliku");
		btnZapiszSowaDo.setBounds(612, 42, 188, 23);
		panel.add(btnZapiszSowaDo);
		
		String btnlbl = "<html>" + "Analizuj wiadomo\u015Bci" + "<br>" + "w wybranej lokalizacji" + "</html>";
		JButton btnAnalizujWiadomociW = new JButton(btnlbl);
		btnAnalizujWiadomociW.setBounds(612, 89, 188, 48);
		panel.add(btnAnalizujWiadomociW);
		
		JButton btnAnalizujZaznaczonWiadomo = new JButton("<html>" + "Analizuj zaznaczon\u0105" + "<br>" + "wiadomo\u015B\u0107" + "</html>");
		btnAnalizujZaznaczonWiadomo.setBounds(414, 89, 188, 48);
		panel.add(btnAnalizujZaznaczonWiadomo);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		
		JTextPane txtpnA = new JTextPane();
		txtpnA.setPreferredSize(new Dimension(400, 400));
		txtpnA.setEditable(false);
		txtpnA.setText("asdasd");
		panel_1.add(txtpnA);

		/*--------------------------- JTREE -----------------------------*/
		File root = new File(System.getProperty("user.home"));

		// Create a TreeModel object to represent our tree of files
		FileTreeModel model = new FileTreeModel(root);

		// Create a JTree and tell it to display our model
		JTree tree = new JTree();
		tree.setModel(model);

		// The JTree can get big, so allow it to scroll.
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(400, 100));
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
//		frame.setPreferredSize(new Dimension(600, 600));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
