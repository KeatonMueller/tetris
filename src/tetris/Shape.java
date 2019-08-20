package tetris;

public class Shape {
	public int a, b, c, d;
	public int shape; //0 - Line | 1 - T | 2 - L1 | 3 - L2 | 4 - Z1 | 5 - Z2
	public Shape(int a1, int b1, int c1, int d1, int s){
		a = a1;
		b = b1;
		c = c1;
		d = d1;
		shape = s;
	}
	public Shape(Shape s){
		a = s.get1();
		b = s.get2();
		c = s.get3();
		d = s.get4();
		shape = s.getShape();
	}
	public boolean update(){
		if(d/10 != 23){
			if(checkDown(a) && checkDown(b) && checkDown(c) && checkDown(d)){
				return true;
			}
		}
		return false;
	}
	public void drop(){
		while(update()){
			move();
		}
	}
	public void move(){
		a+=10;
		b+=10;
		c+=10;
		d+=10;
	}
	public void moveLeft(){
		a--;
		b--;
		c--;
		d--;
	}
	public void moveRight(){
		a++;
		b++;
		c++;
		d++;
	}
	public void change(int i, int j, int k, int l){
		a = i;
		b = j;
		c = k;
		d = l;
	}
	public boolean check(int i){
		return (Tetris.board[i/10][i%10].isEmpty() || Tetris.getShape(i) == this);
	}
	public boolean check(int i, int j, int k, int l){
		return check(i) && check(j) && check(k) && check(l);
	}
	public boolean rotate(){
		System.out.println("Unoverriden Rotate Method");
		return false;
	}
	public boolean checkDown(int i){
		return (Tetris.board[i/10+1][i%10].isEmpty());
	}
	public boolean checkLeft(int i){
		return Tetris.board[i/10][i%10-1].isEmpty();
	}
	public boolean checkLeft(int i, int j){
		return checkLeft(i) && checkLeft(j);
	}
	public boolean checkLeft(int i, int j, int k){
		return checkLeft(i,j) && checkLeft(k);
	}
	public boolean checkLeft(int i, int j, int k, int l){
		return checkLeft(i,j,k) && checkLeft(l);
	}
	public boolean left(){
		System.out.println("Unoverriden Left Method");
		return false;
	}
	public boolean checkRight(int i){
		return Tetris.board[i/10][i%10+1].isEmpty();
	}
	public boolean checkRight(int i, int j){
		return checkRight(i) && checkRight(j);
	}
	public boolean checkRight(int i, int j, int k){
		return checkRight(i,j) && checkRight(k);
	}
	public boolean checkRight(int i, int j, int k, int l){
		return checkRight(i,j,k) && checkRight(l);
	}
	public boolean right(){
		System.out.println("Unoverriden Right Method");
		return false;
	}
	public int get1(){
		return a;
	}
	public int get2(){
		return b;
	}
	public int get3(){
		return c;
	}
	public int get4(){
		return d;
	}
	public int getShape(){
		return shape;
	}
	public String toString(){
		return shape + " " + a+b+c+d;
	}
	public void set1(int i){
		a = i;
	}
	public void set2(int i){
		b = i;
	}
	public void set3(int i){
		c = i;
	}
	public void set4(int i){
		d = i;
	}
}
