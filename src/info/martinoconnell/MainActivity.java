/*
The SUMMARISED sequence of events is as follows for reading the contents of a text file:
	
1. 	BufferedReader object reads the raw file and extracts a stream of data in its own custom unreadable (to the human) format
2. 	This data is then transformed into a stream of String data by the readMovie method using a tab to identify separate attributes
3. 	While still in the readMovie method, it finally churns out an entry in the properly formatted Movie class
4. 	This is then printed to the console and the sequence repeats until there are no more entries in the text file

The DETAILED sequence of events is as follows for reading the contents of a text file:

1.	A BufferedReader object is created using the getReader method to return the contents of the text file in a BufferedReader format
2. 	A BufferedReader object needs to be converted into something that can be displayed on screen. The readMovie method takes as its 
	input a BufferedReader object and returns a properly formatted Movie object, separated out into its proper data types, not as a 
	constant stream of String objects
3. 	But in order to output the contents to a properly formatted Movie object, the Movie object class has to be created defining the
	format of the data once its extracted. This is reality is step 0.
4. 	The while loop then cycles through the created Movie objects all called "movie" and as long as there is another entry, prints the
	contents to the console.
	
*/

package info.martinoconnell;

import java.io.*;	
import java.text.NumberFormat;

public class MainActivity
{
	public static void main(String[] args)
	{
		NumberFormat cf = NumberFormat.getCurrencyInstance();

		BufferedReader in = getReader("movies.txt");	// Calls the getReader method and returns it as the object "in"

		Movie movie = readMovie(in);	// Starting instance calling the readMovie method which takes all the first text file string information and properly formats it into a Movie object

		while (movie != null)	// With the data now properly formatted its time to display it all as a String on the console. This continues as long as there is another line entry
		{
			String msg = Integer.toString(movie.year);
			msg += ": " + movie.title;
			msg += " (" + cf.format(movie.price) + ")";
			System.out.println(msg);

			movie = readMovie(in);		// With the first instance out of the way, this moves onto the next line as repeats until the condition of the while loop is met, i.e. a null entry
		}
	}	

	private static BufferedReader getReader(String name)	// The getReader method which takes a file name as an input and returns a BufferedReader object
	{
		BufferedReader in = null;			// Declaring a new BufferedReader object before the try/catch block
		try
		{
			File file = new File(name);		// New file object called "file" taking the "movie.txt" file as its input
			in = new BufferedReader(		// Creates a BufferedReader object with its input being a FileReader object which itself takes the file object as an input
					new FileReader(file) );
		}
		catch (FileNotFoundException e)		// Displayed if file not found
		{
			System.out.println(
					"The file doesn't exist.");
			System.exit(0);
		}
		return in;							// the BufferedReader object "in" is returned out of the try/catch block containing all the data in the movie.txt file
	}

	private static Movie readMovie(BufferedReader in)	// The readMovie method which takes the BufferedReader object as its input and separates out the data into a String array returning the data as a Movie object
	{
		String title;
		int year;
		double price;
		String line = "";
		String[] data;

		try
		{
			line = in.readLine();			// This reads the content of the BufferedReader object "in"
		}
		catch (IOException e)
		{
			System.out.println("I/O Error");
			System.exit(0);
		}

		if (line == null)
			
			return null;
		
		else
		{
			data = line.split("\t");				// This separates out the line according to where there is a tab
			title = data[0];						// The title goes into index 0
			year = Integer.parseInt(data[1]);		// The year goes into index 1
			price = Double.parseDouble(data[2]);	// The price goes index 2

			return new Movie(title, year, price);	// Once the data has been split up and assigned their data type it is passed through the Movie object constructor to properly format it
		}
	}


	private static class Movie	//	This defines the "format" or template of the information to be displayed
	{
		public String title;
		public int year;
		public double price;

		Movie(String title, int year, double price) //	The constructor that's called when a new Movie object is created
		{
			this.title = title;
			this.year = year;
			this.price = price;
		}
	}
}
