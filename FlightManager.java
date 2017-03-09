import java.io.*;
import java.util.*;
import javax.swing.*;

public class FlightManager
{
	public static ArrayList<ArrayList<String>> Airports;
	public static ArrayList<ArrayList<String>> Flights;

	public static void main (String [] args) throws IOException
	{

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

	
}