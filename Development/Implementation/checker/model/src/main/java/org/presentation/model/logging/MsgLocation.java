package org.presentation.model.logging;

/**
 *
 * @author Jindřich Máca
 */
public class MsgLocation {

    private final Integer row;
    private final Integer column;

    public MsgLocation(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

}
