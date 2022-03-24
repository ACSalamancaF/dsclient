package com.acsalamancaf.dsclient.dto;

import java.io.Serializable;
import java.time.Instant;

import com.acsalamancaf.dsclient.entities.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String cpf;	
	private Instant birthDate;
	private Integer children;
	
	public ClientDTO(Client entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.cpf = entity.getCpf();
		this.birthDate = entity.getBirthDate();
		this.children= entity.getChildren();
	}
}
