package dynamicTT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class TimeTable {
	private ArrayList<Aula> aulas = new ArrayList<Aula>();
	private int fittness;
	private ArrayList<Dictado> clases=new ArrayList<>();
	private ArrayList<GruposAlumnos> gruposAlumnos=new ArrayList<>(); 
	private ArrayList<Aula> aulasPracticas = new ArrayList<Aula>();
	private ArrayList<Aula> aulasTeoricas = new ArrayList<Aula>();
	private ArrayList<GruposAlumnos> gruposAlumnosTeoricos=new ArrayList<>(); 
	private ArrayList<GruposAlumnos> gruposAlumnosPracticos=new ArrayList<>(); 
	private HashMap<Combinacion, Semana> personalTimeTable= new HashMap<Combinacion, Semana>();
	//private ArrayList<Professor> professors=new ArrayList<>();
	//adds more rooms to timetable

	public TimeTable(ArrayList<Aula> classroom, ArrayList<Dictado> lectures){//, ArrayList<Professor> professors){
		this.aulas=classroom;
		this.clases=lectures;
		this.fittness=999;
//		this.professors=professors;
	}
	
//	public void initialization(ArrayList<ClassRoom> classroom, ArrayList<Lecture> lectures){
//		this.rooms=classroom;
//		this.classes=lectures;
//		this.fittness=999;
//	}
	
	public int getFittness() {
		return fittness;
	}
	
	public void setFittness(int fittness) {
		this.fittness = fittness;
	}

	public void agregarGruposAlumnos(ArrayList<GruposAlumnos> studentgrps) {
		// TODO Auto-generated method stub
		gruposAlumnos.addAll(studentgrps);
	}
	
	public void initializeTimeTable(){
		for (Iterator<Aula> roomsIterator = aulas.iterator(); roomsIterator.hasNext();) {
			Aula room = roomsIterator.next();
			if(room.esLaboratorio()){
				aulasPracticas.add(room);
			}
			else{
				aulasTeoricas.add(room);
			}
		}
		for (Iterator<GruposAlumnos> studentGroupIterator = gruposAlumnos.iterator(); studentGroupIterator.hasNext();) {
			GruposAlumnos studentGroup = studentGroupIterator.next();
			if(studentGroup.isPractica()){
				gruposAlumnosPracticos.add(studentGroup);
			}
			else{
				gruposAlumnosTeoricos.add(studentGroup);
			}
		}
		aulas.clear();
		//studentGroups.clear();
		setTimeTable(gruposAlumnosPracticos, aulasPracticas, "practical");
		setTimeTable(gruposAlumnosTeoricos, aulasTeoricas, "theory");
		aulas.addAll(aulasPracticas);
		aulas.addAll(aulasTeoricas);
		//studentGroups.addAll(gruposAlumnosPracticos);
		//studentGroups.addAll(gruposAlumnosTeoricos);
	}
	
	public void setTimeTable(ArrayList<GruposAlumnos> studentGroups2, ArrayList<Aula> rooms2, String string) {
		// TODO Auto-generated method stub
		Collections.shuffle(studentGroups2);
		Stack<Dictado> lecturesStack=new Stack<Dictado>();
		for (Iterator<GruposAlumnos> sdtGrpIterator = studentGroups2.iterator(); sdtGrpIterator.hasNext();) {			
			GruposAlumnos studentGrp = sdtGrpIterator.next();
			String subject = studentGrp.getNombreMateria();
			int noOfLectures = studentGrp.getDictadosPorSemana();
			for(int i=0; i<noOfLectures; i++){
				Collections.shuffle(clases);
				Iterator<Dictado> classIterator = clases.iterator();
				while(classIterator.hasNext()){
					Dictado lecture = classIterator.next();
					if(lecture.getMateria().equalsIgnoreCase(subject)){
						Dictado mainLecture=new Dictado(lecture.getDocente(), lecture.getMateria());
						mainLecture.setGrupoEstudiantes(studentGrp);
						lecturesStack.push(mainLecture);
						break;
					}
				}
			}
		}
		while(!(lecturesStack.empty())){
				Collections.shuffle(lecturesStack);
				Dictado lecture2 = lecturesStack.pop();
				if(string.equalsIgnoreCase("theory")){
					ponerMateriaTeorica(lecture2, rooms2);
				}
				if(string.equalsIgnoreCase("practical")){
					ponerMateriaPractica(lecture2, rooms2);
				}
		}	
	}	
	
		
	
	private void ponerMateriaPractica(Dictado lecture2, ArrayList<Aula> rooms2) {
		// TODO Auto-generated method stub
		int size = lecture2.getGrupoEstudiantes().getTamanio();
		String dept=lecture2.getGrupoEstudiantes().getDepartmento();
		int i=0;
		boolean invalid=true;
		Aula room = null;
		Collections.shuffle(rooms2);
		while(invalid){
		room=getMejorAula(size, rooms2);
		if(room.getDepartamento().equalsIgnoreCase(dept)){
			invalid=false;
			Collections.shuffle(rooms2);
			}
		else{
			Collections.shuffle(rooms2);
			}
		}
		ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
		Iterator<Dia> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext() && i<3){
			Dia day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext() && i<3){
				TimeSlot lecture3 = timeslotIterator.next();
				if(lecture3.getDictado()==null){
				lecture3.setDictado(lecture2);
				i++;
				}
			}
		}		
	}



	private void ponerMateriaTeorica(Dictado lecture, ArrayList<Aula> rooms2) {
		// TODO Auto-generated method stub
		int size = lecture.getGrupoEstudiantes().getTamanio();
		String dept=lecture.getGrupoEstudiantes().getDepartmento();
		boolean invalid=true;
		Aula room = null;
		Collections.shuffle(rooms2);
		while(invalid){
			room=getMejorAula(size, rooms2);
			if(room.getDepartamento().equalsIgnoreCase(dept)){
				invalid=false;
				Collections.shuffle(rooms2);
				}
			else{
				Collections.shuffle(rooms2);
				}
			}
		ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
		Iterator<Dia> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext()){
			Dia day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext()){
				TimeSlot lecture2 = timeslotIterator.next();
				if(lecture2.getDictado()==null){
				lecture2.setDictado(lecture);
				return;				
				}
			}
		}		
	}



	private boolean chequearAulaOcupada(Aula tempRoom, ArrayList<Aula> rooms2) {
		// TODO Auto-generated method stub
		for (Iterator<Aula> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			Aula room = roomsIterator.next();
			if(room.equals(tempRoom)){
			ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
			Iterator<Dia> daysIterator=weekdays.iterator();
			while(daysIterator.hasNext()){
				Dia day = daysIterator.next();
				ArrayList<TimeSlot> timeslots = day.getTimeSlot();
				Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
				while(timeslotIterator.hasNext()){
					TimeSlot lecture = timeslotIterator.next();
					if(lecture.getDictado()==null){
						return false;
					}
				}
			}
			return true;
			}		
		}
		return false;
	}



	private Aula getMejorAula(int size, ArrayList<Aula> rooms2) {
		// TODO Auto-generated method stub
		int delta = 1000;
		Aula room = null;
		for (Iterator<Aula> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			Aula tempRoom = roomsIterator.next();
			if(!chequearAulaOcupada(tempRoom, rooms2)){
		        int tmp = Math.abs(size - tempRoom.getTamanio());
		        if(tmp < delta){
		            delta = tmp;
		            room = tempRoom;
		    }
			}
		}
		return room;
	}

