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
        
        //Uncomment this section for real case
        /*
        while (true) {
        	String input = sc.nextLine();
            
        	try {
        		Statement SQL = parseManager(input);
                requestHandler(input, SQL, database);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}  
        }
        */
        //Uncomment this section for real case
        
        //Test
        
        String command = "CREATE TABLE t (name string, surname string);";
        Statement SQL = parseManager(command);
        requestHandler(command, SQL, database);
        
        command = "INSERT INTO t (name, surname) VALUES (sontony, dinh);";
        SQL = parseManager(command);
        requestHandler(command, SQL, database);
        
        command = "INSERT INTO t (name, surname) VALUES (remi, derville);";
        SQL = parseManager(command);
        requestHandler(command, SQL, database);
        
        
        while (true) {
        	String input = sc.nextLine();
            
        	try {
        		SQL = parseManager(input);
                requestHandler(input, SQL, database);
        	} catch (Exception e) {
        		System.out.println("Wrong input");
        	}
        }
        
        //Test
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static Statement parseManager(String input) {
		StatementBuilder builder = new StatementBuilder();
		
		String[] tokens = input.split("\\s+");
		if (tokens[0].equalsIgnoreCase("SELECT")) {
			parseSelectStatement(input, builder);
		}
		else if (tokens[0].equalsIgnoreCase("CREATE")) {
			parseCreateTableStatement(input, builder);
		}
		else if (tokens[0].equalsIgnoreCase("INSERT")) {
			parseInsertStatement(input, builder);
		}
		else if (tokens[0].equalsIgnoreCase("UPDATE")) {
			parseUpdateStatement(input, builder);
		}
		else if (tokens[0].equalsIgnoreCase("DELETE")) {
			parseDeleteStatement(input, builder);
		}
		
		parseWhereCondition(input, builder); 
		
		return builder.getResult();
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
        
        if (input.split("\\s+")[0].equalsIgnoreCase("DELETE")) {
        	manageDeleteStatement(database, SQL);
        }
	}
	
	private static void parseSelectStatement(String input, StatementBuilder builder) {
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
	    
	    builder.setTableName(tableName);
	    builder.setColumns(columns);
	    
	}
	
	private static void parseCreateTableStatement(String input, StatementBuilder builder) {
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
        
        builder.setTableName(tableName);
	    builder.setColumns(columns);
	    builder.setTypes(types);
	    
	}
	
	private static void parseInsertStatement(String input, StatementBuilder builder) {
		String regex = "INSERT INTO (\\w+) \\(([\\w\\s,]+)\\) VALUES \\(([\\w\\s,]+)\\);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        List<String> requestColumns = new ArrayList<>();
        List<String> requestValues = new ArrayList<>();
        String requestTableName = "";
        
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] columns = matcher.group(2).split(", ");
            String[] values = matcher.group(3).split(", ");

            requestTableName = tableName;
            
            for (int i = 0; i < columns.length; i++) {
                requestColumns.add(columns[i]);
                requestValues.add(values[i]);
                
            }
        } else {
        	throw new RuntimeException("Invalid insert input");
        }
        
        builder.setTableName(requestTableName);
	    builder.setColumns(requestColumns);
	    builder.setValues(requestValues);
        
	}
	
	private static void parseUpdateStatement(String input, StatementBuilder builder) {
		String regex = "UPDATE (\\w+) SET ([\\w\\s=,]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        List<String> requestColumns = new ArrayList<>();
        List<String> requestValues = new ArrayList<>();
        String requestTableName = "";
        
        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] updates = matcher.group(2).split(", ");

            requestTableName = tableName;
            
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
        
        builder.setTableName(requestTableName);
	    builder.setColumns(requestColumns);
	    builder.setValues(requestValues);
		
	}
	
	private static void parseDeleteStatement (String input, StatementBuilder builder) {
		String regex = "DELETE FROM (\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        
        String requestTableName = "";

        if (matcher.find()) {
            String tableName = matcher.group(1);
            requestTableName = tableName;
        } else {
        	throw new RuntimeException("Invalid delete input");
        }
        
        builder.setTableName(requestTableName);

	}
	
	private static void parseWhereCondition(String input, StatementBuilder builder) {
		String regex = "WHERE (\\w+) (\\S+) (\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String column = matcher.group(1);
            String comparator = matcher.group(2);
            String value = matcher.group(3);
            
            builder.setWhereColumn(column);
            builder.setWhereComparator(comparator);
            builder.setWhereValue(value);
        }
        
	}
	
	private static void manageSelectStatement(List<Table> database, Statement SQL) {
		for (Table table : database) {
    		if (table.getTableName().equals(SQL.getTableName())) {
    			
    			if (SQL.getWhereColumn().equals("")) {
    				if (SQL.getColumns().get(0).equals("*")) {
                    	System.out.println("Print all columns of the table");
                    	table.displayTable();
                    } else {
                    	System.out.println("Print specific columns of the table");
                    	table.displayTable(SQL.getColumns());
                    }
    			} else {
    				String whereColumn = SQL.getWhereColumn();
    				String whereComparator = SQL.getWhereComparator();
    				String whereValue = SQL.getWhereValue();
    				
    				table.whereRequest(whereColumn, whereValue, whereComparator, SQL.getColumns());
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
	
	private static void manageDeleteStatement (List<Table> database, Statement SQL) {
		for (Table table : database) {
			if (table.getTableName().equals(SQL.getTableName())) {
    			
				for (int i = 0; i < table.getTable().size(); i++) {
					//Where condition here
					for (int k = table.getTable().get(i).size()-1; k >= 1; k--) {
						table.getTable().get(i).remove(k);
						System.out.println("element deleted");
					}			
				}
    		}
		}
	}
	

}
