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
public class gene {

    int[] g;

    gene(int course, int stdgrp, int ins) {
        g = new int[Integer.toBinaryString(course).length() + Integer.toBinaryString(stdgrp).length() + Integer.toBinaryString(ins).length()];
    }
}
