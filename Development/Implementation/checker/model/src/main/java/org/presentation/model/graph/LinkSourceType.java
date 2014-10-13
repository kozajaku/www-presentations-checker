package org.presentation.model.graph;

/**
 * This class represents source of link
 * @author Adam Kugler
 * @version 1.0
 */
public enum LinkSourceType {
    /**
     * Link from &lta href=...&gt.
     */
    A_HREF,
    /**
     * Link from &ltimg src=...&gt.
     */
    IMG_SRC,
    /**
     * Link from &ltlink href=...&gt.
     */
    LINK_HREF,
    /**
     * Link from &ltscript src=...&gt.
     */
    SCRIPT_SRC,
    /**
     * Link from CSS file.
     */
    INSIDE_CSS
}
