package dynamicTT;

public class Aula {
	
	//represents each classroom
	
private String id;
private int tamanio;
private boolean eslaboratorio;
private Semana semana;
private String departamento;

public Aula(String id, int i, boolean islab, String dept){
	this.id=id;
	this.setSemana(new Semana());
	this.setTamanio(i);
	this.eslaboratorio=islab;
	this.departamento=dept;
}


public boolean esLaboratorio() {
	return eslaboratorio;
}
public void setLaboratorio(boolean laboratory) {
	this.eslaboratorio = laboratory;
}

public String getID() {
	return id;
}
public void setID(String roomNo) {
	this.id = roomNo;
}

public Semana getSemana() {
	return semana;
}

public void setSemana(Semana semana) {
	this.semana = semana;
}

public int getTamanio() {
	return tamanio;
}

public void setTamanio(int tamanio) {
	this.tamanio = tamanio;
}


public String getDepartamento() {
	return departamento;
}


public void setDepartamento(String departamento) {
	this.departamento = departamento;
}
}
