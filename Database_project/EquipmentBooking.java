package proj;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class EquipmentBooking extends JFrame {

	static String[] variety = new String[] {"Basketball","Football","Tennis Racket","Tennis","Badminton Racket","Badminton","Table Tennis Bats","Table Tennis","Volleyball"};
	private JPanel contentPane;

	private JTextField txtAvaliableSites;

	
	/**
	 * Launch the application.
	 */
	static EquipmentBooking frame = new EquipmentBooking();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
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
	public EquipmentBooking() {
		setTitle("SUSTech Sports Booking Syetem");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 709, 513);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBookingRules = new JButton("Booking Rules");
		btnBookingRules.setFont(new Font("宋体", Font.PLAIN, 11));
		btnBookingRules.setHorizontalAlignment(SwingConstants.LEFT);
		btnBookingRules.setBounds(0, 114, 113, 23);
		contentPane.add(btnBookingRules);
		
		JButton btnNewButton = new JButton("Sports Booking");
	//	btnNewButton.setSelectedIcon(new ImageIcon(SportsBooking.class.getResource("/org/eclipse/jface/preference/images/pref_dialog_title.png")));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 11));
		btnNewButton.setBounds(112, 114, 130, 23);
		contentPane.add(btnNewButton);
				
		 
		
		JButton btnNewButton_1 = new JButton("Equipment Borrowing");
		btnNewButton_1.setBackground(Color.DARK_GRAY);
	//	btnNewButton_1.setSelectedIcon(new ImageIcon(SportsBooking.class.getResource("/org/eclipse/jface/contentassist/images/content_assist_cue@2x.png")));
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 11));
		btnNewButton_1.setBounds(241, 114, 155, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Instructor Appoinment");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 11));
		btnNewButton_2.setBounds(396, 114, 171, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnMy = new JButton("My Information");
		btnMy.setFont(new Font("宋体", Font.PLAIN, 11));
		btnMy.setBounds(563, 114, 130, 23);
		contentPane.add(btnMy);
		
		
		JComboBox comboBox = new JComboBox();
		for(int i = 0 ; i < SportsBooking.listData.length-1; i++) {
			comboBox.addItem(SportsBooking.listData[i]);
		}
		comboBox.setBounds(241, 147, 82, 21);
		contentPane.add(comboBox);
		
		
		
		JComboBox comboBox_1 = new JComboBox();
		for(int i = 1 ; i < SportsBooking.listData.length; i++) {
			comboBox_1.addItem(SportsBooking.listData[i]);
		}
		comboBox_1.setBounds(362, 147, 82, 21);
		contentPane.add(comboBox_1);
		
	
		 comboBox.addItemListener(new ItemListener() {	           
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getStateChange() == ItemEvent.SELECTED) 
						SportsBooking.starttime = comboBox.getSelectedItem().toString();	
					
					
				}
	        });
	        comboBox.setSelectedIndex(0);
	        
	        comboBox_1.addItemListener(new ItemListener() {	           
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if (e.getStateChange() == ItemEvent.SELECTED) 
						SportsBooking.endtime = comboBox_1.getSelectedItem().toString();
				}
	        });
	        comboBox_1.setSelectedIndex(0);
		
	        JComboBox comboBox_2 = new JComboBox();
			comboBox_2.setBounds(496, 147, 113, 21);
			for(int i = 0 ; i < variety.length; i++) {
				comboBox_2.addItem(variety[i]);
			}

			contentPane.add(comboBox_2);
			
			comboBox_2.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					if(e.getStateChange() == ItemEvent.SELECTED) SportsBooking.item_kind = comboBox_2.getSelectedItem().toString();
				}
			});
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(67, 147, 130, 21);
		for(int i = 0; i < 5; i++) {
			comboBox_3.addItem(SportsBooking.date.format(SportsBooking.getDate(i)));
		}
		contentPane.add(comboBox_3);
