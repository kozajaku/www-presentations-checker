/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.cssredundancychecker.model;

/**
 * <p>
 * DeclarationPosition class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class DeclarationPosition {

    protected int line;
    protected int col;

    /**
     * <p>
     * Constructor for DeclarationPosition.</p>
     *
     * @param line a int.
     * @param col a int.
     */
    public DeclarationPosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return line + ":" + col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.line;
        hash = 29 * hash + this.col;
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeclarationPosition other = (DeclarationPosition) obj;
        if (this.line != other.line) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Getter for the field <code>line</code>.</p>
     *
     * @return a int.
     */
    public int getLine() {
        return line;
    }

    /**
     * <p>
     * Getter for the field <code>col</code>.</p>
     *
     * @return a int.
     */
    public int getCol() {
        return col;
    }

}
