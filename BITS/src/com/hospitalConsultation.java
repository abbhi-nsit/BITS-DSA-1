package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class hospitalConsultation {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		File inputFile = new File("input.txt");
		FileReader reader;
		try {
			clearTheContentsOfOutputFile();
			reader = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(reader);
			String str=null;
			patientRecord records = new patientRecord();
			while((str=br.readLine())!=null){
				String[] arr=str.split(",");
				records.registerPatient(arr[0].trim(), Integer.valueOf(arr[1].trim()) );
			}
			consultQueue patientHeap = new consultQueue(records);
			patientHeap.displayQueue();
			boolean terminate = false;
			while(!terminate){
			
				System.out.println("Press 1 to display patient queue.\nPress 2 to register new patient.\nPress 3 to get next patient in line.\nPress any other key to terminate the program.");
				String ch = sc.next();
				switch(ch){
				case "1": patientHeap.displayQueue();
						  break;
				case "2": System.out.println("Enter name of the patient:");
						  String name = sc.next();
						  System.out.println("Enter age of the patient");
						  int age = sc.nextInt();
						  int patientId = records.registerPatient(name, age);
						  patientHeap.enqueuePatient(patientId);
						  break;
				case "3": patientHeap.nextPatient();
						  break;
				default: terminate = true;
							  break;
				}
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found. Please put input.txt in the project folder file.");;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void clearTheContentsOfOutputFile() throws FileNotFoundException {
		File outputFile = new File("output.txt");
		PrintWriter writer = new PrintWriter(outputFile);
		writer.print("");
		writer.close();
	}

}
