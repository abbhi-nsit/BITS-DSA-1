package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class consultQueue {
	
	List<patientNode> patientQueue = new ArrayList<>();
	patientRecord records;
	
	public consultQueue(patientRecord records){
		patientQueue.add(null);
		this.records = records;
		if(records.getPatientCount()!=0){
			addAll(records.getPatientsList());
		}
	}
	
	private void addAll(List<patientNode> patientsList) {
		if(patientsList!=null){
			for(patientNode patient: patientsList){
				patientQueue.add(patient);
				heapify(patientQueue.size()-1);
			}
		}
	}
	
	private void heapify(int index) {
		if(index==1)
			return;
		if(patientQueue.get(index/2).getAge()<patientQueue.get(index).getAge()){
			Collections.swap(patientQueue, index/2, index);
			heapify(index/2);
		}
	}
	
	public void enqueuePatient(int patientId){
		if(patientQueue.size()==0){
			patientQueue.add(null);
		}
		patientNode patient = records.getPatientNode(patientId);
		if(patient!=null){
			patientQueue.add(patient);
			heapify(patientQueue.size()-1);
		}
	}
	
	public void nextPatient(){
		if(patientQueue.size()>0){
			patientNode patient= this.getMaxAgePatient();
			System.out.println("patient id:"+patient.getId()+" name:"+patient.getName()+" age:"+patient.getAge()+"\n");
			this.dequeuePatient(patient.getId());
		}
		else{
			System.out.println("No more patients to consult");
		}
	}
	
	public void dequeuePatient(int patientId){
		Collections.swap(patientQueue, 1, patientQueue.size()-1);
		patientQueue.remove(patientQueue.size()-1);
		int index=1;
		boolean terminateSwap=false;
		while(2*index<patientQueue.size()
				&& !terminateSwap){
			terminateSwap=true;
			if(2*index+1<patientQueue.size()
					&& patientQueue.get(2*index+1).getAge()>patientQueue.get(2*index).getAge()
					&& patientQueue.get(2*index+1).getAge()>patientQueue.get(index).getAge()){
				Collections.swap(patientQueue, index, 2*index+1);
				terminateSwap=false;
				index=2*index+1;
			}
			else if(patientQueue.get(2*index).getAge()>patientQueue.get(index).getAge()){
				Collections.swap(patientQueue, index, 2*index);
				terminateSwap=false;
				index=2*index;
			}
		}
	}
	
	public void displayQueue() throws IOException{
		FileWriter writer = new FileWriter(new File("output.txt"));
		BufferedWriter  bw = new BufferedWriter(writer);
		int sequenceNumber=1;
		List<patientNode> restorePatientsInHeap = new ArrayList<>();  
		bw.write("\t--------------");
		bw.newLine();
		while(patientQueue.size()>1){
			patientNode patient = this.getMaxAgePatient();
			this.dequeuePatient(patient.getId());
			bw.write(sequenceNumber+","+patient.getId()+","+patient.getName()+','+patient.getAge());
			bw.newLine();
			System.out.println(sequenceNumber+","+patient.getId()+","+patient.getName()+','+patient.getAge());
			sequenceNumber++;
			restorePatientsInHeap.add(patient);
		}
		bw.write("\t--------------");
		bw.close();
		writer.close();
		System.out.println();
		for(patientNode patient : restorePatientsInHeap){
			patientQueue.add(patient);
		}
	}
	
	public patientNode getMaxAgePatient(){
		return patientQueue.get(1);
	}
}
