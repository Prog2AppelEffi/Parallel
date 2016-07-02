import java.util.ArrayList;

/**
 * @author Martin Appelmann 4685580 Group 2a 
 * @author Benjamin Effner 4633079 Group 2a
 */
public class Main {

    private static ArrayList<Integer> liste = new ArrayList<Integer>();

	/**
	Main Methode, die die beiden Threads startet
	@param args wird nicht genutzt
	*/
    public static void main(String[] args) {

        for (int i = 0; i < 50; i++) {
            int randomNumber = (int) (Math.random() * 100) + 1;
            if (!liste.contains(randomNumber)) {
                liste.add(randomNumber);
            }
        }

        //=========================================erster thread========================================================

        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < liste.size(); i++) {
                    synchronized (Main.class) {
                        if (!checkPrim(liste.get(i))) {
                            liste.remove(i);
                            i--;
                        }
                    }
                }
            }
        };

        //============================================zweiter thread====================================================

        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 100; i++) {
                    synchronized (Main.class) {
                        if (checkPrim(i)) {
                            if (!liste.contains(i)) {
                                liste.add(i);
                            }
                        }
                    }
                }
            }
        };

        Thread deleteThread = new Thread(deleteRunnable);
        Thread insertThread = new Thread(insertRunnable);

        System.out.println("Anfangsliste");
        System.out.println(liste.toString());

        deleteThread.start();
        insertThread.start();

        try {
            deleteThread.join();
            insertThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Beenden eines Threads");
        }
        //Bubblesort ftw
        bubblesort(liste);
        
        System.out.println("Endliste");
        System.out.println(liste.toString());
        System.out.println("Anzahl der Zahlen: " + liste.size());
    }

	/**
	Prueft ob eine zahl eine Primzahl ist
	@param number die zu pruefende zahl
	@return booleam, true wenn zahl eine primzahl ist
	*/
    public static boolean checkPrim(int number) {
        if (number == 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Der Gute alte Bubblesort Algorithmus
     * @param list ist die zu sortierende ArrayList mit Integers
     * */
    public static void bubblesort(ArrayList<Integer> list) {
		int temp;
		for (int i = 1; i < list.size(); i++) {
			for (int j = 0; j < list.size() - i; j++) {
				if (list.get(j) > list.get(j + 1)) {
					temp = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, temp);
				}
			}
		}
	}
}
