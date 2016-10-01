package dynamicTT;

public class Materia {
	
	private int id;
	private String nombre;
	private int dictadoPorSemana;
	private boolean eslaboratorio;
	private String departamento;
	
	Materia(int id, String name, int lectures, boolean lab, String dept){
		this.id=id;
		this.nombre=name;
		this.dictadoPorSemana=lectures;
		this.eslaboratorio=lab;
		this.departamento=dept;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDictadoPorSemana() {
		return dictadoPorSemana;
	}
	public void setDictadoPorSemana(int dictadoPorSemana) {
		this.dictadoPorSemana = dictadoPorSemana;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean isEslaboratorio() {
		return eslaboratorio;
	}

	public void setEslaboratorio(boolean eslaboratorio) {
		this.eslaboratorio = eslaboratorio;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
}
