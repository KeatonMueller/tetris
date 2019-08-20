package tetris;

public class T extends Shape{
	private int orient;
	/* 
	 0: O		1:O 
	   OOO		  OO
	 			  O 

	 2:OOO		 3:O
	    O		  OO
	 			   O
	 */
	public T(int a, int b, int c, int d){
		super(a,b,c,d,1);
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
			if(b/10 < 23 && anchor(a)){
				change(a);
				orient = 1;
				return true;
			}
			if(anchor(a-10)){
				change(a-10);
				orient = 1;
				return true;
			}
			break;
		case 1:
			if(a%10 > 0 && anchor(b-1)){
				change(b-1);
				orient = 2;
				return true;
			}
			if(c%10 < 9 && anchor(b)){
				change(b);
				orient = 2;
				return true;
			}
			break;
		case 2:
			if(anchor(b-10)){
				change(b-10);
				orient = 3;
				return true;
			}
			break;
		case 3:
			if(a%10 < 9 && anchor(a)){
				change(a);
				orient = 0;
				return true;
			}
			if(b%10 > 0 && anchor(a-1)){
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
		case 3:
			 change(i,i+10-1,i+10,i+10+1);
			 break;
		case 0:
			 change(i,i+10,i+10+1,i+20);
			 break;
		case 1:
			 change(i,i+1,i+2,i+10+1);
			 break;
		case 2:
			 change(i,i+10-1,i+10,i+20);
			 break;
		}
	}
	public boolean anchor(int i){
		switch(orient){
		case 3:
			return check(i,i+10-1,i+10,i+10+1);
		case 0:
			return check(i,i+10,i+10+1,i+20);
		case 1:
			return check(i,i+1,i+2,i+10+1);
		case 2:
			return check(i,i+10-1,i+10,i+20);
		}
		return false;
	}
	public boolean update(){
		if(d/10 != 22){
			if(orient == 0){
				if(super.checkDown(b) && super.checkDown(c) && super.checkDown(d)){
					return true;
				}
			}
			else if(orient == 1){
				if(super.checkDown(d) && super.checkDown(c)){
					return true;
				}
			}
			else if(orient == 2){
				if(super.checkDown(a) && super.checkDown(c) && super.checkDown(d)){
					return true;
				}
			}
			else if(orient == 3){
				if(super.checkDown(d) && super.checkDown(b)){
					return true;
				}
			}
		}
		return false;
	}
	public boolean left(){
		if(orient == 0){
			if(b%10 != 0){
				if(checkLeft(a,b)){
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(a%10 != 0){
				if(checkLeft(a,b,d)){
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(a%10 != 0){
				if(checkLeft(a,d)){
					super.moveLeft();
					return true;
				}
			}
		}
		else if(orient == 3){
			if(b%10 != 0){
				if(checkLeft(a,b,d)){
					super.moveLeft();
					return true;
				}
			}
		}
		return false;
	}
	public boolean right(){
		if(orient == 0){
			if(d%10 != 9){
				if(checkRight(a,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 1){
			if(c%10 != 9){
				if(checkRight(a,c,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 2){
			if(c%10 != 9){
				if(checkRight(c,d)){
					moveRight();
					return true;
				}
			}
		}
		else if(orient == 3){
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