//	public void createTimeTableGroups(ArrayList<Combination> combinations2){
////		ArrayList<Combination> combinations=new ArrayList<>();
////		
////			for(Iterator<Combination> combItr = combinations2.iterator(); combItr.hasNext();){
////				 Combinacion comb = combItr.next();
////				if(!combinations.contains(comb)){
////					combinations.add(comb);
////				}
////			}
//		
//		
////		for(Iterator<Combination> combIterator = combinations2.iterator(); combIterator.hasNext();){
////			Combinacion combtn = combIterator.next();
////			personalTimeTable.put(combtn, new Semana());
////		}
//		
//		for(Iterator<Combination> combIterator = combinations2.iterator(); combIterator.hasNext();){
//			Combinacion combtn = combIterator.next();
//			for (Iterator<ClassRoom> roomsIterator = aulasTeoricas.iterator(); roomsIterator.hasNext();){
//				Aula room=roomsIterator.next();
//				Iterator<Day> daysIterator = room.getWeek().getWeekDays().iterator();
//				while(daysIterator.hasNext()){
//					Dia day = daysIterator.next();
//					ArrayList<TimeSlot> timeslots = day.getTimeSlot();
//					Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
//					while(timeslotIterator.hasNext()){
//						TimeSlot lecture = timeslotIterator.next();
//						if(lecture.getDictado()==null){
//							System.out.print(" free ");
//						}
//						else if(lecture.getDictado().getStudentGroup().getCombinaciones().contains(combtn)){
//							System.out.print("###Room="+room.getID()/*+" Dia="+day.getName()+" Time="+lecture.getSlotTime()*/+" Docente="+lecture.getDictado().getProfessor()+" Subject="+lecture.getDictado().getSubject());
//						}
//						else{
//							System.out.print(" free ");
//						}
//					}
//					System.out.print("\n");
//				}
//			}
//		}
//	}
	
