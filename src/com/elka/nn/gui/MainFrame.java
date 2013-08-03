package com.elka.nn.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import com.elka.nn.LearnWithVector;
import com.elka.nn.NeuralNet;
import com.elka.nn.WeightsUtils;
import com.elka.nn.mail.analyzer.AnaylzeWords;
import com.elka.nn.mail.analyzer.HashMapUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class MainFrame {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private JTextPane txtpnA;

	private FileTreeModel model;
	private JTree tree;

	private StyledDocument doc;

	private HashMapUtils hsu = null;
	private WeightsUtils wu = null;
	private NeuralNet NN;

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
		hsu = new HashMapUtils();
		wu = new WeightsUtils();
		NN = new NeuralNet(0.1, 2, 5, 6, 1);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Neural Network Mail Analyzer");
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 600);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000, 150));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 11, 240, 30);
		textField.setForeground(Color.red.darker());
		textField.setText("Nie wczytano słów!");
		textField.setEditable(false);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(509, 11, 237, 30);
		textField_1.setForeground(Color.red.darker());
		textField_1.setText("Nie wczytano wag!");
		textField_1.setEditable(false);
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setBounds(208, 111, 444, 24);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(259, 11, 240, 30);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(756, 11, 246, 30);
		panel.add(textField_4);

		JButton btnWczytajSowaZ = new JButton("Wczytaj słowa z pliku");
		btnWczytajSowaZ.setBounds(10, 52, 154, 23);
		btnWczytajSowaZ.addActionListener(new LoadWordsWithJFC());
		panel.add(btnWczytajSowaZ);

		JButton btnZapiszSowaZ = new JButton("Zapisz słowa z analizy");
		btnZapiszSowaZ.setBounds(174, 52, 155, 23);
		btnZapiszSowaZ.addActionListener(new SaveWordsToFileWithJFC());
		panel.add(btnZapiszSowaZ);

		JButton btnWczytajWagiZ = new JButton("Wczytaj wagi z pliku");
		btnWczytajWagiZ.setBounds(507, 52, 145, 23);
		btnWczytajWagiZ.addActionListener(new OpenWeightsFromFileWithJFC());
		panel.add(btnWczytajWagiZ);

		JButton btnZapiszWagiDo = new JButton("Zapisz wagi do pliku");
		btnZapiszWagiDo.addActionListener(new SaveWeightsToFileWithJFC());
		btnZapiszWagiDo.setBounds(662, 52, 164, 23);
		panel.add(btnZapiszWagiDo);

		String btnlbl = "<html>" + "Analizuj wiadomości" + "<br>"
				+ "w wybranej lokalizacji" + "</html>";
		JButton btnAnalizujWiadomociW = new JButton(btnlbl);
		btnAnalizujWiadomociW.setBounds(836, 87, 164, 48);
		panel.add(btnAnalizujWiadomociW);

		JButton btnAnalizujZaznaczonWiadomo = new JButton("<html>"
				+ "Analizuj zaznaczoną" + "<br>" + "wiadomość" + "</html>");
		btnAnalizujZaznaczonWiadomo.setBounds(662, 87, 164, 48);
		panel.add(btnAnalizujZaznaczonWiadomo);

		JButton btnWybierzLokalizacj = new JButton("Wybierz lokalizację");
		btnWybierzLokalizacj.addActionListener(new ChangeDirLocation());
		btnWybierzLokalizacj.setBounds(10, 111, 188, 24);

		panel.add(btnWybierzLokalizacj);

		JButton btnWykonajAnalizSw = new JButton("Wykonaj analizę słów");
		btnWykonajAnalizSw.setBounds(339, 52, 154, 23);
		btnWykonajAnalizSw.addActionListener(new GetWordsFromCurrentLocation());
		panel.add(btnWykonajAnalizSw);

		JButton btnNauczSiedobierz = new JButton("Naucz sieć (dobierz wagi)");
		btnNauczSiedobierz.setBounds(836, 52, 167, 23);
		btnNauczSiedobierz.addActionListener(new SetWeightsByLearning());
		panel.add(btnNauczSiedobierz);
	

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);

		txtpnA = new JTextPane();
		scrollPane_1.setViewportView(txtpnA);
		txtpnA.setPreferredSize(new Dimension(500, 450));
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

	private class ChangeDirLocation implements ActionListener {

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

	private class LoadWordsWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcWords = new JFileChooser();
			jfcWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int openRet = jfcWords.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File jfcWordsFile = jfcWords.getSelectedFile();
				try {
					hsu.readHashMapFromFile(jfcWordsFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textField.setForeground(Color.red.darker());
					textField.setText("Niepoprawnie wczytano słowa!");
				}
				try {
					doc.insertString(doc.getLength(), "Wczytane słowa: \n ",
							null);
					for (String el : hsu.getWordsArray()) {
						doc.insertString(doc.getLength() - 1, el + "\n", null);
					}
					doc.insertString(doc.getLength(),
							"\n ------------------- \n ", null);
				} catch (Exception e_doc) {
					e_doc.printStackTrace();
				}
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wczytano słowa!");

				// @TODO TU trzeba jeszcze porobic jakies pola boolowskie, ktore
				// po wczytaniu zmienilyby na true, ze wczytano
				// i wtedy mozna dopiero by odpalic jakakolwiek analize, bo tak
				// to puscimy analize bez slow to chujnia bedzie
			}
		}
	}

	private class SaveWordsToFileWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcSaveWords = new JFileChooser();
			jfcSaveWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int saveRet = jfcSaveWords.showSaveDialog(frame);
			if (saveRet == JFileChooser.APPROVE_OPTION) {
				File toSave = jfcSaveWords.getSelectedFile();
				try {
					hsu.sortHashMapByValuesToFile(toSave);
					textField_3.setForeground(Color.green.darker());
					textField_3.setText("Poprawnie zapisano słowa!");
				} catch (Exception e2) {
					e2.printStackTrace();
					textField_3.setForeground(Color.red.darker());
					textField_3.setText("Niepoprawnie zapisano słowa!");

				}
			}
		}
		// @TODO trzeba by odpowiednie pliki wczytać i potestować.
	}

	private class OpenWeightsFromFileWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcOpenWeights = new JFileChooser();
			jfcOpenWeights.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// jfcOpenWeights.setFileFilter(filter); tu trzeba poczytac
			int openRet = jfcOpenWeights.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File toOpen = jfcOpenWeights.getSelectedFile();
				try {
					wu.loadWeightsFromFile(toOpen);
					textField_1.setForeground(Color.green.darker());
					textField_1.setText("Poprawnie wczytano wagi!");
					doc.insertString(doc.getLength(), "Wczytane wagi:\n ", null);
					for (Double element : wu.getWeightsList()) {
						doc.insertString(doc.getLength(), element.toString()
								+ ", ", null);
					}
					doc.insertString(doc.getLength(),
							"\n ------------------- \n ", null);
				} catch (Exception e3) {
					e3.printStackTrace();
					textField_1.setForeground(Color.red.darker());
					textField_1.setText("Niepoprawnie wczytano wagi!");
				}
			}
		}

	}

	public class SaveWeightsToFileWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcSaveWeights = new JFileChooser();
			jfcSaveWeights.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int saveRet = jfcSaveWeights.showSaveDialog(frame);
			if (saveRet == JFileChooser.APPROVE_OPTION) {
				File toSave = jfcSaveWeights.getSelectedFile();
				try {
					wu.writeWeightsToFile(toSave);
					textField_4.setForeground(Color.green.darker());
					textField_4.setText("Poprawnie zapisano wagi");
				} catch (Exception ex) {
					ex.printStackTrace();
					textField_4.setForeground(Color.red.darker());
					textField_4.setText("Niepoprawnie wczytano wagi!");
				}
			}
		}
	}

	public class GetWordsFromCurrentLocation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File currentRoot = model.getRoot();
			AnaylzeWords aw = new AnaylzeWords(hsu);
			try {
				aw.readFiles(currentRoot);
				hsu.sortHashMapByValuesInNN();
				textField.setForeground(Color.green.darker());
				textField.setText(textField.getText() + " || "
						+ "Poprawnie wykonano analizę!");
				doc.insertString(doc.getLength(), "Słowa \n", null);
				/*
				 * for (String strEl : hsu.getWordsArray()) {
				 * doc.insertString(doc.getLength(), strEl + " ", null); }
				 */
				String[] strArr = hsu.getWordsArray();
				for (int i = 0; i < strArr.length; i++) {
					doc.insertString(doc.getLength(), strArr[i], null);
					doc.insertString(doc.getLength(), ", ", null);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				textField.setForeground(Color.red.darker());
				textField.setText(textField.getText() + " || "
						+ "Niepoprawnie wykonano analizę!");
			}
		}

	}

	public class SetWeightsByLearning implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				doc.insertString(doc.getLength(),
						"Trwa ustalanie wag, proszę czekać...\n", null);
				LearnWithVector lwv = new LearnWithVector(NN);
				lwv.setWeights();
				doc.insertString(doc.getLength(),
						" Poprawnie zakończono ustalanie wag!\n", null);
				wu.setWeightsList(NN.getWeightsTable());
				textField_1.setForeground(Color.green.darker());
				textField_1.setText("Poprawnie ustalono wagi!");
			} catch (Exception e_set) {
				e_set.printStackTrace();
				textField_1.setForeground(Color.red.darker());
				textField_1.setText("Niepoprawnie ustalano wagi!");
				try {
					doc.insertString(doc.getLength(),
							"Niepoprawnie ustalono wagi, błąd!", null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
} // main class
