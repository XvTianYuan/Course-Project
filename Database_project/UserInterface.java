package proj;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UserInterface extends JFrame {
	
	private JPanel contentPane;
    static UserInterface frame = new UserInterface();
	/**
	 * Launch the application.
	 */
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
	public UserInterface() {
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
		btnBookingRules.setBackground(Color.DARK_GRAY);
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
		btnMy.setFont(new Font("宋体", Font.PLAIN, 11));
		btnMy.setBounds(563, 114, 130, 23);
		contentPane.add(btnMy);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 157, 622, 317);
		contentPane.add(scrollPane);
		
		JTextArea txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf = new JTextArea();
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setWrapStyleWord(true);
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setLineWrap(true);
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setText("                                                                          Booking System Instruction\r\n1.Before using this system, please read this instruction.\r\n2.There are two booking steps. You should query first to check whether there are things you need and then you can book with correct booking information.\r\n3.After valid booking, you have to attend on time. If you don't show up 15 minutes after begin time, your booking would be canceled and your credit scores would reduce.\r\n4.Credit scores are relative to your privilege which means high credit scores could  order a long time and borrow more equipment. The max score is 100 and a no-show reduce 10. The credit scores below 60 means you cannot booking this semester. Every semester the credit will back to 100.\r\n5.You can find your booking message in My Information. You can cancel the order before it takes effect.\r\n6.If you booking instructor seccessfully, a message would send to the instructor and you can directly contact the instructor because the instructor's contact information would return to you.\r\n7.If you borrow equipment, you need to fetch youself in certain place. After using, you need return on time, or else your credit scores would reduce 10. If the equipment is broken after using, you need to price as compensation.\r\n8.If you have any problem, contact tele:15602992435 qq:1402129509.\r\n\r\n\r\n");
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setBackground(Color.LIGHT_GRAY);
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setForeground(Color.BLACK);
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setEditable(false);
		txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf.setBounds(47, 160, 620, 285);
		scrollPane.setViewportView(txtrJksdfjkjksadAsdkfjkasdfAksjdfjasdf);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\8.jpg"));
		label.setBounds(0, 0, 693, 117);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\Administrator\\Desktop\\T1OVC_BvdT1RCvBVdK.jpg"));
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
	}
}
