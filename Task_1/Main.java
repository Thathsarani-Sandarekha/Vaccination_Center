package Task_1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    private static String pFirstName;
    private static String pSurName;
    private static String vaccine = "Pfizer";
    private static int booth;
    private static String agentName;
    static int boothNum = 0;
    static int noOfVaccine = 150;

    public static String getpFirstName() {
        return pFirstName;
    }

    public static String getpSurName() {
        return pSurName;
    }

    public void setpSurName(String pSurName) {
        this.pSurName = pSurName;
    }

    public static String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public static int getBooth() {
        return booth;
    }

    public void setBooth(int booth) {
        this.booth = booth;
    }

    public static String getAgentName() {
        return agentName;
    }

    public static void setAgentName(String agentName) {
        Main.agentName = agentName;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] ServiceCenter = new String[6];

        initialise(ServiceCenter); //better to initialise in a procedure
        ConsoleMenu(ServiceCenter);
    }

    private static void initialise(String[] hotelRef) {
        Arrays.fill(hotelRef, "e");
        System.out.println("initialise ");
    }

    public static void ConsoleMenu(String[] array) throws FileNotFoundException {
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
                case 1 -> VVB(array);
                case 2 -> VEB(array);
                case 3 -> APB(array);
                case 4 -> RPB(array);
                case 5 -> VPS();
                case 6 -> LPD();
                case 7 -> VRV();
                case 8 -> AVS();
                case 9 -> {
                    EXT();
                }
                default -> System.out.println("Invalid input.");
            }
        } while (option != 0);
    }

    public static void VVB(String[] VaccinationBooths) {

        System.out.println("----- All Vaccination Booths -----");
        for (int x = 0; x < VaccinationBooths.length; x++) {
            if (VaccinationBooths[x].equals("e")) {
                System.out.println("Booth " + x + " is empty");
            } else {
                System.out.println("Booth " + x + " occupied by " + VaccinationBooths[x]);
            }
        }
        System.out.print("\n");
    }

    public static void VEB(String[] EmptyBooths) {
        System.out.println("----- Empty Vaccination Booths -----");

        for (int x = 0; x < EmptyBooths.length; x++) {
            if (EmptyBooths[x].equals("e"))
                System.out.println("Booth " + x);
        }
        System.out.print("\n");
    }

    public static void APB(String[] PatientBooth) {
        Scanner input = new Scanner(System.in);

        while (boothNum < 7) {
            System.out.println("------ Empty Booths ------");
            for (int x = 0; x < PatientBooth.length; x++) {
                if (PatientBooth[x].equals("e"))
                    System.out.println("Booth " + x);
            }

            System.out.println("Enter Booth number (0-5) or 6 to Task_1.Main:");
            boothNum = input.nextInt();

            if (boothNum == 6) {
                try {
                    ConsoleMenu(PatientBooth);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (boothNum == 0) {
                    agentName = "Agent 0";
                } else if (boothNum == 1) {
                    agentName = "Agent 1";
                } else if (boothNum == 2) {
                    agentName = "Agent 2";
                } else if (boothNum == 3) {
                    agentName = "Agent 3";
                } else if (boothNum == 4) {
                    agentName = "Agent 4";
                } else if (boothNum == 5) {
                    agentName = "Agent 5";
                }

                System.out.println("Enter customer first name for Booth " + boothNum + " :");
                pFirstName = input.next();

                System.out.println("Enter customer Surname for Booth " + boothNum + " :");
                pSurName = input.next();

                SPD(boothNum);

                booth = boothNum;
                noOfVaccine = noOfVaccine - 1;
                PatientBooth[boothNum] = pFirstName;
            }

            OccBooths(PatientBooth);
        }
    }

    public static void RPB(String[] PatientBooth) {
        Scanner scanner = new Scanner(System.in);
        OccBooths(PatientBooth);

        System.out.println("Enter the Booth number to remove the patient");
        int boothNo = scanner.nextInt();

        for (int x = 0; x < PatientBooth.length; x++) {
            if (PatientBooth[x].equals("e")) {
                if (boothNo == x) {
                    System.out.println("This Booth is Empty.");
                }
            } else {
                if (boothNo == x) {
                    PatientBooth[x] = "e";
                    System.out.println("The patient successfully removed from the " + x + " Booth");
                }
            }
        }
    }

    public static void VPS() throws FileNotFoundException {
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
            sortData(booth);
        } else {
            System.out.println("Invalid Output.");
        }
    }

    public static void SPD(int boothNo) {
        File file = new File("/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task1.xlsx");
        XSSFWorkbook workbook;
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
        } else {
            try (
                    InputStream input = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        XSSFSheet sheet = null;
        if (workbook.getNumberOfSheets() != 0) {
            System.out.println("There are sheets in this excel file");

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetName(i).equals("Patients Information Booth " + boothNo)) {
                    System.out.println("Found the Patients Information Booth " + boothNo + "sheet");
                    sheet = workbook.getSheet("Patients Information Booth " + boothNo);
                    break;
                } else if (!workbook.getSheetName(i).equals("Patients Information Booth " + boothNo)) {
                    continue;
                }

                System.out.println("Else part 1------ ====");
                System.out.println("Not found. So created");
                sheet = workbook.createSheet("Patients Information Booth " + boothNo);
                System.out.println("Else part 1------");

                Row mainTitle = sheet.createRow(0);
                System.out.println("created the first row ");
                Cell Cell0 = mainTitle.createCell(0);
                Cell Cell1 = mainTitle.createCell(1);
                Cell Cell2 = mainTitle.createCell(2);
                Cell Cell3 = mainTitle.createCell(3);
                Cell Cell4 = mainTitle.createCell(4);

                Cell0.setCellValue("Booth");
                Cell1.setCellValue("Agent");
                Cell2.setCellValue("First Name");
                Cell3.setCellValue("Surname");
                Cell4.setCellValue("Vaccination");
            }

        } else {
            System.out.println("Else part 2------ ====");
            System.out.println("Not found. So created");
            sheet = workbook.createSheet("Patients Information Booth " + boothNo);
            System.out.println("Else part 2------");

            Row mainTitle = sheet.createRow(0);
            System.out.println("created the first row ");
            Cell Cell0 = mainTitle.createCell(0);
            Cell Cell1 = mainTitle.createCell(1);
            Cell Cell2 = mainTitle.createCell(2);
            Cell Cell3 = mainTitle.createCell(3);
            Cell Cell4 = mainTitle.createCell(4);

            Cell0.setCellValue("Booth");
            Cell1.setCellValue("Agent");
            Cell2.setCellValue("First Name");
            Cell3.setCellValue("Surname");
            Cell4.setCellValue("Vaccination");
        }

            ArrayList<Object[]> patientDetails = new ArrayList<>();

        patientDetails.add(new Object[]{boothNum, getAgentName(), getpFirstName(), getpSurName(), getVaccine()});

        assert sheet != null;
        int rowCount = sheet.getLastRowNum();
        for (Object[] patientInfo : patientDetails) {
            XSSFRow row = sheet.createRow(++rowCount);
            int cellCount = 0;
            for (Object value : patientInfo) {
                XSSFCell cell = row.createCell(cellCount++);
                if (value instanceof String)
                    cell.setCellValue(String.valueOf(value));
                if (value instanceof Integer)
                    cell.setCellValue((Integer) value);
                if (value instanceof Double)
                    cell.setCellValue((Double) value);
                if (value instanceof Boolean)
                    cell.setCellValue((Boolean) value);
            }
        }

        try {
            FileOutputStream output = new FileOutputStream("COVID_19_Task1.xlsx");
            workbook.write(output);
            output.close();
            System.out.println("Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            loadData(booth);
        } else {
            System.out.println("Invalid Output.");
        }
    }

    public static void VRV() {
        System.out.println("Remaining Vaccinations ---> " + noOfVaccine);

        if (noOfVaccine < 20)
            System.out.println("Please restock. Less than 20 injections left.");
    }
    public static void AVS() {
        Scanner input = new Scanner(System.in);
        System.out.println("How many vaccines do you want to add?");
        int newVaccine = input.nextInt();

        noOfVaccine = noOfVaccine + newVaccine;

        System.out.println("New Vaccination Stock --> " + noOfVaccine);
    }
    public static void EXT() {
        System.out.println("Thank you. Keep your heart and mind strong.");
        System.exit(0);
    }

    public static void OccBooths(String[] OccBooth) {
        System.out.println("------ Occupied Booths ------");
        for (int x = 0; x < OccBooth.length; x++) {
            if (OccBooth[x].equals("e")) {
                continue;
            } else {
                System.out.println("Booth " + x + " occupied by " + OccBooth[x]);
            }
        }
    }

    public static void sortData(int boothNo) throws FileNotFoundException {

        String excelFilePath = "/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task1.xlsx";

        FileInputStream inputFile = new FileInputStream(excelFilePath);

        XSSFWorkbook workbook1;
        try {
            workbook1 = new XSSFWorkbook(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet1 = workbook1.getSheet("Patients Information Booth " + boothNo);

        int rows = sheet1.getLastRowNum();
        String[] names = new String[rows + 1];

        for (int r = 1; r <= rows; r++) {
            XSSFRow row1 = sheet1.getRow(r);
            int c = 2;
            XSSFCell cell1 = row1.getCell(c);
            names[r] = String.valueOf(cell1);
            switch (cell1.getCellType()) {
                case STRING -> cell1.getStringCellValue();
                case NUMERIC -> cell1.getNumericCellValue();
                case BOOLEAN -> cell1.getBooleanCellValue();
            }
        }

        System.out.println("----- Patients Sorted in alphabetical order ------");
        System.out.println("----- Booth " + boothNo + " ------");
        for (int i = 1; i < names.length; i++) {  //Holds each element
            for (int j = i + 1; j < names.length; j++) { //Check for remaining elements
                //compares each element of the array to all the remaining elements
                if (names[i].compareTo(names[j]) > 0) {
                    //swapping array elements
                    String temp = names[i];
                    names[i] = names[j];
                    names[j] = temp;
                }
            }
            System.out.println(names[i]);
        }
    }

    public static void loadData(int boothNo) throws FileNotFoundException {
        String excelFilePath = "/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task1.xlsx";

        FileInputStream inputFile = new FileInputStream(excelFilePath);

        XSSFWorkbook workbook1;
        try {
            workbook1 = new XSSFWorkbook(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        XSSFSheet sheet1 = workbook1.getSheet("Patients Information Booth " + boothNo);

        int rows = sheet1.getLastRowNum();
        int columns = sheet1.getRow(1).getLastCellNum();

        System.out.println("Booth " + boothNo);
        System.out.println("------------------------------------------------------------------");

        System.out.print("Booth Number  |  Agent  |  First Name  |  Surname  |  Vaccination");
        System.out.println();
        System.out.println("------------------------------------------------------------------");

        for (int r = 1; r <= rows; r++) {
            XSSFRow row1 = sheet1.getRow(r);

            for (int c = 0; c < columns; c++) {
                XSSFCell cell1 = row1.getCell(c);
                System.out.print("  ");
                System.out.print(cell1);
                System.out.print("      |     ");
                switch (cell1.getCellType()) {
                    case STRING -> cell1.getStringCellValue();
                    case NUMERIC -> cell1.getNumericCellValue();
                    case BOOLEAN -> cell1.getBooleanCellValue();
                }
            }
            System.out.println("\n");
        }
    }
}