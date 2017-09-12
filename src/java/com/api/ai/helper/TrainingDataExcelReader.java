/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author gowtham.juluri
 */
public class TrainingDataExcelReader {

    public static void main(String[] args) {

        TrainingDataExcelReader t= new TrainingDataExcelReader();
        String FILE_NAME = "C:\\artificial-intelligence-poc\\TrainingData.xlsx";
        t.getTrainingDataAsList(FILE_NAME);
    }

    public ArrayList<String> getTrainingDataAsList(String fileName) {
        
        ArrayList<String> al = new ArrayList<String>();
        try {
            FileInputStream excelFile = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> excelRowIterator = datatypeSheet.iterator();
//            TrainingDataVO tdo = null;
            
            while (excelRowIterator.hasNext()) {
//                tdo = new TrainingDataVO();
                Row currentRow = excelRowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                if(currentRow.getRowNum() == 0)
                {
                    continue;
                }
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) 
                    {
                        System.out.print(currentCell.getStringCellValue() + "--");
                        al.add(currentCell.getStringCellValue());
//                        tdo.setUserSay(currentCell.getStringCellValue());
                    }
//                    else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) 
//                    {
//                        String s = NumberToTextConverter.toText(currentCell.getNumericCellValue());
//                        System.out.print(s + "--");
//                        tdo.setIntentName(s);
//                    }
                    
//                    if (cellIterator.hasNext()) {
//                        currentCell = cellIterator.next();
//                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
//                            System.out.print(currentCell.getStringCellValue() + "--");
//                            tdo.setUserSay(currentCell.getStringCellValue());
//                        } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
//                            String s = NumberToTextConverter.toText(currentCell.getNumericCellValue());
//                            System.out.print(s + "--");
//                            tdo.setUserSay(s);
//                        }
//                    }
//                    //Entity Processing
//                    if (cellIterator.hasNext()) {
//                        currentCell = cellIterator.next();
//                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
//                            String entityMap = currentCell.getStringCellValue();
//                            ArrayList<EntityVO> eal = new ArrayList<EntityVO>();
//                            if (entityMap != null && entityMap.length() > 0) {
//                                StringTokenizer st = new StringTokenizer(entityMap, ",");
//                                while (st.hasMoreTokens()) {
//                                    String ePair = st.nextToken();
//                                    StringTokenizer eT = new StringTokenizer(ePair, "#");
//                                    EntityVO eVO = null;
//                                    while (eT.hasMoreTokens()) {
//                                        eVO = new EntityVO();
//                                        eVO.setEntityName(eT.nextToken());
//                                        eVO.setEntityValue(eT.nextToken());
//                                        eal.add(eVO);
//                                    }
//                                }
//                            }
//                            System.out.print(currentCell.getStringCellValue() + "--");
//                            tdo.setEntityList(eal);
//                        }
//                    }
                }
                System.out.println(al);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }
}
