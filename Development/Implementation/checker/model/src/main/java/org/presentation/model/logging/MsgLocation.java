package org.presentation.model.logging;

/**
 * This class represents location of
 * {@link org.presentation.model.logging.Message} in page content.
 * {@link org.presentation.model.logging.MsgLocation} is given by row and column
 * position of content which is this
 * {@link org.presentation.model.logging.Message} related to.
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
     * Creates new instance of
     * {@link org.presentation.model.logging.MsgLocation} with row and column
     * position of {@link org.presentation.model.logging.Message} in page
     * content.
     *
     * @param row {@link java.lang.Integer} row on which is the
     * {@link org.presentation.model.logging.Message} located
     * @param column {@link java.lang.Integer} column on which is the
     * {@link org.presentation.model.logging.Message} located
     */
    public MsgLocation(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns column on which is this
     * {@link org.presentation.model.logging.Message} located.
     *
     * @return {@link java.lang.Integer} column on which is the
     * {@link org.presentation.model.logging.Message} located
     */
    public Integer getColumn() {
        return column;
    }

    /**
     * Returns row on which is this
     * {@link org.presentation.model.logging.Message} located.
     *
     * @return {@link java.lang.Integer} row on which is the
     * {@link org.presentation.model.logging.Message} located
     */
    public Integer getRow() {
        return row;
    }

}
