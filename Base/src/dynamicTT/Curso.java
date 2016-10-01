package dynamicTT;

import java.util.ArrayList;
import java.util.Iterator;

public class Curso {
    
	//represents each course
	
	private int id;
	private String nombre;
	private ArrayList<Materia> materiasIncluidas= new ArrayList<Materia>();
	private ArrayList<Combinacion> combinaciones=new ArrayList<Combinacion>();
	
	public ArrayList<Combinacion> getCombinaciones() {
		return combinaciones;
	}

	public void setCombinaciones(ArrayList<Combinacion> combinaciones) {
		this.combinaciones = combinaciones;
	}
	private ArrayList<GruposAlumnos> gruposAlumnos=new ArrayList<GruposAlumnos>();
	
	public ArrayList<GruposAlumnos> getGruposAlumnos() {
		return gruposAlumnos;
	}

	public void setGruposAlumnos(ArrayList<GruposAlumnos> gruposAlumnos) {
		this.gruposAlumnos = gruposAlumnos;
	}

	Curso(int id, String name, ArrayList<Materia> subjects){
		System.out.println("creando nuevo curso.......");
		this.id=id;
		this.nombre=name;
		this.materiasIncluidas=subjects;
	}
	
	public void crearGruposAlumnos(){
		int size=0;
		ArrayList <Combinacion>combs=new ArrayList<Combinacion>();
		Iterator<Materia> subjectIterator=materiasIncluidas.iterator();
		while(subjectIterator.hasNext()){
			Materia subject=subjectIterator.next();
			Iterator combIterator =combinaciones.iterator();
			while(combIterator.hasNext()){
				Combinacion combination = (Combinacion) combIterator.next();
				ArrayList<String> subjects = combination.getMaterias();
				Iterator<String> subjectItr = subjects.iterator();
				while(subjectItr.hasNext()){
					if(subjectItr.next().equalsIgnoreCase(subject.getNombre())){
						size=size+combination.getTamanioDeClase();
						if(!combs.contains(combination.getMaterias())){
						combs.add(combination);
						}
					}
				}
			}
			GruposAlumnos studentGroup=new GruposAlumnos(this.nombre+"/"+subject.getNombre(), subject.getDictadoPorSemana(), size, combs, subject.getNombre(), subject.isEslaboratorio(), subject.getDepartamento());
		    gruposAlumnos.add(studentGroup);
		    size=0;
		}
	}
	
	//creates all possible professor x subject he teaches combinaciones and saves as lecture objects
	
	public void crearCombinacion(String subjects, int size){
		Combinacion combination=new Combinacion(subjects, size);
		combinaciones.add(combination);
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
//	public ArrayList<Professor> getProfessorsTeaching() {
//		return professorsTeaching;
//	}
//	public void setProfessorsTeaching(ArrayList<Professor> professorsTeaching) {
//		this.professorsTeaching = professorsTeaching;
//	}
	public ArrayList<Materia> getMateriasDadas() {
		return materiasIncluidas;
	}
	public void setMateriasDadas(ArrayList<Materia> subjectsTaught) {
		this.materiasIncluidas = subjectsTaught;
	}
	
}
