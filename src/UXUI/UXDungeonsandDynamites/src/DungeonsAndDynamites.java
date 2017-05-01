/**
 * Dungeons and Dynamites desktop application for a text based game.
 * author Max , Shanade Beharry, Ronald Chaplin
 * 
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JCheckBox;

public class DungeonsAndDynamites extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Component frame = null;
	private JPanel contentPane;
	private JTextField Playerid;
	public String uId = "";
	
	String connectionString = 
    		"jdbc:sqlserver://csc450.database.windows.net:1433;" + 
    		"database=testdb;" + 
    		"user=mc1838@csc450;password=Project450;" + 
    		"encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    String connectionString_GameDB = 
    		"jdbc:sqlserver://csc450.database.windows.net:1433;" + 
    		"database=GameDB;" + 
    		"user=mc1838@csc450;password=Project450;" + 
    		"encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	private Connect connectClass = new Connect(connectionString);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonsAndDynamites frame = new DungeonsAndDynamites();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame and contains the action listeners required to communicate with the database.
	 */
	public DungeonsAndDynamites() {
		URL iconURL = getClass().getResource("Dynamite.png"); //Puts the icon I made onto the JFrame so it wouldn't have that weird tea cup logo.
		ImageIcon icon= new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setTitle("Dungeons and Dynamites");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnFile.add(mntmPrint);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmFaq = new JMenuItem("FAQ");
		mntmFaq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Dungeon and Dynamites FAQ \n\n How do you log in? \n "
						+ "For Login: Enter a Player or DM ID and click Login\n"
						+ "For Register: Simply click Register and \n"
						+ "a new ID will be generated");
			}
		});
		mnHelp.add(mntmFaq);
		
		Playerid = new JTextField();
		Playerid.setBounds(138, 79, 145, 30);
		contentPane.add(Playerid);
		Playerid.setColumns(10);
		
		JLabel idlabel = new JLabel("PLAYER ID");
		idlabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		idlabel.setBounds(56, 86, 72, 16);
		contentPane.add(idlabel);
		
		JCheckBox chckbxDm = new JCheckBox("DM");
		chckbxDm.setBounds(138, 116, 97, 23);
		contentPane.add(chckbxDm);
		
		//Here will need to have SQL connection and try-catch block. 
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String uId= Playerid.getText();
				boolean isValid = false;
				//NEW
				if (!chckbxDm.isSelected() && !uId.isEmpty())
				{
					isValid = connectClass.login(uId);
					if (isValid)
					{
						JOptionPane.showMessageDialog(frame, "Login Successful");
						//create object
						reportframe reportframe= new reportframe(uId); //need to pass in uId!
						reportframe.setVisible(true);
						
						dispose(); //Closes the login window so the Report window will appear. Wasn't sure how to have a new Panel appear, hope this is ok.
					}
					
				}
				
				else if (chckbxDm.isSelected() && !uId.isEmpty())
				{
					isValid = connectClass.loginDM(uId);
					if (isValid)
					{
						JOptionPane.showMessageDialog(frame, "Login Successful, DUNGEON MASTER!");
						//create object
						reportframe reportframe= new reportframe(uId); //need to pass in uId!
						reportframe.setVisible(true);
						
						dispose();
					}
					
				}
				
				else if ((uId.isEmpty()) || (!isValid)) //empty field or invalid by proof
				{
					JOptionPane.showMessageDialog(frame, "Invalid Player ID");
					Playerid.setText("");
					uId = "";
				}
				
				else if (!isValid)
				{
					JOptionPane.showMessageDialog(frame, "Invalid Player ID");
					Playerid.setText("");
					uId = "";
				}
				
			}
		});
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLogin.setBounds(93, 178, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		//NEW
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int newId = connectClass.register();
				JOptionPane.showMessageDialog(frame, "Registration successful. New ID: " + newId);
				reportframe reportframe= new reportframe(String.valueOf(newId)); //need to pass in uId!
				reportframe.setVisible(true);
				dispose();
			}
		});
		btnRegister.setBackground(Color.LIGHT_GRAY);
		btnRegister.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnRegister.setBounds(228, 178, 107, 23);
		contentPane.add(btnRegister);
		
	}
}
