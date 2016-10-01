package dynamicTT;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.*;

public class GeneticAlgorithm {

    //private int numberOfRooms;
    //private ArrayList <String> subjects=new ArrayList();
    //private ArrayList <ClassRoom> rooms=new ArrayList();
    //static HashMap<Integer,TimeTable> ttscore=new HashMap();
    private static TimeTable GlobalBestTimetable;
    private static int min = 1000;
    private static ArrayList<String> nombresDiasSemana = new ArrayList<>();
    private static ArrayList<String> horariosDictados = new ArrayList<>();

    public static void receptorPoblacion(ArrayList<TimeTable> timeTableCollection) throws IOException {
        // randomly got population from the initialization class
        Iterator<TimeTable> timetableIterator = timeTableCollection.iterator();
        for (Iterator<TimeTable> iterator = timeTableCollection.iterator(); iterator.hasNext();) {
            TimeTable tt = iterator.next();
            fitness(tt);
        }
        crearSemana();
        crearHorarioDictado();
        seleccion(timeTableCollection);
    }

    private static void crearSemana() {
        String[] weekDaysName = new DateFormatSymbols().getWeekdays();
        for (int i = 1; i < weekDaysName.length; i++) {
            System.out.println("día de semana = " + weekDaysName[i]);
            //if(!(weekDaysName[i].equalsIgnoreCase("Sunday"))){
            if (!(i == Calendar.SUNDAY)) {
                nombresDiasSemana.add(weekDaysName[i]);
            }
        }
    }

    private static void crearHorarioDictado() {
        for (int i = 9; i < 16; i++) {
            //if(i!=12){
            horariosDictados.add(i + ":00" + " A " + (i + 1) + ":00");
            //}			
        }
    }

    public static void seleccion(ArrayList<TimeTable> timetables) throws IOException {
        int iterations = 50;
        int i = 1;
        ArrayList<TimeTable> mutants = new ArrayList<>();
        Iterator<TimeTable> ttItr = timetables.iterator();
        while (ttItr.hasNext()) {
            fitness(ttItr.next());
        }
        while (iterations != 0) {
            //Iterator<Integer> scoreIterator=ttscore.keySet().iterator();
            Iterator<TimeTable> timetableIterator = timetables.iterator();
            //Iterator<TimeTable> tempIterator=timetableIterator;		
            //min= timetableIterator.next().getFittness();

            while (timetableIterator.hasNext()) {
                TimeTable tt = timetableIterator.next();
                int score = tt.getFittness();
                if (score < min) {
                    System.out.println("Se encuentra un nuevo mejor global");
                    min = score;
                    GlobalBestTimetable = new TimeTable(tt);
//                    GlobalBestTimetable = tt;
                    display();
                    writeToExcelFile();
                }
            }

            if (min == 0) {
                //ArrayList<TimeTable> timeTable=new ArrayList();
                //timeTable.add(GlobalBestTimetable);
                display();
                System.exit(0);
            } else {
                System.out.println("Iteración :" + i);
                i++;
                iterations--;
                //timetables.remove(GlobalBestTimetable);			
                for (Iterator<TimeTable> iterator = timetables.iterator(); iterator.hasNext();) {
                    TimeTable timetable1 = iterator.next();
                    //TimeTable timetable2 = (TimeTable) iterator.next();				
//				SingleTimeTable timetable1=ttscore.get(key1);
//				SingleTimeTable timetable2=ttscore.get(key2);				
                    TimeTable childTimetable = crossOver(timetable1);
//				if(childTimetable.getFittness()< GlobalBestTimetable.getFittness()){
//					GlobalBestTimetable=childTimetable;
//				}	
//				for (int j = 0; j < arr.length; j++) {
//					TimeTable singleTimeTable = arr[j];					
                    TimeTable mutant = Mutation(childTimetable);
//					if(childTimetable.getFittness()< GlobalBestTimetable.getFittness()){
//						GlobalBestTimetable=childTimetable;
//					}
                    mutants.add(mutant);
                    //}		
                }

                timetables.clear();
                for (int j = 0; j < mutants.size(); j++) {
                    fitness(mutants.get(j));
                    timetables.add(mutants.get(j));
                }
                mutants.clear();
            }
        }
//        displayMejor();
        display();
    }

