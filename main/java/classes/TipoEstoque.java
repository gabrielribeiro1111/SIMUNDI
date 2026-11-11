package classes;

public class TipoEstoque {
	private Integer codTipoEstoque;
	private String descTipoEstoque;
	private String unidadeEstoque;

	public TipoEstoque() {
	}

	public Integer getCodTipoEstoque() {
		return codTipoEstoque;
	}

	public void setCodTipoEstoque(Integer codTipoEstoque) {
		this.codTipoEstoque = codTipoEstoque;
	}

	public String getDescTipoEstoque() {
		return descTipoEstoque;
	}

	public void setDescTipoEstoque(String descTipoEstoque) {
		this.descTipoEstoque = descTipoEstoque;
	}

	public String getUnidadeEstoque() {
		return unidadeEstoque;
	}

	public void setUnidadeEstoque(String unidadeEstoque) {
		this.unidadeEstoque = unidadeEstoque;
	}
}
