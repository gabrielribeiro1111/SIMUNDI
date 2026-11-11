package classes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentacaoEstoque {
	private Integer id;
	private Integer idItem;
	private Integer idControleValidade;
	private Integer idEstoqueOrigem;
	private Integer idEstoqueDestino;
	private BigDecimal quantidade;
	private LocalDateTime dataHora;

	public MovimentacaoEstoque() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public Integer getIdControleValidade() {
		return idControleValidade;
	}

	public void setIdControleValidade(Integer idControleValidade) {
		this.idControleValidade = idControleValidade;
	}

	public Integer getIdEstoqueOrigem() {
		return idEstoqueOrigem;
	}

	public void setIdEstoqueOrigem(Integer idEstoqueOrigem) {
		this.idEstoqueOrigem = idEstoqueOrigem;
	}

	public Integer getIdEstoqueDestino() {
		return idEstoqueDestino;
	}

	public void setIdEstoqueDestino(Integer idEstoqueDestino) {
		this.idEstoqueDestino = idEstoqueDestino;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
}