    public static void fitness(TimeTable timetable) {
        ArrayList<Aula> rooms = timetable.getAulas();
        Iterator<Aula> roomIterator1 = rooms.iterator();
        while (roomIterator1.hasNext()) {
            int score = 0;
            Aula room1 = roomIterator1.next();
            Iterator<Aula> roomIterator2 = rooms.iterator();
            while (roomIterator2.hasNext()) {
                Aula room2 = roomIterator2.next();
                if (room2 != room1) {
                    ArrayList<Dia> weekdays1 = room1.getSemana().getDiasSemana();
                    ArrayList<Dia> weekdays2 = room2.getSemana().getDiasSemana();
                    Iterator<Dia> daysIterator1 = weekdays1.iterator();
                    Iterator<Dia> daysIterator2 = weekdays2.iterator();
                    while (daysIterator1.hasNext() && daysIterator2.hasNext()) {
                        Dia day1 = daysIterator1.next();
                        Dia day2 = daysIterator2.next();
                        ArrayList<TimeSlot> timeslots1 = day1.getTimeSlot();
                        ArrayList<TimeSlot> timeslots2 = day2.getTimeSlot();
                        Iterator<TimeSlot> timeslotIterator1 = timeslots1.iterator();
                        Iterator<TimeSlot> timeslotIterator2 = timeslots2.iterator();
                        while (timeslotIterator1.hasNext() && timeslotIterator2.hasNext()) {
                            TimeSlot lecture1 = timeslotIterator1.next();
                            TimeSlot lecture2 = timeslotIterator2.next();
                            if (lecture1.getDictado() != null && lecture2.getDictado() != null) {
//							String subject1=lecture1.getDictado().getSubject();
//							String subject2=lecture2.getDictado().getSubject();							
                                String professorName1 = lecture1.getDictado().getDocente().getNombre();
                                String professorName2 = lecture2.getDictado().getDocente().getNombre();
                                String stgrp1 = lecture1.getDictado().getGrupoEstudiantes().getNombre();
                                String stgrp2 = lecture2.getDictado().getGrupoEstudiantes().getNombre();
                                if (stgrp1.equals(stgrp2) || professorName1.equals(professorName2)) {
                                    score = score + 1;
                                }
                                ArrayList<Combinacion> stcomb1 = lecture1.getDictado().getGrupoEstudiantes().getCombinaciones();
                                Iterator<Combinacion> stcombItr = stcomb1.iterator();
                                while (stcombItr.hasNext()) {
                                    if (lecture2.getDictado().getGrupoEstudiantes().getCombinaciones().contains(stcombItr.next())) {
                                        score = score + 1;
                                        break;
                                    }
                                }

                            }
                        }
                    }
                }
            }
            timetable.setFittness(score);
            //ttscore.put(score,timetable);
            //System.out.println("\nScore : "+score);
        }
        System.out.println("Puntaje..................................." + timetable.getFittness());
//		Iterator iterator = ttscore.keySet().iterator(); 
//		while (iterator.hasNext()) {  
//			   Aula key = (Aula) iterator.next();  
//			   int value = (int) ttscore.get(key);  
//			   
//			   System.out.println("\nScore : "+value);  
//			}  
    }

    private static TimeTable Mutation(TimeTable parentTimetable) {
        TimeTable mutantTimeTable = parentTimetable;
        int rnd1, rnd2;
        Random randomGenerator = new Random();
        ArrayList<Aula> presentClassroom = mutantTimeTable.getAulas();
        for (Iterator<Aula> iterator = presentClassroom.iterator(); iterator.hasNext();) {
            Aula classRoom = iterator.next();
            //for (Iterator <Day> iterator2 = classRoom.getWeek().getWeekDays().iterator(); iterator2.hasNext();) {

            // i have got the two days here which i have to exchange... but wat i actually 
            //want to shuffle is not the days but the schedule for the day!				
            rnd1 = randomGenerator.nextInt(5);
            rnd2 = -1;
            while (rnd1 != rnd2) {
                rnd2 = randomGenerator.nextInt(5);
            }
            ArrayList<Dia> weekDays = classRoom.getSemana().getDiasSemana();
            Dia day1 = weekDays.get(rnd1);
            Dia day2 = weekDays.get(rnd2);

            ArrayList<TimeSlot> timeSlotsOfday1 = day1.getTimeSlot();
            ArrayList<TimeSlot> timeSlotsOfday2 = day2.getTimeSlot();

            day1.setTimeSlot(timeSlotsOfday2);
            day2.setTimeSlot(timeSlotsOfday1);

            // if i am limiting this to two days i am breaking out... 
            //or else all the days will get exchanged in a sorted order
            //like monday-tue,wed thu,fri sat in pairs!
            break;
            //}			
        }
        // apply repairstrategy here! check whether mutant 
        //better than parent and vice versa and choose the best		
        return mutantTimeTable;
    }