//	private void putInPersonalTimeTable(Combinacion combtn, String roomNo, String name, TimeSlot lecture) {
//		// TODO Auto-generated method stub
//		Semana week = personalTimeTable.get(combtn);
//		Iterator<Day> daysIterator=week.getWeekDays().iterator();
//		while(daysIterator.hasNext()){
//			Dia day = daysIterator.next();
//			if(day.getName().equalsIgnoreCase(name)){
//				Iterator<TimeSlot> timeslotIterator=day.getTimeSlot().iterator();
//				while(timeslotIterator.hasNext()){
//					TimeSlot lecture2 = (TimeSlot) timeslotIterator.next();
//					if(lecture2)
//				}
//			}
//		}
//	}
		

	//creates random assignment of lecture using lecture objects, subjects and number of lectures per week to a room
	
//	private Aula randomTimetable(Aula room, ArrayList<Subject> subjectsTaught, ArrayList<Lecture> lectureList) {
//		Iterator subIterator=subjectsTaught.iterator();
//		Stack<Lecture> lecturesStack=new Stack();
//		while(subIterator.hasNext()){
//			Subject subject = (Subject) subIterator.next();
//			int noOfLecturesPerWeek = subject.getNumberOfLecturesPerWeek();
//			for(int i=0; i<noOfLecturesPerWeek; i++){
//				Collections.shuffle(lectureList);
//				Iterator<Lecture> classIterator = lectureList.iterator();
//				while(classIterator.hasNext()){
//					Dictado getDictado = classIterator.next();
//					if(getDictado.getSubject().equalsIgnoreCase(subject.getSubjectName())){
//						lecturesStack.push(getDictado);
//						break;
//					}
//				}
//			}
//		}
//		
//		Collections.shuffle(lecturesStack);
//		ArrayList<Day> weekdays = room.getWeek().getWeekDays();
//		Iterator<Day> daysIterator=weekdays.iterator();
//		while(daysIterator.hasNext()){
//			Dia day = daysIterator.next();
//			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
//			Iterator timeslotIterator= timeslots.iterator();
//			while(timeslotIterator.hasNext() && !(lecturesStack.isEmpty())){
//				TimeSlot lecture = (TimeSlot) timeslotIterator.next();
//				lecture.setDictado(lecturesStack.pop());
//				Collections.shuffle(lecturesStack);
//			}
//		}		
//		return room;
//	}
		
	

	public ArrayList<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(ArrayList<Aula> room) {
		this.aulas = room;
	}



	public ArrayList<Aula> getAulasPracticas() {
		return aulasPracticas;
	}



	public void setAulasPracticas(ArrayList<Aula> aulasPracticas) {
		this.aulasPracticas = aulasPracticas;
	}



	public ArrayList<Aula> getAulasTeoricas() {
		return aulasTeoricas;
	}



	public void setAulasTeoricas(ArrayList<Aula> aulasTeoricas) {
		aulasTeoricas = aulasTeoricas;
	}



	public ArrayList<GruposAlumnos> getGruposAlumnosTeoricos() {
		return gruposAlumnosTeoricos;
	}



	public void setGruposAlumnosTeoricos(ArrayList<GruposAlumnos> gruposAlumnosTeoricos) {
		this.gruposAlumnosTeoricos = gruposAlumnosTeoricos;
	}



	public ArrayList<GruposAlumnos> getGruposAlumnosPracticos() {
		return gruposAlumnosPracticos;
	}



	public void setGruposAlumnosPracticos(ArrayList<GruposAlumnos> gruposAlumnosPracticos) {
		this.gruposAlumnosPracticos = gruposAlumnosPracticos;
	}
}
