package project;
import java.util.ArrayList;
import java.util.List;

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
	
	public void displayTable() {
		int i = 0;
		for (ArrayList<String> column : table) {
			for (String columnElement : column) {
				System.out.print(columnElement);
				System.out.print(" ");
			}
			System.out.println("");
			i += 1;
		}
	}
	
	public void displayTable(List<String> requestColumns) {
		int i = 0;
		for (ArrayList<String> column : table) {
			for (String requestColumn : requestColumns) {
				if (column.get(0).equals(requestColumn)) {
					for (String columnElement : column) {
						System.out.print(columnElement);
					}
					System.out.println("");
				}
			}
			i += 1;
		}
	}
	
	private void displayRow(int id) {
		for (int j = 0; j < this.table.size(); j++) {
			String elem = this.table.get(j).get(id);
			System.out.println(elem);
		}
	}
	
	public void whereRequest(String whereColumn, String whereValue, String whereComparator) {
		for (int i = 0; i < this.table.get(0).size(); i++) {
			for (int j = 0; j < this.table.size(); j++) {
				String elem = this.table.get(j).get(i);
				String tableColumnName = this.table.get(j).get(0);
				if (tableColumnName.equals(whereColumn) && elem.compareTo(whereValue) == 0 && whereComparator.equals("=")) {
					displayRow(i);
				}
				
				if (tableColumnName.equals(whereColumn) && elem.compareTo(whereValue) < 0 && whereComparator.equals("<")) {
					displayRow(i);
				}
				
				if (tableColumnName.equals(whereColumn) && elem.compareTo(whereValue) > 0 && whereComparator.equals(">")) {
					displayRow(i);
				}
			}
		}
	}

}
