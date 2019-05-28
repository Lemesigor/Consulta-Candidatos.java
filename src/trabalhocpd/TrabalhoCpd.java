
package trabalhocpd;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TrabalhoCpd {

    public static void main(String args[]) throws IOException, FileNotFoundException, ClassNotFoundException {
  
        /* System.out.println("\n");
             System.out.println("1 - GERAR ARQUIVO BINARIO");
             System.out.println("2 - CARREGAR ARQUIVO BINARIO");
             System.out.println("3 - ORDENAR POR CPF");
             System.out.println("4 - ORDENAR INVERSAMENTE POR CPF");
             System.out.println("5 - EXIBIR CANDIDATOS");
             System.out.println("6 - EXIBIR CANDIDATOS ORDENADO");
             System.out.println("7 - EXIBIR CANDIDATOS ORDEM INVERSA");
             System.out.println("8 - BUSCAR CANDIDATO CPF");
             System.out.println("9 - BUSCAR CANDIDATOS POR PARTIDO");
        Scanner ler = new Scanner(System.in);*/
        Arquivos arq = new Arquivos();
      //  int opcao = Integer.parseInt(ler.nextLine()); 
        int opcao; 
        
        do {
             System.out.println("\n");
             System.out.println("1 - GERAR ARQUIVO BINARIO");
             System.out.println("2 - CARREGAR ARQUIVO BINARIO");
             System.out.println("3 - ORDENAR POR CPF");
             System.out.println("4 - ORDENAR INVERSAMENTE POR CPF");
             System.out.println("5 - EXIBIR CANDIDATOS");
             System.out.println("6 - EXIBIR CANDIDATOS ORDENADO");
             System.out.println("7 - EXIBIR CANDIDATOS ORDEM INVERSA");
             System.out.println("8 - BUSCAR CANDIDATO CPF");
             System.out.println("9 - BUSCAR CANDIDATOS POR PARTIDO");
          Scanner  ler = new Scanner(System.in);
            opcao = Integer.parseInt(ler.nextLine());
            
            
    // Nos argumentos de ordenação,
    //Se ordem = 0 , exibe eles ordenados
    //Se ordem = 1, exibe na ordem inversa
    //Se oordem = 2, exibe na ordem aleatória em que foram catalogados
            
        switch(opcao){
                 case 1: arq.criaBin();
                        break;
                 
                 case 2: arq.carregaBin();
                        break;
                 
                 case 3: arq.ordenaCPF(0);
                         arq.criarquivoOrdenado(0);
                         break;
                
                 case 4: arq.ordenaCPF(1);
                        arq.criarquivoOrdenado(1);
                         break;
                         
                 
                 case 5: arq.exibeCandidatos(2);
                        break;
                 
                 case 6: arq.exibeCandidatos(0);
                        break;
                 
                 case 7: arq.exibeCandidatos(1);
                        break;
                 
                 case 8: Scanner l = new Scanner(System.in);
                         System.out.print("CPF candidato: ");
                         Candidato c = arq.buscaCPF(l.nextLine());
                         if (c == null)
                             System.out.println("CPF invalido");
                         else   
                             System.out.println(c);
                         break;
                 
                 
                 case 9: Scanner g = new Scanner(System.in);
                         System.out.print("Partido: ");
                         if(!(arq.buscaPartido(g.nextLine())))
                             System.out.println("Partido invalido");
                         break;
                         
                 default: System.out.println("Opcao invalida");
                        break;
             }
             
             
            // arq.criaBin();
            //arq.carregaBin();
            //System.out.println(arq.buscaCPF("91939658004"));
           // arq.exibeCandidatos(0);
            //arq.ordenaCPF(1);
            // arq.criarquivoOrdenado(1);
            //arq.exibeCandidatos(1);
           // arq.buscaPartido("PP");
        } while(opcao !=0);
       
    }
}

    
