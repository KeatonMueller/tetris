package tetris;

public class Box {
	private boolean isEmpty;
	private int shape;
	private int position;
	public Box(int a){
		isEmpty = true;
		shape = -1;
		position = a;
	}
	public boolean isEmpty(){
		return isEmpty;
	}
	public void setEmpty(boolean b){
		isEmpty = b;
	} 
	public int getShape(){
		return shape;
	}
	public void setShape(int a){
		isEmpty = false;
		shape = a;
		if(a == 7){
			isEmpty = true;
		}
	}
	public void setPosition(int a){
		position = a;
	}
	public int getPosition(){
		return position;
	}
	
}
