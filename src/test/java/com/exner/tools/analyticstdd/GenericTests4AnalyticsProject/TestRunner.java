package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestSuite;

public class TestRunner {

	public static void main(String[] args) {
		// make sure we have proper command line args
		Cli cli = new Cli(args);
		cli.parse();
		
		// read the test description from the JSON file, then build a list of tests
		String testDescriptionFilename = cli.getFilename();
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject testDescription = (JSONObject) jsonParser
					.parse(new BufferedReader(new FileReader(testDescriptionFilename)));
			//
			// this is where we pull that JSON apart and make individual test descriptions out of it
			//
			// TODO
			//
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// create us a lovely TestSuite
		TestSuite testSuite = new TestSuite();
		
	}

}
