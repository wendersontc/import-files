package br.com.agibank.importfiles.domain;

import java.util.Arrays;

import br.com.agibank.importfiles.process.SaveFactory;
import br.com.agibank.importfiles.service.ClientsService;
import br.com.agibank.importfiles.service.SalesManService;
import br.com.agibank.importfiles.service.SalesService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeData {
	
	VENDEDOR("001") {
		@Override
		public SaveFactory importar() {
			return new SalesManService();
		}
	},CLIENTE("002") {
		@Override
		public SaveFactory importar() {
			return new ClientsService();
		}
	},VENDAS("003") {
		@Override
		public SaveFactory importar() {
			return new SalesService();
		}
	};
	
	private final String codeSplit;
	
	public static TypeData getCodeSplit(final String code) {
		return Arrays.asList(TypeData.values())
				.stream().filter(cod -> cod.codeSplit.equals(code))
				.findFirst()
				.orElse(null);
	}
	
	public abstract SaveFactory importar();

}
