package classes;

import java.math.BigDecimal;

public class EstoqueReal {
	private Integer id;
	private String identificacao;
	private Integer posXInicial;
	private Integer posYInicial;
	private Integer posZInicial;
	private Integer posXFinal;
	private Integer posYFinal;
	private Integer posZFinal;
	private Integer codTipoEstoque;
	private BigDecimal armazenamentoMax;
	private boolean virtual;
	private boolean ativa;

	public EstoqueReal() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public Integer getPosXInicial() {
		return posXInicial;
	}

	public void setPosXInicial(Integer posXInicial) {
		this.posXInicial = posXInicial;
	}

	public Integer getPosYInicial() {
		return posYInicial;
	}

	public void setPosYInicial(Integer posYInicial) {
		this.posYInicial = posYInicial;
	}

	public Integer getPosZInicial() {
		return posZInicial;
	}

	public void setPosZInicial(Integer posZInicial) {
		this.posZInicial = posZInicial;
	}

	public Integer getPosXFinal() {
		return posXFinal;
	}

	public void setPosXFinal(Integer posXFinal) {
		this.posXFinal = posXFinal;
	}

	public Integer getPosYFinal() {
		return posYFinal;
	}

	public void setPosYFinal(Integer posYFinal) {
		this.posYFinal = posYFinal;
	}

	public Integer getPosZFinal() {
		return posZFinal;
	}

	public void setPosZFinal(Integer posZFinal) {
		this.posZFinal = posZFinal;
	}

	public Integer getCodTipoEstoque() {
		return codTipoEstoque;
	}

	public void setCodTipoEstoque(Integer codTipoEstoque) {
		this.codTipoEstoque = codTipoEstoque;
	}

	public BigDecimal getArmazenamentoMax() {
		return armazenamentoMax;
	}

	public void setArmazenamentoMax(BigDecimal armazenamentoMax) {
		this.armazenamentoMax = armazenamentoMax;
	}

	public boolean isVirtual() {
		return virtual;
	}

	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
}
