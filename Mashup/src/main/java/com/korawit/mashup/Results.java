
package com.korawit.mashup;

public class Results{
   	private Geometry geometry;
   	private String id;
   	private String reference;

 	public Geometry getGeometry(){
		return this.geometry;
	}
	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getReference(){
		return this.reference;
	}
	public void setReference(String reference){
		this.reference = reference;
	}
}