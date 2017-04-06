import java.io.*;
import java.util.*;
import javax.swing.*;
import java.text.*;
//import java.util.GregorianCalendar;
 
public class FlightManager

{
    public static ArrayList<ArrayList<String>> airports;
    public static ArrayList<ArrayList<String>> flights;
    public static void main (String [] args) throws IOException //john number
    {
	/*
	John long 12132306
	-Main method takes in command line arguements to accept user inupt
	-Calls other methods based on the first argument passed 
	-If the first arg is invalid a  message giving instructions is displayed
	*/
        if (args.length!=0)
        {
            args[0] = args[0].toUpperCase();
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
                        case "AA": addAirportToArrayList(args[1],args[2]);                              break;
                        case "EA": editAirportName(args[1],args[2]);                                    break;
                        case "DA": deleteAirportFromArrayList(args[1]);                                 break;
                        case "EF": editFlightTimes(args[1],args[2],args[3],args[4]);                    break;
                        case "DF": deleteFlightDetailsFromArrayList(args[1]);                           break;
                        case "SF": searchFlightsSF(args[1],args[2]);                                    break;
                        case "SD": searchFlightsSD(args[1],args[2],args[3]);                            break;    
                    }      
                }
            }
        }
        else
            displayMessage(0);
    }
    public static boolean readFilesIntoArrayLists() throws IOException 
    {
	/*
	Jamie Mcloughlin 16129377
	-Passed nothing
	-reads all files into the correspnding arraylist
	-then validates that they are in the correct order, 
	-if so this method returns true
	*/	
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
    public static boolean validateInput(String[] userInput)                                             //args validation..happy
    {
	/*
	Jamie McLoughlin 16129377
	-This method is pass a Sting array that has the user information enterd at the command line interface..
	-This method vailds the information pass going off the first place in the array, 
	bending on that it will carry out the right validation for the users information for what he want to do.
	-It returns True or False, True if the information is vaild and false if it is not vaild.	
	*/
        String pattern1 = "AA|EA|DA|EF|DF|SF|SD";
        String pattern2 = "[A-Z]{3}";
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
                        if (!(pattern(1,3,pattern4,userInput)))
                            validInput = false;
                        else if(!(pattern(2,2,pattern2,userInput)))
                            validInput = false;
                    }
                    break;
                case "EA": 
                    if(userInput.length != 3)
                        displayMessage(0);
                    else
                    {
                        if (!(pattern(1,2,pattern2,userInput)))
                            validInput = false;
                        else if(!(pattern(2,3,pattern4,userInput)))
                            validInput = false;
                    }
                    break;
                case "DA": 
                    if(userInput.length != 2)
                        displayMessage(0);
                    else
                    {
                        if (!(pattern(1,2,pattern2,userInput)))
                            validInput = false;
                    }
                    break;
                case "EF":
                    if(userInput.length != 5)
                        displayMessage(0);
                    else
                    {
                        if (!(pattern(1,1,pattern3,userInput)))
                            validInput = false;
                        else if(!(pattern(2,9,pattern6,userInput)))
                            validInput = false;
                        else if(!(isValidDate(userInput[3],5))) 
                              validInput = false;
                        else if(!(isValidDate(userInput[4],6))) 
                              validInput = false;
                    }
                    break;
                case "DF":
                    if(userInput.length != 2)
                        displayMessage(0);
                    else
                    {
                        if (!(pattern(1,1,pattern3,userInput)))
                            validInput = false;
                    }
                    break;
                case "SF":
                    if(userInput.length != 3)
                        displayMessage(0);
                    else
                    {
                       
                        if (!(pattern(1,3,pattern4,userInput)))
                            validInput = false;
                        else if(!(pattern(2,3,pattern4,userInput)))
                            validInput = false;
                    }
                    break;
                case "SD":
                    if(userInput.length != 4)
                        displayMessage(0);
                    else
                    {
                        if (!(pattern(1,3,pattern4,userInput)))
                            validInput = false;
                        else if(!(pattern(2,3,pattern4,userInput)))
                            validInput = false;
                        else if(!(isValidDate(userInput[3],5))) 
                              validInput = false;
                    }
                    break;
            }
        }
        return validInput;
    }
    public static boolean pattern(int num, int mesNum, String patternNum,String[] userInput)
    {
	/*
	Jamie McLoughlin 16129377
	-This method is passed 4 values, int num for the position in the array, int mesnum to show the correct error msg,
	-string patternNum passes the correct pattern for that method, array userInput is the user info
	-Using the information. checks whether the info matches the pattern provided
	-It returns a boolean based on this.
	*/
        boolean validInput = true;
        if(!(userInput[num].matches(patternNum)))
            {
                displayMessage(mesNum);
                validInput = false;
            }      
        return validInput;
    }
    public static boolean isValidDate(String userInput,int mesNum)
    {
	/*
	Jamie McLoughlin 16129377
	-This method is passed the date provided as a string and 'mesNum',the error message number
	-Checks whether the date provided is valid or not
	-It returns a boolean
	*/	
		
		
		
        String dateElements[];
        int ddInt, mmInt, yyInt;
        int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
        boolean dateIsValid = true;
        String pattern = "[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}";
        dateElements = userInput.split("/");
       
        if (!(userInput.matches(pattern))) //date
        {
            displayMessage(mesNum);
            dateIsValid = false;
        }
        else
        {  
            ddInt = Integer.parseInt(dateElements[0]);
            mmInt = Integer.parseInt(dateElements[1]);
            yyInt = Integer.parseInt(dateElements[2]);
           
            if((ddInt == 0) || (mmInt == 0) || (yyInt == 0))
                    dateIsValid = false;
            else if(mmInt > 12)
                    dateIsValid = false;
            else if((ddInt == 29) && (mmInt == 2) && ((((yyInt % 4 == 0) && (yyInt % 100 != 0)) || (yyInt % 400 == 0))))
                    dateIsValid = true;
            else if(ddInt > daysArray[mmInt - 1])
                    dateIsValid = false;
               
            if (!dateIsValid)
                displayMessage(10);
        }  
        return dateIsValid;
    }
    public static void displayMessage(int msgNum) 
    {
	/*
	25% Jamie McLoughlin 16129377 and 75% John long 12132306
	-Passed msgNum which is the error message number
	-Goes through switch case and displays correct error message
	-return is void
	*/
        String msg ="";
        switch(msgNum)
        {
            case 0: msg = "Invalid number of command-line arguments.";                      				break; 
            case 1: msg = "Invalid flight code.";                                   				break; 
            case 2: msg = "Airport code must be three alphabetic characters in length and all capitals.";   break; 
            case 3: msg = "Airport names must be alphabetic characters.";                   				break; 
            case 4: msg = "Airport details added.";                                         				break;
            case 5: msg = "Invalid departure date format.";                                 				break;
            case 6: msg = "Invalid arrivala date format.";                                  				break;
            case 7: msg = "Invalid date format";                                            				break;  
            case 8: msg = "File named Airports.txt or Flights.txt doesn't exist";           				break; 
            case 9: msg = "Invalid day format";                                             				break;
            case 10: msg ="One or more of your day month or year are out of bounds"; 						break; 
        }
        System.out.println(msg);
        displayInstructions();
    }
    public static void displayInstructions() 
    {
	/*
	25% Jamie McLoughlin 16129377 75% John Long 12132306
	-Passed nothing, called if error made in user info
	-Displays correct format for the command line input
	-returns void
	*/	
        String displayHelp = "\u250C";
        for (int i = 1; i <= 88; i++)
            displayHelp += "\u2500";
        displayHelp += "\u2510\n\u2502\t\t\t\t\tAssistance\t\t\t\t\t \u2502\n\u2502\t\t\t\t\t\t\t\t\t\t\t \u2502\n";
        displayHelp +="\u2502Add new airport             e.g java FlightManager AA Lisbon LIS                        \u2502\n";
        displayHelp +="\u2502Edit airport                e.g java FlightManager EA BHD Belfast                       \u2502\n";
        displayHelp +="\u2502Delete airport              e.g java FlightManager DA SNN                               \u2502\n";
        displayHelp +="\u2502Edit flight                 e.g java FlightManager EF EI3240 -TWTF-- 1/5/2017 31/10/2017\u2502\n";
        displayHelp +="\u2502Delete flight               e.g java FlightManager DF EI3240                            \u2502\n";
        displayHelp +="\u2502Search for flights          e.g java FlightManager SF Dublin Shannon                    \u2502\n";
        displayHelp +="\u2502Search for flights on date  e.g java FlightManager SD Dublin Shannon 1/12/2017          \u2502\n";
        displayHelp +=("\u2514");
        for (int i = 1; i <= 88; i++)
            displayHelp +=("\u2500");
        System.out.println(displayHelp + "\u2518");
    }
    public static void addAirportToArrayList(String airportName, String airportCode)throws IOException            
    {	
	/*Daire Lavelle-16192249
    -This method takes the name and code of an airport as arguments.
    -The airports ArrayList is looped over in order to insert the airport such that the file will be in alphabetic order.
    If the airport name is the sameas one in the ArrayList a message is displayed saying that the airport is already registerd.
    It then calls the writeToAirportsFile method if an airport was added.
    -The method has no return type
    */
		boolean found=false;
        if (airports.get(0).size()==0)
        {
            airports.get(0).add(airportName);
            airports.get(1).add(airportCode);
            found=true;
        }
        else
        {
            boolean finished=false;
            for (int i=0; i<airports.get(0).size() && !finished; i++)
            {
                if (airports.get(0).get(i).compareToIgnoreCase(airportName)==0)
                {
                    finished=true;
                    System.out.println(airportName+" is already registerd.");
                }
                else if (airports.get(0).get(i).compareToIgnoreCase(airportName)>0)
                {
                    airports.get(0).add(i,airportName);
                    airports.get(1).add(i,airportCode);
                    finished=true;
                    found=true;
                }
                else if (i==airports.get(0).size()-1)
                {
                    airports.get(0).add(airportName);
                    airports.get(1).add(airportCode);
                    finished=true;
                    found=true;
                }
            }
        }
        if (found)
		{
			System.out.println(airportName +" has been added to Airports.txt");
            writeToAirportsFile();
		}
    }
    public static void editAirportName(String code, String name)throws IOException
    {	
    /*
    Patrick O'Hora
    -passed the airport code and the name you would like to change it to
    -accesses the index of that same code, accesses the second list and changes it
    -writes this new array list to file
    */
        int index = findIndexOfAirCode(code);
        if(index == -1)
        {
            System.out.println("This code does not have a corresponding airport");
        }
        else
        {
            airports.get(0).set(index,name);
            System.out.print("Has been succesfully edited");
            writeToAirportsFile();
        }
    }
    public static void deleteAirportFromArrayList(String airportCode)throws IOException                              
    {
	/*
    Daire Lavelle-16192249
    -This method takes the code of the airport the end user wishes to delete from the file as its argument
    -The findIndexOfCode method is called to check if the airport exists in the ArrayList. If the airport is found corresponding
    elements from the airports and flights Arraylists are deleted. writeToAirportsFile and writeToFlightsFile are called. If
    the airport isn't found a message is displayed.
    -The method has no return type.
    */
        airportCode=airportCode.toUpperCase();
        int airportCodeIndex = findIndexOfAirCode(airportCode);
        String flightsDeleted="";
        boolean flightsExist=false;
        if (airportCodeIndex>=0)
        {
            String airportName=airports.get(0).get(airportCodeIndex);
            airports.get(0).remove(airportCodeIndex);
            airports.get(1).remove(airportCodeIndex);
            writeToAirportsFile();
           
            for (int i=0; i<flights.get(0).size();)
            {
                if (flights.get(1).get(i).equals(airportCode) || flights.get(2).get(i).equals(airportCode))
                {
                    flightsExist=true;
                    flightsDeleted+="\n"+flights.get(0).get(i);
                    for (int j=0; j<flights.size(); j++)
                    {
                        flights.get(j).remove(i);
                    }
                }
                else
                    i++;
            }
            System.out.println(airportName+" has been deleted from Airports.txt");
            if (flightsExist)
                System.out.println("The following flights have been deleted from Flights.txt:"+flightsDeleted);
            writeToFlightsFile();
        }
        else
            System.out.print("Airport not found");
    }
    public static int findIndexOfAirCode(String code)
    {
        /*
        Patrick O'Hora 16179315
        This method is passed the code of the airport
        Searches the array list for this same code
        -returns the index at which this code is found
        */
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
    public static void editFlightTimes(String code, String days,String startDate, String endDate)throws IOException
    {
        /*
        Patrick O'Hora
        -Passed the code to find the flight,followed by the time details
        -if flight is found, access those parts in the array list and change the details
        -Then write to flights file
        */
        int index = findIndexOfFlightCode(code);
        if(index == -1)
        {
            System.out.println("the corresponding Flight code does not exist");
        }
        else
        {
            flights.get(5).set(index,days);
            flights.get(6).set(index,startDate);
            flights.get(7).set(index,endDate);
            System.out.println("Flight times have been succesfully edited");
            writeToFlightsFile();
        }
    }
    public static void deleteFlightDetailsFromArrayList(String flightCode) throws IOException 
    {
	/*
    Daire Lavelle-16192249
    -This method takes a flight code as its argument
    -It calls the findIndexOfCode method to check if the code exists in the flights ArrayList and if it does, get its index.
    If the flight exists it is deleted from flights and flights is written to Flights.txt. Otherwise a messages is displayed
    to the end user.
    -The method has no return type
    */
		int flightCodeIndex=findIndexOfFlightCode(flightCode);
        if (flightCodeIndex>=0)
        {
            for (int i=0; i<flights.size(); i++)
                flights.get(i).remove(flightCodeIndex);
            System.out.println("Flight "+flightCode+" has been deleted from Flights.txt");
            writeToFlightsFile();
        }
        else
            System.out.print("Flight not found");
    }
    public static int findIndexOfFlightCode(String code)
    {
        /*
        Patrick O'Hora 16179315
        -This method is passed a string representing the flight code
        -Goes through the array list until that code is found
        -It returns an int representing the position of that flight in the array list
        */
       
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
    public static void searchFlightsSF(String leavingAir,String destinationAir)                             
    {
	/*
	75% John long	25% Patrick O Hora 16179315
	-Passed the two airports
	-Finds corresponding airport code in airport array list
	-Then checks if there are flights for these two codes
	-Prints out corresponding flights if there is
	*/
    boolean found=false;
        int departureIndex=airports.get(0).indexOf(leavingAir);
        int destinationIndex=airports.get(0).indexOf(destinationAir);
        if (departureIndex==-1 || destinationIndex==-1)
            System.out.println("one or more airports were not found in the flights file");
        else
            found=true;
       
        if (found)
        {
            String code1 = airports.get(1).get(airports.get(0).indexOf(leavingAir));
            String code2 = airports.get(1).get(airports.get(0).indexOf(destinationAir));
            boolean exists = false;
           
           
            for(int i = 0;i<flights.get(0).size();i++)
            {
                if(code1.equals(flights.get(1).get(i)) && code2.equals(flights.get(2).get(i)))
                {
                    System.out.println(flights.get(0).get(i) + " " + flights.get(1).get(i) + " " + flights.get(2).get(i)
                                        + " " + flights.get(3).get(i) + " " + flights.get(4).get(i) + " " + flights.get(5).get(i) + " " +
                                        flights.get(6).get(i) + " " + flights.get(7).get(i));
                    exists = true;             
                }
            }
            if(!exists)
            {
                System.out.println("No flight of this type exists");
            }
        }
    }
    public static void searchFlightsSD(String departure, String destination, String date)
    {
	/*
	10% John long and 90% Daire Lavelle-16192249
    -This method takes the name of the departure airport, the name of the desination airport and the date as arguments.
    -The flights ArrayList is checked to see if the airports are referenced in it. If both are, the flights file is looped over.
    If a flight is found on the given date, from the departure airport to the destination aiport, it is added to the validFlights
    String. If an airport is not found in the airports ArrayList an error message is displayed. If no valid flights are found
    given the arguments an error message is displayed.
    -The method has no return type
    */	
        boolean found=false;
        int departureIndex=airports.get(0).indexOf(departure);
        int destinationIndex=airports.get(0).indexOf(destination);
        if (departureIndex==-1 || destinationIndex==-1)
            System.out.print("one or more airports were not found in the flights file");
        else
            found=true;
       
        if (found)
        {
            String departureCode=airports.get(1).get(departureIndex);
            String destinationCode=airports.get(1).get(destinationIndex);
            String validFlights="";
            boolean departureBool, destinationBool, dateBool;
            for (int i=0; i<flights.size(); i++)
            {
                String temp="";
                departureBool=flights.get(1).get(i).equals(departureCode);
                destinationBool=flights.get(2).get(i).equals(destinationCode);
                dateBool=flights.get(6).get(i).equals(date);
                if (departureBool && destinationBool && dateBool)
                {
                    temp+="Flight "+flights.get(0).get(i)+" ";
                    temp+="departs "+departure+" at "+flights.get(3).get(i)+" ";
                    temp+="and arrives in "+destination+" at "+flights.get(4).get(i)+" ";
                    temp+="on "+date+". The return date is "+flights.get(7).get(i)+".\n";
                }
                validFlights+=temp;
            }
            if(validFlights.equals(""))
                System.out.print("There are no flights from "+departure+" to "+destination+" on "+date);
            else
                System.out.print(validFlights);
        }
    }
	/*
	Did not realise that this form of answer was neccessary until very late and due to time constraints could not 
	implement it into the project, we now know that it was needed for question 7 to find out if the flight was
	leaving on that particular date not the day of the week
	
	

    public static void gregorianCalendar (String actualStartDate, String actualEndDate)  //needs a return
    {  
    GregorianCalendar aCalender = new GregorianCalendar();
    String dateParts = aCalender.get(Calendar.DATE) + "/" + ((aCalender.get(Calendar.MONTH)) + 1) + "/" + aCalender.get(Calendar.YEAR);
    Date actualStartDate, actualEndDate, currentDate;
    DateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy");
    try
    {
    currentDate = (Date) dateFormatter.parse(dateParts);
    actualStartDate = (Date) dateFormatter.parse(startDate);
    actualEndDate = (Date) dateFormatter.parse(endDate);
    if ((currentDate.compareTo(actualStartDate) >= 0) && (currentDate.compareTo(actualEndDate) <= 0))
    System.out.println(" data should be considered");
    else
    System.out.println("data should not be considered");
    }
    catch(ParseException pe)
    {
    System.out.println("unable to format one or more dates");
    }
    */
    public static void writeToAirportsFile()throws IOException
    {
		 /*
        Jamie McLoughlin 16129377
        -This method is not pass anything.
        -Goes through the Airports Multidimensional Arrays list and and writes 
		the information line by line out to the Airport text file.
        -It has no returns.
        */
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
		 /*
        50% Jamie McLoughlin 16129377 50% Daire Lavelle-16192249
        -This method is not pass anything.
        -Goes through the Flight Multidimensional Arrays list and and writes
		the information line by line out to the Flight text file.
        -It has no returns.
        */
        File outFile = new File ("Flights.txt");
        PrintWriter out = new PrintWriter(outFile);
        int row=0;
        for (int col = 0; col < flights.get(0).size(); col++)
        {
            row=0;
            for (; row < 7; row++)
            {
                out.print(flights.get(row).get(col)+",");
            }
            out.println(flights.get(row).get(col));
        }
        out.close();
    }
}