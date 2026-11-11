package classes;

public class Produto {
	private String sku;
	private String codBarras;
	private String descProduto;
	private String tipoProduto;
	private Integer nivelAreaVermelha;
	private Integer nivelCritico;
	private String unidade;

	public Produto() {
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public Integer getNivelAreaVermelha() {
		return nivelAreaVermelha;
	}

	public void setNivelAreaVermelha(Integer nivelAreaVermelha) {
		this.nivelAreaVermelha = nivelAreaVermelha;
	}

	public Integer getNivelCritico() {
		return nivelCritico;
	}

	public void setNivelCritico(Integer nivelCritico) {
		this.nivelCritico = nivelCritico;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
}
