package br.com.agibank.importfiles.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.agibank.importfiles.model.SalesManModel;
import br.com.agibank.importfiles.process.SaveFactory;
import br.com.agibank.importfiles.utils.Const;
import br.com.agibank.importfiles.utils.DataHolder;

@Service
public class SalesManService implements SaveFactory {

	List<SalesManModel> salesman = new ArrayList<SalesManModel>();

	@Override
	public void saveData(String[] data) {
		salesman = (List<SalesManModel>) DataHolder.retrieve(Const.SALESMANSAVE);
		salesman.add(SalesManModel
				.builder()
				.cpf(data[1])
				.name(data[2])
				.salary(new BigDecimal(data[3]))
				.build());
		DataHolder.save(Const.SALESMANSAVE, salesman);
	}
	
	public List<SalesManModel> getSalesManAll(){
		List<SalesManModel> lista = (List<SalesManModel>) DataHolder.retrieve(Const.SALESMANSAVE);
		return lista.stream()
				.distinct()
				.collect(Collectors.toList());
	}
	
}
