package cli;

import ticket.Planet;

import java.util.Scanner;

public class PlanetChooser {
    private final Scanner sc;

    public PlanetChooser(Scanner sc) {
        this.sc = sc;
    }

    public Planet ask() {
       while (true) {

           String line = sc.nextLine();

           try {

               return Planet.valueOf(line);

           } catch (Exception e) {
               System.out.println("Planet " + line + " not fount. Please enter correct Planet name:");
           }

       }
    }
}
