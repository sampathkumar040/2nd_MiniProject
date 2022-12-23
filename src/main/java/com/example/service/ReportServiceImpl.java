package com.example.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.binding.CitizenPlane;
import com.example.binding.SearchRequest;
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
		return repository.getPlaneNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return repository.getPlaneStatus();
	}

	@Override
	public List<CitizenPlane> getCitizenPlans(SearchRequest request) {
		CitizenPlane entity = new CitizenPlane();

		if (request.getPlanName() != null && !request.getPlanName().equals("")) {
			entity.setPlaneName(request.getPlanName());
		}

		if (request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
			entity.setPlaneStatus(request.getPlanStatus());
		}

		if (request.getGender() != null && !request.getGender().equals("")) {
			entity.setGender(request.getGender());
		}

		Example<CitizenPlane> example = Example.of(entity);

		List<CitizenPlane> records = repository.findAll(example);

		return records;
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
			dataRow.createCell(6).setCellValue(planes.getPlaneName());
			dataRow.createCell(7).setCellValue(planes.getPlaneStatus());

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

		cell.setPhrase(new Phrase("Plane Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Plane Status", font));
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
			table.addCell(record.getPlaneName());
			table.addCell(record.getPlaneStatus());
		}

		document.add(table);

		document.close();
	}

}
