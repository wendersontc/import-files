package br.com.agibank.importfiles.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class ReportModel {
	
	private Integer qtdClients;
	private Integer qtdSalesMan;
	private Integer idSales;
	private String  salesMan;

}
