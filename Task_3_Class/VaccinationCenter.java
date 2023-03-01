package Task_3_Class;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class VaccinationCenter {

    static int noOfVaccinations = 19;
    static int AstraZeneca = noOfVaccinations / 3;
    static int Sinopharm = noOfVaccinations / 3;
    static int Pfizer = noOfVaccinations / 3;
    static Booth[] booths;

    public static void main(String[] args) throws FileNotFoundException {
        initialise(); //better to initialise in a procedure
        ConsoleMenu();
    }

    public static void initialise() {

        booths = new Booth[6];

        booths[0] = new Booth(0,"Agent 0", "AstraZeneca", "e");
        booths[1] = new Booth(1, "Agent 1", "AstraZeneca", "e");
        booths[2] = new Booth(2, "Agent 2", "Sinopharm","e");
        booths[3] = new Booth(3, "Agent 3", "Sinopharm", "e");
        booths[4] = new Booth(4, "Agent 4", "Pfizer", "e");
        booths[5] = new Booth(5, "Agent 5", "Pfizer", "e");
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
        System.out.println("----- All Vaccination Booths -----");

        for (int i = 0; i < booths.length; i++) {
            if (booths[i].boothStates.equals("e")) {
                System.out.println("Booth " + i + " is Empty");
            } else {
                System.out.println("Booth " + i + " is occupied by " + booths[i].boothStates);
            }
        }
    }
    public static void VEB() {
        System.out.println("------ Empty Booths ------");

        for (int i = 0; i < booths.length; i++) {
            if (booths[i].boothStates.equals("e")) {
                System.out.println("Booth " + i);
            }
        }
    }

    public static void saveChanges(int i) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Please conform the patient information to save the changes");
        System.out.println("""
                                      1 -> Yes
                                      2 -> No
                                      """);
        int boothNo = input.nextInt();
        if (boothNo == 1) {
            SPD(i);
            System.out.println();
        } else {
            ConsoleMenu();
        }
    }

    public static void enterQueue(int i) throws FileNotFoundException {

        if (booths[i].boothStates.equals("e")) {
            booths[i].enqueue();
            saveChanges(i);
        } else if (booths[i + 1].boothStates.equals("e")) {
            booths[i + 1].enqueue();
            saveChanges(i + 1);
        } else {
            System.out.println("Wait in the Queue. All the Booths are Full.");
        }

    }

    public static void APB() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Vaccination that you required:");
        System.out.println("""
                    1 -> AstraZeneca
                    2 -> Sinopharm
                    3 -> Pfizer
                    """);

        System.out.println("Enter '0' to redirect to the Main Menu");
        int userInput = scanner.nextInt();

        if (userInput == 0) {
            try {
                ConsoleMenu();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (userInput == 1) {
                AstraZeneca = AstraZeneca - 1;
                enterQueue(0);

            } else if (userInput == 2) {
                Sinopharm = Sinopharm - 1;
                enterQueue(2);

            } else if (userInput == 3) {
                Pfizer = Pfizer - 1;
                enterQueue(4);

            } else {
                System.out.println("Invalid input");
                ConsoleMenu();
            }
            noOfVaccinations = noOfVaccinations - 1;
        }
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
    public static void VPS() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("What is the information that you want to see?");
        System.out.println("""
                    1 -> According to Booths
                    2 -> According to Vaccine
                    """);
        int sortNo = input.nextInt();

        if (sortNo == 1) {
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
                booths[booth].sortData(booth);
            } else {
                System.out.println("Invalid Output.");
            }

        } else if (sortNo == 2) {
            System.out.println("What is the vaccine that you want to see?");
            System.out.println("""
                    1 -> AstraZeneca
                    2 -> Sinopharm
                    3 -> Pfizer
                    """);
            int vaccineNo = input.nextInt();
            if (vaccineNo == 1) {
                booths[0].sortData(0);
                booths[1].sortData(1);
            } else if (vaccineNo == 2) {
                booths[2].sortData(2);
                booths[3].sortData(3);
            } else if (vaccineNo == 3) {
                booths[4].sortData(4);
                booths[5].sortData(5);
            } else {
                System.out.println("Invalid Output.");
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

        System.out.println("What is the information that you want to see?");
        System.out.println("""
                    1 -> According to Booths
                    2 -> According to Vaccine
                    """);
        int infoNo = input.nextInt();

        if (infoNo == 1) {
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
                booths[booth].loadData(booth);
            } else {
                System.out.println("Invalid Output.");
            }

        } else if (infoNo == 2) {
            System.out.println("What is the vaccine that you want to see?");
            System.out.println("""
                    1 -> AstraZeneca
                    2 -> Sinopharm
                    3 -> Pfizer
                    """);
            int vaccineNo = input.nextInt();
            if (vaccineNo == 1) {
                booths[0].loadData(0);
                booths[1].loadData(1);
            } else if (vaccineNo == 2) {
                booths[2].loadData(2);
                booths[3].loadData(3);
            } else if (vaccineNo == 3) {
                booths[4].loadData(4);
                booths[5].loadData(5);
            } else {
                System.out.println("Invalid Output.");
            }
        }
    }
    public static void VRV() {
        System.out.println("Remaining All type of Vaccinations ---> " + noOfVaccinations);
        System.out.println("Remaining AstraZeneca Vaccinations ---> " + AstraZeneca);
        System.out.println("Remaining Sinopharm Vaccinations ---> " + Sinopharm);
        System.out.println("Remaining Pfizer Vaccinations ---> " + Pfizer);

        if (noOfVaccinations < 20)
            System.out.println("Please restock. Less than 20 vaccinations left.");
        if (AstraZeneca < 20)
            System.out.println("Please restock. Less than 20 AstraZeneca vaccinations left.");
        if (Sinopharm < 20)
            System.out.println("Please restock. Less than 20 Sinopharm vaccinations left.");
        if (Pfizer < 20)
            System.out.println("Please restock. Less than 20 Pfizer vaccinations left.");
    }

    public static void AVS() {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Vaccination that you required:");
        System.out.println("""
                    1 -> AstraZeneca
                    2 -> Sinopharm
                    3 -> Pfizer
                    """);
        int vaccine = input.nextInt();

        System.out.println("How many vaccines do you want to add?");
        int newVaccine = input.nextInt();

        if (vaccine == 1) {
            AstraZeneca = AstraZeneca + newVaccine;
        } else if (vaccine == 2) {
            Sinopharm = Sinopharm + newVaccine;
        } else if (vaccine == 3) {
            Pfizer = Pfizer + newVaccine;
        } else {
            System.out.println("Invalid input.");
        }

        noOfVaccinations = noOfVaccinations + newVaccine;

        System.out.println("New Vaccination Stock --> ");
        System.out.println("All Stock   --> " + noOfVaccinations);
        System.out.println("AstraZeneca --> " + AstraZeneca);
        System.out.println("Sinopharm   --> " + Sinopharm);
        System.out.println("Pfizer      --> " + Pfizer);
    }

    public static void EXT() {
        System.out.println("Thank you. Keep your heart and mind strong.");
        System.exit(0);
    }
}
