package org.willian.domain.entities;

import java.time.LocalDate;

public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(LocalDate dataNascimento, String nome) {
        this.dataNascimento = dataNascimento;
        this.nome = nome;
    }

    public Pessoa() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
