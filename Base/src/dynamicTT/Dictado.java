package dynamicTT;

public class Dictado {
	private Docente docente;
	private String materia;
	private GruposAlumnos grupoEstudiantes;
	
	//represents one lecture having docente and materia
	
	Dictado(Docente prof, String sub){
		this.docente=prof;
		this.materia=sub;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public GruposAlumnos getGrupoEstudiantes() {
		return grupoEstudiantes;
	}

	public void setGrupoEstudiantes(GruposAlumnos grupoEstudiantes) {
		this.grupoEstudiantes = grupoEstudiantes;
	}
}
