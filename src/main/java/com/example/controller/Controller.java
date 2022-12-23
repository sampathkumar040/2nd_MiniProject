package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.CitizenPlane;
import com.example.binding.SearchRequest;
import com.example.service.ReportService;
@RestController
public class Controller {
	@Autowired
	private ReportService reportservice;
	
	@GetMapping("/planeName")
	public List<String> getPlanName(){
		List<String> planName = reportservice.getPlanName();
		return planName;
	}
	@GetMapping("/status")
	public List<String> getPlaneStatus(){
		List<String> status = reportservice.getPlanStatus();
		return status;
		
	}
	@GetMapping("/getAllPlans")
	public List<CitizenPlane> getAllPlanesAndStatus(SearchRequest request){
		return reportservice.getCitizenPlans(request);
		
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
