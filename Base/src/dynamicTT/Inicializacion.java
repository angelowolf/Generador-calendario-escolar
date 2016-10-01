package dynamicTT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Inicializacion {

    //this class takes all inputs from a file. courseID, courseName, roomID's, subjects and professors associated with course
    //currently hardcoded by taking one course with 6 subjects and 6 teachers
    private ArrayList<Materia> materias = new ArrayList();
    private ArrayList<Docente> docentes = new ArrayList();
    private ArrayList<TimeTable> timetables = new ArrayList();
    private ArrayList<Dictado> dictados = new ArrayList<>();
    private ArrayList<Combinacion> combinaciones = new ArrayList<>();

    //reads input from a file.
    public void readInput() throws IOException {

        ArrayList<Aula> classroom = new ArrayList<>();
        Aula room1 = new Aula("D101", 20, false, "Common");
        classroom.add(room1);
        Aula room2 = new Aula("E101", 20, false, "ComputerScience");
        classroom.add(room2);
        Aula room3 = new Aula("LAB1", 20, true, "ComputerScience");
        classroom.add(room3);
//		Aula room4 = new Aula("LAB2", 20, true);
//		classroom.add(room4);
//		Aula room5 = new Aula("G101", 20, false);
//		classroom.add(room5);
//		Aula room6 = new Aula("H101", 20, false);
//		classroom.add(room6);
//		Aula room6 = new Aula("I101", 60, false);
//		classroom.add(room6);

        docentes.add(new Docente(1, "Shruti", "IR/IRlab/DM"));
        docentes.add(new Docente(2, "Snehal", "P&S"));
        docentes.add(new Docente(3, "Ramrao", "DS"));
        docentes.add(new Docente(4, "Ranjit", "WR"));
        docentes.add(new Docente(5, "Shekhar", "TOC"));
        docentes.add(new Docente(6, "Monica", "SS"));
        docentes.add(new Docente(7, "Ravi", "R"));
        docentes.add(new Docente(8, "Amit", "ML/MLlab"));
        docentes.add(new Docente(9, "Rama", "DAA/UML"));

        createLectures(docentes);

        TimeTable timetb1 = new TimeTable(classroom, dictados);//, professors);
        //timetb1.initialization(classroom, classes);
        //TimeTable timetb2=new TimeTable(classroom, classes);
        //TimeTable timetb3=new TimeTable(classroom, classes);

        int courseid = 1;
        String courseName = "MSc.I.T. Part I";
        System.out.println("reading input.......");
        materias.add(new Materia(1, "IR", 4, false, "ComputerScience"));
        materias.add(new Materia(2, "P&S", 4, false, "ComputerScience"));
        materias.add(new Materia(3, "DS", 4, false, "ComputerScience"));
        materias.add(new Materia(4, "WR", 1, false, "Common"));
        materias.add(new Materia(5, "TOC", 4, false, "ComputerScience"));
        materias.add(new Materia(6, "IRlab", 3, true, "ComputerScience"));
        materias.add(new Materia(7, "JAVA", 3, true, "ComputerScience"));

        System.out.println("new course creation.......");
        Curso course1 = new Curso(courseid, courseName, materias);
        course1.crearCombinacion("IR/P&S/DS/WR/TOC/IRlab/JAVA/", 20);
        course1.crearGruposAlumnos();
        ArrayList<GruposAlumnos> studentGroups = course1.getGruposAlumnos();
        timetb1.agregarGruposAlumnos(studentGroups);
        //combinations.addAll(course1.getCombinations());

        //timetb2.agregarGruposAlumnos(studentGroups);
        ///timetb3.agregarGruposAlumnos(studentGroups);
        materias.clear();

        materias.add(new Materia(8, "DM", 4, false, "ComputerScience"));
        materias.add(new Materia(9, "DAA", 4, false, "ComputerScience"));
        materias.add(new Materia(10, "SS", 1, false, "ComputerScience"));
        materias.add(new Materia(11, "ML", 4, false, "Common"));
        materias.add(new Materia(12, "UML", 4, false, "ComputerScience"));
        materias.add(new Materia(13, "MLlab", 3, true, "ComputerScience"));
        materias.add(new Materia(14, "R", 3, true, "ComputerScience"));

        Curso course2 = new Curso(2, "MSc.I.T. Part II", materias);
        course2.crearCombinacion("DM/DAA/SS/ML/UML/MLlab/R/", 20);
        course2.crearGruposAlumnos();
        studentGroups = course2.getGruposAlumnos();
        timetb1.agregarGruposAlumnos(studentGroups);
        //combinations.addAll(course2.getCombinations());
        //timetb2.agregarGruposAlumnos(studentGroups);
        //timetb3.agregarGruposAlumnos(studentGroups);

        System.out.println("Setting tt.......");

        System.out.println("adding tt.......");
        timetb1.initializeTimeTable();
        //timetb2.initializeTimeTable();
        //timetb3.initializeTimeTable();
        timetables.add(timetb1);
        //timetable.add(timetb2);
        //timetable.add(timetb3);

        System.out.println("populating.......");

        //display();
        populateTimeTable(timetb1);
        GeneticAlgorithm ge = new GeneticAlgorithm();

        //ge.fitness(timetb1);
//		timetb1.createTimeTableGroups(combinations);
        ge.populationAccepter(timetables);
//		//ge.fitness(timetb2);

        //ge.fitness(timetb3);
        //populateTimeTable();
    }

    public void populateTimeTable(TimeTable timetb1) {
        int i = 0;
        System.out.println("populating started.......");
        while (i < 3) {
            TimeTable tempTimetable = timetb1;
            ArrayList<Aula> allrooms = tempTimetable.getAulas();
            Iterator<Aula> allroomsIterator = allrooms.iterator();
            while (allroomsIterator.hasNext()) {
                Aula room = allroomsIterator.next();
                ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
                Collections.shuffle(weekdays);
                if (!room.esLaboratorio()) {
                    Iterator<Dia> daysIterator = weekdays.iterator();
                    while (daysIterator.hasNext()) {
                        Dia day = daysIterator.next();
                        Collections.shuffle(day.getTimeSlot());
                    }
                }
            }
            timetables.add(tempTimetable);
            i++;
        }
        System.out.println("populating done.......");
        System.out.println("display called.......");
        display();
    }

    private void createLectures(ArrayList<Docente> professors) {
        // TODO Auto-generated method stub

        java.util.Iterator<Docente> professorIterator = professors.iterator();
        while (professorIterator.hasNext()) {
            Docente professor = professorIterator.next();
            ArrayList<String> subjectsTaught = professor.getMateriasQueDicta();
            Iterator<String> subjectIterator = subjectsTaught.iterator();
            while (subjectIterator.hasNext()) {
                String subject = subjectIterator.next();
                dictados.add(new Dictado(professor, subject));
            }
        }
    }

    //creates another 3 timetable objects for population by taking first yimetable and shuffling it.
//	public void populateTimeTable(){
//		int i=0;
//		System.out.println("populating started.......");
//		while(i<6){
//			TimeTable tempTimetable = timetbl;
//			ArrayList<ClassRoom> allrooms = tempTimetable.getAulas();
//			Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
//			while(allroomsIterator.hasNext()){
//				Aula room = allroomsIterator.next();
//				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
//				Iterator<Day> daysIterator=weekdays.iterator();
//				while(daysIterator.hasNext()){
//					Dia day = daysIterator.next();
//					Collections.shuffle(day.getTimeSlot());
//				}
//			}
//			timetable.add(tempTimetable);
//			i++;
//		}
//		System.out.println("populating done.......");
//		System.out.println("display called.......");
//		display();
//		
//		GeneticAlgorithm.populationAccepter(timetable);
//	}
    //displays all timetables
    private void display() {
        // TODO Auto-generated method stub
        int i = 1;
        System.out.println("displaying all tt's.......");
        Iterator<TimeTable> timetableIterator = timetables.iterator();
        while (timetableIterator.hasNext()) {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++\nTime Table no. " + i);
            TimeTable currentTimetable = timetableIterator.next();
            System.out.println("Score : " + currentTimetable.getFittness());
            ArrayList<Aula> allrooms = currentTimetable.getAulas();
            Iterator<Aula> allroomsIterator = allrooms.iterator();
            while (allroomsIterator.hasNext()) {
                Aula room = allroomsIterator.next();
                System.out.println("Room: " + room.getID());
                ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
                Iterator<Dia> daysIterator = weekdays.iterator();
                while (daysIterator.hasNext()) {
                    Dia day = daysIterator.next();
                    ArrayList<TimeSlot> timeslots = day.getTimeSlot();
                    Iterator<TimeSlot> timeslotIterator = timeslots.iterator();
                    //System.out.print(""+day.getName()+": ");
                    while (timeslotIterator.hasNext()) {
                        TimeSlot lecture = (TimeSlot) timeslotIterator.next();
                        if (lecture.getDictado() != null) {
                            //System.out.print(" (Subject: "+lecture.getDictado().getSubject()+" --> Docente: "+lecture.getDictado().getProfessor().getProfessorName()+" GrpName: "+lecture.getDictado().getStudentGroup().getName()+")");
                            System.out.print("(" + lecture.getDictado().getMateria() + "#" + lecture.getDictado().getDocente().getNombre() + "#" + lecture.getDictado().getGrupoEstudiantes().getNombre().split("/")[0] + ")");
                        } else {
                            System.out.print("   free   ");
                        }
                    }
                    System.out.print("\n");
                }
                System.out.print("\n\n");
            }
            i++;
        }
    }
}
