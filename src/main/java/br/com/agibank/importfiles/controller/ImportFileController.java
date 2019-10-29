package br.com.agibank.importfiles.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.agibank.importfiles.model.ClientModel;
import br.com.agibank.importfiles.model.ReportModel;
import br.com.agibank.importfiles.model.SalesManModel;
import br.com.agibank.importfiles.model.SalesModel;
import br.com.agibank.importfiles.service.ProcessSplitService;
import br.com.agibank.importfiles.service.ReportService;
import br.com.agibank.importfiles.utils.Const;
import br.com.agibank.importfiles.utils.DataHolder;

/**
 * 
 * @author Wenderson.silva
 * 
 * Responsável por iniciar o processamento e arquivar o resultado final
 *
 */
@Component
@EnableScheduling
public class ImportFileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportFileController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ProcessSplitService splitService;
	
	@Value("${import.file.extension}")
	private String extension;
	
	@Value("${import.file.name.out}")
	private String fileName;

	@Value("${import.folder.in}")
	private String importFolder;

	@Value("${import.folder.out}")
	private String exportFolder;
	
	
	@Scheduled(cron = "*/15 * * * * *")
	public void initProcess() throws IOException {
		
		logger.info("Sales Report iniciado {}", LocalDateTime.now());
		
		String In = System.getProperty(Const.HOMEPATH) + importFolder;
		String Out = System.getProperty(Const.HOMEPATH) + exportFolder;
		
		DataHolder.save(Const.CLIENTSAVE, new ArrayList<ClientModel>());
		DataHolder.save(Const.SALESMANSAVE, new ArrayList<SalesManModel>());
		DataHolder.save(Const.SALESSAVE, new ArrayList<SalesModel>());
		
		create(In);
		create(Out);
		
		File[] files = getFilesAll(In);
		
		splitService.split(files);
		
		File file = new File(In);
		FileUtils.cleanDirectory(file);
		
		saveExportFile(Out);	
		
	}
	
	
	private File[] getFilesAll(String directory) {
		File file = new File(directory);
		return file.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(extension);
			}
		});
	}
	
	private void create(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	private void saveExportFile(String out) throws IOException {
		try {
			ReportModel report = reportService.execReport();
			
			logger.info("Report Save > {} ", report);
			
			String data = "Número de clientes " + report.getQtdClients() 
						 + "\n" + "Número de Vendedores " + report.getQtdSalesMan()
						 + "\n" + "Id da venda mais cara " + report.getIdSales()
						 + "\n" + "Pior vendedor " + report.getSalesMan();
			
			StringBuilder fileOut = new StringBuilder(out).append(fileName).append(extension);
			FileWriter fileWriter = new FileWriter(fileOut.toString());
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(data);
			fileWriter.close();	
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
