package com.skirpsi.api.posyandu.service;

import java.io.File;
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
import com.skirpsi.api.posyandu.repository.ImunisasiRepository;

@Service
public class ReportService {
	
	@Autowired CheckupRepository checkupRepo;
	@Autowired ImunisasiRepository imuRepo;

	public File createCheckupReportCheckup(String dateFrom, String dateTo) {
		String lokasiFile = "D:/random/excelTestCheckup.xlsx";
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
		headerCell = titleRow.createCell(8);
		headerCell.setCellValue("tanggaltimbang");
		headerCell = titleRow.createCell(9);
		headerCell.setCellValue("berat");
		headerCell = titleRow.createCell(10);
		headerCell.setCellValue("tinggi");
		
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
			dataRowCell.setCellValue(x.getBeratSaatLahirBalita());
			
			dataRowCell = dataRow.createCell(5);
			dataRowCell.setCellValue(x.getTinggiSaatLahirBalita());
			
			dataRowCell = dataRow.createCell(6);
			dataRowCell.setCellValue(x.getAlamatUser());
			
			dataRowCell = dataRow.createCell(7);
			dataRowCell.setCellValue(x.getNamaUser());
			
			dataRowCell = dataRow.createCell(8);
			dataRowCell.setCellValue(x.getTanggalCheckup().toString().substring(0,10));

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
			FileOutputStream out = new FileOutputStream(lokasiFile);
            workbook.write(out);
            workbook.close();
            
            File fileExcel = new File(lokasiFile);
            
            return fileExcel;
            		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public File createCheckupReportImunisasi(String dateFrom, String dateTo) {
		String lokasiFile = "D:/random/excelTestImunisasi.xlsx";
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Test new Sheet");
		
		List<ReportInterface> data = imuRepo.getDataForReporting(dateFrom, dateTo);
		
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
		headerCell = titleRow.createCell(8);
		headerCell.setCellValue("tanggalImunisasi");
		headerCell = titleRow.createCell(9);
		headerCell.setCellValue("namaImunisasi");
		
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
			dataRowCell.setCellValue(x.getBeratSaatLahirBalita());
			
			dataRowCell = dataRow.createCell(5);
			dataRowCell.setCellValue(x.getTinggiSaatLahirBalita());
			
			dataRowCell = dataRow.createCell(6);
			dataRowCell.setCellValue(x.getAlamatUser());
			
			dataRowCell = dataRow.createCell(7);
			dataRowCell.setCellValue(x.getNamaUser());
			
			dataRowCell = dataRow.createCell(8);
			dataRowCell.setCellValue(x.getTanggalImunisasi().toString().substring(0,10));
			
			dataRowCell = dataRow.createCell(9);
			dataRowCell.setCellValue(x.getNamaImunisasi());
			
			count++;
		}
		
		for (int a=0;a<11;a++) {
			sheet.autoSizeColumn(a);
		}
		
		try {
			FileOutputStream out = new FileOutputStream(lokasiFile);
            workbook.write(out);
            workbook.close();
            
            File fileExcel = new File(lokasiFile);
            
            return fileExcel;
            		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
