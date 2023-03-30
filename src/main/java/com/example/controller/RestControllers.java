package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.SearchRequest;
import com.example.binding.SearchResponse;
import com.example.entity.CitizenPlane;
import com.example.service.ReportService;

@RestController
public class RestControllers {
	@Autowired
	private ReportService reportservice;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>>  getPlanName(){
		List<String> planName = reportservice.getPlanName();
		return new ResponseEntity<>(planName,HttpStatus.OK);
	}
	@GetMapping("/status")
	public ResponseEntity<List<String>>  getPlaneStatus(){
		List<String> status = reportservice.getPlanStatus();
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request){
		List<SearchResponse> citizenPlans = reportservice.getCitizenPlans(request);
		return new ResponseEntity<>(citizenPlans,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/excel")	
	public void getExportExcelReport(HttpServletResponse response)throws Exception{
		response.setContentType("application/actet-stream");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=CitizenPlanes.xls";
		
		response.setHeader(headerKey, headerValue);
		reportservice.exportExcel(response);
		
	}
	@GetMapping("/pdf")
	public void getExportPdf(HttpServletResponse response)throws Exception{
		
		response.setContentType("application/pdf");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=CitizenPlanes.pdf";
		
		response.setHeader(headerKey, headerValue);
		
		reportservice.exportPdf(response);
		
		
	}
}
