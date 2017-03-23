package br.com.heiderlopes.firebasecrud.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by heider on 23/03/17.
 */

@IgnoreExtraProperties
public class Tarefa {

    public Tarefa(String descricao) {
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
