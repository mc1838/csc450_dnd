import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class DungeonsAndDynamites extends JFrame {

	private JPanel contentPane;
	private JTextField UserName;
	private JTextField Password;

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
	 * Create the frame.
	 */
	public DungeonsAndDynamites() {
		setType(Type.UTILITY);
		setTitle("Dungeons and Dynamites");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		UserName = new JTextField();
		UserName.setBounds(138, 79, 145, 30);
		contentPane.add(UserName);
		UserName.setColumns(10);
		
		Password = new JTextField();
		Password.setBounds(138, 134, 145, 30);
		contentPane.add(Password);
		Password.setColumns(10);
		
		JLabel namelabel = new JLabel("USERNAME");
		namelabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		namelabel.setBounds(59, 85, 79, 16);
		contentPane.add(namelabel);
		
		JLabel pwlabel = new JLabel("PASSWORD");
		pwlabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		pwlabel.setBounds(59, 142, 79, 14);
		contentPane.add(pwlabel);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnLogin.setBounds(93, 178, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBackground(Color.LIGHT_GRAY);
		btnRegister.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnRegister.setBounds(228, 178, 107, 23);
		contentPane.add(btnRegister);
	}
}
