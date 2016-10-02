package dynamicTT;

import java.util.ArrayList;

public class Combinacion {

    private int tamanioDeClase;
    private ArrayList<String> combinacionMaterias = new ArrayList<>();

    public Combinacion(String subjects, int size) {
        // TODO Auto-generated constructor stub
        setTamanioDeClase(size);
        String[] subj = subjects.split("/");
        for (int i = 0; i < subj.length; i++) {
            combinacionMaterias.add(subj[i]);
        }
    }

    public int getTamanioDeClase() {
        return tamanioDeClase;
    }

    public void setTamanioDeClase(int tamanioDeClase) {
        this.tamanioDeClase = tamanioDeClase;
    }

    public ArrayList<String> getMaterias() {
        return combinacionMaterias;
    }

    public void setMaterias(ArrayList<String> subjects) {
        this.combinacionMaterias = subjects;
    }

    @Override
    public String toString() {
        return "Combinacion{" + "tamanioDeClase=" + tamanioDeClase + ", combinacionMaterias=" + combinacionMaterias + '}';
    }

}
