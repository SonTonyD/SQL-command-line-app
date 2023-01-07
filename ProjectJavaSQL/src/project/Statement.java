package project;

import java.util.List;

public class Statement {

	private  List<String> columns;
	private  List<String> types;
	private  String tableName;
	
	public Statement(List<String> columns, String tableName) {
		this.columns = columns;
		this.tableName = tableName;
	}
	
	public Statement(List<String> columns, List<String> types, String tableName) {
		this.columns = columns;
		this.types = types;
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
	

}
