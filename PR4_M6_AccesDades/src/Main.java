import java.io.IOException;
import java.util.*;


public class Main {
  static Scanner in = new Scanner(System.in); // System.in és global


  // Main
  public static void main(String[] args) throws InterruptedException, IOException {
    boolean running = true;
    while (running) {
      String menu = "Escull una opció:";
      menu = menu + "\n 1) PR140Main";
      menu = menu + "\n 2) PR141Main";
      menu = menu + "\n 3) PR142Main";
      menu = menu + "\n 4) Exit";
      System.out.println(menu);


      int opcio = Integer.valueOf(llegirLinia("Opció:"));
      try {
        switch (opcio) {
          case 1: PR140Main.main(args); break;
          case 2: PR141Main.main(args); break;
          case 3: PR142Main.main(args); break;
          case 4: running = false; break;
          default: break;
        }
      } catch (Exception e) {
          System.out.println(e);
      }
    }
    in.close();
  }


  static public String llegirLinia (String text) {
    System.out.print(text);
    return in.nextLine();
  }
}

