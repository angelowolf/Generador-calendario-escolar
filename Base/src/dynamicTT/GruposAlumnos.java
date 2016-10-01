package dynamicTT;

import java.util.ArrayList;

public class GruposAlumnos {
	
	private String nombre;
	private int dictadosPorSemana;
	private ArrayList<Combinacion> combinaciones=new ArrayList<>();
	private int tamanio;
	private String nombreMateria;
	private boolean esPractica;
	private String departmento;

	public GruposAlumnos(String string, int numberOfLectures, int i, ArrayList<Combinacion> combs, String subject, boolean lab, String dept) {
		// TODO Auto-generated constructor stub
		this.setNombre(string);
		this.setDictadosPorSemana(numberOfLectures);
		this.setCombinaciones(combs);
		this.setTamanio(i);
		this.nombreMateria=subject;
		this.esPractica=lab;
		this.setDepartmento(dept);
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public ArrayList getCombinaciones() {
		return combinaciones;
	}

	public void setCombinaciones(ArrayList combination) {
		this.combinaciones = combination;
	}

	public int getDictadosPorSemana() {
		return dictadosPorSemana;
	}

	public void setDictadosPorSemana(int dictadosPorSemana) {
		this.dictadosPorSemana = dictadosPorSemana;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public boolean isPractica() {
		return esPractica;
	}

	public void setPractica(boolean isPractical) {
		this.esPractica = isPractical;
	}

	public String getDepartmento() {
		return departmento;
	}

	public void setDepartmento(String departmento) {
		this.departmento = departmento;
	}

}
