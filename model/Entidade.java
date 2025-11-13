package model;

public abstract class Entidade {
    protected final int id;

    protected Entidade(int id) {
        this.id = id;
    }

    public int obterId() {
        return id;
    }

    public abstract String toCSV();
    public abstract void fromCSV(String linha);
}