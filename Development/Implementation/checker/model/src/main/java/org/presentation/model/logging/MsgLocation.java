package org.presentation.model.logging;

/**
 * Represents location of message content.
 *
 * @author Jindřich Máca
 */
public class MsgLocation {

    /**
     * Row on which is message located.
     */
    private final Integer row;
    /**
     * Column on which is message located.
     */
    private final Integer column;

    /**
     * Constructs a message location.
     *
     * @param row Row on which is message located.
     * @param column Column on which is message located.
     */
    public MsgLocation(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns column on which is message located.
     *
     * @return Column on which is message located.
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * Returns row on which is message located.
     *
     * @return Row on which is message located.
     */
    public Integer getRow() {
        return row;
    }

}
