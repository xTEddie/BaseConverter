import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Robot;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 * @author Edward Tran
 *
 */

@SuppressWarnings("serial")
public class BaseConverterGUI extends JFrame implements KeyListener, MouseListener, FocusListener{

	public static final int WIDTH = 350;
	public static final int HEIGHT = 270;
	public static final String[] BASELIST = {"Base 2 (Binary)",
		"Base 3 (Ternary",
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
	private JComboBox<String> inputBox;
	private JTextField inputText;
	private JComboBox<String> outputBox;
	private JTextField outputText;
	private String number = null;
	private int inputBase = -1;
	private int outputBase = -1;
	private boolean vk1_IsPressed = false;
	long vk1Pressed;

	public BaseConverterGUI() throws IOException{
		super("Base^ Converter");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null); // Center window
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Menu
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

		JPanel parentPane = new JPanel();
		parentPane.setLayout(new BoxLayout(parentPane, 1));
		parentPane.setBorder(BorderFactory.createEmptyBorder(10,8,10,8));
		parentPane.setBackground(new Color(169,198,236)); 


		// Input Pane
		JPanel inputPane = new JPanel();
		inputPane.setBackground(new Color(169,198,236)); 
		inputPane.setBorder(BorderFactory.createTitledBorder("Input"));
		JLabel inputLabel = new JLabel("Input Base:");
		inputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		inputBox = new JComboBox<String>(BASELIST); // Dropdown
		inputBox.addKeyListener(new QuitProgram());
		inputBox.addKeyListener(this);
		inputBox.addFocusListener(this);
		JLabel inputValueLabel = new JLabel("Input Value:");
		inputValueLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		inputText = new JTextField(15);
		inputText.addKeyListener(new QuitProgram());
		inputText.addKeyListener(this);
		inputText.addFocusListener(this);

		// Output Pane
		JPanel outputPane = new JPanel();
		outputPane.setBackground(new Color(169,198,236)); 
		outputPane.setBorder(BorderFactory.createTitledBorder("Output"));
		JLabel outputLabel = new JLabel("Output Base:");
		outputLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		outputBox = new JComboBox<String>(BASELIST); // Dropdown
		outputBox.addKeyListener(new QuitProgram());
		outputBox.addKeyListener(this);
		outputBox.addFocusListener(this);
		JLabel outputValueLabel = new JLabel("Output Value:");
		outputValueLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,50));
		outputText = new JTextField(15);
		outputText.setEnabled(false);

		converter.add(quit);
		help.add(about);
		bar.add(converter);
		bar.add(help);
		this.setJMenuBar(bar);

		inputPane.add(inputLabel);
		inputPane.add(inputBox);
		inputPane.add(inputValueLabel);
		inputPane.add(inputText);
		parentPane.add(inputPane);
		parentPane.add(Box.createRigidArea(new Dimension(0,10)));
		parentPane.add(outputPane);
		outputPane.add(outputLabel);
		outputPane.add(outputBox);
		outputPane.add(outputValueLabel);
		outputPane.add(outputText);

		this.add(parentPane);
		this.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e);
		if(e.getKeyCode() == KeyEvent.VK_1){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 200 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(9);
				vk1_IsPressed = false;
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 200 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(9);
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
			if(e.getSource().equals(inputBox)){
				inputBase = inputBox.getSelectedIndex() + 2;
			}else if(e.getSource().equals(inputText)){
				number = inputText.getText();
			}else if(e.getSource().equals(outputBox)){
				outputBase = outputBox.getSelectedIndex() + 2;
			}

			// Convert
			if(number != null && inputBase != -1 && outputBase != -1){	
				outputText.setText(BaseConverter.convertBase(number, inputBase, outputBase));
			}
		}else if(e.getKeyCode() == KeyEvent.VK_2){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(10);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(10);	
			}else if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(0);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(0);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_3){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(11);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(11);	
			}else if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(1);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(1);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_4){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(12);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(12);	
			}else if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(2);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(2);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_5){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(13);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(13);	
			}else if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(3);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(3);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_6){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(14);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(14);	
			}else if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(4);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(4);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_7){
			if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(5);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(5);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_8){
			if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(6);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(6);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_9){
			if(e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(7);
			}else if(e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(7);
			}
		}else if (e.getKeyCode() == KeyEvent.VK_0){
			if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 100 && e.getSource().equals(inputBox)){
				inputBox.setSelectedIndex(8);	
			}else if(vk1_IsPressed && (System.currentTimeMillis()-vk1Pressed) <= 1000 && e.getSource().equals(outputBox)){
				outputBox.setSelectedIndex(8);	
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

	}

	@Override
	public void focusLost(FocusEvent e) {
		//		System.out.println(e);
		if(e.getSource().equals(inputBox)){
			inputBase = inputBox.getSelectedIndex() + 2;
		}else if(e.getSource().equals(inputText)){
			number = inputText.getText();
		}else if(e.getSource().equals(outputBox)){
			outputBase = outputBox.getSelectedIndex() + 2;
		}

		// Convert
		if(number != null && inputBase != -1 && outputBase != -1){
			outputText.setText(BaseConverter.convertBase(number, inputBase, outputBase));
		}

	}
}
