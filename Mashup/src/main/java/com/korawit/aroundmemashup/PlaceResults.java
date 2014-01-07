
package com.korawit.aroundmemashup;

import java.util.List;

public class PlaceResults{
   	private List debug_info;
   	private List html_attributions;
   	private List<Results> results;
   	private String status;

 	public List getDebug_info(){
		return this.debug_info;
	}
	public void setDebug_info(List debug_info){
		this.debug_info = debug_info;
	}
 	public List getHtml_attributions(){
		return this.html_attributions;
	}
	public void setHtml_attributions(List html_attributions){
		this.html_attributions = html_attributions;
	}
 	public List<Results> getResults(){
		return this.results;
	}
	public void setResults(List<Results> results){
		this.results = results;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}

}