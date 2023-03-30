package com.example.service;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.binding.SearchRequest;
import com.example.binding.SearchResponse;
import com.example.entity.CitizenPlane;
import com.example.repository.CitizenRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {
	@Autowired
	private CitizenRepository repository;

	@Override
	public List<String> getPlanName() {
		return repository.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return repository.getPlanStatus();
	}

	@Override
	public List<SearchResponse> getCitizenPlans(SearchRequest request) {
		
		CitizenPlane entity = new CitizenPlane();
		List<SearchResponse> req=new ArrayList<>();

		if (request.getPlanName() != null && !request.getPlanName().equals("")) {
			entity.setPlanName(request.getPlanName());
		}

		if (request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		
		if(request.getStartDate()!=null && !request.getStartDate().equals("")) {
			entity.setStartDate(request.getStartDate());
		}
		if(request.getEndDate()!=null && !request.getEndDate().equals("")) {
			entity.setEndDate(request.getEndDate());
		}

		
		
		//return repository.findAll(Example.of(entity));
		 Example<CitizenPlane> example = Example.of(entity);
		 List<CitizenPlane> records = repository.findAll(example);
		 
		 for (CitizenPlane entities : records) {
				SearchResponse sr = new SearchResponse();
				BeanUtils.copyProperties(entities, sr);
				req.add(sr);
			}
		return req;
		 /*
		 *

		List<CitizenPlane> records = repository.findAll(example);
		
		for (CitizenPlane entities : records) {
			SearchRequest sr = new SearchRequest();
			BeanUtils.copyProperties(entities, sr);
			req.add(sr);
		}
		return records;

		*/
		//return repository.findAll(Example.of(entity));
		//return repository.findAllByConditions(entity, request.getStartDate(), request.getEndDate());
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws IOException {
		List<CitizenPlane> citizenPlane = repository.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("CitizenPlan info");

		XSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("cid");
		row.createCell(1).setCellValue("planeName");
		row.createCell(2).setCellValue("planeStatus");
		row.createCell(3).setCellValue("cname");
		row.createCell(4).setCellValue("cemail");
		row.createCell(5).setCellValue("gender");
		row.createCell(6).setCellValue("phno");
		row.createCell(7).setCellValue("ssn");

		int dataRowIndex = 1;

		for (CitizenPlane planes : citizenPlane) {
			XSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(planes.getCid());
			dataRow.createCell(1).setCellValue(planes.getCname());
			dataRow.createCell(2).setCellValue(planes.getCemail());
			dataRow.createCell(3).setCellValue(planes.getGender());
			dataRow.createCell(4).setCellValue(planes.getPhno());
			dataRow.createCell(5).setCellValue(planes.getSsn());
			dataRow.createCell(6).setCellValue(planes.getPlanName());
			dataRow.createCell(7).setCellValue(planes.getPlanStatus());

			dataRowIndex++;
		}

		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}

	@Override
	public void exportPdf(HttpServletResponse response) throws Exception {

		// createing the object of document
		Document document = new Document(PageSize.A4);

		// getting instance of pdfwriter
		PdfWriter.getInstance(document, response.getOutputStream());

		// open the created document to modify it
		document.open();

		// creating font
		// setting the font style and size
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		fontTitle.setColor(Color.BLUE);

		// create paragraph
		Paragraph paragraph = new Paragraph("Citizen Plan Details", fontTitle);
		// allignement of paragraph
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// adding the created paragraph in doccumnet
		document.add(paragraph);

		// creating the table of 8 columns
		PdfPTable table = new PdfPTable(8);

		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.5f, 1.5f, 3.5f, 3.5f, 2.5f, 2.5f });
		table.setSpacingBefore(10);

		// write the table header
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.RED);

		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Phno", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status", font));
		table.addCell(cell);

		// write the table data
		List<CitizenPlane> records = repository.findAll();
		for (CitizenPlane record : records) {
			table.addCell(String.valueOf(record.getCid()));
			table.addCell(record.getCname());
			table.addCell(record.getCemail());
			table.addCell(String.valueOf(record.getSsn()));
			table.addCell(record.getGender());
			table.addCell(String.valueOf(record.getPhno()));
			table.addCell(record.getPlanName());
			table.addCell(record.getPlanStatus());
		}

		document.add(table);

		document.close();
	}
}
