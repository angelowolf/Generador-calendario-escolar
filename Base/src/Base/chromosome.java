package Base;

public class chromosome {

    gene[] period;

    chromosome(int rooms) {
        period = new gene[rooms * 5 * 7];//no.of working days=5; no. working hours each day=7(8 to 12 and 1:30 to 4:30)
    }
    //determine fitness value of chromosome

    double fitness(inputdata input, int norooms, int nocourse, int nostgrp, int noins) {
        int k, i, j, room = -1, flag;
        double fitnessvalue = 0, fit = 0;
        int lroom = Integer.toBinaryString(norooms).length();
        int lcourse = Integer.toBinaryString(nocourse).length();
        int lstgrp = Integer.toBinaryString(nostgrp).length();
        int lins = Integer.toBinaryString(noins).length();
        String course = "", stgrp = "", ins = "", tempst = "", tempin = "", tempcs = "";
        int st, cs, in;
        for (i = 0; i < norooms * 5 * 7; i++) {
            if (i % 35 == 0) {
                room++;
            }
            //course for current gene
            course = "";
            for (j = 0; j < lcourse; j++) {
                course = course + period[i].g[j];
            }
            //stgrp for current gene
            stgrp = "";
            for (j = lcourse; j < lcourse + lstgrp; j++) {
                stgrp = stgrp + period[i].g[j];
            }

            //ins for current gene;
            ins = "";
            for (j = lcourse + lstgrp; j < lcourse + lstgrp + lins; j++) {
                ins = ins + period[i].g[j];
            }
            //integer value of stgrp
            st = Integer.valueOf(stgrp, 2);

            //integer value of course
            cs = Integer.valueOf(course, 2);

            //integer value of ins
            in = Integer.valueOf(ins, 2);

            //check for the roomns and stgrp strength
            if (input.rooms[room].strength >= input.stgrp[st].strength) {
                fit++;
            }

            //check for stgrp repeat
            flag = 1;
            for (j = i + 35; j < norooms * 5 * 7; j += 35) {
                tempst = "";
                for (k = lcourse; k < lcourse + lstgrp; k++) {
                    tempst = tempst + period[j].g[k];
                }
                if (tempst.equals(stgrp)) {
                    flag = 0;
                }
            }
            for (j = i - 35; j >= 0; j -= 35) {
                tempst = "";
                for (k = lcourse; k < lcourse + lstgrp; k++) {
                    tempst = tempst + period[j].g[k];
                }
                if (tempst.equals(stgrp)) {
                    flag = 0;
                }
            }
            if (flag == 1) {
                fit++;
            }

            //check for ins repeat
            flag = 1;
            for (j = i + 35; j < norooms * 5 * 7; j += 35) {
                tempin = "";
                for (k = lcourse + lstgrp; k < lcourse + lstgrp + lins; k++) {
                    tempin = tempin + period[j].g[k];
                }
                if (tempin.equals(ins)) {
                    flag = 0;
                    break;
                }
            }
            for (j = i - 35; j >= 0; j -= 35) {
                tempin = "";
                for (k = lcourse + lstgrp; k < lcourse + lstgrp + lins; k++) {
                    tempin = tempin + period[j].g[k];
                }
                if (tempin.equals(ins)) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                fit++;
            }

            //lab fitness checking
            /*if(input.course[cs].lab=='y'||input.course[cs].lab=='Y')
			{
				if((i%7==0)||(i%7==1)||(i%7==4))
				{
					
					if(((input.course[cs+1].lab=='y')||(input.course[cs+1].lab=='Y'))&&((input.course[cs+2].lab=='y')||(input.course[cs+2].lab=='Y')))
						
				}
			}*/
        }
        //calculate fitness value
        fitnessvalue = fit / (norooms * 35);
        return (fitnessvalue);
    }
}
