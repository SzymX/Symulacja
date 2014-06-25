package main;

public class Obiekt {
    private final int x;
    private final int y;
    private final String nazwa;
    //private final int id;
    
    public Obiekt(int x, int y, String nazwa){
        this.x = x;
        this.y = y;
        this.nazwa = nazwa;
        //this.id = id;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
