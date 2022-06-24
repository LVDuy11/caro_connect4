package btl2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Rank extends PointN implements ActionListener{
	private JFrame frame_1 = new JFrame("Diem thang");
	private JPanel title_panel_1 = new JPanel();
	private JLabel textfield_1 = new JLabel();
	private JLabel textfield_2 = new JLabel();
	private JLabel textfield_3 = new JLabel();
	private JLabel textfield_4 = new JLabel();
	private JLabel textfield_5 = new JLabel();
	private JLabel textfield_6 = new JLabel();
	
	private JPanel menu = new JPanel();
	private JButton backMenu;
	private JButton delete;
	
	
	
	public Rank() {
		frame_1.setSize(600,400);
		frame_1.getContentPane().setBackground(new Color(80,90,100));
		frame_1.setLayout(new BorderLayout());
		
		//
		textfield_1.setBackground(new Color(25,25,25));
		textfield_1.setForeground(new Color(25,255,0));
		textfield_1.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_1.setHorizontalAlignment(JLabel.LEFT);
		textfield_1.setText("TicTacToe X thang: " + ticTacToe_x);
		title_panel_1.add(textfield_1);
		textfield_1.setOpaque(true);
		
		textfield_2.setBackground(new Color(25,25,25));
		textfield_2.setForeground(new Color(25,255,0));
		textfield_2.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_2.setHorizontalAlignment(JLabel.LEFT);
		textfield_2.setText("TicTacToe O thang: " + ticTacToe_o);
		title_panel_1.add(textfield_2);
		textfield_2.setOpaque(true);
		
		textfield_3.setBackground(new Color(25,25,25));
		textfield_3.setForeground(new Color(25,255,0));
		textfield_3.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_3.setHorizontalAlignment(JLabel.LEFT);
		textfield_3.setText("Caro X thang: " + caro_x);
		title_panel_1.add(textfield_3);
		textfield_3.setOpaque(true);
		
		textfield_4.setBackground(new Color(25,25,25));
		textfield_4.setForeground(new Color(25,255,0));
		textfield_4.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_4.setHorizontalAlignment(JLabel.LEFT);
		textfield_4.setText("Caro O thang:" + caro_o);
		title_panel_1.add(textfield_4);
		textfield_4.setOpaque(true);
		
		textfield_5.setBackground(new Color(25,25,25));
		textfield_5.setForeground(new Color(25,255,0));
		textfield_5.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_5.setHorizontalAlignment(JLabel.LEFT);
		textfield_5.setText("Connect Four Yellow thang: " + cn4_y);
		title_panel_1.add(textfield_5);
		textfield_5.setOpaque(true);
		
		textfield_6.setBackground(new Color(25,25,25));
		textfield_6.setForeground(new Color(25,255,0));
		textfield_6.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_6.setHorizontalAlignment(JLabel.LEFT);
		textfield_6.setText("Connect Four Red thang: " + cn4_r);
		title_panel_1.add(textfield_6);
		textfield_6.setOpaque(true);
		
		backMenu = new JButton();
		backMenu.setText("Back Menu");
		backMenu.addActionListener(this);
		
		delete = new JButton();
		delete.setText("Delete Point");
		delete.addActionListener(this);
		
		menu.add(backMenu);
		menu.add(delete);
		menu.setLayout(new GridLayout(1, 1));
		menu.setBounds(0,0,800,100);
		
		title_panel_1.setLayout(new GridLayout(7, 1));
		//
		frame_1.add(title_panel_1, BorderLayout.NORTH);
		//
		frame_1.add(menu, BorderLayout.SOUTH);
		frame_1.setLocationRelativeTo(null);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==backMenu) {
			frame_1.dispose();
			new Menu();
			frame_1.setVisible(false);
		}
		
		if(e.getSource()==delete) {
			ticTacToe_x=0;
			ticTacToe_o=0;
			caro_x=0;
			caro_o=0;
			cn4_r=0;
			cn4_y=0;
			caro_y=0;
			
			frame_1.dispose();
			frame_1.setVisible(false);
			try {
				deletePoint();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			new Rank();
		}
	}
}
