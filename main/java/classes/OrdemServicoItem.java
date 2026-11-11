package classes;

import java.math.BigDecimal;

public class OrdemServicoItem {
	private Integer id;
	private Integer ordemServico;
	private String sku;
	private String descProduto;
	private String codBarras;
	private BigDecimal qtde;
	private String unidade;
	private String tipo;

	public OrdemServicoItem() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(Integer ordemServico) {
		this.ordemServico = ordemServico;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
