package main;

public class Miejsce {
    private final int x;
    private final int y;
    private boolean zajete;
    private int czas;
    private double wartSystem;
    
    public Miejsce(int x, int y){
        this.x = x;
        this.y = y;
        this.zajete = false;
        this.wartSystem = 0.5;
    }
    
    public void setZajete(boolean zajete){
        this.zajete = zajete;
    }
    public boolean getZajete(){
        return this.zajete;
    }
    public void setWartSystem(double wartSystem) {
        this.wartSystem = wartSystem;
    }
    public double getWartSystem(){
        return this.wartSystem;
    }
    
    public void setCzas(int czas){
        this.czas = czas;
    }
    
    public int getCzas(){
        return this.czas;
    }
    
    public void zmniejszCzas(){
        this.czas --;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
}
