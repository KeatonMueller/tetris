package tetris;

public class Z2 extends Shape{
	private int orient;
	/*
	 0: OO		1: O
	 	 OO		  OO
	 	 		  O
	 */
	public Z2(int a, int b, int c, int d){
		super(a,b,c,d,5);
		orient = 0;
	}
	public int getOrient(){
		return orient;
	}
	public void setOrient(int o){
		orient = o;
	}
	public boolean rotate(){
		switch(orient){
		case 0:
			if(anchor(d-20)){
				change(d-20);
				orient = 1;
				return true;
			}
			if(anchor(b-10)){
				change(b-10);
				orient = 1;
				return true;
			}
			if(c/10 < 23 && anchor(b+1)){
				change(b+1);
				orient = 1;
				return true;
			}
			if(c/10 < 23 && anchor(b)){
				change(b);
				orient = 1;
				return true;
			}
			if(c/10 < 23 && a%10 > 0 && anchor(a)){
				change(a);
				orient = 1;
				return true;
			}
			if(c/10 < 23 && d%10 < 9 && anchor(b+2)){
				change(b+2);
				orient = 1;
				return true;
			}
			break;
		case 1:
			if(b%10 > 0 && anchor(b-1)){
				change(b-1);
				orient = 0;
				return true;
			}
			if(c%10 < 9 && anchor(b)){
				change(b);
				orient = 0;
				return true;
			}
			if(c%10 < 8 && anchor(c)){
				change(c);
				orient = 0;
				return true;
			}
			if(c%10 < 9 && anchor(a-1)){
				change(a-1);
				orient = 0;
				return true;
			}
			if(a%10 < 8 && anchor(a)){
				change(a);
				orient = 0;
				return true;
			}
			if(a%10 < 7 && anchor(c+1)){
				change(c+1);
				orient = 0;
				return true;
			}
			break;
		}
		return false;
	}
	public void change(int i){
		switch(orient){
		case 0:
			change(i,i+10-1,i+10,i+20-1);
			break;
		case 1:
			change(i,i+1,i+10+1,i+10+2);
			break;
		}
	}
	public boolean anchor(int i){
		switch(orient){
		case 0:
			return check(i,i+10-1,i+10,i+20-1);
		case 1:
			return check(i,i+1,i+10+1,i+10+2);
		}
		return false;
	}
	public boolean update(){
		if(d/10 != 23){
			if(orient == 0){
				if(super.checkDown(a) && super.checkDown(c) && super.checkDown(d)){
					
					return true;
				}
			}
			else if(orient == 1){
				if(super.checkDown(c) && super.checkDown(d)){
					
					return true;
				}
			}
		}
		return false;
	}
	public boolean left(){
		if(orient == 0){
			if(a%10 != 0){
				if(checkLeft(a,c)){
					moveLeft();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(b%10 != 0){
				if(checkLeft(a,b,d)){
					moveLeft();
					return true;
				}
			}
		}
		return false;
	}
	public boolean right(){
		if(orient == 0){
			if(d%10 != 9){
				if(checkRight(b,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(a%10 != 9){
				if(checkRight(a,c,d)){
					moveRight();
					return true;
				}
			}
		}
		return false;
	}
}
