package practica1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JTable;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class InterfazBasica extends JFrame implements DocumentListener{

	private JTextField textField_setncarac,
	textField_setentropia,textField_estudiacaracter,textField_setfrec,textField_setprob;
	private JButton btn_selectData,btn_CalcularEntropia, btn_CalcularFyP;
	private JTextArea textArea_texto;
	private JSpinner spinner;

	private JPanel contentPane;
	private AnalisisTxt txt;
	private boolean wasBtnEntropiaPressed;
	private JPanel panel;
	private JScrollPane scrollthepanelbch;
	private JLabel lblNewLabel;
	private JTextField textField_eficacia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazBasica frame = new InterfazBasica();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InterfazBasica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 150, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_selectData = new JButton("Select data");
		btn_selectData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBtnSelectData();
			}
		});
		btn_selectData.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_selectData.setBounds(89, 11, 141, 38);
		contentPane.add(btn_selectData);

		textArea_texto = new JTextArea();
		textArea_texto.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		spinner  = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner.setBounds(254, 306, 56, 26);
		spinner.setEnabled(false);
		contentPane.add(spinner);

		JScrollPane scrollpane=new JScrollPane(textArea_texto);
		scrollpane.setBounds(10, 60, 300, 235);

		contentPane.add(scrollpane);

		JLabel lbl_numCaracteresTot = new JLabel("Caracteres totales del código:");
		lbl_numCaracteresTot.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_numCaracteresTot.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_numCaracteresTot.setBounds(10, 342, 191, 23);
		contentPane.add(lbl_numCaracteresTot);

		JLabel lblLengthpalab = new JLabel("Introduzca Nº caracteres/palabra:");
		lblLengthpalab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLengthpalab.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLengthpalab.setBounds(10, 306, 234, 25);
		contentPane.add(lblLengthpalab);

		JLabel lbl_entropia = new JLabel("Entropía:");
		lbl_entropia.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_entropia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_entropia.setBounds(10, 376, 71, 23);
		contentPane.add(lbl_entropia);

		textField_setncarac = new JTextField();
		textField_setncarac.setEditable(false);
		textField_setncarac.setColumns(10);
		textField_setncarac.setBounds(211, 343, 57, 24);
		contentPane.add(textField_setncarac);

		textField_setentropia = new JTextField();
		textField_setentropia.setEditable(false);
		textField_setentropia.setColumns(10);
		textField_setentropia.setBounds(89, 376, 57, 24);
		contentPane.add(textField_setentropia);

		JLabel lblIntroduzcaCaractersPara = new JLabel("Introduzca caracter/s para calcular f+p:");
		lblIntroduzcaCaractersPara.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIntroduzcaCaractersPara.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIntroduzcaCaractersPara.setBounds(309, 306, 274, 25);
		contentPane.add(lblIntroduzcaCaractersPara);

		textField_estudiacaracter = new JTextField();
		textField_estudiacaracter.setColumns(10);
		textField_estudiacaracter.setBounds(593, 308, 57, 24);
		contentPane.add(textField_estudiacaracter);

		textField_setfrec = new JTextField();
		textField_setfrec.setEditable(false);
		textField_setfrec.setColumns(10);
		textField_setfrec.setBounds(543, 343, 57, 24);
		contentPane.add(textField_setfrec);

		JLabel lbl_frec_1 = new JLabel("Frecuencia (f):");
		lbl_frec_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_frec_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_frec_1.setBounds(403, 342, 130, 23);
		contentPane.add(lbl_frec_1);

		JLabel lbl_prob_1 = new JLabel("Probabilidad(p):");
		lbl_prob_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_prob_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_prob_1.setBounds(403, 376, 130, 23);
		contentPane.add(lbl_prob_1);

		textField_setprob = new JTextField();
		textField_setprob.setEditable(false);
		textField_setprob.setColumns(10);
		textField_setprob.setBounds(543, 377, 57, 24);
		contentPane.add(textField_setprob);

		wasBtnEntropiaPressed=false;
		btn_CalcularEntropia = new JButton("Calcular datos");
		btn_CalcularEntropia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBtnCalcularEntropia();
			}
		});
		btn_CalcularEntropia.setEnabled(false);
		btn_CalcularEntropia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_CalcularEntropia.setBounds(89, 412, 141, 38);
		contentPane.add(btn_CalcularEntropia);

		btn_CalcularFyP = new JButton("Calcular f y p");
		btn_CalcularFyP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionBtnCalcularFyP();
			}
		});
		btn_CalcularFyP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn_CalcularFyP.setEnabled(false);
		btn_CalcularFyP.setBounds(442, 410, 141, 38);
		contentPane.add(btn_CalcularFyP);


		scrollthepanelbch = new JScrollPane();
		scrollthepanelbch.setBounds(370, 60, 300, 235);
		contentPane.add(scrollthepanelbch);

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollthepanelbch.setViewportView(panel);
		
		lblNewLabel = new JLabel("Fuente");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(442, 13, 141, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lbl_eficacia_a = new JLabel("Eficacia:");
		lbl_eficacia_a.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_eficacia_a.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbl_eficacia_a.setBounds(173, 376, 71, 23);
		contentPane.add(lbl_eficacia_a);
		
		textField_eficacia = new JTextField();
		textField_eficacia.setEditable(false);
		textField_eficacia.setColumns(10);
		textField_eficacia.setBounds(254, 376, 57, 24);
		contentPane.add(textField_eficacia);
		//contentPane.add(panel);

		textField_estudiacaracter.getDocument().addDocumentListener(this);

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed(e);		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed(e);		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed(e);

	}

	public void changed(DocumentEvent e) {
		if(e.getDocument()==textField_estudiacaracter.getDocument()) {
			if (textField_estudiacaracter.getText().equals("")){
				btn_CalcularFyP.setEnabled(false);
			}
			else {
				if(wasBtnEntropiaPressed)btn_CalcularFyP.setEnabled(true);
				//btn_CalcularFyP.setEnabled(true);
			}
		}
	}


	public void accionBtnSelectData() {
		StringBuffer strbuff =new StringBuffer();
		JFileChooser fileChooser = new JFileChooser();
		int selec= fileChooser.showSaveDialog(btn_selectData);
		File file = fileChooser.getSelectedFile();
		String imgpath=file.getName();
		String extension=imgpath.substring(imgpath.lastIndexOf("."), imgpath.length());
		if(selec==JFileChooser.APPROVE_OPTION && extension.equals(".txt")) {
			try {
				String strAux;
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				strbuff =new StringBuffer();
				while((strAux=reader.readLine())!=null) {
					strbuff.append(strAux+"  ");
					textArea_texto.append(strAux+"\n");
				}
				strbuff.delete(strbuff.length()-2, strbuff.length());
				//wasBtnEntropiaPressed=true;
				spinner.setEnabled(true);
				btn_CalcularEntropia.setEnabled(true);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Datos no reconocidos. Admitido .txt");
			return;
		}
		/*
		try {
			File file = new File("datos_1.txt");
			Scanner myReader = new Scanner(file);
			strbuff =new StringBuffer();
			while (myReader.hasNextLine()) {
				strbuff.append(myReader.nextLine());
				if(myReader.hasNextLine()) strbuff.append("  ");
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}*/
		System.out.println("Input: " + strbuff.toString());
		if(strbuff.isEmpty())return;
		txt=new AnalisisTxt(0,strbuff.toString());


		SpinnerNumberModel modelNSpinner;
		modelNSpinner= new SpinnerNumberModel(1,1,txt.getDatosBase().length(),1);
		spinner.setModel(modelNSpinner);

		/*
		AnalisisTxt txt=new AnalisisTxt(strbuff.toString(),2);
		System.out.println("-1 = error\nFrec ', ': "+txt.getFrecChar(", ")+" / Prob ', ': "+txt.getProbChar(", ") + " /Entropia de F: "+txt.getEntropia());


		System.out.println("-1 = error\nFrec d: "+txt.getFrecChar("d")+" / Prob d: "+txt.getProbChar("d") + " /Entropia de F: "+txt.getEntropia());
		System.out.println(txt.mapToString(txt.sortedMap()));*/
	}

	public void accionBtnCalcularEntropia() {
		DecimalFormat df = new DecimalFormat("##.#####");
		txt.setMap((int)spinner.getValue());
		System.out.println(txt.mapToString(txt.getMapaDatos()));
		textField_setentropia.setText( String.valueOf(df.format(txt.getEntropia())) );
		textField_setncarac.setText( String.valueOf(txt.getNumCaracteres()) );
		textField_eficacia.setText(String.valueOf(df.format(txt.getEficacia((int)spinner.getValue()))));
		Map<String, Integer> mapaDatos= txt.sortedMap();

		//.setBounds(370, 60, 300, 235);
		panel.removeAll();
		scrollthepanelbch.setSize(300, 235);
		panel.setLayout(new GridLayout(mapaDatos.size()+1, 3, 5, 5));

		JLabel clblpalabra = new JLabel("Palabra");
		clblpalabra.setHorizontalAlignment(0);
		JLabel clblfrec = new JLabel("Frecuencia");
		clblfrec.setHorizontalAlignment(0);
		JLabel clblprob = new JLabel("Probabilidad");
		clblprob.setHorizontalAlignment(0);

		panel.add(clblpalabra);
		panel.add(clblfrec);
		panel.add(clblprob);

		for (Map.Entry<String, Integer> entry : mapaDatos.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			//frecAux=entry.getValue();
			//c+=(frecAux*log(frecAux, 2));
			JLabel tpalabra =new JLabel("'"+entry.getKey()+"'");
			tpalabra.setHorizontalAlignment(0);
			JLabel tfrec  =new JLabel(String.valueOf(entry.getValue()));
			tfrec.setHorizontalAlignment(0);			
			JLabel tprob = new JLabel(String.valueOf(df.format(txt.getProbChar(entry.getKey()))));
			tprob.setHorizontalAlignment(0);

			panel.add(tpalabra);
			panel.add(tfrec);
			panel.add(tprob);
		}

		SwingUtilities.updateComponentTreeUI(panel);
		textField_estudiacaracter.setText("");
		textField_setfrec.setText("");
		textField_setprob.setText("");
		wasBtnEntropiaPressed=true;
	}

	public void accionBtnCalcularFyP() {
		String caracter=textField_estudiacaracter.getText();
		DecimalFormat df = new DecimalFormat("##.#####");
		if(!txt.getMapaDatos().containsKey(caracter)) {
			textField_setfrec.setText("0");
			textField_setprob.setText("0");
			JOptionPane.showMessageDialog(null, "Palabra no encontrada");
		}
		else {
			textField_setfrec.setText(String.valueOf(txt.getFrecChar(caracter)));
			textField_setprob.setText( String.valueOf(df.format(txt.getProbChar(caracter))) );
		}

	}
}
