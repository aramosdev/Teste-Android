package br.com.aramosdev.testeandroid.model.movie;

import java.io.Serializable;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

class MovieProperty implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
