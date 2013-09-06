package com.elka.nn.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.jws.soap.SOAPBinding.Style;
import javax.mail.MessagingException;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

import com.elka.nn.FlowVariables;
import com.elka.nn.LearnWithVector;
import com.elka.nn.NeuralNet;
import com.elka.nn.WeightsUtils;
import com.elka.nn.mail.analyzer.AnalyzeMail;
import com.elka.nn.mail.analyzer.AnalyzeMailsFromDir;
import com.elka.nn.mail.analyzer.AnaylzeWords;
import com.elka.nn.mail.analyzer.DirUtils;
import com.elka.nn.mail.analyzer.HashMapUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import javax.swing.JLabel;

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
	private FlowVariables fv;
	private AnalyzeMail am = null;
	private AnalyzeMailsFromDir amfd = null;
	private JTextField txtIlNeuronw;

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
		wu = new WeightsUtils();
		fv = new FlowVariables();
		hsu = new HashMapUtils(fv);
		NN = new NeuralNet(fv.LEARN_RATE,
							fv.LAYERS, 
							fv.NEURONS_HID, 
							fv.NUM_INPUT,
							fv.NEURONS_OUT);
		try {
			am = new AnalyzeMail(hsu, fv);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Neuronowy klasyfikator poczty elektronicznej");
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

		JButton btnWczytajSowaZ = new JButton("Wczytaj słowa spamowe");
		btnWczytajSowaZ.setBounds(10, 52, 154, 23);
		btnWczytajSowaZ.addActionListener(new LoadWordsSPAMWithJFC());
		panel.add(btnWczytajSowaZ);

		JButton btnZapiszSowaZ = new JButton("Zapisz słowa spamowe");
		btnZapiszSowaZ.setBounds(172, 52, 155, 23);
		btnZapiszSowaZ.addActionListener(new SaveWordsSPAMToFileWithJFC());
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
		btnAnalizujWiadomociW.addActionListener(new AnalyzeAllMails());
		panel.add(btnAnalizujWiadomociW);

		JButton btnAnalizujZaznaczonWiadomo = new JButton("<html>"
				+ "Analizuj zaznaczoną" + "<br>" + "wiadomość" + "</html>");
		btnAnalizujZaznaczonWiadomo.setBounds(662, 87, 164, 48);
		btnAnalizujZaznaczonWiadomo.addActionListener(new AnalyzeChosenMail());
		panel.add(btnAnalizujZaznaczonWiadomo);

		JButton btnWybierzLokalizacj = new JButton("Wybierz lokalizację");
		btnWybierzLokalizacj.addActionListener(new ChangeDirLocation());
		btnWybierzLokalizacj.setBounds(10, 111, 188, 24);

		panel.add(btnWybierzLokalizacj);

		JButton btnWykonajAnalizSw = new JButton("Analiza słów spamowych");
		btnWykonajAnalizSw.setBounds(337, 52, 162, 23);
		btnWykonajAnalizSw.addActionListener(new GetWordsSPAMFromCurrentLocation());
		panel.add(btnWykonajAnalizSw);

		JButton btnNauczSiedobierz = new JButton("Naucz sieć (dobierz wagi)");
		btnNauczSiedobierz.setBounds(836, 52, 167, 23);
		btnNauczSiedobierz.addActionListener(new SetWeightsByLearning());
		panel.add(btnNauczSiedobierz);
		
		JButton button = new JButton("Analiza słów pożądanych");
		button.setBounds(337, 76, 162, 24);
		button.addActionListener(new GetWordsGOODFromCurrentLocation());
		panel.add(button);
		
		txtIlNeuronw = new JTextField();
		txtIlNeuronw.setText("il. neuronów");
		txtIlNeuronw.setBounds(509, 80, 86, 20);
		
		panel.add(txtIlNeuronw);
		txtIlNeuronw.setColumns(10);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setBounds(605, 77, 47, 24);
		btnNewButton.addActionListener(new AcceptNumberOfNeurons());
		panel.add(btnNewButton);
		
		JButton button_1 = new JButton("Wczytaj słowa pożądane");
		button_1.setBounds(10, 77, 154, 23);
		button_1.addActionListener(new LoadWordsGOODWithJFC());
		panel.add(button_1);
		
		JButton button_2 = new JButton("Zapisz słowa pożądane");
		button_2.setBounds(172, 77, 155, 23);
		button_2.addActionListener(new SaveWordsGOODToFileWithJFC());
		panel.add(button_2);
	

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

	private class LoadWordsSPAMWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcWords = new JFileChooser();
			jfcWords.setFileFilter(new FilterTxt());
			jfcWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int openRet = jfcWords.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File jfcWordsFile = jfcWords.getSelectedFile();
				try {
					hsu.readHashMapFromFile(jfcWordsFile, frame, true);
					fv.setWordsSPAMOn(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textField.setForeground(Color.red.darker());
					textField.setText("Niepoprawnie wczytano słowa spamowe!");
				}
				try {
					doc.insertString(doc.getLength(), "Wczytane słowa spamowe: \n ",
							null);
					for (String el : hsu.getWordsSPAMArray()) {
						doc.insertString(doc.getLength() - 1, el + "\n", null);
					}
					doc.insertString(doc.getLength(),
							"\n ------------------- \n ", null);
				} catch (Exception e_doc) {
					e_doc.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Wystąpił błąd - niepoprawne wczytanie słów", "Błąd wczytywania słów", JOptionPane.ERROR_MESSAGE);
				}
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wczytano słowa spamowe!");
			}
		}
	}

	private class LoadWordsGOODWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcWords = new JFileChooser();
			jfcWords.setFileFilter(new FilterTxt());
			jfcWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int openRet = jfcWords.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File jfcWordsFile = jfcWords.getSelectedFile();
				try {
					hsu.readHashMapFromFile(jfcWordsFile, frame, false);
					fv.setWordsSPAMOn(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textField.setForeground(Color.red.darker());
					textField.setText("Niepoprawnie wczytano słowa pożądane!");
				}
				try {
					doc.insertString(doc.getLength(), "Wczytane słowa pożądane: \n ",
							null);
					for (String el : hsu.getWordsGOODArray()) {
						doc.insertString(doc.getLength() - 1, el + "\n", null);
					}
					doc.insertString(doc.getLength(),
							"\n ------------------- \n ", null);
				} catch (Exception e_doc) {
					e_doc.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Wystąpił błąd - niepoprawne wczytanie słów", "Błąd wczytywania słów", JOptionPane.ERROR_MESSAGE);
				}
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wczytano słowa spamowe!");
			}
		}
	}
	
	private class SaveWordsSPAMToFileWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcSaveWords = new JFileChooser();
			jfcSaveWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int saveRet = jfcSaveWords.showSaveDialog(frame);
			if (saveRet == JFileChooser.APPROVE_OPTION) {
				File toSave = jfcSaveWords.getSelectedFile();
				try {
					hsu.sortHashMapByValuesToFile(toSave, true);
					textField_3.setForeground(Color.green.darker());
					textField_3.setText("Poprawnie zapisano słowa spamowe!");
				} catch (Exception e2) {
					e2.printStackTrace();
					textField_3.setForeground(Color.red.darker());
					textField_3.setText("Niepoprawnie zapisano słowa spamowe!");

				}
			}
		}
		// @TODO trzeba by odpowiednie pliki wczytać i potestować.
	}
	
	private class SaveWordsGOODToFileWithJFC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfcSaveWords = new JFileChooser();
			jfcSaveWords.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int saveRet = jfcSaveWords.showSaveDialog(frame);
			if (saveRet == JFileChooser.APPROVE_OPTION) {
				File toSave = jfcSaveWords.getSelectedFile();
				try {
					hsu.sortHashMapByValuesToFile(toSave, false);
					textField_3.setForeground(Color.green.darker());
					textField_3.setText("Poprawnie zapisano słowa pożadane!");
				} catch (Exception e2) {
					e2.printStackTrace();
					textField_3.setForeground(Color.red.darker());
					textField_3.setText("Niepoprawnie zapisano słowa pożadane!");

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
			jfcOpenWeights.setFileFilter(new FilterTxt());
			int openRet = jfcOpenWeights.showOpenDialog(frame);
			if (openRet == JFileChooser.APPROVE_OPTION) {
				File toOpen = jfcOpenWeights.getSelectedFile();
				try {
					wu.loadWeightsFromFile(toOpen);
					fv.setWeightsOn(true);
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

	public class GetWordsSPAMFromCurrentLocation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File currentRoot = model.getRoot();
			AnaylzeWords aw = new AnaylzeWords(hsu);
			try {
				aw.readFiles(currentRoot, doc, true);
				hsu.sortHashMapByValuesInNN(true);
				fv.setWordsSPAMOn(true);
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wykonano analizę słów spamowych!");
				doc.insertString(doc.getLength(), "Słowa \n", null);
				String[] strArr = hsu.getWordsSPAMArray();
				for (int i = 0; i < strArr.length; i++) {
					doc.insertString(doc.getLength(), strArr[i], null);
					doc.insertString(doc.getLength(), ", ", null);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				textField.setForeground(Color.red.darker());
				textField.setText("Niepoprawnie wykonano analizę słów spamowych!");
				JOptionPane.showMessageDialog(frame, "Wystąpił błąd - zmień lokalizację na taką, która zawiera pliki typu .eml", "Błąd wykonania analizy", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	
	public class GetWordsGOODFromCurrentLocation implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			File currentRoot = model.getRoot();
			AnaylzeWords aw = new AnaylzeWords(hsu);
			try {
				aw.readFiles(currentRoot, doc, false);
				hsu.sortHashMapByValuesInNN(false);
				fv.setWordsGOODOn(true);
				textField.setForeground(Color.green.darker());
				textField.setText("Poprawnie wykonano analizę słów pożądanych!");
				doc.insertString(doc.getLength(), "Słowa \n", null);
				String[] strArr = hsu.getWordsGOODArray();
				for (int i = 0; i < strArr.length; i++) {
					doc.insertString(doc.getLength(), strArr[i], null);
					doc.insertString(doc.getLength(), ", ", null);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				textField.setForeground(Color.red.darker());
				textField.setText("Niepoprawnie wykonano analizę słów pożądanych!");
				JOptionPane.showMessageDialog(frame, "Wystąpił błąd - zmień lokalizację na taką, która zawiera pliki typu .eml", "Błąd wykonania analizy", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public class SetWeightsByLearning implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				doc.insertString(doc.getLength(),
						"\nTrwa ustalanie wag, proszę czekać...\n", null);
				LearnWithVector lwv = new LearnWithVector(NN, fv);
				lwv.setWeightsHash();
				fv.setWeightsOn(true);
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
	
	public class AnalyzeChosenMail implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) throws NullPointerException {
			String pathname = null;
			try {
				pathname = tree.getLastSelectedPathComponent().toString();
			} catch (NullPointerException nullex) {
				
			}
			try {
				if (!pathname.endsWith(".eml")) {
					throw new Exception();
				}
				if (fv.getWordsSPAMOn() && fv.getWordsGOODOn() && fv.getWeightsOn()) {
					DirUtils du = new DirUtils(model.getRoot());
					if (du.checkFolderExisting() == false) {
						du.createFolders();
					}
					am.makeDoubleVector(pathname);
					double[] wek = am.getbinVector();
					for (int i = 0; i < wek.length; i++) {
						Double tmp = wek[i];
						doc.insertString(doc.getLength(), tmp.toString(), null);
						doc.insertString(doc.getLength(), ", ", null);
					}
					double wynik = NN.goForward(am.getbinVector());
					du.copyFile(new File(pathname), wynik);
					doc.insertString(doc.getLength(), "\nWynik to: " + wynik + "\n", null);
					if (wynik >= 0.51) {
						doc.insertString(doc.getLength(), "SPAM!\n", null);
					} else {
						doc.insertString(doc.getLength(), "Wiadomość pożądana!\n", null);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Wagi lub słowa nie są poprawnie ustalone w sieci", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(frame, "Nie wybrano pliku .eml!", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public class AnalyzeAllMails implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			File currentRoot = model.getRoot();
			amfd = new AnalyzeMailsFromDir(hsu, fv, NN, currentRoot);
			if (fv.getWordsSPAMOn() && fv.getWordsGOODOn() && fv.getWeightsOn()) {
				long startTime = System.currentTimeMillis();
				amfd.getScoreFromCurrentDir(currentRoot, doc);
				long endTime = System.currentTimeMillis();
				System.out.println("That took " + (endTime - startTime) + " milliseconds");
				try {
					amfd.infoDoc(doc);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(frame, "Przeanalizowano " + amfd.getCount() + " wiadomości.", "Zakończono analizę", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(frame, "Wagi lub słowa nie są poprawnie ustalone w sieci", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public class AcceptNumberOfNeurons implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String textInField = txtIlNeuronw.getText();
			try {
				int check = Integer.parseInt(textInField);
				fv.setNumberOfNeurons(check);
				doc.insertString(doc.getLength(), "Ilość neuronów użyta w warstwie ukrytej sieci: " + check, null);
				doc.insertString(doc.getLength(), "\n", null);
			} 
			catch(NumberFormatException | BadLocationException nfe) {
				JOptionPane.showMessageDialog(frame, "Nie wprowadzono ilości neuronów bądź nie jest ona liczbą całkowitą", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
} // main class
