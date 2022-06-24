package btl2;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

public class Caro_20 extends PointN implements ActionListener{
	
	private int leveln = 20;
	private int buttonSize = (120 * 3) / leveln;
	private Random random = new Random();
	private JFrame frame = new JFrame("Caro_size 20x20 LVD-PTH");
	private JPanel title_panel = new JPanel();
	private JPanel button_panel = new JPanel();
	private JPanel menu_panel = new JPanel();
	private JLabel textfield = new JLabel();
	private JButton[][] buttons = new JButton[leveln][leveln];
	//resetButton
	private JButton resetButton;
	private boolean player1_turn;
	
	//undo
	private JButton undoButton;
	private Stack<Point> undo = new Stack<>();
	//menu
	private JButton backMenu;
	
	private JButton score;
	
	public Caro_20() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
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
		
		score = new JButton();
		score.setText("Storage point");
		score.addActionListener(this);
		
		//
		menu_panel.add(backMenu);
		menu_panel.add(score);
		menu_panel.setLayout(new GridLayout(1, 1));
		menu_panel.setBounds(0,0,800,100);
		
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
		
		button_panel.setLayout(new GridLayout(leveln,leveln));
		button_panel.setBackground(new Color(170,170,170));
		
		for(int i = 0; i<leveln;i++) {
			for(int j = 0; j<leveln;j++) {
				buttons[i][j] = new JButton();
				button_panel.add(buttons[i][j]);
				buttons[i][j].setFont(new Font("MV Boli", Font.BOLD,buttonSize));
				buttons[i][j].setBackground(Color.WHITE);
				buttons[i][j].setFocusable(false);
				buttons[i][j].addActionListener(this);
			}
		}
		
