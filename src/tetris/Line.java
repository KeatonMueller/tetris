package tetris;

public class Line extends Shape{
	private boolean isVertical;
	public Line(int a, int b, int c, int d){
		super(a,b,c,d,0);
		isVertical = true;
	}
	public Line(Line l){
		super(l.get1(),l.get2(),l.get3(),l.get4(),0);
		isVertical = l.isVertical();
	}
	public boolean isVertical(){
		return isVertical;
	}
	public void setVertical(boolean b){
		isVertical = b;
	}
	public boolean rotate(){
		if(isVertical){
			if(anchor(c-1) && c%10 > 0 && c%10 < 8){
				change(c-1);
				isVertical = false;
				return true;
			}
			if(anchor(c-2) && c%10 > 1 && c%10 < 9){
				change(c-2);
				isVertical = false;
				return true;
			}
			if(anchor(b-1) && b%10 > 0 && b%10 < 8){
				change(b-1);
				isVertical = false;
				return true;
			}
			if(anchor(b-2) && b%10 > 1 && b%10 < 9){
				change(b-2);
				isVertical = false;
				return true;
			}
			if(anchor(a-1) && a%10 > 0 && a%10 < 8){
				change(a-1);
				isVertical = false;
				return true;
			}
			if(anchor(a-2) && a%10 > 1 && a%10 < 9){
				change(a-2);
				isVertical = false;
				return true;
			}
			if(anchor(c) && c%10 < 7){
				change(c);
				isVertical = false;
				return true;
			}
			if(anchor(c-3) && c%10 > 2){
				change(c-3);
				isVertical = false;
				return true;
			}
			if(anchor(b) && b%10 < 7){
				change(b);
				isVertical = false;
				return true;
			}
			if(anchor(b-3) && b%10 > 2){
				change(b-3);
				isVertical = false;
				return true;
			}
			if(anchor(a) && a%10 < 7){
				change(a);
				isVertical = false;
				return true;
			}
			if(anchor(a-3) && a%10 > 2){
				change(a-3);
				isVertical = false;
				return true;
			}
		}
		else{
			if(b/10 < 23 && anchor(b-20)){
				change(b-20);
				isVertical = true;
				return true;
			}
			if(c/10 < 23 && anchor(c-20)){
				change(c-20);
				isVertical = true;
				return true;
			}
			if(b/10 < 22 && anchor(b-10)){
				change(b-10);
				isVertical = true;
				return true;
			}
			if(c/10 < 22 && anchor(c-10)){
				change(c-10);
				isVertical = true;
				return true;
			}
			if(a/10 < 23 && anchor(a-20)){
				change(a-20);
				isVertical = true;
				return true;
			}
			if(d/10 < 23 && anchor(d-20)){
				change(d-20);
				isVertical = true;
				return true;
			}
			if(a/10 < 22 && anchor(a-10)){
				change(a-10);
				isVertical = true;
				return true;
			}
			if(d/10 < 22 && anchor(d-10)){
				change(d-10);
				isVertical = true;
				return true;
			}
			if(b/10 < 21 && anchor(b)){
				change(b);
				isVertical = true;
				return true;
			}
			if(c/10 < 21 && anchor(c)){
				change(c);
				isVertical = true;
				return true;
			}
			if(a/10 < 21 && anchor(a)){
				change(a);
				isVertical = true;
				return true;
			}
			if(d/10 < 21 && anchor(d)){
				change(d);
				isVertical = true;
				return true;
			}
			if(anchor(b-30)){
				change(b-30);
				isVertical = true;
				return true;
			}
			if(anchor(c-30)){
				change(c-30);
				isVertical = true;
				return true;
			}
			if(anchor(a-30)){
				change(a-30);
				isVertical = true;
				return true;
			}
			if(anchor(d-30)){
				change(d-30);
				isVertical = true;
				return true;
			}
		}
		return false;
	}
	public void change(int i){
		if(isVertical){
			change(i,i+1,i+2,i+3);
		}
		else{
			change(i,i+10,i+20,i+30);
		}
	}
	public boolean anchor(int i){
		if(isVertical){
			return check(i,i+1,i+2,i+3);
		}
		else{
			return check(i,i+10,i+20,i+30);
		}
	}
	public boolean update(){
		if(isVertical){
			if(d/10 != 23){
				if(super.checkDown(d)){
					return true;
				}
			}
		}
		else{
			return super.update();
		}
		return false;
	}
	public boolean left(){
		if(a%10 != 0){
			if(isVertical){
				if(checkLeft(a,b,c,d)){
					super.moveLeft();
					return true;
				}
			}
			else{
				if(checkLeft(a)){
					super.moveLeft();
					return true;
				}
			}
		}
		return false;
	}
	public boolean right(){
		if(d%10 != 9){
			if(isVertical){
				if(checkRight(a,b,c,d)){
					moveRight();
					return true;
				}
			}
			else{
				if(checkRight(d)){
					moveRight();
					return true;
				}
			}
		}
		return false;
	}
}
