package com.example.listapersonagem.model;

public class Personagem {
    private final String nome;
    private final String altura;
    private final String nascimento;

    public Personagem(String nome, String altura, String nascimento) {

        this.nome = nome;
        this.altura= altura;
        this.nascimento = nascimento;
    }

    @Override
    public String toString() {
        return nome;
    }


}
