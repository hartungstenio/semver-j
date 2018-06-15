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
package io.github.hartungstenio.semver;

/**
 * Exception used to indicate a problem while parsing a version.
 * <p>
 * This exception is used to indicate problems with creating,
 * and manipulating version objects.
 * 
 * @implSpec
 * This class is intended for use in a single thread.
 * 
 * @author  Christian Hartung
 */
public class SemVerException extends RuntimeException {
    
    /**
     * Serialization version
     */
	private static final long serialVersionUID = 1430388494494533110L;
	
    /**
     * Constructs a new version exception with the specified detail message. 
     *
     * @param message  the message to use for this exception, may be null
     */
    public SemVerException(final String message) {
    	super(message);
    }
    
    /**
     * Constructs a new version exception with the specified message and cause.
     *
     * @param message  the message to use for this exception, may be null
     * @param cause    the cause of the exception, may be null
     */
    public SemVerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
