package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        System.out.println("Enter a SQL statement:");
        
        List<Table> database = new ArrayList<>();
        
        while (true) {
        	String input = sc.nextLine();
            
            Statement SQL = parseManager(input);
            
            requestHandler(input, SQL, database);
            
        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static Statement parseManager(String input) {
		String[] tokens = input.split("\\s+");
		if (tokens[0].equalsIgnoreCase("SELECT")) {
			return parseSelectStatement(input);
		}
		else if (tokens[0].equalsIgnoreCase("CREATE")) {
			return parseCreateTableStatement(input);
		}
		else if (tokens[0].equalsIgnoreCase("INSERT")) {
			return parseInsertStatement(input);
		}
		else if (tokens[0].equalsIgnoreCase("UPDATE")) {
			return parseUpdateStatement(input);
		}
		else {
			return parseSelectStatement(input);
		}
		
	}
	
	private static void requestHandler(String input, Statement SQL, List<Table> database) {
		if (input.split("\\s+")[0].equalsIgnoreCase("CREATE")) {
        	Table table = new Table(SQL);
        	database.add(table);
        	System.out.println("Table created");
        }
        
        if (input.split("\\s+")[0].equalsIgnoreCase("SELECT")) {
        	manageSelectStatement(database, SQL);
        }
        
        if (input.split("\\s+")[0].equalsIgnoreCase("INSERT")) {
        	manageInsertStatement(database, SQL);
        }
        
        if (input.split("\\s+")[0].equalsIgnoreCase("UPDATE")) {
        	manageUpdateStatement(database, SQL);
        }
	}
	
	private static Statement parseSelectStatement(String input) {
		String newInput = input.replaceAll(",", " ");
		
		String[] tokens = newInput.split("\\s+");
		
	    
	    // Check that the first token is "SELECT"
	    if (!tokens[0].equalsIgnoreCase("SELECT")) {
	        throw new RuntimeException("Not a SELECT statement");
	    }
	    
	    //Parse Column
	    List<String> columns = new ArrayList<>();
	    int i = 1;
	    while (i < tokens.length && !tokens[i].equalsIgnoreCase("FROM")) {
	        columns.add(tokens[i]);
	        i++;
	    }
	    
	    // Check that there is a "FROM" token
	    if (i == tokens.length || !tokens[i].equalsIgnoreCase("FROM")) {
	        throw new RuntimeException("Missing FROM clause");
	    }
	    
	    // Parse the table name
	    String tableName = tokens[i + 1];   
	    
	    return new Statement(columns, tableName);
	}
	
	private static Statement parseCreateTableStatement(String input) {
		String regex = "CREATE TABLE (\\w+) \\(([\\w\\s,]+)\\);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        List<String> columns = new ArrayList<>();
        List<String> types = new ArrayList<>();
        String tableName = "";
        
		
        if (matcher.find()) {
            tableName = matcher.group(1);
            String[] columnsStr = matcher.group(2).split(", ");

            //System.out.println("Table Name: " + tableName);
            //System.out.println("Number of Columns: " + columnsStr.length);
            
            for (String column : columnsStr) {
                String[] parts = column.split(" ");
                String columnName = parts[0];
                String dataType = parts[1];
                columns.add(columnName);
                types.add(dataType);
            }
        } else {
            throw new RuntimeException("Invalid create table input");
        }
	    
	    return new Statement(columns, types, tableName);
	}
	
	private static Statement parseInsertStatement(String input) {
		String regex = "INSERT INTO (\\w+) \\(([\\w\\s,]+)\\) VALUES \\(([\\w\\s,]+)\\);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        List<String> requestColumns = new ArrayList<>();
        List<String> requestValues = new ArrayList<>();
        String requestTableName = "";
        int requestColumnSize = 0;
        
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] columns = matcher.group(2).split(", ");
            String[] values = matcher.group(3).split(", ");

            requestTableName = tableName;
            requestColumnSize = columns.length;
            
            for (int i = 0; i < columns.length; i++) {
                requestColumns.add(columns[i]);
                requestValues.add(values[i]);
                
            }
        } else {
        	throw new RuntimeException("Invalid insert input");
        }
        return new Statement(requestColumns, requestValues, requestTableName, requestColumnSize);
	}
	
	private static Statement parseUpdateStatement(String input) {
		String regex = "UPDATE (\\w+) SET ([\\w\\s=,]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        List<String> requestColumns = new ArrayList<>();
        List<String> requestValues = new ArrayList<>();
        String requestTableName = "";
        int numberOfUpdate = 0;
        
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] updates = matcher.group(2).split(", ");

            requestTableName = tableName;
            numberOfUpdate = updates.length;
            
            for (String update : updates) {
                String[] parts = update.split(" = ");
                String columnName = parts[0];
                String value = parts[1];
                requestColumns.add(columnName);
                requestValues.add(value);
            }
        } else {
        	throw new RuntimeException("Invalid update input");
        }
		
		return new Statement(requestColumns, requestValues, requestTableName, numberOfUpdate);
		
	}
	
	private static void manageSelectStatement(List<Table> database, Statement SQL) {
		for (Table table : database) {
    		if (table.getTableName().equals(SQL.getTableName())) {
    			if (SQL.getColumns().get(0).equals("*")) {
                	System.out.println("Print all columns of the table");
                	table.displayTable();
                } else {
                	System.out.println("Print specific columns of the table");
                	table.displayTable(SQL.getColumns());
                }
    			
    		}
    	}
	}
	
	private static void manageInsertStatement(List<Table> database, Statement SQL) {
		for (Table table : database) {
			if (table.getTableName().equals(SQL.getTableName())) {
    			
				for (int i = 0; i < SQL.getColumns().size(); i++) {
					for (int j = 0; j < table.getTable().size(); j++) {
						String tableColumnName = table.getTable().get(j).get(0);
						String requestColumnName = SQL.getColumns().get(i);
						String requestValue = SQL.getValues().get(i);
						
						if (tableColumnName.equals(requestColumnName)) {
							table.getTable().get(j).add(requestValue);
							System.out.println("element inserted");
						}
					}
				}
    		}
		}
	}
	
	private static void manageUpdateStatement (List<Table> database, Statement SQL) {
		for (Table table : database) {
			if (table.getTableName().equals(SQL.getTableName())) {
    			
				for (int i = 0; i < SQL.getColumns().size(); i++) {
					for (int j = 0; j < table.getTable().size(); j++) {
						String tableColumnName = table.getTable().get(j).get(0);
						String requestColumnName = SQL.getColumns().get(i);
						String requestValue = SQL.getValues().get(i);
						
						if (tableColumnName.equals(requestColumnName)) {
							//Where condition here
							for (int k = 1; k < table.getTable().get(j).size(); k++) {
								table.getTable().get(j).set(k, requestValue);
								System.out.println("element updated");
							}			
						}
					}
				}
				
    		}
		}
	}
	

}
