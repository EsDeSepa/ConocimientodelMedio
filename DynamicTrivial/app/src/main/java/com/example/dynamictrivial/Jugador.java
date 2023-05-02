package com.example.dynamictrivial;


public class Jugador {
    private String nombre;
    private int puntos;
    private int turno;

    public Jugador() {
        // Constructor vacío requerido para DataSnapshot.getValue(Jugador.class)
    }
    public Jugador(String nombre, int puntos,int turno) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.turno = turno;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }
    public int getTurno() {
        return turno;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntos = puntos;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
}
