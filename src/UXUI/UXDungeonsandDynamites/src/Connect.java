import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;

/**
 * @author Maxwell Crawford
 * Connect module for Java/JDBC to MSSQL backend.
 * Has support for basic executeQuery/execute statements.
 * DESKTOP JAVA version!
 */
public class Connect 
{
	/**
	 * General variables
	 */
	String connectionString = "";
	Connection connection = null;
//	String countQuery = "SELECT COUNT(*) FROM";
	
	
	/**
	 * Constructor, which sets up the connectionstring
	 * to use. Can be re-set again later.
	 * @param connectionString - the string which determines which server/db to use and how
	 */
	public Connect(String connectionString) {
		super();
		this.connectionString = connectionString;
	}

	/**
	 * connectSelect - used for Select statements and 
	 * queries which return some 'ResultSet'.
	 * 
	 * Can use the ResultSet to retrieve and store data!
	 * @param SQLQuery - the specified SQL Select statement
	 * @param tableName - the current table name, used for count of rows
	 */
	public ArrayList<String[]> connectSelect(String SQLQuery)
	{
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		// 1) Establish initial connection
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(connectionString);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// 2) Grab results from DB and store in 2D array
		try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLQuery))
        {
            ResultSetMetaData rsmd = resultSet.getMetaData(); //needed for column data/indices
            int columns = rsmd.getColumnCount();

            while (resultSet.next())
            {
            	String[] row = new String[columns];
            	for (int i=1; i<=columns; i++)
            	{
            		row[i-1] = resultSet.getString(i);
            	}
            	result.add(row);
            }

            connection.close();
            statement.close();
            resultSet.close();
        }

        catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * connectExecute - Used for Insert/Update/Delete and other
	 * statements which execute an action, but don't return data.
	 * 
	 * @param SQLQuery - the specified SQL statement for execution
	 */
	public void connectExecute(String SQLQuery)
	{
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(connectionString);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		try (Statement exstatement = connection.createStatement())
		{
            exstatement.execute(SQLQuery);
            
            exstatement.close();
            connection.close();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * login - Parses int from Player ID string,
	 * checks DB IDs, and compares given ID to existing set.
	 * @param playerID - given Player_ID, as string
	 * @return isValid - true if ID is valid and exists; false if invalid/doesn't exist
	 */
	public boolean login(String playerID)
	{
      boolean isValid = false;
      int Player_ID = 0;
      int tempID = 0;
      
      // CHECK 1: See if input is a valid number
      while (!isValid)
      {
          try
          {
          	tempID = Integer.parseInt(playerID);
          }
          
          catch (Exception e)
          {
        	isValid = false;
        	return isValid;
          }
          
          // Was valid number
          Player_ID = tempID;
          isValid = true;
      }
      
      // Get current IDs in DB:
      String LoginQuery = "SELECT [Player_ID] FROM [dbo].[Player]";
      
      ArrayList<String[]> result = this.connectSelect(LoginQuery);
      String[] currentIDs = new String[result.size()];
      for (int i=0; i<result.size(); i++)
      {
      	currentIDs[i] = result.get(i)[0]; //0th index since Player_ID has only 1 char.
      }
      
      isValid = false; //reset to check again
      
      // CHECK 2: See if given ID is actually in DB
      for (int i=0; i<currentIDs.length; i++)
      {
      	tempID = this.parseIntsFromResult(currentIDs[i])[0];
      	if (tempID == Player_ID)
      	{
      		isValid = true;
      		break;
      	}
      	
      }
      
      return isValid;
	}
	
	/**
	 * register - Inserts a default row into Player table,
	 * then grabs the newest Player_ID for the player,
	 * for later usage.
	 * @return lastID - new Player_ID
	 */
	public int register()
	{
		//Insert new default row in Player
        String newPlayerQuery = "INSERT INTO [dbo].[Player] DEFAULT VALUES";
        this.connectExecute(newPlayerQuery);
        
        // Check current IDs in DB
        String LoginQuery = "SELECT [Player_ID] FROM [dbo].[Player]";
        ArrayList<String[]> result = this.connectSelect(LoginQuery);
        String[] currentIDs = new String[result.size()];
        for (int i=0; i<result.size(); i++)
        {
        	currentIDs[i] = result.get(i)[0]; //0th index since Player_ID fits into first index
        }
        
        // Grab only the last ID:
        int lastID = this.parseIntsFromResult(currentIDs[currentIDs.length-1])[0];
        return lastID;
	}
	
	/**
	 * getReport - gets nicely formatted report of Player's
	 * game statistics and score.
	 * Player_ID can be int OR string, as long as it is valid.
	 * @param Player_ID - ID number which is matched to row in Player table
	 * @return report - the pre-formatted report, as one string.
	 */
	public String getReport(int Player_ID)
	{
		String reportQuery = "SELECT * FROM [dbo].[Player] WHERE Player_ID=" + Player_ID;
        ArrayList<String[]> result = this.connectSelect(reportQuery);
        int rowsize = result.get(0).length;
        String[] row = new String[rowsize];
        for (int i=0; i<rowsize; i++)
        {
        	row[i] = result.get(0)[i]; //0th index since Player_ID has only 1 char.
        }
        
        String report = "Report for Player:\n================================================\n";
        report += "Player_ID = " + row[0] + "\t";
        report += "Game_ID = " + row[1] + "\n";
        report += "You are " + row[2] + " the " + row[3] + ",\n";
        report += " with " + row[4] + " Health and " + row[5] + " Mana.\n";
        report += "Dexterity: " + row[6] + " , Intelligence: " + row[7] + "\n";
        report += "Agility: " + row[8] + " , Defense: " + row[9] + "\n";
        report += "Current Inventory: " + row[10] + "\n";
        report += "# of Successes = " + row[11] + ", # of Fails = " + row[12] + "\n";
        report += "Total Score = " + row[13];
        return report;
	}
	
	public String getReport(String Player_ID)
	{
		String reportQuery = "SELECT * FROM [dbo].[Player] WHERE Player_ID=" + Player_ID;
        ArrayList<String[]> result = this.connectSelect(reportQuery);
        int rowsize = result.get(0).length;
        String[] row = new String[rowsize];
        for (int i=0; i<rowsize; i++)
        {
        	row[i] = result.get(0)[i]; //0th index since Player_ID has only 1 char.
        }
        
        String report = "Report for Player:\n================================================\n";
        report += "Player_ID = " + row[0] + "\t";
        report += "Game_ID = " + row[1] + "\n";
        report += "You are " + row[2] + " the " + row[3] + ",\n";
        report += " with " + row[4] + " Health and " + row[5] + " Mana.\n";
        report += "Dexterity: " + row[6] + " , Intelligence: " + row[7] + "\n";
        report += "Agility: " + row[8] + " , Defense: " + row[9] + "\n";
        report += "Current Inventory: " + row[10] + "\n";
        report += "# of Successes = " + row[11] + ", # of Fails = " + row[12] + "\n";
        report += "Total Score = " + row[13];
        return report;
	}

	/**
	 * @return the connectionString
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @param connectionString the connectionString to set
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * getStringAtIndex - using the given String array and index number,
	 * return the string at the index.
	 * @param inputstr - array of strings retrived from database row
	 * @param index - integer location of target string
	 * @return strresult - the target string to parse
	 */
	public String[] getRowAtIndex(ArrayList<String[]> inputstr, int index)
	{
		String strresult[] = inputstr.get(index);		
		return strresult;
	}
	
	/**
	 * getStringAtIndex - using the given String array and index number,
	 * return the string at the index.
	 * @param inputstr - array of strings retrived from database row
	 * @param index - integer location of target string
	 * @return strresult - the target string to parse
	 */
	public String getStringAtIndex(String[] inputstr, int index)
	{
		String strresult = inputstr[index];		
		return strresult;
	}
	
	/**
	 * parseIntsFromResult - With inputstr from database row,
	 * parse the specific integers from the String and place
	 * into an array of actual integers.
	 * @param inputstr - the database row, as String
	 * @return intresult - array of int
	 */
	public int[] parseIntsFromResult(String inputstr)
	{
		// Check if all one number as string, or multiple,
		// with commas, etc.
		int[] intresult = null;
		int hasParen = inputstr.indexOf("(");
		int hasSpace = inputstr.indexOf(" ");
		int hasComma = inputstr.indexOf(",");
		
		if ((hasParen>=0) || (hasSpace>=0) || (hasComma>=0))
		{
		
			// 1) Initialize temp Int array to total length of String
			// and check each character in String if it is a valid number
			int[] inttemp = new int[inputstr.length()];
			int intcount = 0;
			
			for (int i=0; i<inputstr.length(); i++)
			{
				char temp = inputstr.charAt(i);
				boolean isNumber = Character.isDigit(temp);
				if (isNumber)
				{
					intcount +=1;
					inttemp[i] = Character.getNumericValue(temp);
				}
				
				else
				{
					inttemp[i] = -1;
				}
			}
			
			// 2) Initialize actual result Int array, from number of valid digits
			// recorded above, and place those ints in the new array, in correct order.
			intresult = new int[intcount];
			int tempcount = 0;
			for (int i=0; i<inttemp.length; i++)
			{
				if (inttemp[i] < 0)
				{
					continue;
				}
				
				else
				{
					intresult[tempcount] = inttemp[i];
					tempcount +=1;
				}
			}
		
		}
		
		else
		{
			intresult = new int[1];
			intresult[0] = Integer.parseInt(inputstr);
		}
		return intresult;
	}
}
