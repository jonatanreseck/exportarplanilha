package com.planilha.teste.entities;

public class Pareto {
	
	public Pareto() {}
	
	public Pareto(String problema, Integer quantidade) {
		this.setProblema(problema);
		this.setQuantidade(quantidade);
	}
	
	private String Problema;
	private Integer quantidade;
	private Integer ocorrencia;
	private Integer acumulado;
	private Integer totalQuantidade;
	
	public String getProblema() {
		return Problema;
	}
	public void setProblema(String problema) {
		Problema = problema;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Integer ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public Integer getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(Integer acumulado) {
		this.acumulado = acumulado;
	}

	public Integer getTotalQuantidade() {
		return totalQuantidade;
	}

	public void setTotalQuantidade(Integer totalQuantidade) {
		this.totalQuantidade = totalQuantidade;
	}
}
