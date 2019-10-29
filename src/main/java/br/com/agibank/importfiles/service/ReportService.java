package br.com.agibank.importfiles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agibank.importfiles.model.ReportModel;

@Service
public class ReportService {
	
	@Autowired
	private ClientsService clientService;
	
	@Autowired
	private SalesManService salesmanService;
	
	@Autowired
	private SalesService salesService;
	
	public ReportModel execReport() {
		return ReportModel
				.builder()
				.qtdClients(clientService.getClientsAll().size())
				.qtdSalesMan(salesmanService.getSalesManAll().size())
				.idSales(salesService.getSalesMax().getId())
				.salesMan(salesService.getSalesMan().getSalesmanName())
				.build();
	}

}
