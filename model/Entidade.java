package model;

public abstract class Entidade {
    protected final long id;

    protected Entidade(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entidade)) return false;
        Entidade entidade = (Entidade) o;
        return id == entidade.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}