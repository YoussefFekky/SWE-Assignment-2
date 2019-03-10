package p2p;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Peer {

	private JFrame frame;
	private JTextField Sockettxt;
	private JTextField IPtxt;
	private JTextField Messagetxt;
	private JTextArea chat;
	MulticastReceiver multicastReceiver;
	MulticastPublisher multicastPublisher;
	
	public void updateChat(String buffer, JTextArea txt) {
		txt.append(buffer + System.lineSeparator());
	}
	public void updateChat(String buffer) {
		updateChat(buffer, chat);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Peer window = new Peer();
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
	public Peer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 577, 524);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Sockettxt = new JTextField();
		Sockettxt.setBounds(314, 52, 116, 22);
		frame.getContentPane().add(Sockettxt);
		Sockettxt.setColumns(10);
		
		IPtxt = new JTextField();
		IPtxt.setBounds(12, 52, 290, 22);
		frame.getContentPane().add(IPtxt);
		IPtxt.setColumns(10);
		
		JLabel IPlbl = new JLabel("IP");
		IPlbl.setBounds(12, 23, 56, 16);
		frame.getContentPane().add(IPlbl);
		
		JLabel Socketlbl = new JLabel("Socket");
		Socketlbl.setBounds(314, 23, 56, 16);
		frame.getContentPane().add(Socketlbl);
		
		chat = new JTextArea();
		chat.setEditable(false);
		chat.setBounds(12, 87, 527, 280);
		frame.getContentPane().add(chat);
		chat.setColumns(10);
		
		Messagetxt = new JTextField();
		Messagetxt.setBounds(12, 380, 527, 22);
		frame.getContentPane().add(Messagetxt);
		Messagetxt.setColumns(10);
		
		Peer pointer = this;
		
		JButton Listenbtn = new JButton("Listen");
		Listenbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multicastReceiver = new MulticastReceiver();
				try {
					multicastReceiver.setGroup(IPtxt.getText());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				try {
					multicastReceiver.setSocket(Integer.parseInt(Sockettxt.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				multicastReceiver.setPeer(pointer);
				multicastPublisher = new MulticastPublisher();
				try {
					multicastPublisher.setGroup(IPtxt.getText());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				try {
					multicastPublisher.setSocket(Integer.parseInt(Sockettxt.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						multicastReceiver.run();
					}
				}).start();
			}
		});
		Listenbtn.setBounds(442, 51, 97, 25);
		frame.getContentPane().add(Listenbtn);
		
		JButton Sendbtn = new JButton("Send");
		Sendbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					multicastPublisher.multicast(Messagetxt.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				Messagetxt.setText(null);
			}
		});
		Sendbtn.setBounds(12, 415, 97, 25);
		frame.getContentPane().add(Sendbtn);
		
	}
}
