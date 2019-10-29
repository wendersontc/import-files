package br.com.agibank.importfiles.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.agibank.importfiles.domain.TypeData;

@Service
public class ProcessSplitService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessSplitService.class);

	private final static String SPLIT = "ç";
	
	public String split(File[] files) throws FileNotFoundException {
		
		for (File file : files) {
			Scanner body = new Scanner(file);
			while (body.hasNextLine()) {
				String row = body.nextLine();
				logger.info("Processando linha > {} ", row);
				String[] parts = row.split(SPLIT);
				TypeData.getCodeSplit(parts[0]).importar().saveData(parts);
			}
			body.close();
		}
		
		return null;
	}
	
	
	
	
}
