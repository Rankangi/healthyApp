package fr.pcmaintenance.healthy.Modele;

public class User {

    private String name;
    private Date birthday = new Date();
    private int sexe;
    private int taille;
    private float poids;
    private int activité;
    private int objectif;

    public User() {
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getSexe() {
        return sexe;
    }

    public int getTaille() {
        return taille;
    }

    public float getPoids() {
        return poids;
    }

    public int getActivité() {
        return activité;
    }

    public int getObjectif() {
        return objectif;
    }

    public void setBirthday(String date){
        this.birthday.setDate(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSexe(int sexe) {
        this.sexe = sexe;
    }

    public void setSexe(boolean sexe) {
        if (sexe){
            this.sexe = 1;
        }else{
            this.sexe = 0;
        }
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public void setActivité(int activité) {
        this.activité = activité;
    }

    public void setObjectif(int objectif) {
        this.objectif = objectif;
    }
}
