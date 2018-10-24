package ru.defo.model.local.advice;

public class Client {
	private String code;
	private String description;

	public String getCode() { return code; }

	public void setCode(String code) { this.code = code; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	@Override
	public String toString()
	{
		return "Code: ["+getCode()+"] Description: ["+getDescription()+"]";
	}
}
