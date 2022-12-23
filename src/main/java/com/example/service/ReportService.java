package com.example.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.binding.CitizenPlane;
import com.example.binding.SearchRequest;

public interface ReportService {
	
	public List<String> getPlanName();
	
	public List<String> getPlanStatus();
	
	public List<CitizenPlane> getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;
	
	public void exportPdf(HttpServletResponse response) throws Exception;

}
