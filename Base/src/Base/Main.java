/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author ang_2
 */
public class Main {

    static chromosome[] classes;
    static int noclass, nocourse, noins, nostgrp;
    static double[] fit;
    static inputdata input;

    public static void main(String[] args) {
        Date date = new Date();
        long time1 = date.getTime();

        int i = 0, j, k = 0, l, count = 0, g = 0;
        chromosome temp;
        double tempfitt[] = new double[5];
        double tempfit, maxfit = 0;
        chromosome[] newgen = new chromosome[10000];
        chromosome[] sonparent = new chromosome[6];               //stores current parents,crossover sons and mutation sons
        double[] fsonparent = new double[6];                      //stores corresponding fitness values
        double fit1, fit2, fitp1, fitp2;
        int size = 10000;						//size of population
        int[] parent = new int[2];
        input = new inputdata();
        input.takeinput();
        chromosome maxchrome = new chromosome(noclass);
        chromosome tempchrome = new chromosome(noclass);

        //chromosome population creation/initialization
        createpopulation();
        int len_course = Integer.toBinaryString(nocourse).length();
        int len_stgrp = Integer.toBinaryString(nostgrp).length();
        int len_ins = Integer.toBinaryString(noins).length();
        int len_gene = len_course + len_stgrp + len_ins;

        //fitness initialization
        fitness(size);
        for (l = 0; l < 3; l++) {

            //branch and bound to reduce the size and improve the quality of population set
            size = branchandbound(size);

            //fitness calculation
            fitness(size);

            //while(maxfit<2.1){
            for (i = 0; i < size / 2; i++) {
                //parent selection for crossover operation
                newgen[2 * i] = new chromosome(noclass);
                newgen[2 * i + 1] = new chromosome(noclass);

                parent = select(size);
                fitp1 = fit[parent[0]];
                fitp2 = fit[parent[1]];

                sonparent[0] = classes[parent[0]];
                sonparent[1] = classes[parent[1]];
                fsonparent[0] = fitp1;
                fsonparent[1] = fitp2;

                //crossover
                chromosome son1 = new chromosome(noclass);
                chromosome son2 = new chromosome(noclass);
                crossover(parent, son1, son2);
                fit1 = son1.fitness(input, noclass, nocourse, nostgrp, noins);
                fit2 = son2.fitness(input, noclass, nocourse, nostgrp, noins);

                sonparent[2] = son1;
                sonparent[3] = son2;
                fsonparent[2] = fit1;
                fsonparent[3] = fit2;

                fit1 = son1.fitness(input, noclass, nocourse, nostgrp, noins);
                fit2 = son2.fitness(input, noclass, nocourse, nostgrp, noins);

                sonparent[4] = son1;
                sonparent[5] = son2;
                fsonparent[4] = fit1;
                fsonparent[5] = fit2;

                //sort sonparent on basis of fsonparent
                for (j = 1; j < 6; j++) {
                    temp = sonparent[j];
                    tempfit = fsonparent[j];
                    for (k = j - 1; k >= 0; k--) {
                        if (fsonparent[k] > tempfit) {
                            sonparent[k + 1] = sonparent[k];
                            fsonparent[k + 1] = fsonparent[k];
                        } else {
                            break;
                        }
                    }
                    sonparent[k + 1] = temp;
                    fsonparent[k + 1] = tempfit;
                }
                newgen[2 * i] = sonparent[5];
                newgen[2 * i + 1] = sonparent[4];

            }

            for (i = 0; i < size; i++) {

                classes[i] = newgen[i];
                fit[i] = newgen[i].fitness(input, noclass, nocourse, nostgrp, noins);
            }
            tempfit = fit[0];
            for (i = 1; i < size; i++) {
                if (tempfit < fit[i]) {
                    tempfit = fit[i];
                    tempchrome = classes[i];
                    //x1=i;
                }
            }
            if (count % 5 == 0) {
                tempfitt[count % 5] = tempfit;
            }
            if (count % 5 == 1) {
                tempfitt[count % 5] = tempfit;
            }
            if (count % 5 == 2) {
                tempfitt[count % 5] = tempfit;
            }
            if (count % 5 == 3) {
                tempfitt[count % 5] = tempfit;
            }
            if (count % 5 == 4) {
                tempfitt[count % 5] = tempfit;
            }
            count++;
            if (tempfitt[0] == tempfitt[1] && tempfitt[0] == tempfitt[2] && tempfitt[0] == tempfitt[3] && tempfitt[0] == tempfitt[4] && count > 4) {
                for (g = 0; g < size; g++) {
                    mutation(classes[g]);
                }
            }
            //System.out.println("MAX ="+tempfit);
            if (maxfit < tempfit) {
                maxfit = tempfit;
                maxchrome = tempchrome;
            }
            if (((maxfit <= 3.0) && (maxfit >= 2.7)) || (size <= 1000)) {
                break;
            }
            //System.out.println(maxfit);
        }

        System.out.println(maxfit);
        timetable table1 = new timetable(maxchrome, nostgrp, noclass, noins, nocourse, input);
        table1.findtable();
        date = new Date();
        long time2 = date.getTime();
        System.out.println(time2 - time1);
    }
    //mutation method

