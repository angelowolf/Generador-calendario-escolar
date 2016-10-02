package Base;

public class timetable {

    private chromosome sol;
    static table[][][] ttable;
    inputdata input;
    int nostgrp, noclass, noins, nocourse;

    timetable(chromosome solution, int stgrp1, int nclass, int ins, int course, inputdata input1) {
        sol = solution;
        nostgrp = stgrp1;
        ttable = new table[nostgrp][7][5];
        input = input1;
        int i, j, k;
        for (i = 0; i < nostgrp; i++) {
            for (j = 0; j < 7; j++) {
                for (k = 0; k < 5; k++) {
                    ttable[i][j][k] = new table();
                }
            }
        }
        noclass = nclass;
        noins = ins;
        nocourse = course;
    }

    void findtable() {
        double[] time = {8.0, 9.0, 10.0, 11.0, 1.0, 2.0, 3.0};
        int lcourse = Integer.toBinaryString(nocourse).length();
        int lstgrp = Integer.toBinaryString(nostgrp).length();
        int lins = Integer.toBinaryString(noins).length();
        int lgene = lcourse + lstgrp + lins;
        String[] day = {"Mon", "Tue", "Wed", "Thurs", "Fri"};
        int i, j, k, cs, st, in, room = -1, d = -1, t = 0;
        double x;
        String course, stgrp, ins;
        for (i = 0; i < noclass * 35; i++) {

            if (i % 35 == 0) {
                room++;
                d = -1;
                //t=0;
            }
            if ((i % 7 == 0)) {
                t = 0;
                d++;
            }
            //course for current gene
            course = "";
            for (j = 0; j < lcourse; j++) {
                course = course + sol.period[i].g[j];
            }
            //stgrp for current gene
            stgrp = "";
            for (j = lcourse; j < lcourse + lstgrp; j++) {
                stgrp = stgrp + sol.period[i].g[j];
            }

            //ins for current gene;
            ins = "";
            for (j = lcourse + lstgrp; j < lcourse + lstgrp + lins; j++) {
                ins = ins + sol.period[i].g[j];
            }
            //integer value of stgrp
            st = Integer.valueOf(stgrp, 2);

            //integer value of course
            cs = Integer.valueOf(course, 2);

            //integer value of ins
            in = Integer.valueOf(ins, 2);

            ttable[st][t][d] = new table();
            ttable[st][t][d].course = ttable[st][t][d].course + input.course[cs].code;
            ttable[st][t][d].ins = ttable[st][t][d].ins + input.ins[in].code;
            ttable[st][t][d].room = ttable[st][t][d].room + input.rooms[room].code;
            t++;
        }
        for (i = 0; i < nostgrp; i++) {
            for (j = 0; j < 7; j++) {
                for (k = 0; k < 5; k++) {
                    if (ttable[i][j][k].course.equals("")) {
                        ttable[i][j][k].course = "--";
                        ttable[i][j][k].room = "--";
                        ttable[i][j][k].ins = "--";
                    }
                }
            }
        }
        System.out.println("\n" + "Time table for given input of Courses:-");
        for (i = 0; i < nostgrp; i++) {
            System.out.println("===============================================================================================");
            System.out.println("**********************************\t" + input.stgrp[i].code + "\t**************************************");
            System.out.println("===============================================================================================");

            System.out.print("\n" + "TIME\\DAY" + "\t");

            for (j = 0; j < 5; j++) {
                System.out.print(day[j] + "\t\t");
            }
            System.out.println("\n ");
            for (j = 0; j < 7; j++) {
                x = time[j] + 1;
                if (j == 1 || j == 2 || j == 3) {
                    System.out.print(time[j] + "-" + x + "\t");
                } else {
                    System.out.print(time[j] + "-" + x + "\t\t");
                }
                for (k = 0; k < 5; k++) {
                    if (!ttable[i][j][k].course.equals("")) {
                        System.out.print(ttable[i][j][k].course + " " + ttable[i][j][k].ins + " " + ttable[i][j][k].room + "\t");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
        printtable print = new printtable(ttable, nostgrp, input);
        print.print();
    }
}
