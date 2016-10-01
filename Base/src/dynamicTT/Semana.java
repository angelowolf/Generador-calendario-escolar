package dynamicTT;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class Semana {

    private String[] nombreDiasSemana;
    private ArrayList<Dia> diasSemana = new ArrayList<Dia>();

    public ArrayList<Dia> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(ArrayList<Dia> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public Semana() {
        this.nombreDiasSemana = new DateFormatSymbols().getWeekdays();
        for (int i = 1; i < nombreDiasSemana.length; i++) {
//            System.out.println("weekday = " + weekDaysName[i]);
//            if (!(weekDaysName[i].equalsIgnoreCase("Sunday"))) {
            if (!(i == Calendar.SUNDAY)) {
//                System.out.println("I::: " + i);
                Dia newday = new Dia(nombreDiasSemana[i]);
                diasSemana.add(newday);
            }
        }
    }
}
