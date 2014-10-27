package org.presentation.model.logging;

/**
 * This class represents location of {@link Message} in page content.
 * {@link MsgLocation} is given by row and column position of content which is
 * this {@link Message} related to.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
public class MsgLocation {

    //Row on which is message located
    private final Integer row;
    //Column on which is message located
    private final Integer column;

    /**
     * Creates new instance of {@link MsgLocation} with row and column position
     * of {@link Message} in page content.
     *
     * @param row {@link Integer} row on which is the {@link Message} located
     * @param column {@link Integer} column on which is the {@link Message}
     * located
     */
    public MsgLocation(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns column on which is this {@link Message} located.
     *
     * @return {@link Integer} column on which is the {@link Message} located
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * Returns row on which is this {@link Message} located.
     *
     * @return {@link Integer} row on which is the {@link Message} located
     */
    public Integer getRow() {
        return row;
    }

}
