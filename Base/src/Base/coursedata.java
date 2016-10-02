/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base;

/**
 *
 * @author ang_2
 */
public class coursedata {

    String name, code, department;
    String[] stgrp, ins;
    int credits, nostgrp, noins;
    ;
	char lab;

    public coursedata() {
        name = new String();
        code = new String();
        department = new String();
        stgrp = new String[100];
        ins = new String[100];
        nostgrp = 0;
        noins = 0;
    }
}