comboBox_3.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) SportsBooking.da = (String) comboBox_3.getSelectedItem();
			}
		});
		
		JButton btnNewButton_3 = new JButton("Query");
		btnNewButton_3.setBounds(619, 147, 74, 23);
		contentPane.add(btnNewButton_3);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 180, 619, 261);
		contentPane.add(scrollPane);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane.setViewportView(textArea_1);
		
		txtAvaliableSites = new JTextField();
		txtAvaliableSites.setEditable(false);
		txtAvaliableSites.setBackground(Color.PINK);
		txtAvaliableSites.setText("Avaliable Variaties");
		txtAvaliableSites.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(txtAvaliableSites);
		txtAvaliableSites.setColumns(10);
		
btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//查询并显示
				Database.t = 1;
				Database.s = 2;
				Database.k = 1;
				Database.backend();
				for(int i = 0; i < SportsBooking.info.size(); i++) {
					textArea_1.append(SportsBooking.info.get(i)+"\r\n");
				}
			}
		});

btnNewButton.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.setVisible(false);
   	 try {
     	   SportsBooking.frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
});
btnNewButton_2.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.setVisible(false);
   	 try {
     	        InstructorBooking.frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
});
btnBookingRules.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.setVisible(false);
   	 try {
     	   UserInterface.frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
});
btnMy.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		frame.setVisible(false);
   	 try {
     	        MyInformation.frame.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}
});

JButton btnBook = new JButton("Book");
btnBook.setBounds(314, 451, 93, 23);
contentPane.add(btnBook);

btnBook.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Database.t = 2;
		Database.s = 2;
		if(SportsBooking.location == "All") Database.k = 1;
		else Database.k = 2;
		SportsBooking.specific_gym = SportsBooking.location + " " + SportsBooking.field_type + " " + SportsBooking.num;
		Database.backend();
		if(!SportsBooking.valid) JOptionPane.showMessageDialog(frame, "Booking failed", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			JOptionPane.showMessageDialog(frame, "Booking successfully", "Feedback", JOptionPane.INFORMATION_MESSAGE);
		}
		for(int i = 0; i < SportsBooking.info.size(); i++) {
			textArea_1.append(SportsBooking.info.get(i)+"\r\n");
		}
	}
});
JComboBox comboBox_4 = new JComboBox();
comboBox_4.setBounds(67, 452, 105, 21);
for(int i = 0; i < SportsBooking.positions.length; i++) {
	comboBox_4.addItem(SportsBooking.positions[i]);
}
contentPane.add(comboBox_4);

comboBox_4.addItemListener(new ItemListener() {
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) SportsBooking.location = comboBox_4.getSelectedItem().toString();
	}
});

JComboBox comboBox_5 = new JComboBox();
comboBox_5.setBounds(241, 452, 41, 21);
    comboBox_5.addItem(null);
for(int i = 1 ; i <= 10; i++) {
	comboBox_5.addItem(i);
}
contentPane.add(comboBox_5);




		JLabel lblSites = new JLabel("Variaty");
		lblSites.setBounds(454, 150, 42, 15);
		contentPane.add(lblSites);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setBounds(341, 150, 23, 15);
		contentPane.add(lblEnd);
		
		JLabel lblBegin = new JLabel("Begin");
		lblBegin.setBounds(207, 150, 45, 15);
		contentPane.add(lblBegin);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(42, 150, 30, 15);
		contentPane.add(lblDate);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("D:\\Database System\\Project\\8.jpg"));
		label.setBounds(0, 0, 693, 117);
		contentPane.add(label);
		
		JLabel lblNewLabel_1 = new JLabel("Quantity");
		lblNewLabel_1.setBounds(182, 455, 60, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Location");
		lblNewLabel.setBounds(10, 455, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("");
		label_1.setForeground(Color.BLACK);
		label_1.setIcon(new ImageIcon("D:\\Database System\\Project\\T1OVC_BvdT1RCvBVdK.jpg"));
		label_1.setBounds(-31, 137, 724, 337);
		contentPane.add(label_1);
		
		
		
		
		
	}
}
