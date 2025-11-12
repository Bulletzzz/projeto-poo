package model;

import exceptions.ValidacaoException;

public final class Cliente extends Entidade {
    private final String nome;
    private final String email;

    private Cliente(long id, String nome, String email) {
        super(id);
        this.nome = nome;
        this.email = email;
    }

    public static Cliente criar(long id, String nome, String email) throws ValidacaoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome do cliente é obrigatório.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new ValidacaoException("E-mail válido é obrigatório.");
        }
        return new Cliente(id, nome.trim(), email.trim());
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
}