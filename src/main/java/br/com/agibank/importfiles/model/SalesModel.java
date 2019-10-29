package br.com.agibank.importfiles.model;

import java.math.BigDecimal;
import java.util.List;

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
public class SalesModel {
	
	private Integer id;
	private List<ItemSalesModel> items;
	private String salesmanName;
	
	public BigDecimal getTotalValue() {
		return items
			.stream()
			.map(c-> c.valueSales())
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
