package com.skirpsi.api.posyandu.service;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.intfc.ReportInterface;
import com.skirpsi.api.posyandu.repository.CheckupRepository;

@Service
public class ReportService {
	
	@Autowired CheckupRepository checkupRepo;

	public void createCheckupReport(String dateFrom, String dateTo) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Test new Sheet");
		
		List<ReportInterface> data = checkupRepo.getDataForReporting(dateFrom, dateTo);
		
		Row titleRow = sheet.createRow(0);
		
		Cell headerCell = titleRow.createCell(0);
		headerCell.setCellValue("posyandu");
		headerCell = titleRow.createCell(1);
		headerCell.setCellValue("idbalita");
		headerCell = titleRow.createCell(2);
		headerCell.setCellValue("namabalita");
		headerCell = titleRow.createCell(3);
		headerCell.setCellValue("nikbalita");
		headerCell = titleRow.createCell(4);
		headerCell.setCellValue("bblahir");
		headerCell = titleRow.createCell(5);
		headerCell.setCellValue("tinggilahir");
		headerCell = titleRow.createCell(6);
		headerCell.setCellValue("alamatdomisili");
		headerCell = titleRow.createCell(7);
		headerCell.setCellValue("namaorangtua");
//		headerCell = titleRow.createCell(8);
//		headerCell.setCellValue("status");
		headerCell = titleRow.createCell(8);
		headerCell.setCellValue("tanggaltimbang");
		headerCell = titleRow.createCell(9);
		headerCell.setCellValue("berat");
		headerCell = titleRow.createCell(10);
		headerCell.setCellValue("tinggi");
//		headerCell = titleRow.createCell(12);
//		headerCell.setCellValue("caraukur");
//		headerCell = titleRow.createCell(13);
//		headerCell.setCellValue("vitamin");
//		headerCell = titleRow.createCell(14);
//		headerCell.setCellValue("taburia");
//		headerCell = titleRow.createCell(15);
//		headerCell.setCellValue("lila");
//		headerCell = titleRow.createCell(16);
		
		Integer count=1;
		
		for(ReportInterface x : data) {
			Row dataRow = sheet.createRow(count);
			Cell dataRowCell = dataRow.createCell(0);
			dataRowCell.setCellValue("POSYANDU X");
			
			dataRowCell = dataRow.createCell(1);
			dataRowCell.setCellValue(x.getIdBalita());
			
			dataRowCell = dataRow.createCell(3);
			dataRowCell.setCellValue(x.getNikbalita());
			
			dataRowCell = dataRow.createCell(2);
			dataRowCell.setCellValue(x.getNamaBalita());
			
			dataRowCell = dataRow.createCell(4);
			dataRowCell.setCellValue(x.getBeratSaatLahir());
			
			dataRowCell = dataRow.createCell(5);
			dataRowCell.setCellValue(x.getTinggiSaatLahir());
			
			dataRowCell = dataRow.createCell(6);
			dataRowCell.setCellValue(x.getAlamatUser());
			
			dataRowCell = dataRow.createCell(7);
			dataRowCell.setCellValue(x.getNamaUser());
			
			dataRowCell = dataRow.createCell(8);
			dataRowCell.setCellValue(x.getTanggalCheckup().toString().substring(0,10));
			System.out.println(x.getTanggalCheckup());
			dataRowCell = dataRow.createCell(9);
			dataRowCell.setCellValue(x.getBeratBadan());
			
			dataRowCell = dataRow.createCell(10);
			dataRowCell.setCellValue(x.getTinggiBadan());
			
			count++;
		}
		
		for (int a=0;a<12;a++) {
			sheet.autoSizeColumn(a);
		}
		
		try {
			FileOutputStream out = new FileOutputStream("D:/random/excelTest.xlsx");
            workbook.write(out);
            workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
