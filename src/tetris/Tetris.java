package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tetris extends JPanel{
	public static boolean clearing;
	public static boolean held;
	public static long time;
	public static int wait;
	public static int actions;
	public static int score;
	public static int clearedLines;
	public static Tetris panel;
	public static JFrame frame;
	public static Box[][] board;
	public static Random rand;
	public static Shape next;
	public static Shape hold;
	public static Shape ghost;
	public static List<Shape> pieces = new ArrayList<Shape>();
	public static List<Integer> lines = new ArrayList<Integer>();
	public static Timer action = new Timer(1000, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(clearedLines >= 5){
				if(action.getDelay() >= 300){
					action.setDelay(action.getDelay()-100);
				}
				else if(action.getDelay() > 100){
					action.setDelay(action.getDelay()-50);
				}
				else if(action.getDelay() > 50){
					action.setDelay(action.getDelay()-10);
				}
				clearedLines = 0;
			}
			//not in process of clearing lines
			if(wait == 0){
				//currently no full lines
				if(checkLines() == 0){
					//piece can move down
					if(pieces.get(pieces.size()-1).update()){
						pieces.get(pieces.size()-1).move();
						time = System.currentTimeMillis();
					}
					//piece can't move down, so add new one
					else{
						//it has been one second since last action, and actions have not exceeded 30
						if(System.currentTimeMillis() - time > 1000 || actions > 30){
							pieces.add(next);
							newPiece();
							actions = 0;
						}
					}
				}
				//there is a full line
				else{
					clearing = true;
					wait = 1;
					clearLines();
				}
			}
			//in the process of clearing
			else{
				wait--;
				dropLines();
				clearing = false;
			}
			updateBoard();
		}
	});
	public static void main(String[] args) {
		panel = new Tetris();
		frame = new JFrame("Tetris");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		frame.setBounds(520,20,400,967);
		frame.add(panel);
		action.setRepeats(true);
		rand = new Random();
		newGame();
		action.stop();
		JOptionPane.showMessageDialog(null, "             CONTROLS:\n  LEFT and RIGHT to Move\n             UP to Rotate\n      DOWN to Shift Down\n       SPACEBAR to Drop\n                C to Hold");
		action.start();
		frame.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()){
				case (KeyEvent.VK_LEFT):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							if(pieces.get(pieces.size()-1).left()){
								time = System.currentTimeMillis();
								actions++;
							}
						}
					}
				updateBoard();
				break;
				case(KeyEvent.VK_RIGHT):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							if(pieces.get(pieces.size()-1).right()){
								time = System.currentTimeMillis();
								actions++;
							}
						}
					}
				updateBoard();
				break;
				case(KeyEvent.VK_DOWN):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							if(pieces.get(pieces.size()-1).update()){
								pieces.get(pieces.size()-1).move();
								time = System.currentTimeMillis();
								score += 5;
							}
						}
					}
				updateBoard();
				break;
				case(KeyEvent.VK_UP):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							if(pieces.get(pieces.size()-1).rotate()){
								updateBoard();
								time = System.currentTimeMillis();
								actions++;
							}
						}
					}
				break;
				case(KeyEvent.VK_SPACE):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							int i = 23 - (pieces.get(pieces.size()-1).get4()/10);
							score += i*10;
							pieces.get(pieces.size()-1).drop();
							updateBoard();
							pieces.add(next);
							newPiece();
							actions = 0;
						}
					}
				break;
				case(KeyEvent.VK_ENTER):
					if(gameOver()){
						newGame();
					}
				break;
				case(KeyEvent.VK_C):
					if(!gameOver()){
						if(!clearing && checkLines() == 0){
							if(!held){
								actions = 0;
								if(hold == null){
									hold = pieces.remove(pieces.size()-1);
									pieces.add(next);
									newPiece();
								}
								else{
									Shape temp = null;
									switch(hold.getShape()){
									case 0:
										temp = new Line(5,15,25,35);
										break;
									case 1:
										temp = new T(25,34,35,36);
										break;
									case 2:
										temp = new L1(14,15,24,34);
										break;
									case 3:
										temp = new L2(14,15,25,35);
										break;
									case 4:
										temp = new Z1(24,25,33,34);
										break;
									case 5:
										temp = new Z2(23,24,34,35);
										break;
									case 6:
										temp = new Square(24,25,34,35);
										break;
									}
									hold = pieces.remove(pieces.size()-1);
									pieces.add(temp);
								}
								switch(hold.getShape()){
								case 0:
									hold = new Line(5,15,25,35);
									break;
								case 1:
									hold = new T(25,34,35,36);
									break;
								case 2:
									hold = new L1(14,15,24,34);
									break;
								case 3:
									hold = new L2(14,15,25,35);
									break;
								case 4:
									hold = new Z1(24,25,33,34);
									break;
								case 5:
									hold = new Z2(23,24,34,35);
									break;
								case 6:
									hold = new Square(24,25,34,35);
									break;
								}
								held = true;
							}
						}
					}
				updateBoard();
				break;
				}
			}
		});
	}
	public static void newGame(){
		board = new Box[24][10];
		for(int x = 0; x < 24; x++){
			for(int y = 0; y < 10; y++){
				board[x][y] = new Box(x*10 + y);
			}
		}
		action.setDelay(1000);
		pieces.clear();
		newPiece();
		pieces.add(next);
		newPiece();
		updateBoard();
		action.start();
		hold = null;
		wait = 0;
		score = 0;
		clearedLines = 0;
	}
	public static boolean gameOver(){
		for(int x = 30; x < 40; x++){
			if(!board[3][x%10].isEmpty() && getShape(x) != pieces.get(pieces.size()-1)){
				return true;
			}
		}
		return false;
	}
	public static void newPiece(){
		held = false;
		int x = rand.nextInt(7);
		switch(x){
		case 0:
			next = new Line(5,15,25,35);
			break;
		case 1:
			next = new T(25,34,35,36);
			break;
		case 2:
			next = new L1(14,15,24,34);
			break;
		case 3:
			next = new L2(14,15,25,35);
			break;
		case 4:
			next = new Z1(24,25,33,34);
			break;
		case 5:
			next = new Z2(23,24,34,35);
			break;
		case 6:
			next = new Square(24,25,34,35);
			break;
		}
	}
	public static void updateBoard(){
		for(Box[] i: board){
			for(Box j: i){
				j.setEmpty(true);
			}
		}
		for(Shape i: pieces){
			int a = i.get1();
			int b = i.get2();
			int c = i.get3();
			int d = i.get4();
			int s = i.getShape();
			board[a/10][a%10].setShape(s);
			board[b/10][b%10].setShape(s);
			board[c/10][c%10].setShape(s);
			board[d/10][d%10].setShape(s);
		}
		switch(pieces.get(pieces.size()-1).getShape()){
		case 0:
			ghost = new Line(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((Line)ghost).setVertical(((Line)pieces.get(pieces.size()-1)).isVertical());
			break;
		case 1:
			ghost = new T(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((T)ghost).setOrient(((T)pieces.get(pieces.size()-1)).getOrient());
			break;
		case 2:
			ghost = new L1(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((L1)ghost).setOrient(((L1)pieces.get(pieces.size()-1)).getOrient());
			break;
		case 3:
			ghost = new L2(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((L2)ghost).setOrient(((L2)pieces.get(pieces.size()-1)).getOrient());
			break;
		case 4:
			ghost = new Z1(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((Z1)ghost).setOrient(((Z1)pieces.get(pieces.size()-1)).getOrient());
			break;
		case 5:
			ghost = new Z2(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			((Z2)ghost).setOrient(((Z2)pieces.get(pieces.size()-1)).getOrient());
			break;
		case 6:
			ghost = new Square(pieces.get(pieces.size()-1).get1(),pieces.get(pieces.size()-1).get2(),pieces.get(pieces.size()-1).get3(),pieces.get(pieces.size()-1).get4());
			break;
		}
		ghost.drop();
		if(gameOver()){
			action.stop();
			JOptionPane.showMessageDialog(null, "           GAME OVER!\n       Your Score: " + score + "\nPress ENTER to Play Again");
		}
		panel.repaint();
	}
	public static int checkLines(){
		int a = 0;
		for(int x = 4; x < 24; x++){
			for(int y = 0; y < 10; y++){
				if(board[x][y].isEmpty()){
					break;
				}
				if(getShape(x*10+y) == pieces.get(pieces.size()-1)){
					break;
				}
				if(y == 9){
					a++;
					clearedLines++;
					if(!lines.contains(x)){
						lines.add(x);
					}
				}
			}
		}
		switch(a){
		case 1:
			score += 150;
			break;
		case 2:
			score += 350;
			break;
		case 3:
			score += 575;
			break;
		case 4:
			score += 800;
			break;
		}
		return a;
	}
	public static void clearLines(){
		for(int x = 0; x < lines.size(); x++){
			int a = lines.get(x);
			clearLine(a);
		}
	}
	public static void dropLines(){
		int y = lines.size();
		for(int x = 0; x < y; x++){
			Integer i = lines.remove((int)0);
			dropBoard(i-1,1);
		}
	}
	public static void clearLine(int a){
		for(int x = 0; x < 10; x++){
			Shape s = getShape(a*10+x);
			if(s != null){
				int i = getNum(s, a*10+x);
				switch(i){
				case 1:
					s.set1(0);
					break;
				case 2:
					s.set2(0);
					break;
				case 3:
					s.set3(0);
					break;
				case 4:
					s.set4(0);
					break;
				}
			}
		}
	}
	public static void dropBoard(int l, int n){//l - line; n - number of lines to drop
		int x = l*10+9;
		for(int i = x; i > 39; i--){
			Shape s = getShape(i);
			if(s != null){
				int p = getNum(s,i);
				for(int j = 0; j < n; j++){
					switch(p){
					case 1:
						s.set1(s.get1()+10);
						break;
					case 2:
						s.set2(s.get2()+10);
						break;
					case 3:
						s.set3(s.get3()+10);
						break;
					case 4:
						s.set4(s.get4()+10);
						break;
					}
				}
			}
		}
	}
	public static Shape getShape(int x){
		Shape s = null;
		for(Shape a : pieces){
			if(a.get1() == x || a.get2() == x || a.get3() == x || a.get4() == x){
				s = a;
			}
		}
		return s;
	}
	public static int getNum(Shape s, int x){
		if(s.get1() == x){
			return 1;
		}
		if(s.get2() == x){
			return 2;
		}
		if(s.get3() == x){
			return 3;
		}
		if(s.get4() == x){
			return 4;
		}
		return -1;
	}
	public void paint(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		for(int x = 4; x < 24; x++){
			for(int y = 0; y < 10; y++){
				g.setColor(Color.DARK_GRAY);
				g.fillRoundRect(y*40+2, x*40-38, 36, 36, 10, 10);
			}
		}
		if(ghost.get1() > 39){
			g.setColor(Color.WHITE);
			g.fillRoundRect((ghost.get1()%10)*40+2, (ghost.get1()/10)*40-38, 36, 36, 10, 10);
			g.fillRoundRect((ghost.get2()%10)*40+2, (ghost.get2()/10)*40-38, 36, 36, 10, 10);
			g.fillRoundRect((ghost.get3()%10)*40+2, (ghost.get3()/10)*40-38, 36, 36, 10, 10);
			g.fillRoundRect((ghost.get4()%10)*40+2, (ghost.get4()/10)*40-38, 36, 36, 10, 10);
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect((ghost.get1()%10)*40+4, (ghost.get1()/10)*40-36, 32, 32, 10, 10);
			g.fillRoundRect((ghost.get2()%10)*40+4, (ghost.get2()/10)*40-36, 32, 32, 10, 10);
			g.fillRoundRect((ghost.get3()%10)*40+4, (ghost.get3()/10)*40-36, 32, 32, 10, 10);
			g.fillRoundRect((ghost.get4()%10)*40+4, (ghost.get4()/10)*40-36, 32, 32, 10, 10);
		}
		for(int x = 4; x < 24; x++){
			for(int y = 0; y < 10; y++){
				if(board[x][y].getShape() == 0){
					g.setColor(new Color(51,204,255));
				}
				else if(board[x][y].getShape() == 1){
					g.setColor(new Color(138,0,184));
				}
				else if(board[x][y].getShape() == 2){
					g.setColor(new Color(0,46,184));
				}
				else if(board[x][y].getShape() == 3){
					g.setColor(new Color(255,102,0));
				}
				else if(board[x][y].getShape() == 4){
					g.setColor(new Color(26,255,0));
				}
				else if(board[x][y].getShape() == 5){
					g.setColor(new Color(255,0,51));
				}
				else if(board[x][y].getShape() == 6){
					g.setColor(new Color(255,255,0));
				}
				if(!board[x][y].isEmpty()){
					g.fillRoundRect(y*40+2, x*40-38, 36, 36, 10, 10);
				}
				//g.setColor(Color.RED);
				//g.drawString(Integer.toString(board[x][y].getPosition()), y*40+12, x*40-22);
			}
		}
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("ARIAL", Font.BOLD, 20));
		g.drawString("HOLD", 31, 24);
		g.fillRect(0, 30, 120, 4);	
		g.drawString("NEXT", 314, 24);
		g.fillRect(280, 30, 120, 4);
		g.drawString("SCORE", 166, 24);
		int pos = 195;
		if(score/10 > 0){
			pos = 190;
		}
		if(score/100 > 0){
			pos = 185;
		}
		if(score/1000 > 0){
			pos = 180;
		}
		if(score/10000 > 0){
			pos = 175;
		}
		if(score/100000 > 0){
			pos = 170;
		}
		g.drawString(Integer.toString(score), pos, 75);
		switch(next.getShape()){
		case 0:
			g.setColor(new Color(51,204,255));				
			g.fillRoundRect(332, 40, 15, 15, 10, 10);
			g.fillRoundRect(332, 57, 15, 15, 10, 10);
			g.fillRoundRect(332, 74, 15, 15, 10, 10);
			g.fillRoundRect(332, 91, 15, 15, 10, 10);
			break;
		case 1:
			g.setColor(new Color(138,0,184));
			g.fillRoundRect(332, 50, 15, 15, 10, 10);
			g.fillRoundRect(332, 67, 15, 15, 10, 10);
			g.fillRoundRect(315, 67, 15, 15, 10, 10);
			g.fillRoundRect(349, 67, 15, 15, 10, 10);
			break;
		case 2:
			g.setColor(new Color(0,46,184));
			g.fillRoundRect(324, 50, 15, 15, 10, 10);
			g.fillRoundRect(324, 67, 15, 15, 10, 10);
			g.fillRoundRect(324, 84, 15, 15, 10, 10);
			g.fillRoundRect(341, 50, 15, 15, 10, 10);
			break;
		case 3:
			g.setColor(new Color(255,102,0));
			g.fillRoundRect(341, 50, 15, 15, 10, 10);
			g.fillRoundRect(341, 67, 15, 15, 10, 10);
			g.fillRoundRect(341, 84, 15, 15, 10, 10);
			g.fillRoundRect(324, 50, 15, 15, 10, 10);
			break;
		case 4:
			g.setColor(new Color(26,255,0));
			g.fillRoundRect(317, 72, 15, 15, 10, 10);
			g.fillRoundRect(334, 72, 15, 15, 10, 10);
			g.fillRoundRect(334, 55, 15, 15, 10, 10);
			g.fillRoundRect(351, 55, 15, 15, 10, 10);
			break;
		case 5:
			g.setColor(new Color(255,0,51));
			g.fillRoundRect(317, 55, 15, 15, 10, 10);
			g.fillRoundRect(334, 55, 15, 15, 10, 10);
			g.fillRoundRect(334, 72, 15, 15, 10, 10);
			g.fillRoundRect(351, 72, 15, 15, 10, 10);
			break;
		case 6:
			g.setColor(new Color(255,255,0));
			g.fillRoundRect(341, 57, 15, 15, 10, 10);
			g.fillRoundRect(324, 57, 15, 15, 10, 10);
			g.fillRoundRect(324, 74, 15, 15, 10, 10);
			g.fillRoundRect(341, 74, 15, 15, 10, 10);
			break;
		}
		if(hold != null){
			switch(hold.getShape()){
			case 0:
				g.setColor(new Color(51,204,255));				
				g.fillRoundRect(52, 40, 15, 15, 10, 10);
				g.fillRoundRect(52, 57, 15, 15, 10, 10);
				g.fillRoundRect(52, 74, 15, 15, 10, 10);
				g.fillRoundRect(52, 91, 15, 15, 10, 10);
				break;
			case 1:
				g.setColor(new Color(138,0,184));
				g.fillRoundRect(52, 50, 15, 15, 10, 10);
				g.fillRoundRect(52, 67, 15, 15, 10, 10);
				g.fillRoundRect(35, 67, 15, 15, 10, 10);
				g.fillRoundRect(69, 67, 15, 15, 10, 10);
				break;
			case 2:
				g.setColor(new Color(0,46,184));
				g.fillRoundRect(44, 50, 15, 15, 10, 10);
				g.fillRoundRect(44, 67, 15, 15, 10, 10);
				g.fillRoundRect(44, 84, 15, 15, 10, 10);
				g.fillRoundRect(61, 50, 15, 15, 10, 10);
				break;
			case 3:
				g.setColor(new Color(255,102,0));
				g.fillRoundRect(61, 50, 15, 15, 10, 10);
				g.fillRoundRect(61, 67, 15, 15, 10, 10);
				g.fillRoundRect(61, 84, 15, 15, 10, 10);
				g.fillRoundRect(44, 50, 15, 15, 10, 10);
				break;
			case 4:
				g.setColor(new Color(26,255,0));
				g.fillRoundRect(37, 72, 15, 15, 10, 10);
				g.fillRoundRect(54, 72, 15, 15, 10, 10);
				g.fillRoundRect(54, 55, 15, 15, 10, 10);
				g.fillRoundRect(71, 55, 15, 15, 10, 10);
				break;
			case 5:
				g.setColor(new Color(255,0,51));
				g.fillRoundRect(37, 55, 15, 15, 10, 10);
				g.fillRoundRect(54, 55, 15, 15, 10, 10);
				g.fillRoundRect(54, 72, 15, 15, 10, 10);
				g.fillRoundRect(71, 72, 15, 15, 10, 10);
				break;
			case 6:
				g.setColor(new Color(255,255,0));
				g.fillRoundRect(61, 57, 15, 15, 10, 10);
				g.fillRoundRect(44, 57, 15, 15, 10, 10);
				g.fillRoundRect(44, 74, 15, 15, 10, 10);
				g.fillRoundRect(61, 74, 15, 15, 10, 10);
				break;
			}
		}
	}
} 