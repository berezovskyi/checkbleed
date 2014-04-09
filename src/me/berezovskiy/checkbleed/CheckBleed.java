package me.berezovskiy.checkbleed;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CheckBleed {

	public static void main(String[] args) throws IOException {
		Settings settings = new Settings(); // TODO replace by JCommander
		
		FileSystem defaultFileSystem = FileSystems.getDefault();
		Path filePath = defaultFileSystem.getPath(settings.getFileName());
		BufferedReader fileBufferedReader = Files.newBufferedReader(filePath);
		
		Set<String> domainSet = new HashSet<>();
		Map<String, Boolean> vulnerableDomainBackingMap = new ConcurrentHashMap<>();
		Set<String> vulnerableDomainSet = Collections.newSetFromMap(vulnerableDomainBackingMap);

		int totalLines = 0;
		int malformedLines = 0;
		for (Iterator<String> iterator = fileBufferedReader.lines().iterator(); iterator.hasNext();) {
			String line = iterator.next();
			totalLines++;
			try {
				String uriString = line; // TODO URLEncode everything except & after first ?
				URI lineUri = new URI(uriString);
				String host = lineUri.getHost();
				if(!domainSet.contains(host)) {
					domainSet.add(host);
//					System.out.println(host); //TODO log4j
				}
			} catch (URISyntaxException e) {
				System.err.printf("Ignoring malformed URL `%s`\n", line);
				malformedLines++;
			}
		}
		
		System.out.printf("Lines read:    %5d\n", totalLines);
		System.out.printf("Lines ignored: %5d\n", malformedLines);
		System.out.printf("Domains added: %5d\n", domainSet.size());
		
		ExecutorService executor = Executors.newFixedThreadPool(settings.getThreadCount());
		
		try {
			for (String domain : domainSet) {
				Runnable heartbleedRunnable = new HeartBleedRunnable(vulnerableDomainSet, domain);
				executor.execute(heartbleedRunnable);
			}
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.err.println("All theads didn't execute");//TODO log4j
			System.err.println(e.getStackTrace());
		}
		
		System.out.println("==============================================================");
		System.out.println("VULNERABLE DOMAINS:");
		System.out.println("==============================================================");
		
		for (String vulnerableDomain : vulnerableDomainSet) {
			System.out.printf("https://%s\n", vulnerableDomain.toLowerCase());
		}
	}
}