    private static TimeTable crossOver(TimeTable fatherTimeTable) {
        // let us say that we give father the priority to stay as the checker!
        // in the outer loop		
        Random randomGenerator = new Random();
        Iterator<Aula> parentTimeTableClassRooms = fatherTimeTable.getAulas().iterator();
        while (parentTimeTableClassRooms.hasNext()) {
            Aula room = parentTimeTableClassRooms.next();
            if (!room.esLaboratorio()) {
                ArrayList<Dia> days = room.getSemana().getDiasSemana();
                int i = 0;
                while (i < 3) {
                    int rnd = randomGenerator.nextInt(5);
                    Dia day = days.get(rnd);
                    Collections.shuffle(day.getTimeSlot());
                    i++;
                }
            }

        }
        return fatherTimeTable;
    }

    private static void writeToExcelFile() throws IOException {
        FileWriter writer = new FileWriter("timetable.csv");
        //PrintWriter pw = new PrintWriter(writer);
        int i = 0;
        writer.append("\n\nMinimum : " + min);
        writer.append("\n\nScore : " + GlobalBestTimetable.getFittness());
        writer.append("\n\n (Subject#Professor#Student Group)");
        ArrayList<Aula> allrooms = GlobalBestTimetable.getAulas();
        Iterator<Aula> allroomsIterator = allrooms.iterator();
        while (allroomsIterator.hasNext()) {
            Aula room = allroomsIterator.next();
            writer.append("\n\nRoom Number: " + room.getID());
            ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
            Iterator<Dia> daysIterator = weekdays.iterator();
            Iterator<String> lectTimeItr = horariosDictados.iterator();
            writer.append("\n\nTimings: ,");
            while (lectTimeItr.hasNext()) {
                writer.append(lectTimeItr.next() + ",");
            }
            i = 0;
            writer.append("\nDays\n");
            while (daysIterator.hasNext()) {
                Dia day = daysIterator.next();
                writer.append(/*Dia: */"" + nombresDiasSemana.get(i) + ",");
                ArrayList<TimeSlot> timeslots = day.getTimeSlot();
                i++;
                for (int k = 0; k < timeslots.size(); k++) {
                    if (k == 3) {
                        writer.append("BREAK,");
                    }
                    TimeSlot lecture = timeslots.get(k);
                    if (lecture.getDictado() != null) {
                        writer.append("(" + lecture.getDictado().getMateria() + "#" + lecture.getDictado().getDocente().getNombre() + "#" + lecture.getDictado().getGrupoEstudiantes().getNombre().split("/")[0] + ")" + ",");
                    } else {
                        writer.append("FREE LECTURE,");
                    }
                }
                writer.append("\n");
            }
            writer.append("\n");
        }
//			i++;			
        //writer.append("This is grahesh&Shridatt copyright @");
        writer.flush();
        writer.close();
    }

