package tetris;

public class L2 extends Shape{
	private int orient;
	/*
	 0:OO		1:  O
		O		  OOO
		O
		
	 2:O		3:OOO
	   O   		  O
	   OO
	*/
	public L2(int a, int b, int c, int d){
		super(a,b,c,d,3);
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
			if(b%10 < 9 && anchor(b+1)){
				change(b+1);
				orient = 1;
				return true;
			}
			if(b%10 < 9 && anchor(c+1)){
				change(c+1);
				orient = 1;
				return true;
			}
			if(a%10 > 0 && anchor(b)){
				change(b);
				orient = 1;
				return true;
			}
			if(a%10 > 0 && anchor(c)){
				change(c);
				orient = 1;
				return true;
			}
			if(b%10 < 8 && anchor(b+2)){
				change(b+2);
				orient = 1;
				return true;
			}
			if(b%10 < 8 && anchor(c+2)){
				change(c+2);
				orient = 1;
				return true;
			}
			if(a%10 > 1 && anchor(a)){
				change(a);
				orient = 1;
				return true;
			}
			if(a%10 > 1 && anchor(c-1)){
				change(c-1);
				orient = 1;
				return true;
			}
			break;
		case 1:
			if(c/10 < 23 && anchor(a-1)){
				change(a-1);
				orient = 2;
				return true;
			}
			if(c/10 < 23 && anchor(a-2)){
				change(a-2);
				orient = 2;
				return true;
			}
			if(a%10 < 9 && c/10 < 23 && anchor(a)){
				change(a);
				orient = 2;
				return true;
			}
			if(c/10 < 22 && anchor(c)){
				change(c);
				orient = 2;
				return true;
			}
			if(c/10 < 22 && anchor(b)){
				change(b);
				orient = 2;
				return true;
			}
			if(c/10 < 22 && a%10 < 9 && anchor(d)){
				change(d);
				orient = 2;
				return true;
			}
			if(anchor(c-20)){
				change(c-20);
				orient = 2;
				return true;
			}
			if(anchor(b-20)){
				change(b-20);
				orient = 2;
				return true;
			}
			if(a%10 < 9 && anchor(a-10)){
				change(a-10);
				orient = 2;
				return true;
			}
			break;
		case 2:
			if(a%10 > 0 && anchor(b-1)){
				change(b-1);
				orient = 3;
				return true;
			}
			if(a%10 > 0 && anchor(a-1)){
				change(a-1);
				orient = 3;
				return true;
			}
			if(a%10 > 0 && c/10 < 23 && anchor(c-1)){
				change(c-1);
				orient = 3;
				return true;
			}
			if(d%10 < 9 && anchor(b)){
				change(b);
				orient = 3;
				return true;
			}
			if(d%10 < 9 && anchor(a)){
				change(a);
				orient = 3;
				return true;
			}
			if(a%10 > 1 && anchor(a-2)){
				change(a-2);
				orient = 3;
				return true;
			}
			if(d%10 < 8 && anchor(a+1)){
				change(a+1);
				orient = 3;
				return true;
			}
			break;
		case 3:
			if(d/10 < 23 && anchor(a)){
				change(a);
				orient = 0;
				return true;
			}
			if(d/10 < 23 && anchor(b)){
				change(b);
				orient = 0;
				return true;
			}
			if(d/10 < 22 && anchor(d)){
				change(d);
				orient = 0;
				return true;
			}
			if(d/10 < 22 && anchor(d+1)){
				change(d+1);
				orient = 0;
				return true;
			}
			if(d/10 < 23 && a%10 > 0 && anchor(a-1)){
				change(a-1);
				orient = 0;
				return true;
			}
			if(d/10 < 22 && a%10 > 0 && anchor(d-1)){
				change(d-1);
				orient = 0;
				return true;
			}
			if(anchor(a-10)){
				change(a-10);
				orient = 0;
				return true;
			}
			if(anchor(b-10)){
				change(b-10);
				orient = 0;
				return true;
			}
			if(a%10 > 0 && anchor(a-10-1)){
				change(a-10-1);
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
			change(i,i+10-2,i+10-1,i+10);
			break;
		case 1:
			change(i,i+10,i+20,i+20+1);
			break;
		case 2:
			change(i,i+1,i+2,i+10);
			break;
		case 3:
			change(i,i+1,i+10+1,i+20+1);
			break;
		}
	}
	public boolean anchor(int i){
		switch(orient){
		case 0:
			return check(i,i+10-2,i+10-1,i+10);
		case 1:
			return check(i,i+10,i+20,i+20+1);
		case 2:
			return check(i,i+1,i+2,i+10);
		case 3:
			return check(i,i+1,i+10+1,i+20+1);
		}
		return false;
	}
	public boolean update(){
		if(d/10 != 23){
			if(orient == 0){
				if(super.checkDown(a) && super.checkDown(d)){
					return true;
				}
			}
			else if(orient == 1){
				if(super.checkDown(b) && super.checkDown(c) && super.checkDown(d)){
					return true;
				}
			}
			else if(orient == 2){
				if(super.checkDown(c) && super.checkDown(d)){
					return true;
				}
			}
			else if(orient == 3){
				if(super.checkDown(b) && super.checkDown(c) && super.checkDown(d)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean left(){
		if(orient == 0){
			if(a%10 != 0){
				if(checkLeft(a,c,d)){
					moveLeft();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(b%10 != 0){
				if(checkLeft(a,b)){
					moveLeft();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(a%10 != 0){
				if(checkLeft(a,b,c)){
					moveLeft();
					return true;
				}
			}
		}
		else if(orient == 3){
			if(a%10 != 0){
				if(checkLeft(a,d)){
					moveLeft();
					return true;
				}
			}
		}
		return false;
	}
	public boolean right(){
		if(orient == 0){
			if(b%10 != 9){
				if(checkRight(b,c,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(a%10 != 9){
				if(checkRight(a,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(d%10 != 9){
				if(checkRight(a,b,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 3){
			if(c%10 != 9){
				if(checkRight(c,d)){
					moveRight();
					return true;
				}
			}
		}
		return false;
	}
}
