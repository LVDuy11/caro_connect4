package btl2;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Stack;

//import java.util.*;
import javax.swing.*;

public class Connect4 extends PointN implements ActionListener{

	private int level = 7;
	private JFrame frame = new JFrame("Connect Four LVD-PTH");
	private JPanel title_panel = new JPanel();
	private JPanel button_panel = new JPanel();
	private JPanel menu = new JPanel();
	private JLabel textfield = new JLabel();
	private JButton[][] buttons = new JButton[level][level];
	private JButton resetButton;
	
	private JButton undoButton;
	private Stack<Point> undo = new Stack<>();
	
	private JButton backMenu;
	private boolean player1_turn;
	
	private JButton score;
	
	
	Connect4() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,850);
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
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("LVD_PTH");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);
		//
		backMenu = new JButton();
		backMenu.setText("Back Menu");
		backMenu.addActionListener(this);
		
		score = new JButton();
		score.setText("Storage point");
		score.addActionListener(this);
		
		menu.add(backMenu);
		menu.add(undoButton);
		menu.add(resetButton);
		menu.add(score);
		menu.setLayout(new GridLayout(1, 1));
		menu.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(level,level));
		button_panel.setBackground(new Color(150,150,150));
		
		for(int j = 0; j<level;j++) {
				buttons[0][j] = new JButton();
				buttons[0][j].setSize(114,114);
				button_panel.add(buttons[0][j]);
				buttons[0][j].setBackground(Color.BLUE);
				buttons[0][j].setFocusable(false);
				buttons[0][j].addActionListener(this);
		}
		
		for(int i = 1; i<level;i++) {
			for(int j = 0; j<level;j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setSize(114,114);
				button_panel.add(buttons[i][j]);
				buttons[i][j].setFont(new Font("MV Boli", 1,1));
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setFocusable(false);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setEnabled(false);
			}
		}
		title_panel.add(textfield);
		frame.add(menu, BorderLayout.SOUTH);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);
		frame.setLocationRelativeTo(null);
		
		player1_turn = true;
		firstTurn();
	}
	
	public void actionPerformed(ActionEvent e) {
		//
		if(e.getSource()==resetButton) {
			for(int i=1;i<level;i++) {
				for(int j=0;j<level;j++) {
					if(buttons[i][j].getText()!="") {
						buttons[i][j].setText("");
						buttons[i][j].setBackground(Color.WHITE);
					}
				}
			}
			for(int j=0;j<level;j++) {
				buttons[0][j].setEnabled(true);
			}
			undoButton.setEnabled(true);
		}
		//
		for(int j = 0; j < level; j++) {
			if(e.getSource()==buttons[0][j]) {
				//RED TURN
				if(player1_turn == true) {
					if(buttons[level-1][j].getText() == "") {
							buttons[level-1][j].setBackground(Color.RED);
							buttons[level-1][j].setText("x");
							undo.push(new Point(level-1,j));
					}
					else {
						for(int i = level-1; i >0; i--) {
							if(buttons[i][j].getText()=="" ) {
								buttons[i][j].setBackground(Color.RED);
								buttons[i][j].setText("x");
								undo.push(new Point(i,j));
								break;
							}
						}
					}
					player1_turn=false;
					textfield.setText("YELLOW Turn");
					checkWin();
				}
				//YELLOW TURN
				else {
					if(buttons[level-1][j].getText() == "") {
						buttons[level-1][j].setBackground(Color.YELLOW);
						buttons[level-1][j].setText("o");
						undo.push(new Point(level-1,j));
					}
					else {
						for(int i = level-1; i >0; i--) {
							if(buttons[i][j].getText()=="" ) {
								buttons[i][j].setBackground(Color.YELLOW);
								buttons[i][j].setText("o");
								undo.push(new Point(i,j));
								break;
							}
						}
					}
					player1_turn=true;
					textfield.setText("RED Turn");
					checkWin();
				}
			}	
		}
		//
		if(e.getSource()==undoButton) {
			if(undo.size()>0){
				Point oldpoint = undo.pop();
				if(buttons[oldpoint.x][oldpoint.y].getText()=="o") {
					textfield.setText("YELLOW Turn");
					player1_turn=false;
				}
				if(buttons[oldpoint.x][oldpoint.y].getText()=="x") {
					textfield.setText("RED Turn");
					player1_turn=true;
				}
				buttons[oldpoint.x][oldpoint.y].setText("");
			    buttons[oldpoint.x][oldpoint.y].setBackground(Color.WHITE);
			    System.out.println(undo.size());
			}
		}
		//
		if(e.getSource()==backMenu) {
			frame.dispose();
			new Menu();
			frame.setVisible(false);
		}
		//
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
		
		if(player1_turn = true) {
			textfield.setText("RED turn");
		}
		else {
			textfield.setText("YELLOW turn");
		}
	}

	public void checkWin() {
		//check RED win conditions
		for(int i=1; i < level; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "x" &&
						buttons[i][j+1].getText()== "x" &&
						buttons[i][j+2].getText()== "x" &&
						buttons[i][j+3].getText()== "x"
						){
					RedWins(i,j,i,j+1,i,j+2,i,j+3);
				}
			}
		}
		for(int i=1; i < level-3; i++) {
			for(int j=0; j<level;j++) {
				if(
						buttons[i][j].getText()== "x" &&
						buttons[i+1][j].getText()== "x" &&
						buttons[i+2][j].getText()== "x" &&
						buttons[i+3][j].getText()== "x"
						){
					RedWins(i,j,i+1,j,i+2,j,i+3,j);
				}
			}
		}
		for(int i=1; i < level-3; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "x" &&
						buttons[i+1][j+1].getText()== "x" &&
						buttons[i+2][j+2].getText()== "x" &&
						buttons[i+3][j+3].getText()== "x"
						){
					RedWins(i,j,i+1,j+1,i+2,j+2,i+3,j+3);
				}
			}
		}
		for(int i=4; i < level; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "x" &&
						buttons[i-1][j+1].getText()== "x" &&
						buttons[i-2][j+2].getText()== "x" &&
						buttons[i-3][j+3].getText()== "x"
						){
					RedWins(i,j,i-1,j+1,i-2,j+2,i-3,j+3);
				}
			}
		}
		//check YELLOW win conditions
		for(int i=1; i < level; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "o" &&
						buttons[i][j+1].getText()== "o" &&
						buttons[i][j+2].getText()== "o" &&
						buttons[i][j+3].getText()== "o"
						){
					YelWins(i,j,i,j+1,i,j+2,i,j+3);
				}
			}
		}
		for(int i=1; i < level-3; i++) {
			for(int j=0; j<level;j++) {
				if(
						buttons[i][j].getText()== "o" &&
						buttons[i+1][j].getText()== "o" &&
						buttons[i+2][j].getText()== "o" &&
						buttons[i+3][j].getText()== "o"
						){
					YelWins(i,j,i+1,j,i+2,j,i+3,j);
				}
			}
		}
		for(int i=1; i < level-3; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "o" &&
						buttons[i+1][j+1].getText()== "o" &&
						buttons[i+2][j+2].getText()== "o" &&
						buttons[i+3][j+3].getText()== "o"
						){
					YelWins(i,j,i+1,j+1,i+2,j+2,i+3,j+3);
				}
			}
		}
		for(int i=4; i < level; i++) {
			for(int j=0; j<level-3;j++) {
				if(
						buttons[i][j].getText()== "o" &&
						buttons[i-1][j+1].getText()== "o" &&
						buttons[i-2][j+2].getText()== "o" &&
						buttons[i-3][j+3].getText()== "o"
						){
					YelWins(i,j,i-1,j+1,i-2,j+2,i-3,j+3);
				}
			}
		}
	}
	
	public void RedWins(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
		buttons[a1][a2].setBackground(Color.GREEN);
		buttons[b1][b2].setBackground(Color.GREEN);
		buttons[c1][c2].setBackground(Color.GREEN);
		buttons[d1][d2].setBackground(Color.GREEN);
		for(int j=0;j<level;j++) {
			buttons[0][j].setEnabled(false);
		}
		undoButton.setEnabled(false);
		cn4_r++;
		textfield.setText("RED Win");
	}
	
	public void YelWins(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
		buttons[a1][a2].setBackground(Color.GREEN);
		buttons[b1][b2].setBackground(Color.GREEN);
		buttons[c1][c2].setBackground(Color.GREEN);
		buttons[d1][d2].setBackground(Color.GREEN);
		for(int j=0;j<level;j++) {
			buttons[0][j].setEnabled(false);
		}
		undoButton.setEnabled(false);
		cn4_y++;
		textfield.setText("YELLOW Win");
	}
}

