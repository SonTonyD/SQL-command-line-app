package project;
import java.util.ArrayList;

public class Table {
	
	private Statement statement;
	
	public Table(Statement statement) {
		for (String column : statement.getColumns()) {
			
		}
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

}
