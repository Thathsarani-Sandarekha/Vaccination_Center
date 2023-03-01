package Task_2;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class VaccinationCenter {

    static int noOfVaccinations = 150;
    static Booth[] booths;

    public static void main(String[] args) throws FileNotFoundException {
        initialise(); //better to initialise in a procedure
        ConsoleMenu();
    }

    public static void initialise() {

        booths = new Booth[6];

        booths[0] = new Booth(0, "Agent 0", "e");
        booths[1] = new Booth(1, "Agent 1", "e");
        booths[2] = new Booth(2, "Agent 2", "e");
        booths[3] = new Booth(3, "Agent 3", "e");
        booths[4] = new Booth(4, "Agent 4", "e");
        booths[5] = new Booth(5, "Agent 5", "e");
        System.out.println("initialise ");
    }

    public static void ConsoleMenu() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        int option;

        do {
            System.out.println("""
                    1 --> View all Vaccination Booths
                    2 --> View all Empty Booths
                    3 --> Add Patient to a Booth
                    4 --> Remove Patient from a Booth
                    5 --> View Patients Sorted in alphabetical order
                    6 --> Load Program Data from file
                    7 --> View Remaining Vaccinations
                    8 --> Add Vaccinations to the Stock
                    9 --> Exit the Program
                    """);

            System.out.println("Choose an Option:  ");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> VVB();
                case 2 -> VEB();
                case 3 -> APB();
                case 4 -> RPB();
                case 5 -> VPS();
                case 6 -> LPD();
                case 7 -> VRV();
                case 8 -> AVS();
                case 9 -> EXT();
                default -> System.out.println("Invalid input.");
            }
        } while (option != 0);
    }

    public static void VVB() {
        for (int i = 0; i < booths.length; i++) {
            if (booths[i].boothStates.equals("e")) {
                System.out.println("Booth " + i + " is Empty");
            } else {
                System.out.println("Booth " + i + " is occupied by " + booths[i].boothStates);
            }
        }
    }
    public static void VEB() {
        for (int i = 0; i < booths.length; i++) {
            if (booths[i].boothStates.equals("e")) {
                System.out.println("Booth " + i);
            }
        }
    }
    public static void APB() {

        if (booths[0].boothStates.equals("e")) {
            booths[0].enqueue();
            SPD(0);
        } else if (booths[1].boothStates.equals("e")) {
            booths[1].enqueue();
            SPD(1);
        } else if (booths[2].boothStates.equals("e")) {
            booths[2].enqueue();
            SPD(2);
        } else if (booths[3].boothStates.equals("e")) {
            booths[3].enqueue();
            SPD(3);
        } else if (booths[4].boothStates.equals("e")) {
            booths[4].enqueue();
            SPD(4);
        } else if (booths[5].boothStates.equals("e")) {
            booths[5].enqueue();
            SPD(5);
        }

        noOfVaccinations = noOfVaccinations - 1;
        System.out.println(noOfVaccinations);
    }
    public static void RPB() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("------ Occupied Booths ------");
        for (int i = 0; i < booths.length; i++) {
            if (booths[i].boothStates.equals("e")) {
                continue;
            } else {
                System.out.println("Booth " + i + " is occupied by " + booths[i].boothStates);
            }
        }

        System.out.println("Enter the Booth number to remove the patient");
        int boothNo = scanner.nextInt();

        for (Booth booth : booths) {
            if (booth.boothStates.equals("e")) {
                if (boothNo == booth.boothNum) {
                    System.out.println("This Booth is Empty.");
                }
            } else {
                if (boothNo == booth.boothNum) {
                    booth.deque();
                }
            }
        }
    }
    public static void VPS() {
        System.out.println("----- Patients Sorted in alphabetical order ------");
        for (int i = 0; i < booths.length; i++)   //Holds each element
        {
            for (int j = i + 1; j < booths.length; j++)  //Check for remaining elements
            {
                //compares each element of the array to all the remaining elements
                if (booths[i].boothStates.compareTo(booths[j].boothStates) > 0) {
                    //swapping array elements
                    String temp = booths[i].boothStates;
                    booths[i].boothStates = booths[j].boothStates;
                    booths[j].boothStates = temp;
                }
            }
            if (booths[i].boothStates.equals("e")) {
                continue;
            } else {
                System.out.println(booths[i].boothStates);
            }
        }

    }
    public static void SPD(int boothNo) {
        for (Booth booth : booths) {
            if (booth.boothStates.equals("e")) {
                continue;
            } else {
                booth.storeData(boothNo);
            }
        }
    }

    public static void LPD() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("What is the Booth that you want to see?");
        System.out.println("""
                    0 -> Booth 0
                    1 -> Booth 1
                    2 -> Booth 2
                    3 -> Booth 3
                    4 -> Booth 4
                    5 -> Booth 5
                    """);
        int booth = input.nextInt();
        if (booth == 0 || booth == 1 || booth == 2 || booth == 3 || booth == 4 || booth == 5) {
            Booth.loadData(booth);
        } else {
            System.out.println("Invalid Output.");
        }
    }
    public static void VRV() {
        System.out.println("Remaining Vaccinations ---> " + noOfVaccinations);

        if (noOfVaccinations < 20)
            System.out.println("Please restock. Less than 20 injections left.");
    }
    public static void AVS() {
        Scanner input = new Scanner(System.in);
        System.out.println("How many vaccines do you want to add?");
        int newVaccine = input.nextInt();

        noOfVaccinations = noOfVaccinations + newVaccine;

        System.out.println("New Vaccination Stock --> " + noOfVaccinations);
    }
    public static void EXT() {
        System.out.println("Thank you. Keep your heart and mind strong.");
        System.exit(0);
    }
}



