package co.com.tactusoft.crm.view.beans;

import java.io.File;
import java.io.Serializable;

public class RIPSBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private File file;
	private int rowsNumber;

	public RIPSBean() {

	}

	public RIPSBean(File file, int rowsNumber) {
		super();
		this.file = file;
		this.rowsNumber = rowsNumber;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getRowsNumber() {
		return rowsNumber;
	}

	public void setRowsNumber(int rowsNumber) {
		this.rowsNumber = rowsNumber;
	}

}