		frame.add(menu_panel, BorderLayout.SOUTH);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);
		frame.setLocationRelativeTo(null);
		
		firstTurn();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==resetButton) {
			for(int i=0;i<leveln;i++) {
				for(int j=0;j<leveln;j++) {
					if(buttons[i][j].getText()!="") {
						buttons[i][j].setText("");
						buttons[i][j].setBackground(Color.WHITE);
					}
				}
			}
			for(int i=0;i<leveln;i++) {
				for(int j=0;j<leveln;j++) {
					buttons[i][j].setEnabled(true);
				}
			}
			undoButton.setEnabled(true);
			firstTurn();
		}
		
		for(int i = 0; i< leveln; i++) {
			for(int j = 0; j< leveln; j++) {
				if(e.getSource()==buttons[i][j]) {
					if(player1_turn == true) {
						if(buttons[i][j].getText()=="") {
							buttons[i][j].setForeground(new Color(255,0,0));
							buttons[i][j].setText("X");
							player1_turn=false;
							textfield.setText("O Turn");
							undo.push(new Point(i,j));
							check();
						}
					}
					else {
						if(buttons[i][j].getText()=="") {
							buttons[i][j].setForeground(new Color(0,0,255));
							buttons[i][j].setText("O");
							player1_turn=true;
							textfield.setText("X Turn");
							undo.push(new Point(i,j));
							check();
						}
					}	
				}
			}
		}
		
		if(e.getSource()==undoButton) {
			if(undo.size()>0){	
				Point oldpoint = undo.pop();
				if(buttons[oldpoint.x][oldpoint.y].getText()=="O") {
					textfield.setText("O Turn");
					player1_turn=false;
				}
				if(buttons[oldpoint.x][oldpoint.y].getText()=="X") {
					textfield.setText("X Turn");
					player1_turn=true;
				}
				buttons[oldpoint.x][oldpoint.y].setText("");
				buttons[oldpoint.x][oldpoint.y].setBackground(Color.WHITE);
			}
		}
		
		if(e.getSource()==backMenu) {
			new Menu();
			frame.dispose();
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
			//check X win conditions
			for(int i=0; i< leveln;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="X"&&
							buttons[i][j+1].getText()=="X"&&
							buttons[i][j+2].getText()=="X"&&
							buttons[i][j+3].getText()=="X"&&
							buttons[i][j+4].getText()=="X"
							){
						xWins(i,j,i,j+1,i,j+2,i,j+3,i,j+4);
					}
				}
			}
			for(int i=0; i< leveln-4;i++) {
				for(int j=0; j< leveln;j++) {
					if(
							buttons[i][j].getText()=="X"&&
							buttons[i+1][j].getText()=="X"&&
							buttons[i+2][j].getText()=="X"&&
							buttons[i+3][j].getText()=="X"&&
							buttons[i+4][j].getText()=="X"
							){
						xWins(i,j,i+1,j,i+2,j,i+3,j,i+4,j);
					}
				}
			}
			for(int i=0; i< leveln-4;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="X"&&
							buttons[i+1][j+1].getText()=="X"&&
							buttons[i+2][j+2].getText()=="X"&&
							buttons[i+3][j+3].getText()=="X"&&
							buttons[i+4][j+4].getText()=="X"		
							){
						xWins(i,j,i+1,j+1,i+2,j+2,i+3,j+3,i+4,j+4);
					}
				}
			}
			for(int i=4; i< leveln;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="X"&&
							buttons[i-1][j+1].getText()=="X"&&
							buttons[i-2][j+2].getText()=="X"&&
							buttons[i-3][j+3].getText()=="X"&&
							buttons[i-4][j+4].getText()=="X"
							){
						xWins(i,j,i-1,j+1,i-2,j+2,i-3,j+3,i-4,j+4);
					}
				}
			}
			//check O win conditions
			for(int i=0; i< leveln;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="O"&&
							buttons[i][j+1].getText()=="O"&&
							buttons[i][j+2].getText()=="O"&&
							buttons[i][j+3].getText()=="O"&&
							buttons[i][j+4].getText()=="O"
							){
						yWins(i,j,i,j+1,i,j+2,i,j+3,i,j+4);
					}
				}
			}
			for(int i=0; i< leveln-4;i++) {
				for(int j=0; j< leveln;j++) {
					if(
							buttons[i][j].getText()=="O"&&
							buttons[i+1][j].getText()=="O"&&
							buttons[i+2][j].getText()=="O"&&
							buttons[i+3][j].getText()=="O"&&
							buttons[i+4][j].getText()=="O"
							){
						yWins(i,j,i+1,j,i+2,j,i+3,j,i+4,j);
					}
				}
			}
			for(int i=0; i< leveln-4;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="O"&&
							buttons[i+1][j+1].getText()=="O"&&
							buttons[i+2][j+2].getText()=="O"&&
							buttons[i+3][j+3].getText()=="O"&&
							buttons[i+4][j+4].getText()=="O"		
							){
						yWins(i,j,i+1,j+1,i+2,j+2,i+3,j+3,i+4,j+4);
					}
				}
			}
			for(int i=4; i< leveln;i++) {
				for(int j=0; j< leveln-4;j++) {
					if(
							buttons[i][j].getText()=="O"&&
							buttons[i-1][j+1].getText()=="O"&&
							buttons[i-2][j+2].getText()=="O"&&
							buttons[i-3][j+3].getText()=="O"&&
							buttons[i-4][j+4].getText()=="O"
							){
						yWins(i,j,i-1,j+1,i-2,j+2,i-3,j+3,i-4,j+4);
					}
				}
			}
	}

	public void xWins(int a1, int a2, int b1, int b2,int c1,int c2, int d1, int d2, int e1, int e2) {
		buttons[a1][a2].setBackground(Color.green);
		buttons[b1][b2].setBackground(Color.GREEN);
		buttons[c1][c2].setBackground(Color.GREEN);
		buttons[d1][d2].setBackground(Color.GREEN);
		buttons[e1][e2].setBackground(Color.GREEN);
		for(int i=0;i<leveln;i++) {
			for(int j=0;j<leveln;j++) {
				buttons[i][j].setEnabled(false);
			}
		}
		undoButton.setEnabled(false);
		caro_x++;
		textfield.setText("X Win");
	}
	
	public void yWins(int a1, int a2, int b1, int b2,int c1,int c2, int d1, int d2, int e1, int e2) {
		buttons[a1][a2].setBackground(Color.green);
		buttons[b1][b2].setBackground(Color.GREEN);
		buttons[c1][c2].setBackground(Color.GREEN);
		buttons[d1][d2].setBackground(Color.GREEN);
		buttons[e1][e2].setBackground(Color.GREEN);
		for(int i=0;i<leveln;i++) {
			for(int j=0;j<leveln;j++) {
				buttons[i][j].setEnabled(false);
			}
		}
		undoButton.setEnabled(false);
		caro_o++;
		textfield.setText("O Win");
	}
}
