/*
 * This file is part of the SemVer-J project.
 * Copyright (c) 2015 Christian Hartung
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution.
 *
 * Contributors:
 *     Christian Hartung - initial API and implementation
 */
package net.hobbysw.semver;

/**
 * Thrown to indicate that the application has attempted to convert 
 * a string to a semantic version, but that the string does not 
 * have the appropriate format. 
 *
 * @author  Christian Hartung
 */
public class VersionFormatException extends IllegalArgumentException {
	private static final long serialVersionUID = 1430388494494533110L;
	
	/**
     * Constructs a <code>VersionFormatException</code> with no detail message.
     */
    public VersionFormatException() {
    	super();
    }

    /**
     * Constructs a <code>VersionFormatException</code> with the 
     * specified detail message. 
     *
     * @param   s   the detail message.
     */
    public VersionFormatException(String s) {
    	super(s);
    }
    
    /**
     * Factory method for making a <code>VersionFormatException</code>
     * given the specified input which caused the error.
     *
     * @param   s   the input causing the error
     */
    static VersionFormatException forInputString(String s) {
        return new VersionFormatException("For input string: \"" + s + "\"");
    }
}
