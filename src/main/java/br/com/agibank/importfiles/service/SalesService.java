package br.com.agibank.importfiles.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import br.com.agibank.importfiles.model.ItemSalesModel;
import br.com.agibank.importfiles.model.SalesModel;
import br.com.agibank.importfiles.process.SaveFactory;
import br.com.agibank.importfiles.utils.Const;
import br.com.agibank.importfiles.utils.DataHolder;

@Service
public class SalesService implements SaveFactory {
	
	List<SalesModel> sales = new ArrayList<SalesModel>();

	@Override
	public void saveData(String[] data) {
		sales = (List<SalesModel>) DataHolder.retrieve(Const.SALESSAVE);
		sales.add(SalesModel
				.builder()
				.id(Integer.parseInt(data[1]))
				.items(insertItens(data[2]))
				.salesmanName(data[3])
				.build());
		DataHolder.save(Const.SALESSAVE, sales);
	}
	
	public List<SalesModel> getAll(){
		return (List<SalesModel>) Optional.ofNullable(DataHolder.retrieve(Const.SALESSAVE)).orElse(null);
	}
	
	public SalesModel getSalesMan() {
		return getAll()
				.stream()
				.min(Comparator.comparing(SalesModel::getTotalValue))
				.get();
	}
	
	public SalesModel getSalesMax() {
		return getAll()
				.stream()
				.max(Comparator.comparing(SalesModel::getTotalValue))
				.get();
	}
	
	private List<ItemSalesModel> insertItens(String data) {
		String regex = "(\\w{1,9}-\\d{1,9}-\\d{1,9}[.]{0,1}\\d{0,4})";
		Pattern pattern = Pattern.compile("[\\[]" + regex + "[,]" + regex + "[,]" + regex + "[\\]]");
		Matcher matcher = pattern.matcher(data);
		matcher.find();

		List<ItemSalesModel> itens = new ArrayList<>();
		for (int i = 1; i <= matcher.groupCount(); i++) {
			String[] item = matcher.group(i).split("-");
			itens.add(ItemSalesModel
					.builder()
					.id(Integer.parseInt(item[0]))
					.quantity(Integer.parseInt(item[1]))
					.price(new BigDecimal(item[2]))
					.build());
		}
		return itens;
	}

}
