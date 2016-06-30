import java.util.ArrayList;

/**
 * Created by Appel on 28.06.2016.
 */
public class Main {

    private static ArrayList<Integer> liste = new ArrayList<Integer>();

    public static void main(String[] args) {

        for (int i = 1; i < 11; i++) {
            liste.add(i);
        }
        /*
        for (int i = 0; i < 50; i++){
            int randomNumber = (int) (Math.random()*100) + 1;
            if (!liste.contains(randomNumber)){
                liste.add(randomNumber);
            }
        }*/

        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < liste.size(); i++){
                    syncDelete(i);
                }
            }
        };

        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    syncInsert(i);
                }
            }
        };

        Thread deleteThread = new Thread(deleteRunnable);
        Thread insertThread = new Thread(insertRunnable);


        System.out.println(liste.toString());

        deleteThread.start();
        insertThread.start();

        try {
            deleteThread.join();
            insertThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(liste.toString());
    }

    private synchronized static void syncDelete(int i){
        //System.out.println(i + " index " + liste.get(i) + " zahl");
        System.out.println("thread delete number " + liste.get(i) + " index: " + i);
        if (!checkPrim(liste.get(i))){
            System.out.println(liste.get(i) + " gelöscht mit dem index " + i);
            liste.remove(i);
            i--;
        }
    }

    private synchronized static void syncInsert(int i){
        System.out.println("thread insert number " + i + " index: " + (i+1));
        if (checkPrim(i + 1)){
            if (!liste.contains(i + 1)){
                liste.add(i + 1);
            }
        }
    }

    public static boolean checkPrim(int number){
        if (number == 1){
            return false;
        }
        for (int i = 2; i < number; i++) {
            //System.out.println(number + " % " + i + " " + (number%i));
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }
}
