package co.com.tactusoft.crm.util;

public class CrmException extends Exception {
	private static final long serialVersionUID = 1L;

	private String error;

	public CrmException() {
		super(); // call superclass constructor
		error = "unknown";
	}

	public CrmException(String err) {
		super(err); // call super class constructor
		error = err; // save message
	}

	public String getError() {
		return error;
	}
}
