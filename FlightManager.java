import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.*;

public class FlightManager
{
	public static ArrayList<ArrayList<String>> airports;
	public static ArrayList<ArrayList<String>> flights;

	/*
		John Long
		Jamie McLoughlin
		-The main method performs the appropriate action if the user input is valid and the files exist
	*/
	public static void main (String [] args) throws IOException, ParseException
	{
		boolean readFile = readFilesIntoArrayLists();
		boolean input = validateInput(args);
		
		if(!readFile)
			displayMessage(8);
		else
		{
			if(input)
			{
				switch(args[0])
				{
					case "AA": addAirportToArrayList(args[1],args[2]);								break;
					case "EA": editAirportName(args[1],args[2]);									break;
					case "DA": deleteAirportFromArrayList(args[1]);									break;
					case "EF": editFlightTimes(args[1],args[2],args[3],args[4]);					break;
					case "DF": deleteFlightDetailsFromArrayList(args[1]);							break;
					case "SF": searchFlightsSF(args[1],args[2]);									break;
					case "SD": searchFlightsSD(args[1],args[2],args[3]);							break;		
				}		
			}
		}
	}

	public static boolean readFilesIntoArrayLists() throws IOException
	{
		String fileName1 = "Airports.txt";
		String fileName2 = "Flights.txt";
		
		String fileElements[];
		int topicsColumns = 2,userPassColumns = 8;
		File inputFile1 = new File (fileName1);
		File inputFile2 = new File (fileName2);
			
		airports = new ArrayList<ArrayList<String>>();
		flights = new ArrayList<ArrayList<String>>();
		
		for (int i = 0 ; i < topicsColumns ; i++)
			airports.add(new ArrayList<String>());
		for (int i = 0 ; i < userPassColumns ; i++)
			flights.add(new ArrayList<String>());
		
		
		if(inputFile1.exists() && inputFile2.exists())
		{
			Scanner sc;
			sc = new Scanner(inputFile1);
			while(sc.hasNext())
			{
				fileElements = (sc.nextLine()).split(",");
				for (int i = 0; i < topicsColumns; i++)
					airports.get(i).add(fileElements[i]);
			}
			sc.close();
			sc = new Scanner(inputFile2);
			while(sc.hasNext())
			{
				fileElements = (sc.nextLine()).split(",");
				for (int i = 0; i < userPassColumns; i++)
					flights.get(i).add(fileElements[i]);
			}
			sc.close();
			return true;
		}
		else 
			return false;
	}

