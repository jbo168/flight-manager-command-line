import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.*;

public class FlightManager
{
	public static ArrayList<ArrayList<String>> Airports;
	public static ArrayList<ArrayList<String>> Flights;

	/*
		John Long
		Jamie McLoughlin
	*/
	public static void main (String [] args) throws IOException
	{
		String pattern = "AA|EA|DA|EF|DF|SF|SD";
		if(args.length<2 || args.length>5)
			displayMessage(0);
		else if(!(args[0].matches(pattern)))
			displayMessage(1);
		else
		{	
			switch(args[0])
			{
				case "AA": addAirport(args);											break;
				case "EA": editAirportName();											break;
				case "DA": deleteAirport();												break;
				case "EF": editFlights();												break;
				case "DF": deleteFlight();												break;
				case "SF": searchFlights();												break;
				case "SD": searchFlights();												break;		
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
			
		Airports = new ArrayList<ArrayList<String>>();
		Flights = new ArrayList<ArrayList<String>>();
		
		for (int i = 0 ; i < topicsColumns ; i++)
			Airports.add(new ArrayList<String>());
		for (int i = 0 ; i < userPassColumns ; i++)
			Flights.add(new ArrayList<String>());
		
		
		if(inputFile1.exists() && inputFile2.exists())
		{
			Scanner sc;
			sc = new Scanner(inputFile1);
			while(sc.hasNext())
			{
				fileElements = (sc.nextLine()).split(",");
				for (int i = 0; i < topicsColumns; i++)
					Airports.get(i).add(fileElements[i]);
			}
			sc.close();
			sc = new Scanner(inputFile2);
			while(sc.hasNext())
			{
				fileElements = (sc.nextLine()).split(",");
				for (int i = 0; i < userPassColumns; i++)
					Flights.get(i).add(fileElements[i]);
			}
			sc.close();
			return true;
		}
		else 
			return false;
	}

	public static boolean validateInput(String[] userInput)
	{
		String pattern1 = "[A-Za-z]{3}";
		boolean validInput = true;
		String pattern = "AA|EA|DA|EF|DF|SF|SD";
		userInput[0] = userInput[0].toUpperCase();
		
		switch(userInput[0])
		{
			case "AA": 
				if(userInput.length != 3)
					displayMessage(0);
				else
				{
					if(userInput[2].length() != 3 || (!(userInput[2].matches(pattern1))))
					{
						displayMessage(2);
						validInput = false;
					}
				}
				break;

			case "EA":
				if(userInput.length != 3)
					displayMessage(0);
				break;

			case "DA":
				if(userInput.length != 2)
					displayMessage(0);
				break;
			case "EF":
				if(userInput.length != 5)
					displayMessage(0);
				break;
			case "DF":
				if(userInput.length != 2)
					displayMessage(0);
				break;
			case "SF":
				if(userInput.length != 3)
					displayMessage(0);
				break;
			case "SD":
				if(userInput.length != 4)
					displayMessage(0);
				break;

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
			case 0: msg = "Invalid number of command-line arguments.";						break;
			case 1:	msg = "Invalid first command-line argument.";							break;
			case 2:	msg = "Airport code must be three alphabetic characters in length.";	break;
			case 3:	msg = "Airport already exists.";										break;
			case 4:	msg = "Airport details added.";											break;
			case 5:	msg = "Invalid departure date format.";									break;
			case 6: msg = "Invalid arrivala date format.";									break;
		}
		System.out.println(msg);
		displayInstructions();
	}

	/*
		John Long
		Jamie McLoughlin
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


	public static void editAirportName(String code, String name) throws IOException
	{
		int index = findIndexOfAirCode(code);
		System.out.println(index);
		Airports.get(0).set(index,name);
		writingToAirportsFile();
	}
	
	public static void editFlightTimes(String code, String days,String startDate, String endDate) throws IOException
	{
		int index = findIndexOfFlightCode(code);
		Flights.get(5).set(index,days);
		Flights.get(6).set(index,startDate);
		Flights.get(7).set(index,endDate);
		writingToFlightsFile();
	}
	
	public static int findIndexOfAirCode(String code)
	{
		int index = -1;
		boolean found = false;
		for(int i = 0;i<Airports.get(1).size() && !found;i++)
		{
			if(code.equals(Airports.get(1).get(i)))
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
		for(int i = 0;i<Flights.get(0).size() && !found;i++)
		{
			if(code.equals(Flights.get(0).get(i)))
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