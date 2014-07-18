package main;

public class Obiekt {
    private final int x;
    private final int y;
    
    public Obiekt(int x, int y){
        this.x = x;
        this.y = y;
        //this.id = id;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
