package com.exner.tools.analyticstdd.GenericTests4AnalyticsProject;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
	private String[] args = null;
	private Options options = new Options();
	private String filename = null;

	public Cli(String[] args) {

		this.args = args;

		options.addOption("h", "help", false, "show help.");
		options.addOption("f", "test-description-file", true, "specify JSON file that contains test description");

	}

	public void parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption("h"))
				help();

			if (cmd.hasOption("f")) {
				// Whatever you want to do with the setting goes here
				filename = cmd.getOptionValue('f');
			} else {
				help();
			}

		} catch (ParseException e) {
			help();
		}
	}

	public String getFilename() {
		return filename;
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Main", options);
		System.exit(0);
	}
}