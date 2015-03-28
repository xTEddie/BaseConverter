import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 * @author Edward Tran
 *
 */

@SuppressWarnings("serial")
public class BaseConverterGUI extends JFrame implements ActionListener, KeyListener, MouseListener, FocusListener{

	public static final int WIDTH = 350;
	public static final int HEIGHT = 290;
	public static final String[] BASELIST = {"Base 2 (Binary)",
		"Base 3 (Ternary)",
		"Base 4 (Quartenary)",
		"Base 5 (Qinary)",
		"Base 6 (Senary)",
		"Base 7 (Septenary)",
		"Base 8 (Octal)",
		"Base 9 (Nonary)",
		"Base 10 (Decimal)",
		"Base 11 (Undecimal)",
		"Base 12 (Duodecimal)",
		"Base 13 (Tridecimal)",
		"Base 14 (Tetradecimal)",
		"Base 15 (Pentadecimal)",
		"Base 16 (Hexadecimal)",
	};
	private JMenu converter;
	private JMenu help;
	private JComboBox<String> converterInputBox;
	private JTextField converterInputText;
	private JComboBox<String> converterOutputBox;
	private JTextField converterOutputText;
	private String number = "";
	private int inputBase = -1;
	private int outputBase = -1;
	private boolean vk1_IsPressed = false;
	private long vk1Pressed;
	
	private JTextField oneCompInputText;
	private JTextField oneCompOutputText;
	private JButton oneCompCopyButton;
	
	private JTextField twoCompInputText;
	private JTextField twoCompOutputText;
	private JButton twoCompCopyButton;

	public BaseConverterGUI() throws IOException{
		super("Base^ Converter");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null); // Center window
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		/***********\
		|	MENU	|
		\***********/
		
