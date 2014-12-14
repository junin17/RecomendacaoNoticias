
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Junin
 */
public class main {
    private static HashMap <Texto, HashMap<String, Integer>> indiceGlobal=new HashMap();
    
    public static void main (String [] args) throws FileNotFoundException, IOException{
        
        /*
        Arquivos de leitura.
        */
        Scanner train=new Scanner(new FileReader("data/02-train.txt"));
        
        Scanner test=new Scanner(new FileReader("data/02-test.txt"));
        int N,K;
        K=Integer.parseInt(args[0]);
        N=Integer.parseInt(args[1]);
        
        while(train.hasNext()){
            String nomeArq=train.next();
            String classe=train.next();
            Texto texto=leArquivo(nomeArq, classe);
            indiceGlobal.put(texto, texto.getFrequencia());
            
            
            
        }
        
        Set<Texto> trains=indiceGlobal.keySet();
        
        while (test.hasNext()){
            String nomeArq=test.next();
            String classe=test.next();
            Texto texto=leArquivo(nomeArq, classe);
            
            System.out.println(nomeArq+" ("+classe+")");
            System.out.println("----------------------");
            System.out.println("TEXTOS MAIS PARECIDOS");
            System.out.println("----------------------");
            
            for (Texto t : trains){
                Integer distancia = distancia(texto.getFrequencia(),t.getFrequencia());
                texto.getTrains().add(t);
                texto.getDistancias().put(t.getNome(), distancia);
                t.getDistancias().put(texto.getNome(), distancia);
            }
            texto.ordenaListaTrains();
            
            for (int i=0;i<K;i++){
                String nomeTexto=texto.getTrains().get(i).getNome();
                System.out.println(nomeTexto+ " "+texto.getDistancias().get(nomeTexto));
            }
            
            texto.calculaClasse();
            List<String> mesmaClasse=new ArrayList();
            
            for (Texto t : trains){
                if (t.getClasse().equals(texto.getClasse())){
                    mesmaClasse.add(t.getNome());
                }
            }
            
            
            
            System.out.println("---------------------------------------------------------");
            System.out.println("TEXTOS ALEATORIOS DA MESMA CLASSE ("+texto.getClasse()+")");
            System.out.println("---------------------------------------------------------");
            sorteiaTextos(mesmaClasse, N);
            System.out.println("");
            System.out.println("");
           
            
        }
        
        
        
        
        
    }
    
    /**
     * Funcão que le um arquivo proveniente do train.txt
     * @param nomeArq nome do arquivo 
     * @param classe classe pertencente
     * @throws FileNotFoundException 
     * @return retorna um objeto da classe texto.
     */
    
    public static Texto leArquivo(String nomeArq, String classe) throws FileNotFoundException{
        
        Scanner arquivo=new Scanner(new FileReader(nomeArq));
        
        Texto texto=new Texto(nomeArq,classe);
        while (arquivo.hasNext()){
            
            String palavra=arquivo.next();
            // se ja existe a palavra na tabela hash, incrimenta a frequencia.
            if (texto.getFrequencia().containsKey(palavra)){
                int qtd=texto.getFrequencia().get(palavra);
                qtd++;
                texto.getFrequencia().put(palavra, qtd);
            }
            else{ //senão, coloque a palavra no hash com frequência 1.
                texto.getFrequencia().put(palavra, 1);
            }
            
        }
        
        
        
        return texto;
        
        
        
        
    }
    
    /**
     * Função que calcula a distância entre 2 textos.
     * @param textoA
     * @param textoB
     * @return 
     */
    public static Integer distancia(HashMap<String, Integer> textoA, 
            HashMap<String, Integer> textoB){
        
        Double dist=0.0;
        
        Set <String> chavesA=textoA.keySet();
        Set <String> chavesB=textoB.keySet();
        
        for (String chave : chavesA){
            if (textoB.containsKey(chave)){
                dist+= Math.pow((textoB.get(chave) - textoA.get(chave)), 2);
            }
            else{
                dist+= Math.pow(textoA.get(chave), 2);
            }
        }
        
        for (String chave : chavesB){
            if (!textoA.containsKey(chave)){
                dist+= Math.pow(textoB.get(chave), 2);
            }
        }
        
        return dist.intValue();
    }
    
    /**
     * Metodo responsável para sortear os textos de mesma classe a serem exibidos.
     * @param textos
     * @param N 
     */
    
    public static void sorteiaTextos(List<String> textos, int N){
        Random aleatorio=new Random();
        List<Integer> jaSorteados=new ArrayList();
        for (int i=0;i<N;i++){
            int sorteado=aleatorio.nextInt(textos.size());
            if (!jaSorteados.contains(sorteado)){
                System.out.println(textos.get(sorteado));
            }
            else{
                i--;
            }
        }
        
    }
}
