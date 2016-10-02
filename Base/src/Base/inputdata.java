/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author ang_2
 */
public class inputdata {

    coursedata[] course;
    classdata[] rooms;
    stgrpdata[] stgrp;
    insdata[] ins;
    int noroom, nocourse, nostgrp, noins;

    public inputdata() {
        course = new coursedata[100];
        rooms = new classdata[100];
        stgrp = new stgrpdata[100];
        ins = new insdata[100];
    }

    boolean classformat(String l) {
        StringTokenizer st = new StringTokenizer(l, " ");
        if (st.countTokens() == 3) {
            return (true);
        } else {
            return (false);
        }
    }

    public void takeinput()//takes input from file input.txt 
    {
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //input courses
                if (line.equals("courses")) {
                    int i = 0;
                    String arr;
                    while (!(line = scanner.nextLine()).equalsIgnoreCase("studentgroups")) {
                        course[i] = new coursedata();
                        StringTokenizer st = new StringTokenizer(line, " ");
                        course[i].name = st.nextToken();
                        course[i].code = st.nextToken();
                        course[i].credits = Integer.parseInt(st.nextToken());
                        course[i].department = st.nextToken();
                        course[i].lab = st.nextToken().charAt(0);
                        i++;
                    }
                    nocourse = i;
                }
                //input student groups
                if (line.equalsIgnoreCase("studentgroups")) {
                    int i = 0, j, k;
                    String arr;
                    while (!(line = scanner.nextLine()).equalsIgnoreCase("instructors")) {
                        stgrp[i] = new stgrpdata();
                        StringTokenizer st = new StringTokenizer(line, " ");
                        stgrp[i].info = st.nextToken();
                        stgrp[i].code = st.nextToken();
                        stgrp[i].strength = Integer.parseInt(st.nextToken());
                        j = 0;
                        while (st.hasMoreTokens()) {
                            stgrp[i].course[j++] = st.nextToken();
                            for (k = 0; k < nocourse; k++) {
                                if (course[k].code.equals(stgrp[i].course[j - 1])) {
                                    course[k].stgrp[course[k].nostgrp++] = stgrp[i].code;
                                }
                            }
                        }
                        i++;
                    }
                    nostgrp = i;
                }
                //input instructors
                if (line.equalsIgnoreCase("instructors")) {
                    int i = 0, j, k;
                    String arr;
                    while (!(line = scanner.nextLine()).equalsIgnoreCase("classrooms")) {
                        ins[i] = new insdata();
                        StringTokenizer st = new StringTokenizer(line, " ");
                        ins[i].name = st.nextToken();
                        ins[i].code = st.nextToken();
                        j = 0;
                        while (st.hasMoreTokens()) {
                            ins[i].course[j++] = st.nextToken();
                            for (k = 0; k < nocourse; k++) {
                                if (course[k].code.equals(ins[i].course[j - 1])) {
                                    course[k].ins[course[k].noins++] = ins[i].code;
                                }
                            }
                        }
                        i++;
                    }
                    noins = i;
                }
                //input classrooms
                if (line.equalsIgnoreCase("classrooms")) {
                    int i = 0, j;
                    while (classformat(line = scanner.nextLine())) {
                        rooms[i] = new classdata();
                        StringTokenizer st = new StringTokenizer(line, " ");
                        rooms[i].code = st.nextToken();
                        rooms[i].department = st.nextToken();
                        rooms[i].strength = Integer.parseInt(st.nextToken());
                        i++;
                    }
                    noroom = i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int returnclassno() {
        return (noroom);
    }

    int returninsno() {
        return (noins);
    }

    int returnstgrpno() {
        return (nostgrp);
    }

    int returncourseno() {
        return (nocourse);
    }
}
