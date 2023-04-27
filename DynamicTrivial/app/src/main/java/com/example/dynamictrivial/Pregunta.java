package com.example.dynamictrivial;

import java.util.List;

public class Pregunta {
    private String pregunta;
    private String categoria;
    private List<String> opciones;
    private int respuesta;

    public Pregunta() {
        // Constructor vacío requerido por Firebase Realtime Database
    }

    public Pregunta(String pregunta, String categoria, List<String> opciones, int respuesta) {
        this.pregunta = pregunta;
        this.categoria = categoria;
        this.opciones = opciones;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
}
