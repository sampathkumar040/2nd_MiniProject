package com.example.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.binding.SearchRequest;
import com.example.binding.SearchResponse;
import com.example.entity.CitizenPlane;

public interface ReportService {
	
	public List<String> getPlanName();
	
	public List<String> getPlanStatus();
	
	public List<SearchResponse> getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;
	
	public void exportPdf(HttpServletResponse response) throws Exception;

}
