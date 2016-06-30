import java.util.ArrayList;

/**
 * Created by Appel on 28.06.2016.
 */
public class Main {

    private static ArrayList<Integer> liste = new ArrayList<Integer>();

    public static void main(String[] args) {


        for (int i = 0; i < 50; i++){
            int randomNumber = (int) (Math.random()*100) + 1;
            if (!liste.contains(randomNumber)){
                liste.add(randomNumber);
            }
        }/**/

        //=========================================erster thread========================================================

        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("-------------------------------------");
                System.out.println("delete thread gestartet");
                System.out.println("-------------------------------------");
                for (int i = 0; i < liste.size(); i++){
                    synchronized (Main.class) {
                        System.out.println("thread delete will auf " + liste.get(i) + " mit dem index " + i + " zugreifen.");
                        if (!checkPrim(liste.get(i))) {
                            System.out.println(liste.get(i) + " gelöscht");
                            liste.remove(i);
                            i--;
                            System.out.println(liste.toString());
                        }
                    }
                }
                System.out.println("-------------------------------------");
                System.out.println("delete thread beendet");
                System.out.println("-------------------------------------");
            }
        };

        //============================================zweiter thread====================================================

        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("-------------------------------------");
                System.out.println("insert thread gestartet");
                System.out.println("-------------------------------------");
                for (int i = 1; i < 100; i++) {
                    synchronized (Main.class) {
                        System.out.println("thread insert will die Zahl " + i + " hinzufügen");
                        if (checkPrim(i)) {
                            if (!liste.contains(i)) {
                                System.out.println((i) + " hinzugefügt");
                                liste.add(i);
                                System.out.println(liste.toString());
                            }
                        }
                    }
                }
                System.out.println("-------------------------------------");
                System.out.println("insert thread beendet");
                System.out.println("-------------------------------------");
            }
        };

        Thread deleteThread = new Thread(deleteRunnable);
        Thread insertThread = new Thread(insertRunnable);

        System.out.println("================================anfangsliste==========================================");
        System.out.println(liste.toString());
        System.out.println("======================================================================================");

        deleteThread.start();
        insertThread.start();

        try {
            deleteThread.join();
            insertThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==================================endliste===========================================");
        System.out.println(liste.toString());
        System.out.println("=====================================================================================");
    }


    public static boolean checkPrim(int number){
        if (number == 1){
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }
}
