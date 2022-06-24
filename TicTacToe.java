package btl2;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

public class TicTacToe extends PointN implements ActionListener{
	
	private int leveln = 9;
	private Random random = new Random();
	private JFrame frame = new JFrame("TicTacToe_LVD-PTH");
	private JPanel title_panel = new JPanel();
	private JPanel button_panel = new JPanel();
	private JPanel menu = new JPanel();
	private JLabel textfield = new JLabel();
	private JButton[] buttons = new JButton[leveln];
	private JButton resetButton;
	private boolean player1_turn;
	private  JButton undoButton;
	private  Stack<Integer> undo = new Stack<>();
	private JButton backMenu;
	private JButton score;
	
	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);		
		
		//resetButton
		resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.addActionListener(this);
		
		//undoButton
		undoButton = new JButton();
		undoButton.setText("Undo");
		undoButton.addActionListener(this);
		
		//
		backMenu = new JButton();
		backMenu.setText("Back Menu");
		backMenu.addActionListener(this);
		//
		score = new JButton();
		score.setText("Storage point");
		score.addActionListener(this);
		
		menu.add(backMenu);
		menu.add(score);
		menu.setLayout(new GridLayout(1, 1));
		menu.setBounds(0,0,800,100);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("LVD_PTH");
		title_panel.add(resetButton);
		title_panel.add(textfield);
		title_panel.add(undoButton);
		textfield.setOpaque(true);
		
		title_panel.setLayout(new GridLayout(1, 1));
		title_panel.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setBackground(new Color(170,170,170));
		
		for(int i=0;i<leveln;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			buttons[i].setBackground(Color.WHITE);
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		frame.add(menu, BorderLayout.SOUTH);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);
		frame.setLocationRelativeTo(null);
		
		firstTurn();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==resetButton) {
			for(int i=0;i<leveln;i++) {
				if(buttons[i].getText()!="") {
					buttons[i].setText("");
					buttons[i].setBackground(Color.WHITE);
				}
			}
			for(int i=0;i<leveln;i++) {
				buttons[i].setEnabled(true);
			}
			undoButton.setEnabled(true);
			firstTurn();
		}
		
		for(int i = 0; i< leveln; i++) {
				if(e.getSource()==buttons[i]) {
					if(player1_turn == true) {
						if(buttons[i].getText()=="") {
							buttons[i].setForeground(new Color(255,0,0));
							buttons[i].setText("X");
							player1_turn=false;
							textfield.setText("O Turn");
							undo.push(i);
							check();
						}
					}
					else {
						if(buttons[i].getText()=="") {
							buttons[i].setForeground(new Color(0,0,255));
							buttons[i].setText("O");
							player1_turn=true;
							textfield.setText("X Turn");
							undo.push(i);
							check(); 
						}
					}	
				}
		}
		
		if(e.getSource()==undoButton) {
			if(undo.size()>0) {
				int oldpoint = undo.pop();
				if(buttons[oldpoint].getText()=="O") {
					textfield.setText("O Turn");
					player1_turn=false;
				}
				if(buttons[oldpoint].getText()=="X") {
					textfield.setText("X Turn");
					player1_turn=true;
				}
				buttons[oldpoint].setText("");
			    buttons[oldpoint].setBackground(Color.WHITE);
			}
		}
		
		if(e.getSource()==backMenu) {
			frame.dispose();
			new Menu();
			frame.setVisible(false);
		}
		
		if(e.getSource()==score) {
			try {
				addPoint();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void firstTurn() {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(random.nextInt(2) == 0) {
			player1_turn = true;
			textfield.setText("X turn");
		}
		else {
			player1_turn = false;
			textfield.setText("O turn");
		}
		
	}	
	public void check() {
		//3x3
		if(
				(buttons[0].getText()=="X") &&
				(buttons[1].getText()=="X") &&
				(buttons[2].getText()=="X")
				) {
			xWins(0,1,2);
		}
		if(
				(buttons[3].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[5].getText()=="X")
				) {
			xWins(3,4,5);
		}
		if(
				(buttons[6].getText()=="X") &&
				(buttons[7].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(6,7,8);
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[3].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
			xWins(0,3,6);
		}
		if(
				(buttons[1].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[7].getText()=="X")
				) {
			xWins(1,4,7);
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[5].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(2,5,8);
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
			xWins(0,4,8);
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
			xWins(2,4,6);
		}
		//check O win conditions
		if(
				(buttons[0].getText()=="O") &&
				(buttons[1].getText()=="O") &&
				(buttons[2].getText()=="O")
				) {
			oWins(0,1,2);
		}
		if(
				(buttons[3].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[5].getText()=="O")
				) {
			oWins(3,4,5);
		}
		if(
				(buttons[6].getText()=="O") &&
				(buttons[7].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(6,7,8);
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[3].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
			oWins(0,3,6);
		}
		if(
				(buttons[1].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[7].getText()=="O")
				) {
			oWins(1,4,7);
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[5].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(2,5,8);
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
			oWins(0,4,8);
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
			oWins(2,4,6);
		}
	}
	
	
	//3x3_chien_thang
	public void xWins(int a,int b,int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		undoButton.setEnabled(false);
		ticTacToe_x++;
		textfield.setText("X wins");
	}
	public void oWins(int a,int b,int c) {
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		undoButton.setEnabled(false);
		ticTacToe_o++;
		textfield.setText("O wins");
	}
}
