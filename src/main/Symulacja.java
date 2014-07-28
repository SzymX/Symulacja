
package main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
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
    private static int czasPrzybycia = 5;//w sekundach
    private static int czasPrzybyciaDoKolejnegoAuta = 5;//w sekundach
    private static int czasPostoju = 60; // w minutach
    private static int szybkoscUplywuCzasu = 1;//1000 to sekunda, bo w milisekundach

    public Symulacja() {
        initComponents(); //tworzenie interfejsu
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {


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
        setLocation(200, 200);
        setResizable(true);
        setLayout(new GridLayout(7, 1));


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

             for(procentAutzApka = 10; procentAutzApka < 95; procentAutzApka += 20) {

        iloscAutCalkowita = Integer.parseInt(jTextField1.getText());
        czasPostoju = Integer.parseInt(jTextField2.getText());
        czasPrzybycia = Integer.parseInt(jTextField3.getText());
        czasPrzybyciaDoKolejnegoAuta = 5;
        iloscAutAktywnych = iloscAutCalkowita;
     //   procentAutzApka = Integer.parseInt(jTextField4.getText());


        //Tworzenie miejsc parkingowych
        ArrayList<Miejsce> listaMiejsc = new ArrayList<Miejsce>();

        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 100, 0, 100, 999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 400, 0, 400, 999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 600, 0, 600, 999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 900, 0, 900, 999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 250, 0, 250, 999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 750, 0, 750, 999);

//        listaMiejsc = dodajuliceZparkingami(listaMiejsc,150,0,150,999);
//        listaMiejsc = dodajuliceZparkingami(listaMiejsc,750,0,750,999);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 0, 100, 999, 100);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 0, 400, 999, 400);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 0, 600, 999, 600);
        listaMiejsc = dodajuliceZparkingami(listaMiejsc, 0, 900, 999, 900);

        //System.out.println(listaMiejsc.size());

        ArrayList<Obiekt> listaWjazdow = new ArrayList<Obiekt>();
        listaWjazdow.add(new Obiekt(100, 0));
        listaWjazdow.add(new Obiekt(400, 0));
        listaWjazdow.add(new Obiekt(600, 0));
        listaWjazdow.add(new Obiekt(250, 0));
        listaWjazdow.add(new Obiekt(750, 0));
        listaWjazdow.add(new Obiekt(900, 0));
        listaWjazdow.add(new Obiekt(0, 100));
        listaWjazdow.add(new Obiekt(0, 400));
        listaWjazdow.add(new Obiekt(0, 600));
        listaWjazdow.add(new Obiekt(100, 999));
        listaWjazdow.add(new Obiekt(400, 999));
        listaWjazdow.add(new Obiekt(600, 999));
        listaWjazdow.add(new Obiekt(900, 999));
        listaWjazdow.add(new Obiekt(250, 999));
        listaWjazdow.add(new Obiekt(750, 999));
        listaWjazdow.add(new Obiekt(999, 100));
        listaWjazdow.add(new Obiekt(999, 400));
        listaWjazdow.add(new Obiekt(999, 600));
//
//


        List<Double> listaWynikow = new ArrayList<Double>();

        //rozpoznanie czy pojazd ma aplikacje
        Random random = new Random();
        Set<Integer> listaAutzAplikacja = listaAutzApka(iloscAutCalkowita, procentAutzApka, random);
        System.out.println(listaAutzAplikacja.size());
        int samochod = 0;
        long drogaRtotal = 0;
        long drogaStotal = 0;
        int counter = 0;

        // glowna petla aplikacji
        while (counter < 43200) { // ograniczenie do 12 h
            counter++;

            //sprawdzanie, czy sa jeszcze samochody
            //jesli nie ma to konczy symulacje
            if (iloscAutAktywnych == 0) {
                System.out.println("ilosc aut aktywnych = 0");
                break;
            }
            //System.out.println(iloscAutAktywnych);
            ArrayList<Miejsce> listaMiejscZajetych = new ArrayList<Miejsce>();

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
                        //System.out.println("Koniec czasu i odjezdza. Zostało: " + iloscAutAktywnych);

                    } else listaMiejscZajetych.add(miejsce);
                }
            }
             System.out.println("Liczba miejsc zajetych: "+ listaMiejscZajetych.size());


            //przybywanie samochodow
            if (czasPrzybyciaDoKolejnegoAuta == 0 /*&& samochod < iloscAutCalkowita*/) {
                samochod = samochod + 1;
                Random r = new Random();
                //System.out.println(samochod);


                //restart licznika
                czasPrzybyciaDoKolejnegoAuta = (int) (Math.random() * 3) - 1 + czasPrzybycia;//random od (-1 do 2) + czas Przybycia
                //System.out.println("czas przybycia do nastepgo auta: " + czasPrzybyciaDoKolejnegoAuta);
                Obiekt wjazd = listaWjazdow.get((int) (Math.random() * 17));
                int drogaR = 0;
                int drogaS = 0;

                //iloscAutCalkowita--;

                //algorytm szukajacy listaMiejsc dla auta
                //losowanie listaMiejsc docelowego
                //przy ktorym chce zaparkowac auto

                int xDocelowe;
                int yDocelowe;
                do {

                    xDocelowe = (int) Math.round(r.nextGaussian() * 200 + 500);
                    yDocelowe = (int) Math.round(r.nextGaussian() * 200 + 500);
                    //System.out.println("x: "+xDocelowe+" y: "+yDocelowe);
 //                   xDocelowe = 500;
 //                   yDocelowe = 500;

                } while (xDocelowe >= 1000 || yDocelowe >= 1000);

                if (listaAutzAplikacja.contains(samochod)) {    //jesli ma aplikacje
                    Miejsce wolnePrzyDocelowymSys = najblizszeWolneMiejsceSystem(listaMiejsc, xDocelowe, yDocelowe); // jedzie do wskazanego przez system
                    if (!(wolnePrzyDocelowymSys.getX() == 99999)) {
                        int gpsX, gpsY;
                        drogaS = drogaMiedzyPktami(wjazd.getX(), wjazd.getY(), wolnePrzyDocelowymSys.getX(), wolnePrzyDocelowymSys.getY());
                        if (!wolnePrzyDocelowymSys.getZajete()) {        //jesli jest wolne
                            wolnePrzyDocelowymSys.setZajete(true);     //zajmuje miejsce
                            //System.out.println("Samochod "+samochod+" zaparkowal z apka. Lczba miejsc zajetych: "+(listaMiejscZajetych.size()+1));
                            wolnePrzyDocelowymSys.setCzas(losujczasPostoju(r, czasPostoju));
                            //System.out.println(wolnePrzyDocelowymSys.getCzas());
                            double promien = Math.random() * 5 - 2.5 + 5.9;       //lapie GPS
                            double angle = Math.toRadians(Math.random() * 360);
                            gpsX = wolnePrzyDocelowymSys.getX() + (int) Math.round(promien * Math.cos(angle));
                            gpsY = wolnePrzyDocelowymSys.getY() + (int) Math.round(promien * Math.sin(angle));

                            Miejsce wolneMiejsceSystem = najblizszeWolneMiejsceSystem(listaMiejsc, gpsX, gpsY); //znajduje najblizsze wolne dla GPS
                            if (!(wolneMiejsceSystem.getX() == 99999)) {
                                wolnePrzyDocelowymSys.setMiejsceSystem(wolneMiejsceSystem); // zajmuje w systemie
                                wolneMiejsceSystem.setWartSystem(1);
                            } //mozna dopisać, że zaznacza najblize miejsce


                        } else {
                            Miejsce wolneMiejsce = najblizszeWolneMiejsce(listaMiejsc, wolnePrzyDocelowymSys.getX(), wolnePrzyDocelowymSys.getY());
                            if (!(wolneMiejsce.getX() == 99999)) {
                                int dodatkowaDroga = drogaMiedzyPktami(wolnePrzyDocelowymSys.getX(), wolnePrzyDocelowymSys.getY(), wolneMiejsce.getX(), wolneMiejsce.getY());
                                drogaS = drogaS + dodatkowaDroga;
                                wolneMiejsce.setZajete(true);     //zajmuje miejsce
                                //System.out.println("Samochod "+samochod+" zaparkowal z apka. Lczba miejsc zajetych: "+(listaMiejscZajetych.size()+1));

                                wolneMiejsce.setCzas(losujczasPostoju(r, czasPostoju));
                               // System.out.println(wolneMiejsce.getCzas());
                                double promien = Math.random() * 5 - 2.5 + 5.9;       //lapie GPS
                                double angle = Math.toRadians(Math.random() * 360);
                                gpsX = wolneMiejsce.getX() + (int) Math.round(promien * Math.cos(angle));
                                gpsY = wolneMiejsce.getY() + (int) Math.round(promien * Math.sin(angle));

                                Miejsce wolneMiejsceSystem = najblizszeWolneMiejsceSystem(listaMiejsc, gpsX, gpsY); //znajduje najblizsze wolne dla GPS
                                if (!(wolneMiejsceSystem.getX() == 99999)) {
                                    wolneMiejsce.setMiejsceSystem(wolneMiejsceSystem); // zajmuje w systemie
                                    wolneMiejsceSystem.setWartSystem(1);
                                }
                            } else { //mozna dopisać, że zaznacza najblize miejsce

                                iloscAutAktywnych--;//czyli samochod nie parkuje i jedzie w dal
                                System.out.println("Użytkownik nie znalal. samochod: " + samochod + " odjechal. Zostalo aktywnych: " + iloscAutAktywnych);
                            }
                        }

                    } else {
                        iloscAutAktywnych--;//czyli samochod nie parkuje i jedzie w dal
                        System.out.println("System nie znalazł. samochod: " + samochod + " odjechal. Zostalo aktywnych: " + iloscAutAktywnych);

                    }


                } else if (!listaAutzAplikacja.contains(samochod)) {

                    Miejsce najblizszePrzyDocelowym = najblizszeMiejsce(listaMiejsc, xDocelowe, yDocelowe);
                    drogaR = drogaMiedzyPktami(wjazd.getX(), wjazd.getY(), najblizszePrzyDocelowym.getX(), najblizszePrzyDocelowym.getY());
                    if (!(najblizszePrzyDocelowym.getX() == 99999) && !najblizszePrzyDocelowym.getZajete()) {
                        najblizszePrzyDocelowym.setZajete(true);
                        //System.out.println("Samochod " + samochod + " zaparkowal bez apki (I proba). Lczba miejsc zajetych: " + (listaMiejscZajetych.size() + 1));
                        najblizszePrzyDocelowym.setCzas(losujczasPostoju(r, czasPostoju));
                        //                  System.out.println(najblizszePrzyDocelowym.getCzas());

                    } else {
                        Miejsce wolnePrzyDocelowym = najblizszeWolneMiejsce(listaMiejsc, najblizszePrzyDocelowym.getX(), najblizszePrzyDocelowym.getY());
                        if (!(wolnePrzyDocelowym.getX() == 99999)) {
                            drogaR = drogaR + drogaMiedzyPktami(najblizszePrzyDocelowym.getX(), najblizszePrzyDocelowym.getY(), wolnePrzyDocelowym.getX(), wolnePrzyDocelowym.getY());
                            wolnePrzyDocelowym.setZajete(true);
                            //System.out.println("Samochod "+samochod+" zaparkowal bez apki (II proba). Lczba miejsc zajetych: "+(listaMiejscZajetych.size()+1));

                            wolnePrzyDocelowym.setCzas(losujczasPostoju(r, czasPostoju));
                            //                        System.out.println(wolnePrzyDocelowym.getCzas());

                        } else {
                            iloscAutAktywnych--;//czyli samochod nie parkuje i jedzie w dal
                            System.out.println("Użytkownik nie znalazł. samochod: " + samochod + " odjechal. Zostalo aktywnych: " + iloscAutAktywnych);

                        }

                    }


                }

                drogaRtotal += drogaR;
                drogaStotal += drogaS;

                //System.out.println(listaMiejscZajetych.size());


            } else
                czasPrzybyciaDoKolejnegoAuta--;

            //liczenie wskaźnika

            if(counter%60 == 0) {
                double suma = 0;
                for (Miejsce miejsce : listaMiejsc) {
                    suma = suma + Math.abs((miejsce.getZajete() ? 1 : 0) - miejsce.getWartSystem());
                }
                //System.out.println(suma);
                double wynik = suma / listaMiejsc.size();
                wynik *= 100000;
                wynik = Math.round(wynik);
                wynik /= 100000;

                listaWynikow.add(wynik);
            }


            try {
                //tzw. timer odpowiada za szybkosc dzialania aplikacji
                Thread.sleep(szybkoscUplywuCzasu);
            } catch (InterruptedException ex) {
                Logger.getLogger(Symulacja.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println(procentAutzApka);
        //System.out.println(listaWynikow);
        System.out.println("Droga z apka " + (int) drogaStotal / listaAutzAplikacja.size());
        System.out.println("Droga bez apki " + (int) drogaRtotal / (iloscAutCalkowita - listaAutzAplikacja.size()));
        try {
            ArrayList<Integer> ustawienia = new ArrayList<Integer>();
            ustawienia.add(iloscAutCalkowita);
            ustawienia.add(czasPostoju);
            ustawienia.add(czasPrzybycia);
            ustawienia.add(procentAutzApka);
            ArrayList<Integer> wyniki = new ArrayList<Integer>();
            int drogaBezApp = (int) (drogaRtotal/(iloscAutCalkowita-listaAutzAplikacja.size()));
            int drogaApp = (int) (drogaStotal/listaAutzAplikacja.size());
            wyniki.add(drogaBezApp);
            wyniki.add(drogaApp);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM-HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            String filename = date + ".txt";
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(ustawienia.toString());
            out.newLine();
            out.write(wyniki.toString());
            out.newLine();
            out.write(listaWynikow.toString());
            out.flush();
            out.close();
            Thread.sleep(1000); //opoznienie w celu zapisu nowego pliku
        } catch (IOException e) {
            System.out.println(" Save file Exception");
        } catch (InterruptedException ex) {
            Logger.getLogger(Symulacja.class.getName()).log(Level.SEVERE, null, ex);

        }
         }
    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) throws InterruptedException {


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Symulacja().setVisible(true);
            }
        });


    }

    private Miejsce najblizszeWolneMiejsce(ArrayList<Miejsce> miejsca, int xDocelowego, int yDocelowego) {

        double odlegloscMiejscaOdObiektu = 999999D;
        Miejsce najblizszeWolneMiejsce = new Miejsce(99999, 99999);

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

    private int drogaMiedzyPktami(int xPocz, int yPocz, int xKon, int yKon) {

        return Math.abs(yKon - yPocz) + Math.abs(xKon - xPocz);
    }

    private Miejsce najblizszeMiejsce(ArrayList<Miejsce> miejsca, int xDoc, int yDoc) {
        double odlegloscMiejscaOdObiektu = 999999D;
        Miejsce najblizszeMiejsce = new Miejsce(99999, 99999);

        for (Miejsce miejsce : miejsca) {
            //sprawdzamy czy miejsce jest wolne
            //System.out.println(i);
            //pobieramy odleglosc od obiektu docelowego
            double odleglosc = Math.abs(Math.sqrt(((miejsce.getX() - xDoc) * (miejsce.getX() - xDoc)) + ((miejsce.getY() - yDoc) * (miejsce.getY() - yDoc))));
            //sprawdzamy, czy jest mniejsza od aktualnie najblizszego miejsca
            if (odleglosc < odlegloscMiejscaOdObiektu) {
                odlegloscMiejscaOdObiektu = odleglosc;
                najblizszeMiejsce = miejsce;
                //System.out.println("odleglosc: " + odleglosc);
                //checkbox1.setState(true);
            }
        }
        return najblizszeMiejsce;

    }

    private int losujczasPostoju(Random r, int czasPostoju) {
        do {
            czasPostoju = (int) (Math.round(r.nextGaussian() * 55 + czasPostoju) * 60);

        } while (czasPostoju <= 0 || czasPostoju >= 28800); //TODO: poprawa do czasu
        return czasPostoju;
    }

    private Set<Integer> listaAutzApka(int liczbaAut, int procentAutzAApka, Random random) {
        int liczbaAutzApka = (int) ((double) procentAutzAApka / 100 * liczbaAut);
        Set<Integer> listaAutzApka = new LinkedHashSet<Integer>();
        while (listaAutzApka.size() < liczbaAutzApka) {
            Integer next = random.nextInt(liczbaAut) + 1;
            // As we're adding to a set, this will automatically do a containment check
            listaAutzApka.add(next);
        }
        return listaAutzApka;
    }

    private Miejsce najblizszeWolneMiejsceSystem(ArrayList<Miejsce> miejsca, int xGPS, int yGPS) {

        double odlegloscMiejscaOdObiektu = 999999D;
        Miejsce najblizszeWolneMiejsceSystem = new Miejsce(99999, 99999);

        for (Miejsce miejsce : miejsca) {
            //sprawdzamy czy miejsce jest wolne
            //System.out.println(i);
            if (miejsce.getWartSystem() == 0 || miejsce.getWartSystem() == 0.5) {
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

    private ArrayList<Miejsce> dodajuliceZparkingami(ArrayList<Miejsce> dotychczasowaListaMiejsc, int xPocz, int yPocz, int xKon, int yKon) {
        for (int x = xPocz; x < xKon + 1; x = x + 5) {
            for (int y = yPocz; y < yKon + 1; y = y + 5) {
                Miejsce noweMiejsce = new Miejsce(x, y);
                if (!dotychczasowaListaMiejsc.contains(noweMiejsce)) {
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
    private JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
