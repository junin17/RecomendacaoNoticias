
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Classe para Armazenar as informações de um texto
 * @author Junin
 */
public class Texto {
    private String nome; //Nome do Texto
    private String classe; //Classe do Texto
    private HashMap<String, Integer> frequencia; // Hash de Frequência
    private HashMap<String, Integer> distancias; // Hash que guarda a distância para os demais texto
    private LinkedList<Texto> trains; // Lista com os textos trains.

    public Texto() {
        frequencia=new HashMap();
        distancias=new HashMap();
        trains=new LinkedList();
    }

    public Texto(String nome, String classe) {
        this.nome = nome;
        this.classe = classe;
        trains=new LinkedList();
        frequencia=new HashMap();
        distancias=new HashMap();
    }

    public Texto(String nome, String classe, HashMap<String, Integer> frequencia, HashMap<String, Integer> distancias, LinkedList<Texto> trains) {
        this.nome = nome;
        this.classe = classe;
        this.frequencia = frequencia;
        this.distancias=distancias;
        this.trains=trains;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public HashMap<String, Integer> getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(HashMap<String, Integer> frequencia) {
        this.frequencia = frequencia;
    }

    public HashMap<String, Integer> getDistancias() {
        return distancias;
    }

    public void setDistancias(HashMap<String, Integer> distancias) {
        this.distancias = distancias;
    }

    public LinkedList<Texto> getTrains() {
        return trains;
    }

    public void setTrains(LinkedList<Texto> trains) {
        this.trains = trains;
    }

    public void ordenaListaTrains(){
        for (int i=0; i< trains.size() -1; i++){
            
            for (int j=i+1; j<trains.size();j++){
                if (distancias.get(trains.get(i).getNome()) > distancias.get(trains.get(j).getNome())){
                    Texto troca=trains.get(i);
                    trains.set(i, trains.get(j));
                    trains.set(j, troca);
                }
                        
            }
            
        }
    }
    
    public void calculaClasse(){
        
        Double pesoMaximo=distancias.get(trains.getLast().getNome()).doubleValue();
        HashMap<String, Double> classes=new HashMap();
        
        for (int i=0;i<trains.size();i++){
            String classe=trains.get(i).getClasse();
            if (!classes.containsKey(classe)){
                int peso=distancias.get(trains.get(i).getNome());
                classes.put(classe, pesoMaximo/peso);
                for (int j=i+1;j<trains.size();j++){
                    if (trains.get(j).getClasse().equals(classe)){
                        Double qtd=classes.get(classe);
                        peso=distancias.get(trains.get(j).getNome());
                        qtd=qtd+(pesoMaximo/peso);
                        classes.put(classe, qtd);
                    }
                }
            }
        }
        
        
        
        String menor=trains.get(0).getClasse();
        
        for (String classe : classes.keySet()){
            if (classes.get(menor) > classes.get(classe)){
                menor=classe;
            }
        }
        
        this.classe=classe;
    }
}
