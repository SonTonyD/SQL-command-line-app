package project;
import java.util.ArrayList;

public class Table {
	
	private Statement statement;
	private String tableName;
	private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
	
	public Table(Statement statement) {
		this.setTableName(statement.getTableName());
		for (int i = 0; i < statement.getColumns().size(); i++) {
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add(statement.getColumns().get(i));
			table.add(tmp);
		}
	}
	
	public ArrayList<ArrayList<String>> getTable() {
		return table;
	}
	

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