    private static void display() {
        // TODO Auto-generated method stub
        int i = 0, j = 0;
        System.out.println("Minimo : " + min);
        System.out.println("\nPuntaje : " + GlobalBestTimetable.getFittness());
        ArrayList<Aula> allrooms = GlobalBestTimetable.getAulas();
        Iterator<Aula> allroomsIterator = allrooms.iterator();
        while (allroomsIterator.hasNext()) {
            Aula room = allroomsIterator.next();
            System.out.println("\nAula: " + room.getID());
            ArrayList<Dia> weekdays = room.getSemana().getDiasSemana();
            Iterator<Dia> daysIterator = weekdays.iterator();
            Iterator<String> lectTimeItr = horariosDictados.iterator();
            System.out.print("\nHorarios:    ");
            while (lectTimeItr.hasNext()) {
                System.out.print(" " + lectTimeItr.next() + " ");
            }
            i = 0;
            System.out.print("\n");
            while (daysIterator.hasNext()) {
                Dia day = daysIterator.next();
                System.out.print("Día: " + nombresDiasSemana.get(i));
                ArrayList<TimeSlot> timeslots = day.getTimeSlot();
                //Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
                i++;
                //System.out.print(""+day.getName()+": ");
                for (int k = 0; k < timeslots.size(); k++) {
                    if (k == 3) {
                        System.out.print("       RECESO       ");
                    }
                    TimeSlot lecture = timeslots.get(k);
                    if (lecture.getDictado() != null) {
                        System.out.print(" (Materia: " + lecture.getDictado().getMateria() + " --> Docente: " + lecture.getDictado().getDocente().getNombre() + " Nombre Grupo: " + lecture.getDictado().getGrupoEstudiantes().getNombre() + ")");
//                        System.out.print("  (" + lecture.getDictado().getSubject() + "#" + lecture.getDictado().getProfessor().getProfessorName() + "#" + lecture.getDictado().getStudentGroup().getName().split("/")[0] + ")");
                    } else {
                        System.out.print(" SIN DICTADO ");
                    }
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
        //System.out.println("This is grahesh&Shridatt copyright @");
    }

    private static void displayMejor() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
          System.out.println("\nPuntaje : " + GlobalBestTimetable.getFittness());
    }
}
//					while(timeslotIterator.hasNext()){
//						
//						TimeSlot lecture = timeslotIterator.next();
//						
//						if(lecture.getDictado()!=null){
//							//System.out.print(" (Subject: "+lecture.getDictado().getSubject()+" --> Docente: "+lecture.getDictado().getProfessor().getProfessorName()+" GrpName: "+lecture.getDictado().getStudentGroup().getName()+")");
//								System.out.print("  ("+lecture.getDictado().getSubject()+"#"+lecture.getDictado().getProfessor().getProfessorName()+"#"+lecture.getDictado().getStudentGroup().getName().split("/")[0]+")");
//							}
//						
//							else{
//								System.out.print("       FREE LECTURE       ");
//							}
//						
//					}

//}
//incomplete class. Not used till now. Working on this. You also start working
//use Elitism for crossover where best 2 timetables are kept and other two are used for crossover
//crossover done by replacing random days of each room in timetable with same room in other timetable
//Mutation : exchange two random days from a room
//Repair Strategy : take clashing lectures, change teacher or take and place at null lecture position
//private static TimeTable[] crossOver(TimeTable fatherTimeTable,TimeTable motherTimeTable){
//	// let us say that we give father the priority to stay as the checker!
//	// in the outer loop		
//	Iterator<ClassRoom> parentTimeTableClassRooms=fatherTimeTable.getAulas().iterator();		
//	while(parentTimeTableClassRooms.hasNext()) {
//		Aula fathersClassRoom = (Aula) parentTimeTableClassRooms.next();			
//		String parentClassId=fathersClassRoom.getID();			
//		Iterator<ClassRoom> motherTimeTableClassRooms=motherTimeTable.getAulas().iterator();			
//		while(motherTimeTableClassRooms.hasNext()){
//			Aula mothersClassRoom = (Aula) motherTimeTableClassRooms.next();				
//			String motherClassId=mothersClassRoom.getID();				
//			// if both are same classes then
//			if(motherClassId.equals(parentClassId)){
//				// change days in them randomly!					
//				int crossoverPoint=0;					
//				Random r=new Random();					
//				while(crossoverPoint==0||crossoverPoint==5){
//					crossoverPoint=r.nextInt(5);
//				}					
//				ArrayList<Day> fatherTTDays= fathersClassRoom.getWeek().getWeekDays();
//				ArrayList<Day> motherTTDays= mothersClassRoom.getWeek().getWeekDays();
//				
//				ArrayList<Day> tempExchange1=new ArrayList<Day>();
//				ArrayList<Day> tempExchange2=new ArrayList<Day>();					
//				for(int i=0;i<crossoverPoint;i++){
//					tempExchange1.add(fatherTTDays.get(i));
//					
//				}					
//				// assuming till 6 days
//				for (int i = crossoverPoint; i < 6; i++) {
//					tempExchange2.add(motherTTDays.get(i));
//				}					
//				fatherTTDays.removeAll(tempExchange1);
//				motherTTDays.removeAll(tempExchange2);					
//				fatherTTDays.addAll(motherTTDays);
//				tempExchange1.addAll(tempExchange2);					
//				motherTTDays.clear();
//				motherTTDays.addAll(tempExchange1);					
//				mothersClassRoom.getWeek().setWeekDays(motherTTDays);
//				fathersClassRoom.getWeek().setWeekDays(fatherTTDays);					
//			}
//		}		
//	}		
//	TimeTable[] offsprings={fatherTimeTable,motherTimeTable};
//	return offsprings;
//}
//public Aula initialRandom(Aula room){
//ArrayList<Day> weekdays = room.getWeek().getWeekDays();
//Iterator<Day> daysIterator=weekdays.iterator();
//while(daysIterator.hasNext()){
//	Collections.shuffle(subjects);
//	Dia day = daysIterator.next();
//	ArrayList<TimeSlot> timeslots = day.getTimeSlot();
//	Iterator timeslotIterator= timeslots.iterator();
//	Iterator subIterator=subjects.iterator();
//	while(timeslotIterator.hasNext() && subIterator.hasNext()){
//		TimeSlot lecture = (TimeSlot) timeslotIterator.next();
////		if(!(lecture.getSlotTime()==12)){
////		lecture.setSubject((String)subIterator.next());
////		}
//	}
//}
//
//return room;
//}
