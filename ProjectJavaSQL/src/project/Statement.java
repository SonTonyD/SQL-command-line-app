package project;

import java.util.List;

public class Statement {

	private static List<String> columns;
	private static List<String> types;
	private static String tableName;
	
	public Statement(List<String> columns, String tableName) {
		this.columns = columns;
		this.tableName = tableName;
	}
	
	public Statement(List<String> columns, List<String> types, String tableName) {
		this.columns = columns;
		this.types = types;
		this.tableName = tableName;
	}
	
	public static List<String> getColumns() {
		return columns;
	}
	public static void setColumns(List<String> columns) {
		Statement.columns = columns;
	}

	public static List<String> getTypes() {
		return types;
	}

	public static void setTypes(List<String> types) {
		Statement.types = types;
	}

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		Statement.tableName = tableName;
	}

}
