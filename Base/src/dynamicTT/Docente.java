package dynamicTT;

import java.util.ArrayList;

public class Docente {
	private int id;
	private String nombre;
	private ArrayList <String> materiaQueDicta = new ArrayList();
	
	Docente(int id, String name, String subj){
		this.id=id;
		this.nombre=name;
		String[] subjectNames=subj.split("/");
		for(int i=0; i<subjectNames.length; i++){
			this.materiaQueDicta.add(subjectNames[i]);
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getMateriasQueDicta() {
		return materiaQueDicta;
	}

	public void setMateriasQueDicta(ArrayList<String> subjectTaught) {
		this.materiaQueDicta = subjectTaught;
	}
	
	
}
