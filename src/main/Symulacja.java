
package main;
import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Symulacja extends javax.swing.JFrame {

    // dla bezpieczenstwa dodano wartosci domyslne,
    // ktore mozna zmienic na wejsciu
    private static int iloscAutCalkowita = 1000;
    private static int procentAutzApka = 100;
    private static int iloscAutAktywnych;
    private static int czasPrzybycia = 3;//w minutach
    private static int czasPrzybyciaDoKolejnegoAuta = 3;//w minutach
    private static int czasPostoju = 60; // w minutach
    private static int szybkoscUplywuCzasu = 1;//1000 to sekunda, bo w milisekundach
    private static boolean symuluj = false;
    private static boolean czynny = true;
    
    public Symulacja() {
        initComponents(); //tworzenie interfejsu
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField(); //ilosc aut
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new JLabel();
        jTextField4 = new JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Symulacja");
        setSize(200, 300);
        setLocation(200,200);
        setResizable(true);
        setLayout(new GridLayout(7,1));



        jLabel1.setText("Ilość Aut:");
        jTextField1.setText("1000");
        jLabel2.setText("Czas postoju:");
        jTextField2.setText("60"); // czas postoju
        jLabel3.setText("Czas przybycia:");
        jTextField3.setText("3"); //czas przybycia
        jLabel4.setText("Procent aut z Aplikacaja");
        jTextField4.setText("100");

        jButton1.setText("Symuluj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        add(jLabel1);
        add(jTextField1);
        add(jLabel2);
        add(jTextField2);
        add(jLabel3);
        add(jTextField3);
        add(jLabel4);
        add(jTextField4);
        add(jButton1);


    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       iloscAutCalkowita = Integer.parseInt(jTextField1.getText());
       czasPostoju = Integer.parseInt(jTextField2.getText());
       czasPrzybycia = Integer.parseInt(jTextField3.getText());
       iloscAutAktywnych = iloscAutCalkowita;
       procentAutzApka = Integer.parseInt(jTextField4.getText());


        //Tworzenie miejsc parkingowych
        //TODO: tworzenie ulic z miejsc parkinowych
//        Miejsce listaMiejsc[] = new Miejsce[21];
        ArrayList<Miejsce> listaMiejsc = new ArrayList<Miejsce>();
//        listaMiejsc[0] = new Miejsce(100,150);
//        listaMiejsc[1] = new Miejsce(120,150);
//        listaMiejsc[2] = new Miejsce(140,150);
//        listaMiejsc[3] = new Miejsce(100,200);
//        listaMiejsc[4] = new Miejsce(120,200);
//        listaMiejsc[5] = new Miejsce(140,200);
//        listaMiejsc[6] = new Miejsce(160,400);
//        listaMiejsc[7] = new Miejsce(160,450);
//        listaMiejsc[8] = new Miejsce(140,450);
//        listaMiejsc[9] = new Miejsce(120,450);
//        listaMiejsc[10] = new Miejsce(100,450);
//        listaMiejsc[11] = new Miejsce(60,650);
//        listaMiejsc[12] = new Miejsce(50,650);
//        listaMiejsc[13] = new Miejsce(40,650);
//        listaMiejsc[14] = new Miejsce(30,650);
//        listaMiejsc[15] = new Miejsce(650,500);
//        listaMiejsc[16] = new Miejsce(700,500);
//        listaMiejsc[17] = new Miejsce(750,500);
//        listaMiejsc[18] = new Miejsce(650,550);
//        listaMiejsc[19] = new Miejsce(700,550);
//        listaMiejsc[20] = new Miejsce(750,550);
//
//        for(int i=0; i< listaMiejsc.length; i++) {
//            listaMiejsc.add(listaMiejsc[i]);
//        }

        listaMiejsc = dodajuliceZparkingami(listaMiejsc,450,0,450,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,550,0,550,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,650,0,650,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,350,0,350,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,150,0,150,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,750,0,750,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,0,550,999,550);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc,0,450,999,450);


        List <Double> listaWynikow = new ArrayList<Double>();

        //rozpoznanie czy pojazd ma aplikacje
        Random random = new Random();
        Set <Integer> listaAutzAplikacja = listaAutzApka(iloscAutCalkowita,procentAutzApka,random);
        int samochod = 0;
        
        // glowna petla aplikacji
        while (true) {

            //sprawdzanie, czy sa jeszcze samochody
            //jesli nie ma to konczy symulacje
            if (iloscAutAktywnych == 0) break;

            //sprawdzanie miejsc parkingowych
            for (Miejsce miejsce : listaMiejsc) {
                if (miejsce.getZajete()) {
                    //jesli zajete to kontrolujemy czas postoju
                    miejsce.zmniejszCzas();

                    if (miejsce.getCzas() <= 0) {
                        miejsce.setZajete(false);
                        if (miejsce.getMiejsceSystem() != null && miejsce.getMiejsceSystem().getWartSystem() == 1) {
                            miejsce.getMiejsceSystem().setWartSystem(0);
                        }
                        iloscAutAktywnych--;//zwalniamy kolejny samochod
                        //System.out.println("Koniec czasu i odjezdza.");

                    }
                    //System.out.println(listaMiejsc[i].getZajete());
                }
            }


            //przybywanie samochodow
            if (czasPrzybyciaDoKolejnegoAuta == 0) {
                samochod = samochod +1;
                Random r = new Random();


                //restart licznika
                czasPrzybyciaDoKolejnegoAuta = (int) (Math.random() * 3) - 1 + czasPrzybycia;//random od -1 do 2 + czas Przybycia
                //System.out.println(czasPrzybyciaDoKolejnegoAuta);

                iloscAutCalkowita--;

                //algorytm szukajacy listaMiejsc dla auta
                //losowanie listaMiejsc docelowego
                //przy ktorym chce zaparkowac auto

                int xDocelowe;
                int yDocelowe;
                do {

                    xDocelowe = (int) Math.round(r.nextGaussian() * 200 + 500);
                    yDocelowe = (int) Math.round(r.nextGaussian() * 200 + 500);
                    //System.out.println("x: "+xDocelowe+" y: "+yDocelowe);

                } while (xDocelowe >= 1000 || yDocelowe >= 1000);

                Miejsce wolnePrzyDocelowym = najblizszeWolneMiejsce(listaMiejsc, xDocelowe, yDocelowe);

                //sprawdzamy, czy zostalo znalezione miejsce wolne
                if (!(wolnePrzyDocelowym.getX() == 99999)) {
                    //samochod parkuje i miejsce jest zajete
                    wolnePrzyDocelowym.setZajete(true);
                    wolnePrzyDocelowym.setCzas((int) Math.round(r.nextGaussian()*30+czasPostoju));

                    double promien = Math.random() * 5 + 5; //TODO zweryfikowac wartosci otrzymane z aplikacji
                    double angle = Math.toRadians(Math.random() * 360);
                    int gpsX = wolnePrzyDocelowym.getX() + (int) Math.round(promien * Math.cos(angle));
                    int gpsY = wolnePrzyDocelowym.getY() + (int) Math.round(promien * Math.sin(angle));

                    if (listaAutzAplikacja.contains(samochod)) {   // procent ktory ma aplikacje

                        Miejsce wolneMiejsceSystem = najblizszeWolneMiejsceSystem(listaMiejsc, gpsX, gpsY);
                        if(!(wolneMiejsceSystem.getX() == 99999)) {
                            wolnePrzyDocelowym.setMiejsceSystem(wolneMiejsceSystem);
                            wolneMiejsceSystem.setWartSystem(1);
                        }
                    }

                } else {
                    iloscAutAktywnych--;//czyli samochod nie parkuje i jedzie w dal   
                    //System.out.println("Nie znalazl i odjechal.");
                }
            } else
                czasPrzybyciaDoKolejnegoAuta--;

            //liczenie wskaźnika
            double suma = 0;
            for(Miejsce miejsce : listaMiejsc){
                suma = suma + Math.abs((miejsce.getZajete() ? 1 : 0) - miejsce.getWartSystem());
            }
            //System.out.println(suma);
            double wynik = suma/listaMiejsc.size();
            wynik *=100000;
            wynik = Math.round(wynik);
            wynik /= 100000;

            listaWynikow.add(wynik);

            try {
                //tzw. timer odpowiada za szybkosc dzialania aplikacji
                Thread.sleep(szybkoscUplywuCzasu);
            } catch (InterruptedException ex) {
                Logger.getLogger(Symulacja.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println(listaWynikow);
        try {
            ArrayList<Integer> ustawienia = new ArrayList<Integer>();
            ustawienia.add(iloscAutCalkowita);
            ustawienia.add(czasPostoju);
            ustawienia.add(czasPrzybycia);
            ustawienia.add(procentAutzApka);
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            out.write(ustawienia.toString());
            out.newLine();
            out.write(listaWynikow.toString());
            out.close();
        } catch (IOException e) {
            System.out.println(" Save file Exception");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) throws InterruptedException {
       

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Symulacja().setVisible(true);
            }
        });

        
    }

    private Miejsce najblizszeWolneMiejsce(ArrayList<Miejsce> miejsca, int xDocelowego, int yDocelowego){

        double odlegloscMiejscaOdObiektu = 999999D;
        Miejsce najblizszeWolneMiejsce = new Miejsce(99999,99999);

        for (Miejsce miejsce : miejsca) {
            //sprawdzamy czy miejsce jest wolne
            //System.out.println(i);
            if (!miejsce.getZajete()) {
                //pobieramy odleglosc od obiektu docelowego
                double odleglosc = Math.abs(Math.sqrt(((miejsce.getX() - xDocelowego) * (miejsce.getX() - xDocelowego)) + ((miejsce.getY() - yDocelowego) * (miejsce.getY() - yDocelowego))));
                //sprawdzamy, czy jest mniejsza od aktualnie najblizszego miejsca
                if (odleglosc < odlegloscMiejscaOdObiektu) {
                    odlegloscMiejscaOdObiektu = odleglosc;
                    najblizszeWolneMiejsce = miejsce;
                    //System.out.println("odleglosc: " + odleglosc);
                    //checkbox1.setState(true);
                }
            }
        }
        return najblizszeWolneMiejsce;

    }
    private Set<Integer> listaAutzApka (int liczbaAut, int procentAutzAApka, Random random) {
        int liczbaAutzApka = (int) ((double) procentAutzAApka/100*liczbaAut);
        Set<Integer> listaAutzApka = new LinkedHashSet<Integer>();
        while (listaAutzApka.size() < liczbaAutzApka)
        {
            Integer next = random.nextInt(liczbaAut) + 1;
            // As we're adding to a set, this will automatically do a containment check
            listaAutzApka.add(next);
        }
        return listaAutzApka;
    }

    private Miejsce najblizszeWolneMiejsceSystem(ArrayList<Miejsce> miejsca, int xGPS, int yGPS){

        double odlegloscMiejscaOdObiektu = 999999D;
        Miejsce najblizszeWolneMiejsceSystem = new Miejsce(99999,99999);

        for (Miejsce miejsce : miejsca) {
            //sprawdzamy czy miejsce jest wolne
            //System.out.println(i);
            if (miejsce.getWartSystem() == 0 || miejsce.getWartSystem()== 0.5) {
                //pobieramy odleglosc od obiektu docelowego
                double odleglosc = Math.abs(Math.sqrt(((miejsce.getX() - xGPS) * (miejsce.getX() - xGPS)) + ((miejsce.getY() - yGPS) * (miejsce.getY() - yGPS))));
                //sprawdzamy, czy jest mniejsza od aktualnie najblizszego miejsca
                if (odleglosc < odlegloscMiejscaOdObiektu) {
                    odlegloscMiejscaOdObiektu = odleglosc;
                    najblizszeWolneMiejsceSystem = miejsce;
                    //System.out.println("odleglosc: " + odleglosc);
                    //checkbox1.setState(true);
                }
            }
        }
        return najblizszeWolneMiejsceSystem;

    }

    private ArrayList<Miejsce> dodajuliceZparkingami(ArrayList<Miejsce> dotychczasowaListaMiejsc, int xPocz, int yPocz, int xKon, int yKon){
        for(int x = xPocz; x < xKon+1; x=x+5){
           for(int y = yPocz; y < yKon+1; y=y+5){
               Miejsce noweMiejsce = new Miejsce(x,y);
               if(!dotychczasowaListaMiejsc.contains(noweMiejsce)) {
                   dotychczasowaListaMiejsc.add(noweMiejsce);
               }
           }
        }
        return dotychczasowaListaMiejsc;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
