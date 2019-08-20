package tetris;

public class L1 extends Shape{
	private int orient;
	/*
	 0: OO		1:OOO
	    O			O
	    O

	 2: O		3:O
	 	O		  OOO
	   OO
	 */
	public L1(int a, int b, int c, int d){
		super(a,b,c,d,2);
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
			if(c%10 > 0 && anchor(c-1)){
				change(c-1);
				orient = 1;
				return true;
			}
			if(b%10 < 9 && anchor(c)){
				change(c);
				orient = 1;
				return true;
			}
			if(c%10 > 0 && d/10 < 23 && anchor(d-1)){
				change(d-1);
				orient = 1;
				return true;
			}
			if(b%10 < 9 && d/10 < 23 && anchor(d)){
				change(d);
				orient = 1;
				return true;
			}
			if(a%10 > 0 && anchor(a-1)){
				change(a-1);
				orient = 1;
				return true;
			}
			if(b%10 < 9 && anchor(a)){
				change(a);
				orient = 1;
				return true;
			}
			if(c%10 > 1 && anchor(c-2)){
				change(c-2);
				orient = 1;
				return true;
			}
			if(c%10 > 1 && d/10 < 23 && anchor(d-2)){
				change(d-2);
				orient = 1;
				return true;
			}
			if(c%10 > 1 && anchor(a-2)){
				change(a-2);
				orient = 1;
				return true;
			}
			break;
		case 1:
			if(anchor(b-10)){
				change(b-10);
				orient = 2;
				return true;
			}
			if(anchor(c-10)){
				change(c-10);
				orient = 2;
				return true;
			}
			if(a%10 > 0 && anchor(a-10)){
				change(a-10);
				orient = 2;
				return true;
			}
			if(d/10 < 23 && anchor(b)){
				change(b);
				orient = 2;
				return true;
			}
			if(d/10 < 23 && anchor(c)){
				change(c);
				orient = 2;
				return true;
			}
			if(d/10 < 23 && a%10 > 0 && anchor(a)){
				change(a);
				orient = 2;
				return true;
			}
			if(c%10 < 9 && anchor(c-10+1)){
				change(c-10+1);
				orient = 2;
				return true;
			}
			break;
		case 2:
			if(b%10 < 9 && anchor(b-1)){
				change(b-1);
				orient = 3;
				return true;
			}
			if(b%10 < 9 && anchor(a-1)){
				change(a-1);
				orient = 3;
				return true;
			}
			if(c%10 > 0 && anchor(c-10-1)){
				change(c-10-1);
				orient = 3;
				return true;
			}
			if(c%10 > 0 && anchor(a-2)){
				change(a-2);
				orient = 3;
				return true;
			}
			if(b%10 < 8 && anchor(b)){
				change(b);
				orient = 3;
				return true;
			}
			if(b%10 < 8 && anchor(a)){
				change(a);
				orient = 3;
				return true;
			}
			break;
		case 3:
			if(anchor(c-20)){
				change(c-20);
				orient = 0;
				return true;
			}
			if(anchor(a-10)){
				change(a-10);
				orient = 0;
				return true;
			}
			if(d%10 < 9 && anchor(d-20)){
				change(d-20);
				orient = 0;
				return true;
			}
			if(c/10 < 23 && anchor(c-10)){
				change(c-10);
				orient = 0;
				return true;
			}
			if(c/10 < 23 && anchor(a)){
				change(a);
				orient = 0;
				return true;
			}
			if(c/10 < 23 && d%10 < 9 && anchor(d-10)){
				change(d-10);
				orient = 0;
				return true;
			}
			if(a%10 > 0 && c/10 < 23 && anchor(a-1)){
				change(a-1);
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
			change(i,i+1,i+2,i+10+2);
			break;
		case 1:
			change(i,i+10,i+20-1,i+20);
			break;
		case 2:
			change(i,i+10,i+10+1,i+10+2);
			break;
		case 3:
			change(i,i+1,i+10,i+20);
			break;
		}
	}
	public boolean anchor(int i){
		switch(orient){
		case 0:
			return check(i,i+1,i+2,i+10+2);
		case 1:
			return check(i,i+10,i+20-1,i+20);
		case 2:
			return check(i,i+10,i+10+1,i+10+2);
		case 3:
			return check(i,i+1,i+10,i+20);
		}
		return false;
	}
	public boolean update(){
		if(d/10 != 23){
			if(orient == 0){
				if(super.checkDown(b) && super.checkDown(d)){

					return true;
				}
			}
			else if(orient == 1){
				if(super.checkDown(a) && super.checkDown(b) && super.checkDown(d)){

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
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(a%10 != 0){
				if(checkLeft(a,d)){
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(c%10 != 0){
				if(checkLeft(a,b,c)){
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 3){
			if(a%10 != 0){
				if(checkLeft(a,b)){
					super.moveLeft();
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
			if(c%10 != 9){
				if(checkRight(c,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(a%10 != 9){
				if(checkRight(a,b,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 3){
			if(d%10 != 9){
				if(checkRight(a,d)){
					moveRight();
					return true;
				}
			}
		}
		return false;
	}
	public void johnsanswertoyouramazingproblemsoftetrisbykeatonmueller(){
		System.out.println("John is awesome and knows how to fix all problems");
	}
}
