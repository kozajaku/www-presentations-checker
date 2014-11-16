package org.presentation.model.graph;

/**
 * This class represents source of link.
 *
 * @author Adam Kugler
 * @version 1.0
 */
public enum LinkSourceType {

    /**
     * Link from &lt;a href=...&gt;.
     */
    A_HREF,
    /**
     * Link from &lt;img src=...&gt;.
     */
    IMG_SRC,
    /**
     * Link from &lt;link href=... type!=text/css &gt;.
     */
    LINK_HREF,
    /**
     * Link from &lt;link href=... type=text/css &gt;.
     */
    LINK_HREF_CSS,
    /**
     * Link from &lt;script src=...&gt;.
     */
    SCRIPT_SRC,
    /**
     * Link from CSS file.
     */
    INSIDE_CSS,
    /**
     * Unknown source.
     */
    UNKNOWN
}
