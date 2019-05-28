/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhocpd;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Arquivos  {
    private Candidato candidato = new Candidato(); 
    private String linha;  //String que serve de buffer para a linha lida do arquivo 
    private ArrayList <String> cpfs = new ArrayList<> (); // Array de cpfs para manipulação e ordenação
    private BTree<String, String> Bcpf = new BTree<String, String>();
    Map<String, String> hash_partidos = new HashMap<String, String>();

    public Map<String, String> getHash_partidos() {
        return hash_partidos;
    }
    
    public Arquivos (){}; 
    
    public void criaBin() throws FileNotFoundException, IOException, ClassNotFoundException{
       
        //tabelha hash
        Map<String, String> partidos = new HashMap<String, String>();
        
        //Arvore B
        BTree<String, String> st = new BTree<String, String>();
        
        ArrayList <String> cpfs = new ArrayList<String> (); 
        //manipulaçào binária
        File arq_binario = new File ("candidatosbin");
        FileOutputStream fo = new FileOutputStream(arq_binario); 
        ObjectOutputStream output = new ObjectOutputStream (fo); 
        
        //manipulaçao do .txt
        FileReader arq = new FileReader ("candidatos.txt"); 
        BufferedReader lerArq = new BufferedReader (arq); 
        this.linha = lerArq.readLine();
        
        //Laço que vai percorrendo  o arquivo brutu em txt e gerando o binário com os dados semi-editados e uma árvore B respectiva
        //também cria uma lista de cpfs para saber a ordem em que foram colocados e para possível ordenação depois
        //e um hash para saber os partidos de cada candidato
        
        int i = 0; //Vai dar a flag do candidato dentro do arquivo
        while (this.linha != null){
            String[] str = this.linha.replaceAll("\t", "").split("\\*"); //retira os tabs do arquivo .txt
            
            
            this.candidato.setNome(str[0]);
            this.candidato.setCidade(str[1]);
            this.candidato.setCargo(str[2]);
            this.candidato.setCpf( str[3]);
            this.candidato.setPartido(str[4]);
            
            this.candidato.setChave(i);
            //coloca o candidato no arquivo
            output.writeObject(candidato);
            output.reset();
            //teste
            //coloca na árvore B
            st.put(this.candidato.getCpf(),Integer.toString(i)); 
            //coloca na lista de CPFS
            cpfs.add(this.candidato.getCpf()); //Salva a ordem em que apareceram
            //Coloca na tabela hash
            partidos.put(Integer.toString(i),this.candidato.getPartido()); 
            
            this.linha = lerArq.readLine();
            i++; 
        }
        
        //Fecha as files e as stream
        arq.close();
        output.close();
        fo.close();
        
        this.Bcpf = st; 
        this.cpfs = cpfs;
        this.hash_partidos = partidos; 
        
        /*FileInputStream fi = new FileInputStream(arq_binario);
        ObjectInputStream input = new ObjectInputStream(fi); 
       
        try{
            while(true){
                Candidato c = (Candidato) input.readObject();
               // System.out.println(c);
                
            }
        } catch (EOFException ex) {} 
        fi.close();
        input.close();*/
    
    
    
      //  System.out.println(st);
    
    }
    
    //Se receber 0, ordem normal, se receber 1 ordem inversa
    public void ordenaCPF(int ordem){
        
        if (ordem ==0)
        Collections.sort(cpfs);
        else
            Collections.sort(cpfs,Collections.reverseOrder());
        
    }

    public void criarquivoOrdenado(int ordem) throws FileNotFoundException, IOException, ClassNotFoundException {

        //Cria streams de escrita e leitura para abrir o arquivo
        //original, pegar pelo registro e colocar na ordem no arquivo novo
        if (ordem == 0) {
            File arq_ordenado = new File("candidatosbinOrdenado");
            FileOutputStream fo = new FileOutputStream(arq_ordenado);
            ObjectOutputStream output = new ObjectOutputStream(fo);

            boolean achou = false;

            for (String registro : this.cpfs) { //Pega o cpf na lista ordenada
                //Cria a stream de leitura
                File arq_binario = new File("candidatosbin");
                FileInputStream fi = new FileInputStream(arq_binario);
                ObjectInputStream input = new ObjectInputStream(fi);

                //Busca na Btree a chave
                int chave = Integer.parseInt(this.Bcpf.get(registro));
                //Busca o candidato pela chave no arquivo orignal
                try {
                    while (true && !achou) {
                        Candidato c = (Candidato) input.readObject();
                        if (Integer.compare(chave, c.getChave()) == 0) {
                            achou = true;
                            this.candidato = c;
                        }

                    }
                } catch (EOFException ex) {
                }
                //Escreve o candidato no novo arquivo    
                output.writeObject(candidato);
                output.reset();
                input.close(); // fecha a stream de LEITURA
                achou = false;

            }
        } else {
            File arq_ordenado = new File("candidatosbinInverso");
            FileOutputStream fo = new FileOutputStream(arq_ordenado);
            ObjectOutputStream output = new ObjectOutputStream(fo);

            boolean achou = false;

            for (String registro : this.cpfs) { //Pega o cpf na lista ordenada
                //Cria a stream de leitura
                File arq_binario = new File("candidatosbin");
                FileInputStream fi = new FileInputStream(arq_binario);
                ObjectInputStream input = new ObjectInputStream(fi);

                //Busca na Btree a chave
                int chave = Integer.parseInt(this.Bcpf.get(registro));
                //Busca o candidato pela chave no arquivo orignal
                try {
                    while (true && !achou) {
                        Candidato c = (Candidato) input.readObject();
                        if (Integer.compare(chave, c.getChave()) == 0) {
                            achou = true;
                            this.candidato = c;
                        }

                    }
                } catch (EOFException ex) {
                }
                //Escreve o candidato no novo arquivo    
                output.writeObject(candidato);
                output.reset();
                input.close(); // fecha a stream de LEITURA
                achou = false;

            }

        }

    }
    
    public Candidato buscaCPF (String CPF) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        
        //busca a stringCPF na árvore
        String registro = this.Bcpf.get(CPF);
        //se não achar o cpf, retorna nulo
        if(registro == null)
            return null;
        
        File arq_binario = new File ("candidatosbin");
        FileInputStream fi = new FileInputStream(arq_binario);
        ObjectInputStream input = new ObjectInputStream(fi); 
        boolean achou = false; 
         
         
         int chave = Integer.parseInt(registro); 
        try{
            while(true && !achou){
                Candidato c = (Candidato) input.readObject();
                if(Integer.compare(chave, c.getChave()) == 0){
                    achou = true;
                    this.candidato = c; 
                }
                    
            }
        } catch (EOFException ex) {}
         
        fi.close();
        input.close();
        return this.candidato; 
        
    }
    //carrega as configurações já editadas de um arquivo binário nas estruturas de dados para ordenação, pesquisa, etc...
    public void carregaBin() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        File arq_binario = new File ("candidatosbin");
        FileInputStream fi = new FileInputStream(arq_binario);
        ObjectInputStream input = new ObjectInputStream(fi); 
       
        //tabelha hash
        Map<String, String> partidos = new HashMap<>();
        
        //Arvore B
        BTree<String, String> st = new BTree<>();
        
        ArrayList <String> cpfs = new ArrayList<> (); 
        
        try{
            while(true){
                Candidato c = (Candidato) input.readObject();
                //System.out.println(c);
                
                //coloca na árvore B
            st.put(c.getCpf(),Integer.toString(c.getChave())); 
            //coloca na lista de CPFS
            cpfs.add(c.getCpf()); //Salva a ordem em que apareceram
            //Coloca na tabela hash
            partidos.put(Integer.toString(c.getChave()),c.getPartido()); 
            }
        } catch (EOFException ex) {}
        fi.close();
        input.close();
        this.Bcpf = st; 
        this.cpfs = cpfs;
        this.hash_partidos = partidos;
        
        
        
    }
 
            //exibe os candidatos na ordem em que foram gerados
    //Se ordem = 0 , exibe eles ordenados
    //Se ordem = 1, exibe na ordem inversa
    //Se oordem = 2, exibe na ordem aleatória em que foram catalogados
      public void exibeCandidatos(int ordem) throws FileNotFoundException, IOException, ClassNotFoundException {

        if (ordem == 0) {
            File arq_binario = new File("candidatosbinOrdenado");
            FileInputStream fi = new FileInputStream(arq_binario);
            ObjectInputStream input = new ObjectInputStream(fi);

            try {
                while (true) {
                    Candidato c = (Candidato) input.readObject();
                    System.out.println(c);

                }
            } catch (EOFException ex) {
            }

            fi.close();
            input.close();
        } else if (ordem == 1) {
            File arq_binario = new File("candidatosbinInverso");
            FileInputStream fi = new FileInputStream(arq_binario);
            ObjectInputStream input = new ObjectInputStream(fi);

            try {
                while (true) {
                    Candidato c = (Candidato) input.readObject();
                    System.out.println(c);

                }
            } catch (EOFException ex) {
            }

            fi.close();
            input.close();

        } else {
            File arq_binario = new File("candidatosbin");
            FileInputStream fi = new FileInputStream(arq_binario);
            ObjectInputStream input = new ObjectInputStream(fi);

            try {
                while (true) {
                    Candidato c = (Candidato) input.readObject();
                    System.out.println(c);

                }
            } catch (EOFException ex) {
            }

            fi.close();
            input.close();
        }
    }
      
      //Retorna todas pessoas de um partido
      public boolean buscaPartido(String partido_procurado) throws FileNotFoundException, IOException, ClassNotFoundException{
          
          ArrayList<Integer> pessoas_partido = new ArrayList<>(); //Array onde ficarão os registros das pessoas que fazem parte do partido
          
          for(String chave : this.hash_partidos.keySet()){
              if(this.hash_partidos.get(chave).equals(partido_procurado)) //se o conteúdo da tabela(partido) for igual ao procurado, coloca a pessoa no array de pessoas no partido
                  pessoas_partido.add(Integer.parseInt(chave));     
          }
          if(pessoas_partido.isEmpty())
              return false;
          //Depois de montar a lista de todas pessoas que participam do partido, vai procurando por elas no arquivo e printando na tela
          
          File arq_binario = new File("candidatosbin");
            FileInputStream fi = new FileInputStream(arq_binario);
            ObjectInputStream input = new ObjectInputStream(fi);

            try { //Lê todo o arquivo. Se a chave estiver presente no array, printa ele na tela pois faz parte do partido
                while (true) {
                    Candidato c = (Candidato) input.readObject();
                    if (pessoas_partido.contains(c.getChave()))
                        System.out.println(c);

                }
            } catch (EOFException ex) {
            }

            fi.close();
            input.close();
            return true;
      }
}
