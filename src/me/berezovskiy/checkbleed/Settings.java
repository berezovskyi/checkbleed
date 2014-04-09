package me.berezovskiy.checkbleed;

public class Settings {
	private String fileName = "D:\\urls.txt";
	private int ThreadCount = 32;

	public int getThreadCount() {
		return ThreadCount;
	}

	public String getFileName() {
		return fileName;
	}
}
