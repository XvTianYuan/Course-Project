package proj;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class MyInformation extends JFrame {
	
	private JPanel contentPane;
    static MyInformation frame = new MyInformation();
    private JTextField txtMyBookingMessage;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_3;
	/**
	 * Launch the application.
	 */
   public static String rst = "";
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
	public MyInformation() {
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
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 11));
		btnNewButton.setBounds(112, 114, 130, 23);
		contentPane.add(btnNewButton);
				
		 
		
		JButton btnNewButton_1 = new JButton("Equipment Borrowing");
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
		btnMy.setBackground(Color.DARK_GRAY);
		btnMy.setFont(new Font("宋体", Font.PLAIN, 11));
		btnMy.setBounds(563, 114, 130, 23);
		contentPane.add(btnMy);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(273, 157, 34, 15);
		contentPane.add(lblName);
		
		JLabel lblGender = new JLabel("Credit");
		lblGender.setBounds(273, 236, 54, 15);
		contentPane.add(lblGender);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(273, 194, 28, 15);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("D:\\Database System\\Project\\no_image.gif"));
		lblNewLabel.setBounds(43, 155, 187, 126);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnEquipment = new JRadioButton("Equipment");
		rdbtnEquipment.setBounds(196, 445, 85, 23);
		contentPane.add(rdbtnEquipment);
		
		JRadioButton rdbtnInstructor = new JRadioButton("Instructor");
		rdbtnInstructor.setBounds(324, 445, 85, 23);
		contentPane.add(rdbtnInstructor);
		

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Field");
		rdbtnNewRadioButton.setBounds(94, 445, 61, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnEquipment );
		group.add(rdbtnInstructor );
		group.add(rdbtnNewRadioButton );
        
		
		JButton btnNewButton_3 = new JButton("Cancel");
		btnNewButton_3.setBounds(475, 445, 93, 23);
		contentPane.add(btnNewButton_3);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Database.t = 3;
				    if(rdbtnNewRadioButton.isSelected()) Database.s = 1;
				    if(rdbtnEquipment.isSelected()) Database.s = 2;
				    if(rdbtnInstructor.isSelected()) Database.s = 3;
				Database.backend();
				int rst = JOptionPane.showConfirmDialog(frame, "Dot you want to cancel this reservation?","Warning",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				//if(rst == JOptionPane.OK_OPTION)
					
			}
		});
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(311, 154, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(SportsBooking.Name);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(311, 191, 66, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(SportsBooking.id);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(311, 233, 66, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText(SportsBooking.Credit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 287, 673, 149);
		contentPane.add(scrollPane);
		
		txtMyBookingMessage = new JTextField();
		txtMyBookingMessage.setEditable(false);
		txtMyBookingMessage.setHorizontalAlignment(SwingConstants.CENTER);
		txtMyBookingMessage.setText("My Booking Message");
		scrollPane.setColumnHeaderView(txtMyBookingMessage);
		txtMyBookingMessage.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setText(rst);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("D:\\Database System\\Project\\8.jpg"));
		label.setBounds(0, 0, 693, 117);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("D:\\Database System\\Project\\T1OVC_BvdT1RCvBVdK.jpg"));
		label_1.setBounds(-31, 137, 724, 337);
		contentPane.add(label_1);
		
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
		 btnNewButton_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					frame.setVisible(false);
	           	 try {
	             	   EquipmentBooking.frame.setVisible(true);
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
	}
}
