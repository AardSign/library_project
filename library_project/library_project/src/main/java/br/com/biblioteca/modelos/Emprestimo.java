package br.com.biblioteca.modelos;

public class Emprestimo {
    private int id;
    private int clienteId;
    private int livroId;
    private String dataEmprestimo;
    private String dataDevolucao;
    private String dataPrevista;
    private String status; // "ativo", "concluido", "atrasado"

    public Emprestimo(int id, int clienteId, int livroId, String dataEmprestimo, String dataDevolucao, String dataPrevista, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.dataPrevista = dataPrevista;
        this.status = status;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getLivroId() {
		return livroId;
	}

	public void setLivroId(int livroId) {
		this.livroId = livroId;
	}

	public String getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(String dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public String getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(String dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
	    return "Empréstimo ID: " + id +
	           "\nCliente ID: " + clienteId +
	           "\nLivro ID: " + livroId +
	           "\nData do Empréstimo: " + dataEmprestimo +
	           "\nData Prevista para Devolução: " + dataPrevista +
	           "\nData da Devolução: " + (dataDevolucao == null ? "Ainda não devolvido" : dataDevolucao) +
	           "\nStatus: " + status + "\n";
	}

   
}