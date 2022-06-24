	package btl2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Menu extends PointN implements ActionListener{
	
	private JFrame frame_1 = new JFrame("LVD-PTH");
	private JPanel title_panel_1 = new JPanel();
	private JPanel button_panel_1 = new JPanel();
	private JLabel textfield_1 = new JLabel();
	//
	private JButton tictactoe;
	private JButton caro_20;
	private JButton connect4;
	private JButton checkPoint;
	//
	
	Menu() {
		//
		frame_1.setSize(400,700);
		frame_1.getContentPane().setBackground(new Color(80,90,100));
		frame_1.setLayout(new BorderLayout());
		
		//
		textfield_1.setBackground(new Color(25,25,25));
		textfield_1.setForeground(new Color(25,255,0));
		textfield_1.setFont(new Font("Ink Free", Font.BOLD, 40));
		textfield_1.setHorizontalAlignment(JLabel.CENTER);
		textfield_1.setText("MENU__LVD_PTH");
		title_panel_1.add(textfield_1);
		textfield_1.setOpaque(true);
		
		title_panel_1.setLayout(new GridLayout(1, 1));
		title_panel_1.setBounds(50,50,600,300);
		title_panel_1.setBorder(new EmptyBorder(new Insets(20, 10, 1,10)));
		
		button_panel_1.setLayout(new GridLayout(4,4));
		button_panel_1.setBackground(new Color(255,255,255));
		button_panel_1.setBorder(new EmptyBorder(new Insets(150, 50, 200, 50)));
		
		//
		tictactoe = new JButton("TicTacToe");
		tictactoe.setFont(new Font("Ink Free", Font.BOLD, 25));
		button_panel_1.add(tictactoe);
		tictactoe.addActionListener(this);
			
		caro_20 = new JButton("Cazo rong 20x20");
		caro_20.setFont(new Font("Ink Free", Font.BOLD, 25));
		button_panel_1.add(caro_20);
		caro_20.addActionListener(this);
		
		connect4 = new JButton("CONNECT FOUR");
		connect4.setFont(new Font("Ink Free", Font.BOLD, 25));
		button_panel_1.add(connect4);
		connect4.addActionListener(this);
		
		checkPoint = new JButton("Ai thang nhieu hon");
		checkPoint.setFont(new Font("Ink Free", Font.BOLD, 25));
		button_panel_1.add(checkPoint);
		checkPoint.addActionListener(this);
		
		//
		frame_1.add(title_panel_1, BorderLayout.NORTH);
		frame_1.add(button_panel_1);
		frame_1.setLocationRelativeTo(null);
		frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame_1.setResizable(false);
		frame_1.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==tictactoe) {
			new TicTacToe();
			frame_1.dispose();
			frame_1.setVisible(false);
		}
		
		
		if(e.getSource()==caro_20) {
			//sizeMap.setHang(20);
			new Caro_20();
			frame_1.dispose();
			frame_1.setVisible(false);
		}
		
		if(e.getSource()==connect4) {
			//sizeMap.setHang(7);
			new Connect4();
			frame_1.dispose();
			frame_1.setVisible(false);
		}
		
		if (e.getSource()==checkPoint) {
			new Rank();
			frame_1.dispose();
			frame_1.setVisible(false);
		}
	}
	
}
