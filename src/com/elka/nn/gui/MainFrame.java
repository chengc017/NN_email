package com.elka.nn.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.StyledDocument;

import com.elka.nn.mail.analyzer.HashMapUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class MainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	private JTextPane txtpnA;

	private FileTreeModel model;
	private JTree tree;
	
	private StyledDocument doc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 750);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000, 150));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 11, 483, 20);
		textField.setForeground(Color.red.darker());
		textField.setText("Nie wczytano s³ów!");
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(509, 11, 491, 20);
		textField_1.setForeground(Color.red.darker());
		textField_1.setText("Nie wczytano wag!");
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBounds(208, 115, 394, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JButton btnWczytajSowaZ = new JButton("Wczytaj s\u0142owa z pliku");
		btnWczytajSowaZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnWczytajSowaZ.setBounds(10, 42, 164, 23);
		btnWczytajSowaZ.addActionListener(new LoadWordsWithJFC());
		panel.add(btnWczytajSowaZ);

		JButton btnZapiszSowaZ = new JButton("Zapisz s\u0142owa z analizy");
		btnZapiszSowaZ.setBounds(184, 42, 164, 23);
		panel.add(btnZapiszSowaZ);

		JButton btnWczytajWagiZ = new JButton("Wczytaj wagi z pliku");
		btnWczytajWagiZ.setBounds(612, 42, 188, 23);
		panel.add(btnWczytajWagiZ);

		JButton btnZapiszSowaDo = new JButton("Zapisz wagi do pliku");
		btnZapiszSowaDo.setBounds(812, 42, 188, 23);
		panel.add(btnZapiszSowaDo);

		String btnlbl = "<html>" + "Analizuj wiadomo\u015Bci" + "<br>"
				+ "w wybranej lokalizacji" + "</html>";
		JButton btnAnalizujWiadomociW = new JButton(btnlbl);
		btnAnalizujWiadomociW.setBounds(812, 87, 188, 48);
		panel.add(btnAnalizujWiadomociW);

		JButton btnAnalizujZaznaczonWiadomo = new JButton("<html>"
				+ "Analizuj zaznaczon\u0105" + "<br>" + "wiadomo\u015B\u0107"
				+ "</html>");
		btnAnalizujZaznaczonWiadomo.setBounds(612, 87, 188, 48);
		panel.add(btnAnalizujZaznaczonWiadomo);

		JButton btnWybierzLokalizacj = new JButton("Wybierz lokalizacj\u0119");
		btnWybierzLokalizacj.addActionListener(new ChangeDirLocation());
		btnWybierzLokalizacj.setBounds(10, 114, 188, 23);
		
		panel.add(btnWybierzLokalizacj);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);
		
				txtpnA = new JTextPane();
				scrollPane_1.setViewportView(txtpnA);
				txtpnA.setPreferredSize(new Dimension(500, 600));
				txtpnA.setEditable(false);
				doc = txtpnA.getStyledDocument();

		/*--------------------------- JTREE -----------------------------*/
		File root = new File(System.getProperty("user.home"));

		// Create a TreeModel object to represent our tree of files
		model = new FileTreeModel(root);

		// Create a JTree and tell it to display our model
		tree = new JTree();
		tree.setModel(model);

		// The JTree can get big, so allow it to scroll.
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(500, 100));

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		// frame.setPreferredSize(new Dimension(600, 600));
		frame.pack();
		frame.setVisible(true);
	}
	
	private class ChangeDirLocation implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcMain = new JFileChooser();
			jfcMain.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int jfcMainRet = jfcMain.showOpenDialog(frame);
			if (jfcMainRet == JFileChooser.APPROVE_OPTION) {
				File jfcMainFile = jfcMain.getSelectedFile();
				model = new FileTreeModel(jfcMainFile);
				tree.setModel(model);
				textField_2.setText(jfcMainFile.getPath());
			}
		}
	}
	
	private class LoadWordsWithJFC implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcWords = new JFileChooser();
			jfcWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int openRet = jfcWords.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File jfcWordsFile = jfcWords.getSelectedFile();
				HashMapUtils hsu = new HashMapUtils();
				try {
					hsu.readHashMapFromFile(jfcWordsFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					doc.insertString(0, "Wczytane s³owa: \n ", null);
					for (String el : hsu.getWordsArray()) {
						doc.insertString(doc.getLength()-1, el + "\n", null);
					}
				}
				catch(Exception e_doc) {
					textField.setForeground(Color.red.darker());
					textField.setText("Niepoprawnie wczytano s³owa!");
				}
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wczytano s³owa!");
				
				//@TODO TU trzeba jeszcze porobic jakies pola boolowskie, ktore po wczytaniu zmienilyby na true, ze wczytano
				// i wtedy mozna dopiero by odpalic jakakolwiek analize, bo tak to puscimy analize bez slow to chujnia bedzie
			}
		}
		
	}
}
