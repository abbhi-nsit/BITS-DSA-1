package com;

import java.util.LinkedList;
import java.util.List;

public class patientRecord {

	private List<patientNode> recordList = new LinkedList<>();
	private int patientCount=0;
	
	public int registerPatient(String name, int age){
		patientNode patient = new patientNode(name,age);
		int patientId=assignIdToPatient(patient);
		recordList.add(patient);
		return patientId;
	}

	private int assignIdToPatient(patientNode patient) {
		patient.setId(patientCount);
		return patientCount++;
	}
	
	public patientNode getPatientNode(int id){
		for(patientNode patient : recordList){
			if(patient.getId()==id){
				return patient;
			}
		}
		return null;
	}

	public int getPatientCount() {
		return patientCount;
	}
	
	public List<patientNode> getPatientsList(){
		return this.recordList;
	}
}
