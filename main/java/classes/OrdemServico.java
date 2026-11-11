package classes;

import java.time.LocalDateTime;

public class OrdemServico {
	private Integer ordemServico;
	private String tipo;
	private String codCliente;
	private LocalDateTime emissao;
	private Integer duracao;
	private String status;
	private String obs;

	public OrdemServico() {
	}

	public Integer getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(Integer ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public LocalDateTime getEmissao() {
		return emissao;
	}

	public void setEmissao(LocalDateTime emissao) {
		this.emissao = emissao;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
