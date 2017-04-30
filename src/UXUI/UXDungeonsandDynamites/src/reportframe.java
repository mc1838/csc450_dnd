import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class reportframe extends JFrame {

	protected static final Component frame = null;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					reportframe frame = new reportframe();
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
	public reportframe() {
		URL iconURL = getClass().getResource("Dynamite.png");
		ImageIcon icon= new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		setTitle("Dungeons And Dynamites");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnFile.add(mntmNewMenuItem);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmFaq = new JMenuItem("FAQ");
		mntmFaq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Dungeon and Dynamites FAQ \n\n How do you become the dungeon master? \n "
						+ "Bruh I don't know \n\n [next question] \n\n");
			}
		});
		mnHelp.add(mntmFaq);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnReportPane = new JTextPane();
		//txtpnReportPane.setText("Character Stats");
		txtpnReportPane.setBounds(188, 11, 236, 218);
		contentPane.add(txtpnReportPane);
		
		JLabel lblHeading = new JLabel("Dungeons And Dynamites");
		lblHeading.setFont(new Font("AR CHRISTY", Font.BOLD, 15));
		lblHeading.setBounds(0, 11, 178, 32);
		contentPane.add(lblHeading);
		
		JButton btngetreport = new JButton("Get Report");
		btngetreport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					FileReader fr= new FileReader("Report.txt");
					BufferedReader br= new BufferedReader(fr);
					
					String str;
					while ((str= br.readLine()) != null){
						txtpnReportPane.setText(str + "\n");
					}
					br.close();
				}catch (IOException args1){
					System.out.println("File not found");
				}
			}
		});
		btngetreport.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btngetreport.setBounds(23, 60, 100, 32);
		contentPane.add(btngetreport);
		
		JButton btnlogout = new JButton("Log out");
		btnlogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DungeonsAndDynamites DnD= new DungeonsAndDynamites();
				DnD.setVisible(true);
				
				dispose();
			}
		});
		btnlogout.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnlogout.setBounds(23, 120, 100, 32);
		contentPane.add(btnlogout);
		
		
	}
}
