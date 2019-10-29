package br.com.agibank.importfiles.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.agibank.importfiles.model.ClientModel;
import br.com.agibank.importfiles.process.SaveFactory;
import br.com.agibank.importfiles.utils.Const;
import br.com.agibank.importfiles.utils.DataHolder;


@Service
public class ClientsService implements SaveFactory {
	
	List<ClientModel> clients = new ArrayList<ClientModel>();

	@Override
	public void saveData(String[] data) {
		clients = (List<ClientModel>) DataHolder.retrieve(Const.CLIENTSAVE);
		clients.add(ClientModel
				.builder()
				.cnpj(data[1])
				.name(data[2])
				.businessArea(data[3])
				.build());
		DataHolder.save(Const.CLIENTSAVE, clients);
	}
	
	public List<ClientModel> getClientsAll(){
		List<ClientModel> clients = (List<ClientModel>) DataHolder.retrieve(Const.CLIENTSAVE);
		return clients
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}

}
