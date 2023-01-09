package project;

import java.util.List;

public class Statement {

	private  List<String> columns;
	private  List<String> types;
	private  List<String> values;
	private  String tableName;
	private  String whereColumn;
	private  String whereValue;
	private  String whereComparator;
	
	public Statement(List<String> columns, List<String> types, List<String> values,
			String tableName, String whereColumn, String whereValue, String whereComparator) 
	{
		this.columns = columns;
		this.types = types;
		this.values = values;
		this.tableName = tableName;
		this.whereColumn = whereColumn;
		this.whereValue = whereValue;
		this.whereComparator = whereComparator;
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public  List<String> getTypes() {
		return types;
	}

	public String getTableName() {
		return tableName;
	}

	public List<String> getValues() {
		return values;
	}

	public String getWhereColumn() {
		return whereColumn;
	}

	public String getWhereValue() {
		return whereValue;
	}

	public String getWhereComparator() {
		return whereComparator;
	}

}
