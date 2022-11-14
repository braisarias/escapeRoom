package com.liceo.escaperoom;
import java.util.Random;

public class Configuracion {
    final int MAX_NUM = 100;
    final int MIN_NUM = 1;
    final int MAX_ATTEMPTS = 6;

    private int number;
    private int maxNumber;
    private int minNumber;
    private int attempts;
    private int maxAttempts;

    public Configuracion(int maxNumber, int minNumber, int maxAttempts) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
        this.maxAttempts = maxAttempts;
    }

    public Configuracion() {
        this.maxNumber = MAX_NUM;
        this.minNumber = MIN_NUM;
        this.maxAttempts = MAX_ATTEMPTS;
        this.attempts = this.maxAttempts;
        this.regenerateNumber();
    }

    private void regenerateNumber(){
        this.number = (int) (Math.random()*(this.maxNumber - this.minNumber + 1) + this.minNumber);
    }

    void restarIntento() throws AttemptsException {
        if (this.attempts > 0){
            this.attempts --;
        } else {
            throw new AttemptsException("Número de intentos superado");
        }
    }

    public int getNumber() {
        return number;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public String toString() {
        return "Configuracion{" +
                "MAX_NUM=" + MAX_NUM +
                ", MIN_NUM=" + MIN_NUM +
                ", MAX_ATTEMPTS=" + MAX_ATTEMPTS +
                ", number=" + number +
                ", maxNumber=" + maxNumber +
                ", minNumber=" + minNumber +
                ", attempts=" + attempts +
                ", maxAttempts=" + maxAttempts +
                '}';
    }
}