		JMenuBar bar = new JMenuBar();
		bar.setBackground(new Color(169,198,236)); 
		//		bar.setBackground(new Color(218,224,241));
		converter = new JMenu("Converter");
		converter.setMnemonic('c'); // ALT + C shortcut
		converter.addMouseListener(this);
		help = new JMenu("Help");
		help.setMnemonic('h'); // ALT + H shorcut
		help.addMouseListener(this);
		JMenuItem quit = new JMenuItem("Quit", KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK)); // CTRL + Q shortcut
		quit.addActionListener(new QuitProgram());
		JMenuItem about = new JMenuItem("Visit Website", KeyEvent.VK_V);
		about.addActionListener(new OpenBrowser()); // Open website
		
		converter.add(quit);
		help.add(about);
		bar.add(converter);
		bar.add(help);
		this.setJMenuBar(bar);
		
		// Tab
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addKeyListener(this);
		tabbedPane.addKeyListener(new QuitProgram());
		
		/*******************\
		|	BASE CONVERTER	|
		\*******************/

		JPanel converterPane = new JPanel();
		converterPane.setLayout(new BoxLayout(converterPane, 1));
		converterPane.setBorder(BorderFactory.createEmptyBorder(10,8,10,8));
		converterPane.setBackground(new Color(169,198,236)); 

		// Input Pane
		JPanel converterInputPane = new JPanel();
		converterInputPane.setBackground(new Color(169,198,236)); 
		converterInputPane.setBorder(BorderFactory.createTitledBorder("Input"));
		JLabel converterInputLabel = new JLabel("Base:");
		converterInputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		converterInputBox = new JComboBox<String>(BASELIST); // Dropdown
		converterInputBox.addKeyListener(new QuitProgram());
		converterInputBox.addKeyListener(this);
		converterInputBox.addFocusListener(this);
		JLabel converterInputValueLabel = new JLabel("Value:");
		converterInputValueLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		converterInputText = new JTextField(15);
		converterInputText.addKeyListener(new QuitProgram());
		converterInputText.addKeyListener(this);
		converterInputText.addFocusListener(this);

		// Output Pane
		JPanel converterOutputPane = new JPanel();
		converterOutputPane.setBackground(new Color(169,198,236)); 
		converterOutputPane.setBorder(BorderFactory.createTitledBorder("Output"));
		JLabel converterOutputLabel = new JLabel("Base:");
		converterOutputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		converterOutputBox = new JComboBox<String>(BASELIST); // Dropdown
		converterOutputBox.addKeyListener(new QuitProgram());
		converterOutputBox.addKeyListener(this);
		converterOutputBox.addFocusListener(this);
		JLabel converterOutputValueLabel = new JLabel("Value:");
		converterOutputValueLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		converterOutputText = new JTextField(15);
		converterOutputText.setEnabled(false);

		converterInputPane.add(converterInputLabel);
		converterInputPane.add(converterInputBox);
		converterInputPane.add(converterInputValueLabel);
		converterInputPane.add(converterInputText);
		converterPane.add(converterInputPane);
		converterPane.add(Box.createRigidArea(new Dimension(0,10)));
		converterPane.add(converterOutputPane);
		converterOutputPane.add(converterOutputLabel);
		converterOutputPane.add(converterOutputBox);
		converterOutputPane.add(converterOutputValueLabel);
		converterOutputPane.add(converterOutputText);
		
		/***********************\
		|	1'S COMPLEMENT TAB	|
		\***********************/
		
		JPanel oneComplementTab = new JPanel();
		oneComplementTab.setBackground(new Color(169,198,236));
		oneComplementTab.setBorder(BorderFactory.createEmptyBorder(10,8,10,8));
		JPanel oneCompInputPane = new JPanel();
		oneCompInputPane.setBackground(new Color(169,198,236));
		oneCompInputPane.setBorder(BorderFactory.createTitledBorder("Binary Input"));
		JLabel oneCompInputLabel = new JLabel("Value:");
		oneCompInputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,90));
		oneCompInputText = new JTextField(15);
		oneCompInputText.addKeyListener(new QuitProgram());
		oneCompInputText.addKeyListener(this);
		oneCompInputText.addFocusListener(this);
		JPanel oneCompOutputPane = new JPanel();
		oneCompOutputPane.setBackground(new Color(169,198,236));
		oneCompOutputPane.setBorder(BorderFactory.createTitledBorder("Binary Output"));
		JLabel oneCompOutputLabel = new JLabel("1's Complement:");
		oneCompOutputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,30));
		oneCompOutputText = new JTextField(15);
		oneCompOutputText.setEnabled(false);
		oneCompCopyButton = new JButton("Copy Output to Clipboard");
		oneCompCopyButton.addActionListener(this);

		oneCompInputPane.add(oneCompInputLabel);
		oneCompInputPane.add(oneCompInputText);
		oneCompOutputPane.add(oneCompOutputLabel);
		oneCompOutputPane.add(oneCompOutputText);
		oneComplementTab.add(oneCompInputPane);
		oneComplementTab.add(oneCompOutputPane);
		oneComplementTab.add(Box.createRigidArea(new Dimension(0,70)));
		oneComplementTab.add(oneCompCopyButton);
		
		/***********************\
		|	2'S COMPLEMENT TAB	|
		\***********************/
		
		JPanel twoComplementTab = new JPanel();
		twoComplementTab.setBackground(new Color(169,198,236));
		twoComplementTab.setBorder(BorderFactory.createEmptyBorder(10,8,10,8));
		JPanel twoCompInputPane = new JPanel();
		twoCompInputPane.setBackground(new Color(169,198,236));
		
		twoCompInputPane.setBorder(BorderFactory.createTitledBorder("Binary Input"));
		JLabel twoCompInputLabel = new JLabel("Value:");
		twoCompInputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,90));
		twoCompInputText = new JTextField(15);
		twoCompInputText.addKeyListener(new QuitProgram());
		twoCompInputText.addKeyListener(this);
		twoCompInputText.addFocusListener(this);
		JPanel twoCompOutputPane = new JPanel();
		twoCompOutputPane.setBackground(new Color(169,198,236));
		twoCompOutputPane.setBorder(BorderFactory.createTitledBorder("Binary Output"));
		JLabel twoCompOutputLabel = new JLabel("2's Complement");
		twoCompOutputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,30));
		twoCompOutputText = new JTextField(15);
		twoCompOutputText.setEnabled(false);
		twoCompCopyButton = new JButton("Copy Output to Clipboard");
		twoCompCopyButton.addActionListener(this);

		twoCompInputPane.add(twoCompInputLabel);
		twoCompInputPane.add(twoCompInputText);
		twoCompOutputPane.add(twoCompOutputLabel);
		twoCompOutputPane.add(twoCompOutputText);
		twoComplementTab.add(twoCompInputPane);
		twoComplementTab.add(twoCompOutputPane);
		twoComplementTab.add(Box.createRigidArea(new Dimension(0,70)));
		twoComplementTab.add(twoCompCopyButton);
		
		//
		tabbedPane.addTab("Converter", converterPane);
		tabbedPane.addTab("1's Complement", oneComplementTab);
		tabbedPane.addTab("2's Complement", twoComplementTab);
		
		this.add(tabbedPane);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(oneCompCopyButton)){
			StringSelection stringSelection = new StringSelection(oneCompOutputText.getText());
			Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipBoard.setContents(stringSelection, null);
		}else if(e.getSource().equals(twoCompCopyButton)){
			StringSelection stringSelection = new StringSelection(twoCompOutputText.getText());
			Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipBoard.setContents(stringSelection, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//		System.out.println(e);
		if(e.getKeyCode() == KeyEvent.VK_1){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 200 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(9);
				vk1_IsPressed = false;
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 200 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(9);
				vk1_IsPressed = false;
			}else{
				vk1_IsPressed = true;
			}
			vk1Pressed = System.currentTimeMillis();
		}

		if(e.getKeyCode() == KeyEvent.VK_ALT){
			e.consume(); // Remove default manner of alt key 
			if(!converter.isOpaque() && !help.isOpaque()){
				converter.setOpaque(true);
				converter.setBackground(new Color(218,224,241));
				help.setOpaque(true);
				help.setBackground(new Color(218,224,241));
			}else{
				converter.setOpaque(false);
				converter.setBackground(null);
				help.setOpaque(false);
				help.setBackground(null);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_C){
			if(converter.isOpaque() && help.isOpaque()){
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_C);
				robot.keyRelease(KeyEvent.VK_ALT);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_H){
			if(converter.isOpaque() && help.isOpaque()){
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_H);
				robot.keyRelease(KeyEvent.VK_ALT);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(e.getSource().equals(converterInputBox)){
				inputBase = converterInputBox.getSelectedIndex() + 2;
			}else if(e.getSource().equals(converterInputText)){
				number = converterInputText.getText();
			}else if(e.getSource().equals(converterOutputBox)){
				outputBase = converterOutputBox.getSelectedIndex() + 2;
			}else if(e.getSource().equals(oneCompInputText)){
				if(!oneCompInputText.getText().isEmpty() && BaseConverter.isValidBase(oneCompInputText.getText(), 2))
					oneCompOutputText.setText(OneComplement.oneComplement(oneCompInputText.getText()));
				else
					oneCompOutputText.setText("Invalid");
			}else if(e.getSource().equals(twoCompInputText)){
				if(!twoCompInputText.getText().isEmpty() && BaseConverter.isValidBase(twoCompInputText.getText(), 2))
					twoCompOutputText.setText(TwoComplement.twoComplement(twoCompInputText.getText()));
				else
					twoCompOutputText.setText("Invalid");
			}

			// Convert
			if(!number.isEmpty() && inputBase != -1 && outputBase != -1){
				if(BaseConverter.isValidBase(number, inputBase))
					converterOutputText.setText(BaseConverter.convertBase(number, inputBase, outputBase));
				else
					converterOutputText.setText("Invalid");
			}

		}else if(e.getKeyCode() == KeyEvent.VK_2){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(10);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(10);	
			}else if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(0);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(0);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_3){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(11);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(11);	
			}else if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(1);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(1);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_4){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(12);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(12);	
			}else if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(2);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(2);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_5){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(13);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(13);	
			}else if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(3);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(3);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_6){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(14);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(14);	
			}else if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(4);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(4);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_7){
			if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(5);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(5);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_8){
			if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(6);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(6);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_9){
			if(e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(7);
			}else if(e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(7);
			}
		}else if (e.getKeyCode() == KeyEvent.VK_0){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(converterInputBox)){
				converterInputBox.setSelectedIndex(8);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(converterOutputBox)){
				converterOutputBox.setSelectedIndex(8);	
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource().equals(converter)){
			converter.setOpaque(true);
			converter.setBackground(new Color(218,224,241));
		}else if(e.getSource().equals(help)){
			help.setOpaque(true);
			help.setBackground(new Color(218,224,241));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource().equals(converter)){
			converter.setOpaque(false);
			converter.setBackground(null);
		}else if(e.getSource().equals(help)){
			help.setOpaque(false);
			help.setBackground(null);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		//		System.out.println(e.getSource());
		//		System.out.println(number.isEmpty());

	}

	@Override
	public void focusLost(FocusEvent e) {
//		System.out.println(e);
		if(e.getSource().equals(converterInputBox)){
			inputBase = converterInputBox.getSelectedIndex() + 2;
		}else if(e.getSource().equals(converterInputText)){
			if(!converterInputText.getText().isEmpty())
				number = converterInputText.getText();
		}else if(e.getSource().equals(converterOutputBox)){
			outputBase = converterOutputBox.getSelectedIndex() + 2;
		}else if(e.getSource().equals(oneCompInputText)){
			if(!oneCompInputText.getText().isEmpty() && BaseConverter.isValidBase(oneCompInputText.getText(), 2))
				oneCompOutputText.setText(OneComplement.oneComplement(oneCompInputText.getText()));
			else
				oneCompOutputText.setText("Invalid");
		}else if(e.getSource().equals(twoCompInputText)){
			if(!twoCompInputText.getText().isEmpty() && BaseConverter.isValidBase(twoCompInputText.getText(), 2))
				twoCompOutputText.setText(TwoComplement.twoComplement(twoCompInputText.getText()));
			else
				twoCompOutputText.setText("Invalid");
		}

		// Convert
		if(!number.isEmpty() && inputBase != -1 && outputBase != -1){
			if(BaseConverter.isValidBase(number, inputBase))
				converterOutputText.setText(BaseConverter.convertBase(number, inputBase, outputBase));
			else
				converterOutputText.setText("Invalid");
		}
	}
	
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		BaseConverterGUI gui = new BaseConverterGUI();
	}

}
