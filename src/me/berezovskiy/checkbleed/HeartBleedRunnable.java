package me.berezovskiy.checkbleed;

import java.io.IOException;
import java.util.Set;

public class HeartBleedRunnable implements Runnable {

	private String domain;
	private Set<String> vulnerableDomainSet;

	public HeartBleedRunnable(Set<String> vulnerableDomainSet, String domain) {
		this.vulnerableDomainSet = vulnerableDomainSet;
		this.domain = domain;
	}

	@Override
	public void run() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process exec = runtime.exec(new String[]{"Heartbleed.exe", domain});
			int exitValue = exec.waitFor();
			if(exitValue == 0) {
//				System.out.printf("Domain %s is secure\n", domain);//TODO log4j
			}
			else if (exitValue == 1){
//				System.err.printf("Domain %s is INSECURE\n", domain);//TODO log4j
				vulnerableDomainSet.add(domain);
			}
			else {
				System.err.printf("Domain %s was not checked (err %d)\n", domain, exitValue);//TODO log4j
			}
		} catch (InterruptedException | IOException e) {
			System.err.printf("Domain %s check failed\n", domain);//TODO log4j
		}
	}
}
