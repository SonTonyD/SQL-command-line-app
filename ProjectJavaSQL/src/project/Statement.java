package project;

import java.util.List;

public class Statement {

	private  List<String> columns;
	private  List<String> types;
	private  List<String> values;
	private  String tableName;
	
	public Statement(String tableName) {
		this.tableName = tableName;
	}
	
	public Statement(List<String> columns, String tableName) {
		this.columns = columns;
		this.tableName = tableName;
	}
	
	public Statement(List<String> columns, List<String> types, String tableName) {
		this.columns = columns;
		this.types = types;
		this.tableName = tableName;
	}
	
	public Statement(List<String> columns, List<String> values, String tableName, int numberOfColumn) {
		this.columns = columns;
		this.values = values;
		this.tableName = tableName;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public  List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	

}
