package br.com.contato.modelo;

import java.io.Serializable;

public class Modelo implements Serializable {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
