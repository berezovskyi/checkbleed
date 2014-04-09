package me.berezovskiy.checkbleed;

import com.beust.jcommander.Parameter;

public class Settings {
	@Parameter(names = "-file", description = "File with URLs. One URL per line.", required = true)
	private String fileName = "D:\\urls.txt";
	
	@Parameter(names = "-d", description = "Thread pool size.")
	private int ThreadCount = 32;

	public int getThreadCount() {
		return ThreadCount;
	}

	public String getFileName() {
		return fileName;
	}
}
