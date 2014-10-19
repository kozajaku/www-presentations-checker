package org.presentation.model.logging;

import org.presentation.model.LinkURL;

/**
 * This interface should serve as object for conversion between some specific
 * kind of {@link Message} or other similar object. Specific implementation is
 * used for conversion between the hierarchical object tree of these elements to
 * classes with linear instance fields which are used especially for persisting
 * into database.
 *
 * @author radio.koza
 */
public interface MessageMapper {

    /**
     * Set string message to implemented instance of MessageMapper.
     *
     * @param message String representing information about message
     */
    void setMessage(String message);

    /**
     * Set url of message source to implemented instance of MessageMapper.
     *
     * @param linkURL LinkURL object representing url of the message source
     */
    void setPage(LinkURL linkURL);

    /**
     * Set location to point on source of the message. This parameter is
     * voluntary in message and it is not necessary to be called.
     * Implementations must not require calling this method.
     *
     * @param msgLocation MsgLocation class instance representing source of
     * method
     */
    void setMsgLocation(MsgLocation msgLocation);

    /**
     * Set error code for this message. This method should be called only on
     * messages where the error code is known, Implementations must not require
     * calling this method, because this argument is voluntary in some kinds of
     * message implementations.
     *
     * @param errorCode ErrorCode class instance representing the error code
     * number. (e.g. 404 for Not Found)
     */
    void setErrorCode(ErrorCode errorCode);

    /**
     * Set priority for this message.
     *
     * @param priority Message priority in integer; priority must be valid or
     * the {@link IllegalArgumentException} will be thrown by the specific
     * implementation of {@link Message}
     */
    void setPriority(Integer priority);

    /**
     * Retrieve message string from the message mapped class. Method is used to
     * reconstruct instance of {@link Message} implementation from the mapped
     * class.
     *
     * @return String representing information about message
     */
    String getMessage();

    /**
     * Retrieve page from the message mapped class.
     *
     * @return LinkURL class instance representing link of page which the
     * message is connected with
     */
    LinkURL getPage();

    /**
     * Retrieve message location from the message mapped class.
     *
     * @return MsgLocation class instance representing location of page that the
     * message was created from
     */
    MsgLocation getMsgLocation();

    /**
     * Retrieve error code of the message. Note that error code can be null in
     * case the mapped message object is not representing implementation of
     * message which does not have error code by itself.
     *
     * @return ErrorCode class instance representing error code.
     */
    ErrorCode getErrorCode();

    /**
     * Retrieve priority of the message.
     *
     * @return Integer priority of message.
     */
    Integer getPriority();

    /**
     * Set disciminator of this message. Discriminator is the full class name of
     * the {@link Message} object implementation and it must be possible to
     * instantiate the message implementing object by use of java reflection
     * feature.
     *
     * @param discriminator String of fully represented class name of the
     * specific implementation of the {@link Message}; it is created by calling {@link Class#getName()
     * } method of the specific message implementation
     */
    void setDiscriminator(String discriminator);
}
