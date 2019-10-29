package br.com.agibank.importfiles.model;

import java.math.BigDecimal;
import java.util.Optional;

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
public class ItemSalesModel {
	
	private Integer id;
	private Integer quantity;
	private BigDecimal price;
	
	public BigDecimal valueSales() {
		return Optional.of(price)
				.map(c -> c.multiply(BigDecimal.valueOf(quantity)))
				.orElse(BigDecimal.ZERO);
	}

}
