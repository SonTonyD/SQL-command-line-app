package project;

import java.util.ArrayList;
import java.util.List;

public class StatementBuilder {
	private  List<String> columns;
	private  List<String> types;
	private  List<String> values;
	private  String tableName;
	private  String whereColumn;
	private  String whereValue;
	private  String whereComparator;
	
	
	public StatementBuilder() {
		this.reset();
	}
	
	public void reset() {
		this.columns = new ArrayList<String>();
		this.types = new ArrayList<String>();
		this.values = new ArrayList<String>();
		this.tableName = "";
		this.whereColumn = "";
		this.whereValue = "";
		this.whereComparator = "";
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}


	public void setTypes(List<String> types) {
		this.types = types;
	}


	public void setValues(List<String> values) {
		this.values = values;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setWhereColumn(String whereColumn) {
		this.whereColumn = whereColumn;
	}
	
	public void setWhereValue(String whereValue) {
		this.whereValue = whereValue;
	}
	
	public void setWhereComparator(String whereComparator) {
		this.whereComparator = whereComparator;
	}
	
	public Statement getResult() {
		return new Statement(this.columns, this.types, this.values, this.tableName, this.whereColumn, this.whereValue, this.whereComparator);
	}

	

	
	
	
}
