package model;

import java.time.LocalDate;

public class Cliente {
	private String cpf;
	private String nome;
	private String email;
	private double limiteDoCartao;
	private LocalDate dataNascimento;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getLimiteDoCartao() {
		return limiteDoCartao;
	}
	public void setLimiteDoCartao(double limiteDoCartao) {
		this.limiteDoCartao = limiteDoCartao;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	@Override
	public String toString() {
		if(dataNascimento != null) {
			return "CPF: "+cpf+" | Nome: "+nome+" | data de nascimento: "+dataNascimento.toString();
		}else {
			return "CPF: "+cpf+" | Nome: "+nome+" | data de nascimento: ";
		}
		
	}
	
}
