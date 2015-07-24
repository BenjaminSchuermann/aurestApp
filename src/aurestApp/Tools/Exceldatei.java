package aurestApp.Tools;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * *****************************************
 * Beispiel für den Einsatz
 * Exceldatei exceldatei = new Exceldatei("D:\\ProduktionsdokumentVorlage150317.xlsm");
 * exceldatei.setTabelle("Daten");
 * exceldatei.setCell(8, 2, "Neuer Text");
 * exceldatei.setCell(9, 2, "Neuer Text der zweite");
 * exceldatei.speichern();
 */
class Exceldatei {
    private final File myFile;
    private XSSFWorkbook myWorkBook;
    private XSSFSheet mySheet;
    //Falls mal lesen kommt
    private Iterator<Row> rowIterator;

    public Exceldatei(String exceldatei) {
        this.myFile = new File(exceldatei);
        try {
            this.myWorkBook = new XSSFWorkbook(new FileInputStream(this.myFile));
        } catch (Exception e) {
            Dialoge.exceptionDialog(e, "Fehler beim Zugriff auf die Datei");
            e.printStackTrace();
        }

    }

    public void setTabelle(int sheetid) {
        mySheet = myWorkBook.getSheetAt(sheetid);
        //Falls mal lesen kommt
        //rowIterator = mySheet.iterator();
    }

    public void setTabelle(String sheetname) {
        mySheet = myWorkBook.getSheet(sheetname);
        //Falls mal lesen kommt
        //rowIterator = mySheet.iterator();
    }

    public void setCell(int reihe, int zelle, String zellinhalt) {
        Row row = mySheet.getRow(reihe - 1);
        Cell cell = row.getCell(zelle - 1);
        if (cell == null) {
            cell = row.createCell(zelle - 1);
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(zellinhalt);
    }

    public void setCell(int reihe, int zelle, int zellinhalt) {
        Row row = mySheet.getRow(reihe - 1);
        Cell cell = row.getCell(zelle - 1);
        if (cell == null) {
            cell = row.createCell(zelle - 1);
        }
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(zellinhalt);
    }

    public void speichern() {
        try {
            myWorkBook.write(new FileOutputStream(myFile));
        } catch (IOException e) {
            Dialoge.exceptionDialog(e, "Fehler beim Speichern der Datei");
            e.printStackTrace();
        }
        //return "Exceltabelle sollte gespeichert sein.";
    }
}

//Falls doch mal noch lesen kommt
// Traversing over each row of XLSX file
    /*
    while (rowIterator.hasNext()) {
        Row row = rowIterator.next();

        // For each row, iterate through each columns
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {

            org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    System.out.print(cell.getStringCellValue() + "\t");
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    System.out.print(cell.getNumericCellValue() + "\t");
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    System.out.print(cell.getBooleanCellValue() + "\t");
                    break;
                default :

            }
        }
        System.out.println("");
    }
    */