	    public static boolean validateInput(String[] userInput) 
    {
		String pattern1 = "AA|EA|DA|EF|DF|SF|SD";
		String pattern2 = "[A-Za-z]{3}";
		String pattern3 = "[A-Za-z]{2}\\d{3,5}";
		String pattern4 = "([a-zA-Z]+)|((([a-zA-Z]+\\s)+)[a-zA-Z]+)";
		String pattern5 = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
		String pattern6 = "[-M]{1}[-T]{1}[-W]{1}[-T]{1}[-F]{1}[-S]{2}";
		boolean validInput = true;
		
        if(userInput.length < 2 || userInput.length > 5)
        {
            validInput = false;
            displayMessage(0);
        }
        else if(!(userInput[0].matches(pattern1)))
        {
            validInput = false;
            displayMessage(1);
        }
        else
        {
            switch(userInput[0])
            {
                case "AA":	
                    if(userInput.length != 3)
                        displayMessage(0);
                    else
                    {
                        if (!(userInput[1].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }
						else if(userInput[2].length() != 3 || (!(userInput[2].matches(pattern2))))
                        {
                            displayMessage(2);
                            validInput = false;
                        }	
                    }
                    break;
                case "EA":						
                    if(userInput.length != 3)
                        displayMessage(0); 
					else
                    {
                        if(userInput[1].length() != 3 || (!(userInput[1].matches(pattern2))))
                        {
                            displayMessage(2);
                            validInput = false;
                        }
						else if (!(userInput[2].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }	
                    }
                    break;
                case "DA":						
                    if(userInput.length != 2)
                        displayMessage(0);
					else
                    {
                        if(userInput[1].length() != 3 || (!(userInput[1].matches(pattern2))))
                        {
                            displayMessage(2);
                            validInput = false;
                        }
                    }
                    break;
                case "EF":
                    if(userInput.length != 5)
                        displayMessage(0);
					else
                    {
                        if(!(userInput[1].matches(pattern3)))
                        {
                            displayMessage(1);
                            validInput = false;
                        }
						else if(!(userInput[2].matches(pattern6)))
                        {
                            displayMessage(9);
                            validInput = false;
                        }
						else if(!(userInput[3].matches(pattern5)))
                        {
                            displayMessage(5);
                            validInput = false;
                        }
						else if(!(userInput[4].matches(pattern5)))
                        {
                            displayMessage(6);
                            validInput = false;
                        }
                    }
                    break;
                case "DF":
                    if(userInput.length != 2)
                        displayMessage(0);
					else
                    {
                        if(!(userInput[1].matches(pattern3)))
                        {
                            displayMessage(1);
                            validInput = false;
                        }
                    }
                    break;
                case "SF":
                    if(userInput.length != 3)
                        displayMessage(0);
					else
                    {
                        if (!(userInput[1].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }
						if (!(userInput[2].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }
                    }
                    break;
                case "SD":
                    if(userInput.length != 4)
                        displayMessage(0);
					else
                    {
                        if (!(userInput[1].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }
						else if (!(userInput[2].matches(pattern4)))
						{
                            displayMessage(3);
                            validInput = false;
                        }
						else if(!(userInput[3].matches(pattern5)))
                        {
                           displayMessage(7);
                            validInput = false;
                        }
                    }
                    break;
            }
        }
        return validInput;
    }

	/*
		John Long
		Jamie McLoughlin
		-This method returns void and displays a message to the user if command-line
		arguments are incorrectly entered
		-It also calls the method displayInstructions to help the user enter details correctly
		-It takes 1 parameter msgNum to select the appropriate message to be displayed
	*/
	public static void displayMessage(int msgNum)
	{
		String msg ="";
		switch(msgNum)
		{
			case 0: msg = "Invalid number of command-line arguments.";                      break;	
            case 1: msg = "Invalid Airport flight code.";                           		break;	
            case 2: msg = "Airport code must be three alphabetic characters in length.";    break;	
            case 3: msg = "Airport names must be alphabetic characters.";                   break;	
            case 4: msg = "Airport details added.";                             			break;
            case 5: msg = "Invalid departure date format.";                                 break;
            case 6: msg = "Invalid arrivala date format.";                                  break;
            case 7: msg = "Invalid date format";                                  			break;  
            case 8: msg = "File named Airports.txt or Flights.txt doesn't exist";           break;	
			case 9: msg = "Invalid day format";         									break;	
			case 10: msg = "No matches found";												break;
		}
		System.out.println(msg);
		displayInstructions();
	}

	/*
		John Long
		-This method takes no arguments and returns void
		-Simply displays how to use the FlightManager program
	*/
	public static void displayInstructions()
	{
		System.out.println("");
		System.out.println("********************************Assistance***********************************************");
		System.out.println("Add new airport 			e.g  java FlightManager AA Lisbon LIS                        ");
		System.out.println("Edit airport 				e.g  java FlightManager EA BHD Belfast                       ");
		System.out.println("Delete airport 				e.g  java FlightManager DA SNN                               ");
		System.out.println("Edit flight 				e.g. java FlightManager EF EI3240 -TWTF-- 1/5/2017 31/10/2017");
		System.out.println("Delete flight 				e.g. java FlightManager DF EI3240                            ");
		System.out.println("Search for flights 			e.g. java FlightManager SF Dublin Shannon                    ");
		System.out.println("Search for flights on date  e.g. java FlightManager SD Dublin Shannon 1/12/2017          ");
		System.out.println("*****************************************************************************************");
	}

	/*
		John Long
		-This method searches for flights by departing airport and destination airport and prints the details of all
		 relevant flights
		-It takes 2 arguments, the source and destination airports as Strings
		-It returns void
	*/
	public static void searchFlightsSF(String source, String destination)
	{
		String aSource = "";
		String aDestination = "";
		int matches = 0;
		
		aSource = getAirportCode(source);
		aDestination = getAirportCode(destination);
		
		for(int i=0; i<flights.get(0).size(); i++)
		{
			if(aSource.equalsIgnoreCase(flights.get(1).get(i)) && aDestination.equalsIgnoreCase(flights.get(2).get(i)))
			{
				// Display flight details
				for(int j=0; j<8; j++)
				{
					System.out.print(flights.get(j).get(i));
				}
				System.out.println("");
				matches++;
			}
		}
		if (matches == 0)
			displayMessage(10);
		
	}

	/*
		John Long
		-This method searches for flights by departing airport, destination airport and departure date and prints the details of all
		 relevant flights
		-It takes 3 arguments, the source and destination airports as Strings and the departure date as a String
		-It returns void
	*/
	
	public static void searchFlightsSD(String source, String destination, String date) throws ParseException
	{
		String aSource = "";
		String aDestination = "";
		int matches = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date date1 = sdf.parse(date);

		aSource = getAirportCode(source);
		aDestination = getAirportCode(destination);
		boolean display1;
		boolean display2;

		for(int i=0; i<flights.get(0).size(); i++)
		{
			Date date2 = sdf.parse(flights.get(6).get(i));
			display1 = false;
		    display2 = false;
			if(aSource.equalsIgnoreCase(flights.get(1).get(i)) && aDestination.equalsIgnoreCase(flights.get(2).get(i)))
			{
				display1 = true;				
			}
			
			if(date1.equals(date2))
				display2 = true;

			if(display1 == true && display2 == true)
			{
				// Display flight details
				for(int j=0; j<8; j++)
				{
					System.out.print(flights.get(j).get(i));
				}
				System.out.println("");
				matches++;
			}
		}
		
		if(matches==0)
			displayMessage(10);
	}

	/*
		John Long
		-This method gets the airport code for a particular airport name
		-It returns a String and takes 1 parameter, a String which is the name of the airport
	*/
	public static String getAirportCode(String name)
	{
		boolean found = false;
		String airportCode = "";
		for(int i = 0; i<airports.get(0).size() && !found; i++)
		{
			if(name.equalsIgnoreCase(airports.get(0).get(i)))
			{
				airportCode = airports.get(0).get(i);
				found = true;
			}
		}
		return airportCode;
	}

	public static void editAirportName(String code, String name) throws IOException
	{
		int index = findIndexOfAirCode(code);
		System.out.println(index);
		airports.get(0).set(index,name);
		writeToAirportsFile();
	}
	
	public static void editFlightTimes(String code, String days,String startDate, String endDate) throws IOException
	{
		int index = findIndexOfFlightCode(code);
		flights.get(5).set(index,days);
		flights.get(6).set(index,startDate);
		flights.get(7).set(index,endDate);
		writeToFlightsFile();
	}
	
	public static int findIndexOfAirCode(String code)
	{
		int index = -1;
		boolean found = false;
		for(int i = 0;i<airports.get(1).size() && !found;i++)
		{
			if(code.equals(airports.get(1).get(i)))
			{
				index = i;
				found = true;
			}
		}
		return index;
	}


	//AA: Add Airport (part 1)
	public static void addAirportToArrayList(String airportName, String airportCode)	throws IOException
	{
		boolean finished=false;
		for (int i=0; i<airports.get(0).size() && !finished; i++)
		{
			if (airports.get(0).get(i).compareTo(airportName)==0)
			{
				finished=true;
				System.out.println(airportName+" is already registerd.");
			}
			else if (airports.get(0).get(i).compareTo(airportName)>0)
			{
				airports.get(0).add(i,airportName);
				airports.get(1).add(i,airportCode);
				finished=true;
			}
		}
		writeToAirportsFile();
	}
		
	//DA delete airport (part 3)
	public static void deleteAirportFromArrayList(String airportCode)	throws IOException
	{
		int airportCodeIndex = findIndexOfCode(airportCode);
		airports.get(0).remove(airportCodeIndex);
		airports.get(1).remove(airportCodeIndex);
		writeToAirportsFile();
	}
	
	//DF delete flight (part 5)
	public static void deleteFlightDetailsFromArrayList(String flightCode) throws IOException
	{
		int flightCodeIndex=findIndexOfFlightCode(flightCode);
		for (int i=0; i<flights.size(); i++)
			flights.get(i).remove(flightCode);
		writeToFlightsFile();
	}

	public static void writeToAirportsFile()throws IOException
	{
		File outFile = new File ("Airports.txt");
		PrintWriter out = new PrintWriter(outFile);
		
		for (int row = 0; row < airports.get(0).size(); row++)
		{
			out.println(airports.get(0).get(row)+","+airports.get(1).get(row));
		}
		out.close();
	}
	
	public static void writeToFlightsFile()throws IOException
	{
		File outFile = new File ("Flights.txt");
		PrintWriter out = new PrintWriter(outFile);
		int row=0;
		System.out.println(row);
		for (int col = 0; col < flights.get(0).size(); col++)
		{
			row=0;
			System.out.println(row);
			for (; row < 7; row++)
			{
				out.print(flights.get(row).get(col)+",");
			}
			out.println(flights.get(row).get(col));
		}
		out.close();
	}
	
	public static int findIndexOfCode(String code)
	{
		int index = -1;
		boolean found = false;
		for(int i = 0;i<airports.get(1).size() && !found;i++)
		{
			if(code.equals(airports.get(1).get(i)))
			{
				index = i;
				found = true;
			}
		}
		return index;
	}
	
	public static int findIndexOfFlightCode(String code)
	{
		int index = -1;
		boolean found = false;
		for(int i = 0;i<flights.get(0).size() && !found;i++)
		{
			if(code.equals(flights.get(0).get(i)))
			{
				index = i;
				found = true;
			}
		}
		return index;
	}
}