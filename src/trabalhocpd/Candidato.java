/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocpd;

import java.io.Serializable;

/**
 *
 * @author Igor
 */
public class Candidato implements Serializable {
    
   
    private int chave; //chave para guarda a posiçao do candidato no arquivo
    private String cidade;
    private String cargo;
    private String nome;
    private String cpf;
    private String partido;
    
    public Candidato() {
      
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }
    

  @Override
  public String toString() {
    return nome + " | "  + cpf + " | " + partido + " | " + cargo + " | " +  cidade;    
}
}
