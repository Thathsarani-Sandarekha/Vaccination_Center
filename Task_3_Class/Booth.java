package Task_3_Class;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Booth {
    Patient patient = new Patient();
    int boothNum;
    String boothAgent;
    String boothStates;
    String vaccine;
    Scanner input = new Scanner(System.in);

    public Booth(int boothNum, String boothAgent, String vaccine, String boothStates) {
        this.boothNum = boothNum;
        this.boothAgent = boothAgent;
        this.vaccine = vaccine;
        this.boothStates = boothStates;
    }

    public void enqueue() {
        System.out.println("Enter customer first name for Booth " + boothNum + " :");
        patient.firstName = input.next();
        boothStates = patient.firstName;

        System.out.println("Enter your Surname:");
        patient.surName = input.next();

        System.out.println("Enter your Age:");
        patient.age = input.nextInt();

        System.out.println("Enter your City:");
        patient.city = input.next();

        System.out.println("Enter your NIC or Passport Number:");
        patient.NIC = input.next();
    }

    public void deque() {
        boothStates = "e";
        System.out.println("The patient successfully removed from the " + boothNum + " Booth");
    }

    public void storeData(int boothNo) {
        File file = new File("/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task3_Class.xlsx");
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
            //System.out.println("There are sheets in this excel file");

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                if (workbook.getSheetName(i).equals("Patients Information Booth " + boothNo)) {
                    //System.out.println("Found the Patients Information Booth " + boothNo + "sheet");
                    sheet = workbook.getSheet("Patients Information Booth " + boothNo);
                    break;
                } else if (!workbook.getSheetName(i).equals("Patients Information Booth " + boothNo)) {
                    continue;
                }

                //System.out.println("Else part 1------ ====");
                //System.out.println("Not found. So created");
                sheet = workbook.createSheet("Patients Information Booth " + boothNo);
                //System.out.println("Else part 1------");

                Row mainTitle = sheet.createRow(0);
                //System.out.println("created the first row ");
                Cell Cell0 = mainTitle.createCell(0);
                Cell Cell1 = mainTitle.createCell(1);
                Cell Cell2 = mainTitle.createCell(2);
                Cell Cell3 = mainTitle.createCell(3);
                Cell Cell4 = mainTitle.createCell(4);
                Cell Cell5 = mainTitle.createCell(5);
                Cell Cell6 = mainTitle.createCell(6);
                Cell Cell7 = mainTitle.createCell(7);


                Cell0.setCellValue("Booth");
                Cell1.setCellValue("Agent");
                Cell2.setCellValue("First Name");
                Cell3.setCellValue("Surname");
                Cell4.setCellValue("Age");
                Cell5.setCellValue("City");
                Cell6.setCellValue("NIC or Passport Number");
                Cell7.setCellValue("Vaccine");
            }

        } else {
            //System.out.println("Else part 2------ ====");
            //System.out.println("Not found. So created");
            sheet = workbook.createSheet("Patients Information Booth " + boothNo);
            //System.out.println("Else part 2------");

            Row mainTitle = sheet.createRow(0);
            //System.out.println("created the first row ");
            Cell Cell0 = mainTitle.createCell(0);
            Cell Cell1 = mainTitle.createCell(1);
            Cell Cell2 = mainTitle.createCell(2);
            Cell Cell3 = mainTitle.createCell(3);
            Cell Cell4 = mainTitle.createCell(4);
            Cell Cell5 = mainTitle.createCell(5);
            Cell Cell6 = mainTitle.createCell(6);
            Cell Cell7 = mainTitle.createCell(7);


            Cell0.setCellValue("Booth");
            Cell1.setCellValue("Agent");
            Cell2.setCellValue("First Name");
            Cell3.setCellValue("Surname");
            Cell4.setCellValue("Age");
            Cell5.setCellValue("City");
            Cell6.setCellValue("NIC or Passport Number");
            Cell7.setCellValue("Vaccine");
        }


        ArrayList<Object[]> patientDetails = new ArrayList<>();

        patientDetails.add(new Object[]{boothNum, boothAgent, patient.firstName, patient.surName, patient.age,
                patient.city, patient.NIC, vaccine});

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
            FileOutputStream output = new FileOutputStream("COVID_19_Task3_Class.xlsx");
            workbook.write(output);
            output.close();
            System.out.println("Successful");
            System.out.println(patient.firstName + " successfully added to the booth " + boothNum + " to get the " + vaccine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(int boothNo) throws FileNotFoundException {
        String excelFilePath = "/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task3_Class.xlsx";

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
        System.out.println("------------------------------------------------------------------------------------------------------------");

        System.out.print("Booth Number  |  Agent  |  First Name  |  Surname  |  Age  |  City  |  NIC or Passport Number  |  Vaccine");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------------------");

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

    public void sortData(int boothNo) throws FileNotFoundException {

        String excelFilePath = "/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task3_Class.xlsx";

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
        for (int i = 1; i < names.length; i++) {
            for (int j = i + 1; j < names.length; j++) {
                if (names[i].compareTo(names[j]) > 0) {
                    String temp = names[i];
                    names[i] = names[j];
                    names[j] = temp;
                }
            }
            System.out.println(names[i]);
        }
    }

}

