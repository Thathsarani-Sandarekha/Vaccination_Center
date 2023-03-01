package Task_2;

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
    int boothNum;
    String boothAgent;
    String boothStates;
    String pFirstName;
    String vaccine = "Pfizer";
    Scanner input = new Scanner(System.in);

    public Booth(int boothNum, String boothAgent, String boothStates) {
        this.boothNum = boothNum;
        this.boothAgent = boothAgent;
        this.boothStates = boothStates;
    }

    public void enqueue() {
        if (boothStates.equals("e")) {
            System.out.println("Enter customer first name for Booth " + boothNum + " :");
            pFirstName = input.next();
            boothStates = pFirstName;
        }
    }

    public void deque() {
        boothStates = "e";
        System.out.println("The patient successfully removed from the " + boothNum + " Booth");
    }

    public void storeData(int boothNo) {
        File file = new File("/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task2.xlsx");
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

                Cell0.setCellValue("Booth");
                Cell1.setCellValue("Agent");
                Cell2.setCellValue("First Name");
                Cell3.setCellValue("Vaccination");
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

            Cell0.setCellValue("Booth");
            Cell1.setCellValue("Agent");
            Cell2.setCellValue("First Name");
            Cell3.setCellValue("Vaccination");
        }


        ArrayList<Object[]> patientDetails = new ArrayList<>();

        patientDetails.add(new Object[]{boothNum, boothAgent, boothStates, vaccine});

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
            FileOutputStream output = new FileOutputStream("COVID_19_Task2.xlsx");
            workbook.write(output);
            output.close();
            System.out.println("Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadData(int boothNo) throws FileNotFoundException {
        String excelFilePath = "/Users/thathsarani/Documents/COVID_19_CommandLine/COVID_19_Task2.xlsx";

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