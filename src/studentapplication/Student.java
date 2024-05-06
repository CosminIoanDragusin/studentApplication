/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentapplication;

/**
 *
 * @author user
 */
public class Student {
    private  Integer id;
    private  String nume;
    private  String prenume;
    private  String nota;
    private  String materie;
    private  String email;
    private  String profesor;

    public Student(Integer id, String nume, String prenume, String nota, String materie, String email, String profesor) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.nota = nota;
        this.materie = materie;
        this.email = email;
        this.profesor = profesor;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(int id){
    this.id=id;
    }
    
    public String getNume() {
        return nume;
    }
   
    public void setNume(String nume){
    this.nume=nume;
    }
    
    public String getPrenume() {
        return prenume;
    }
    
    public void setPrenume(String prenume){
    this.prenume=prenume;
    }

    public String getNota() {
        return nota;
    }
    
    public void setNota(String nota){
    this.nota=nota;
    }

    public String getMaterie() {
        return materie;
    }
    
    public void setMaterie(String materie){
    this.materie=materie;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email){
    this.email=email;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor){
    this.profesor=profesor;
    }
    
}
