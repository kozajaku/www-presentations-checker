package org.presentation.model.logging;

import org.presentation.model.LinkURL;

/**
 *
 * @author Jindřich Máca
 */
public abstract class Message {

    protected static final int MAX_PRIORITY_BOOST  = 50;
    
    private Integer priority = null;
    private String message;
    private LinkURL page;
    private MsgLocation msgLocation;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LinkURL getPage() {
        return page;
    }

    public void setPage(LinkURL page) {
        this.page = page;
    }

    public MsgLocation getMsgLocation() {
        return msgLocation;
    }

    public void setMsgLocation(MsgLocation msgLocation) {
        this.msgLocation = msgLocation;
    }
    
    public void setIntoMapper(MessageMapper mapper){
        mapper.setMessage(message);
        mapper.setPage(page);
        mapper.setMsgLocation(msgLocation);
        mapper.setDiscriminator(this.getClass().getName());
    }
    
    public void setFromMapper(MessageMapper mapper){
        this.message = mapper.getMessage();
        this.page = mapper.getPage();
        this.msgLocation = mapper.getMsgLocation();
    }
       
    public void setPriority(int priority) throws Exception {
	setPriorityBoost(priority - this.getDefaultPriority());
    }
    
    public void setPriorityBoost(int priorityBoost) throws Exception {
	if(priorityBoost < -MAX_PRIORITY_BOOST || priorityBoost > MAX_PRIORITY_BOOST) {
	    throw new Exception("Invalid message priority set!");
	}
	this.priority = this.getDefaultPriority() + priorityBoost;
    }
    
    public int getPriority(){
	return this.priority == null ? this.getDefaultPriority() : this.priority;
    }
    
    public abstract int getDefaultPriority();    

}
