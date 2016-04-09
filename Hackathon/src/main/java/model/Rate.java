/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author Admin
 */
public class Rate {
    private int id;
    private int idUser;
    private int idCouse;
    
    private int score;
    private int passed;

    public Rate() {
    }

    public Rate(int idUser, int idCouse, int score, int passed) {
        this.idUser = idUser;
        this.idCouse = idCouse;
        this.score = score;
        this.passed = passed;
    }

    public Rate(int idUser, int idCouse) {
        this.idUser = idUser;
        this.idCouse = idCouse;
        score = (new Random().nextInt(10)>4)? (new Random().nextInt(11)):-1;
        passed = (new Random().nextInt(100)%2);
    }

    
    
    
    public Rate(int id, int idUser, int idCouse, int score, int passed) {
        this.id = id;
        this.idUser = idUser;
        this.idCouse = idCouse;
        this.score = score;
        this.passed = passed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCouse() {
        return idCouse;
    }

    public void setIdCouse(int idCouse) {
        this.idCouse = idCouse;
    }

    public int getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = (int)score;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    @Override
    public String toString() {
        return "Rate{" + "id=" + id + ", idUser=" + idUser + ", idCouse=" + idCouse + ", score=" + score + ", passed=" + passed + '}';
    }
    
    
}
