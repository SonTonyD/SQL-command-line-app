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
            
            if (input.split("\\s+")[0].equalsIgnoreCase("CREATE")) {
            	Table table = new Table(SQL);
            	database.add(table);
            	System.out.println("Table created");
            }
            
            if (input.split("\\s+")[0].equalsIgnoreCase("SELECT")) {
            	for (Table table : database) {
            		if (table.getTableName().equals(SQL.getTableName())) {
            			if (SQL.getColumns().get(0).equals("*")) {
                        	System.out.println("Print all columns of the table");
                        	table.displayTable();
                        }
            		}
            	}
            }
        }
        
        
        /*
        System.out.println(SQL.getColumns().get(0));
        System.out.println(SQL.getColumns().get(1));
        System.out.println(SQL.getColumns().get(2));
        System.out.println(SQL.getTable());
        */
        
	}
	
	private static Statement parseManager(String input) {
		String[] tokens = input.split("\\s+");
		if (tokens[0].equalsIgnoreCase("SELECT")) {
			return parseSelectStatement(input);
		}
		else if (tokens[0].equalsIgnoreCase("CREATE")) {
			return parseCreateTableStatement(input);
		}
		else {
			return parseSelectStatement(input);
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
            throw new RuntimeException("Invalid input");
        }
	    
	    return new Statement(columns, types, tableName);
	}
	

}