    public static void mutation(chromosome son) {
        int i;
        int len_course = Integer.toBinaryString(nocourse).length();
        int len_stgrp = Integer.toBinaryString(nostgrp).length();
        int len_ins = Integer.toBinaryString(noins).length();
        int len_gene = len_course + len_stgrp + len_ins;
        for (i = 0; i < noclass * 35; i++) {
            Random r = new Random();
            int pos = r.nextInt(len_gene * 35 * noclass);
            int row = pos / len_gene;
            int col = pos % len_gene;
            if (son.period[row].g[col] == 0) {
                son.period[row].g[col] = 1;
            }
            if (son.period[row].g[col] == 1) {
                son.period[row].g[col] = 0;
            }
            anomalyson(son, len_course, len_stgrp, len_ins, row);
        }
    }

    //single point crossover
    public static void crossover(int[] parent, chromosome son1, chromosome son2) {

        Random n1 = new Random();
        int k, r1, r2, row1, col1, row2, col2, i, j;
        int len_course = Integer.toBinaryString(nocourse).length();
        int len_stgrp = Integer.toBinaryString(nostgrp).length();
        int len_ins = Integer.toBinaryString(noins).length();
        int len_gene = len_course + len_stgrp + len_ins;
        r1 = n1.nextInt(len_gene * noclass * 35);
        r2 = n1.nextInt((len_gene) * noclass * 35);
        row1 = r1 / (len_gene);
        col1 = r1 % len_gene;

        //single point crossover
        for (i = 0; i < row1; i++) {
            son1.period[i] = new gene(nocourse, nostgrp, noins);
            son2.period[i] = new gene(nocourse, nostgrp, noins);
            for (j = 0; j < len_gene; j++) {
                son1.period[i].g[j] = classes[parent[1]].period[i].g[j];
                son2.period[i].g[j] = classes[parent[0]].period[i].g[j];
            }
        }
        for (j = 0; j < col1; j++) {
            son1.period[row1] = new gene(nocourse, nostgrp, noins);
            son2.period[row1] = new gene(nocourse, nostgrp, noins);
            son1.period[row1].g[j] = classes[parent[1]].period[row1].g[j];
            son2.period[row1].g[j] = classes[parent[0]].period[row1].g[j];
        }
        for (j = col1; j < len_gene; j++) {
            son1.period[row1] = new gene(nocourse, nostgrp, noins);
            son2.period[row1] = new gene(nocourse, nostgrp, noins);
            son1.period[row1].g[j] = classes[parent[0]].period[row1].g[j];
            son2.period[row1].g[j] = classes[parent[1]].period[row1].g[j];
        }

        for (i = row1 + 1; i < noclass * 35; i++) {
            son1.period[i] = new gene(nocourse, nostgrp, noins);
            son2.period[i] = new gene(nocourse, nostgrp, noins);

            for (j = 0; j < len_gene; j++) {
                son1.period[i].g[j] = classes[parent[0]].period[i].g[j];
                son2.period[i].g[j] = classes[parent[1]].period[i].g[j];
            }
        }

        //check for anomalies
        anomalyson(son1, len_course, len_stgrp, len_ins, row1);
        anomalyson(son2, len_course, len_stgrp, len_ins, row1);
    }

