package tetris;

public class Square extends Shape{
	public Square(int a, int b, int c, int d){
		super(a,b,c,d,6);
	}
	public boolean rotate(){
		return true;
	}
	public boolean update(){
		if(d/10 != 23){
			if(super.checkDown(c) && super.checkDown(d)){
				return true;
			}
		}
		return false;
	}
	public boolean left(){
		if(a%10 != 0){
			if(checkLeft(a,c)){
				moveLeft();
				return true;
			}
		}
		return false;
	}
	public boolean right(){
		if(b%10 != 9){
			if(checkRight(b,d)){
				moveRight();
				return true;
			}
		}
		return false;
	}
}
