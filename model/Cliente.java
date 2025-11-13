package model;

import exceptions.ValidacaoException;

public class Cliente extends Entidade {
    private final String nome;
    private final String email;

    private Cliente(int id, String nome, String email) {
        super(id);
        this.nome = nome;
        this.email = email;
    }

    public static Cliente criar(int id, String nome, String email) throws ValidacaoException {
        if (nome == null || nome.isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio.");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new ValidacaoException("E-mail inválido.");
        }
        return new Cliente(id, nome, email);
    }

    public String obterNome() {
        return nome;
    }

    public String obterEmail() {
        return email;
    }

    public void exibirInformacoes() {
        System.out.println("ID: " + id + ", Nome: " + nome + ", E-mail: " + email);
    }

    @Override
    public String toCSV() {
        return id + "," + nome + "," + email;
    }

    @Override
    public void fromCSV(String linha) {
        String[] partes = linha.split(",");
    }
}