    public static void anomalyson(chromosome son1, int len_course, int len_stgrp, int len_ins, int row1) {
        String bin = "";
        boolean flag;
        String x = "";
        int n, j, i;
        for (i = 0; i < len_course; i++) {
            bin = bin + son1.period[row1].g[i];
        }
        n = Integer.valueOf(bin, 2);
        Random r = new Random();
        int c, s, in, m, le;
        //check for invalid course code
        if (n >= nocourse) {
            c = r.nextInt(nocourse);//course generation
            x = Integer.toBinaryString(c);
            le = x.length();
            m = 0;
            if (len_course > le) {
                for (j = 0; j < len_course - le; j++) {
                    son1.period[row1].g[m++] = 0;
                }
            }
            for (j = 0; j < le; j++) {
                son1.period[row1].g[m++] = (x.charAt(j) - '0');
            }

            //student group generation
            flag = false;
            while (!flag) {
                s = r.nextInt(nostgrp);
                for (j = 0; j < input.course[c].nostgrp; j++) {
                    if (input.course[c].stgrp[j].equals(input.stgrp[s].code)) {
                        m = len_course;
                        x = Integer.toBinaryString(s);
                        le = x.length();
                        if (len_stgrp > le) {
                            for (j = 0; j < len_stgrp - le; j++) {
                                son1.period[row1].g[m++] = 0;
                            }
                        }
                        for (j = 0; j < le; j++) {
                            son1.period[row1].g[m++] = x.charAt(j) - '0';
                        }
                        flag = true;
                        break;
                    }
                }
            }

            //instructor generation
            flag = false;
            while (!flag) {
                in = r.nextInt(noins);
                for (j = 0; j < input.course[c].noins; j++) {
                    if (input.course[c].ins[j].equals(input.ins[in].code)) {
                        m = len_course + len_stgrp;
                        x = Integer.toBinaryString(in);
                        le = x.length();
                        if (len_ins > le) {
                            for (j = 0; j < len_ins - le; j++) {
                                son1.period[row1].g[m++] = 0;
                            }
                        }
                        for (j = 0; j < le; j++) {
                            son1.period[row1].g[m++] = (x.charAt(j) - '0');
                        }
                        flag = true;
                        break;
                    }
                }
            }
        } else {
            bin = "";
            for (i = len_course; i < len_course + len_stgrp; i++) {
                bin = bin + son1.period[row1].g[i];
            }
            c = n;
            n = Integer.valueOf(bin, 2);
            //check for valid student group and if invalis generate a random student group for the course 
            if (n >= nostgrp) {
                flag = false;
                while (!flag) {
                    s = r.nextInt(nostgrp);

                    for (j = 0; j < input.course[c].nostgrp; j++) {
                        if (input.course[c].stgrp[j].equals(input.stgrp[s].code)) {
                            m = len_course;
                            x = Integer.toBinaryString(s);
                            le = x.length();
                            if (len_stgrp > le) {
                                for (j = 0; j < len_stgrp - le; j++) {
                                    son1.period[row1].g[m++] = 0;
                                }
                            }
                            for (j = 0; j < le; j++) {
                                son1.period[row1].g[m++] = x.charAt(j) - '0';
                            }
                            flag = true;
                            break;
                        }
                    }
                }
            }

            bin = "";
            for (i = len_course + len_stgrp; i < len_course + len_stgrp + len_ins; i++) {
                bin = bin + son1.period[row1].g[i];
            }
            n = Integer.valueOf(bin, 2);

            //check for valid instructor and if invalid generate a random ins code for the course
            if (n >= noins) {
                flag = false;
                while (!flag) {
                    in = r.nextInt(noins);
                    for (j = 0; j < input.course[c].noins; j++) {
                        if (input.course[c].ins[j].equals(input.ins[in].code)) {
                            m = len_course + len_stgrp;
                            x = Integer.toBinaryString(in);
                            le = x.length();
                            if (len_ins > le) {
                                for (j = 0; j < len_ins - le; j++) {
                                    son1.period[row1].g[m++] = 0;
                                }
                            }
                            for (j = 0; j < le; j++) {
                                son1.period[row1].g[m++] = (x.charAt(j) - '0');
                            }
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    //parent selection for crossover
    public static int[] select(int size) {
        int[] select = new int[2];
        int[] rwheel = new int[100000];
        int i, j, k = 0, percent;
        double fitsum = 0;
        for (i = 0; i < size; i++) {
            fitsum = fitsum + fit[i];
        }
        for (i = 0; i < size; i++) {
            percent = (int) ((((double) fit[i]) / fitsum) * 100000);
            for (j = k; j < k + percent; j++) {
                rwheel[j] = i;
            }
            k = k + percent;
        }
        Random r = new Random();
        //first parent
        select[0] = rwheel[r.nextInt(100000)];
        //second parent
        select[1] = rwheel[r.nextInt(100000)];

        return (select);
    }

    static int branchandbound(int size) {
        int i, flag, l, j, k = 0;
        double small = fit[0], high = fit[0];
        double[][] value = new double[1500][2];
        for (i = 1000; i >= 8; i /= 5) {
            k = 0;
            for (j = 0; j < size; j++) {
                if ((j % i == 0) && (j != 0)) {
                    value[k][0] = small;
                    value[k][1] = high;
                    small = fit[j];
                    high = fit[j];
                    k++;
                    continue;
                }
                if (small > fit[j]) {
                    small = fit[j];
                }
                if (high < fit[j]) {
                    high = fit[j];
                }
            }
            for (j = 0; j < k; j++) {
                flag = 0;
                for (l = 0; l < k; l++) {
                    if (l == j) {
                        continue;
                    }
                    if (value[j][1] < value[l][0]) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    for (l = (j * i); l < size - i; l++) {
                        classes[l] = classes[l + i];
                    }
                    size = size - i;
                }
            }
        }
        return (size);
    }

    public static void fitness(int size) {
        fit = new double[size];
        int i;
        for (i = 0; i < size; i++) {
            fit[i] = classes[i].fitness(input, noclass, nocourse, nostgrp, noins);
        }
    }

    static void createpopulation() {
        int i, j, k, l, le, c, q, s, in, m = 0, n;
        boolean flag;
        noclass = input.returnclassno();//no of classrooms
        nocourse = input.returncourseno();//no of courses
        nostgrp = input.returnstgrpno();//no of stgrps
        noins = input.returninsno();//no of ins
        Random r = new Random();
        classes = new chromosome[10000];
        for (k = 0; k < 10000; k++) {
            classes[k] = new chromosome(noclass);
            for (i = 0; i < noclass * 5 * 7; i++) {
                m = 0;

                l = Integer.toBinaryString(nocourse).length();

                c = r.nextInt(nocourse);//course generation

                classes[k].period[i] = new gene(nocourse, nostgrp, noins);
                String x = Integer.toBinaryString(c);
                le = x.length();
                if (l > le) {
                    for (j = 0; j < l - le; j++) {
                        classes[k].period[i].g[m++] = 0;
                    }
                }
                for (j = 0; j < le; j++) {
                    classes[k].period[i].g[m++] = (x.charAt(j) - '0');
                }
                n = m;

                //student group generation
                flag = false;
                while (!flag) {
                    s = r.nextInt(nostgrp);
                    for (j = 0; j < input.course[c].nostgrp; j++) {
                        if (input.course[c].stgrp[j].equals(input.stgrp[s].code)) {
                            m = n;
                            l = Integer.toBinaryString(nostgrp).length();
                            x = Integer.toBinaryString(s);
                            le = x.length();
                            if (l > le) {
                                for (j = 0; j < l - le; j++) {
                                    classes[k].period[i].g[m++] = 0;
                                }
                            }
                            for (j = 0; j < le; j++) {
                                classes[k].period[i].g[m++] = x.charAt(j) - '0';
                            }
                            flag = true;
                            break;
                        }
                    }
                }
                n = m;
                //instructor generation
                flag = false;
                while (!flag) {
                    in = r.nextInt(noins);
                    for (j = 0; j < input.course[c].noins; j++) {
                        if (input.course[c].ins[j].equals(input.ins[in].code)) {
                            m = n;
                            l = Integer.toBinaryString(noins).length();
                            x = Integer.toBinaryString(in);
                            le = x.length();
                            if (l > le) {
                                for (j = 0; j < l - le; j++) {
                                    classes[k].period[i].g[m++] = 0;
                                }
                            }
                            for (j = 0; j < le; j++) {
                                classes[k].period[i].g[m++] = (x.charAt(j) - '0');
                            }
                            flag = true;
                            break;
                        }
                    }
                }

                //chromosome initialization finished
            }
        }
        //chromosome initial population created
    }

}
