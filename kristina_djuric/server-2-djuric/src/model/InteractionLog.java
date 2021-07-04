package model;

import java.time.LocalDateTime;

public class InteractionLog {
	
	private Integer interactionId;
	private LocalDateTime date;
	private String fileName;
	private ActionType actionType;
	 
	public InteractionLog() {
		super();
	}

	public InteractionLog(Integer interactionId, LocalDateTime date, String fileName, ActionType actionType) {
		super();
		this.interactionId = interactionId;
		this.date = date;
		this.fileName = fileName;
		this.actionType = actionType;
	}

	public InteractionLog(LocalDateTime date, ActionType actionType) {
		super();
		this.date = date;
		this.actionType = actionType;
	}

	
	public InteractionLog(LocalDateTime date, String fileName, ActionType actionType) {
		super();
		this.date = date;
		this.fileName = fileName;
		this.actionType = actionType;
	}

	public Integer getInteractionId() {
		return interactionId;
	}

	public void setInteractionId(Integer interactionId) {
		this.interactionId = interactionId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
	
	
}
