package com.example.dynamictrivial;

import java.util.ArrayList;

public class DatosPartida {
    private ArrayList<Jugador> jugadores;
    private String turno;

    public DatosPartida(ArrayList<Jugador> jugadores, String turno) {
        this.jugadores = jugadores;
        this.turno = turno;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
