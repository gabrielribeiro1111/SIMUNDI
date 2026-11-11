package classes;

public class Cliente {
    private String codCliente;
    private String nome;
    private String tipo;
    private String documento;
    private String contato;
    private boolean inativo;
    private boolean bloqueado;

    public Cliente() {}

    public String getCodCliente() { return codCliente; }
    public void setCodCliente(String codCliente) { this.codCliente = codCliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }

    public boolean isInativo() { return inativo; }
    public void setInativo(boolean inativo) { this.inativo = inativo; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }
}
