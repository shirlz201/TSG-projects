/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author shirl
 */
public class FlooringMasteryAuditDaoFileImpl implements FlooringMasteryAuditDao {

    public static final String AUDIT_FILE = "audit.txt";

    public void writeAuditEntry(String entry) throws PersistenceException {
        //purpose of PW is to write out to a file
        PrintWriter out;

        try {    //file its being written to (true)- is making it so it doesnt overwrite
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new PersistenceException("Could not persist audit information.", e);
        }
        //Localdatetime datatype stores the current date and time
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        //makes sure the text is in the file before it moves on
        out.flush();
    }

}
