//Lindsey Cook

import java.io.*;
import javax.swing.JOptionPane;


public class wapo {
	public static void main(String[] args) {
		BufferedReader br = null;
		try { 
			String inputFileLine = JOptionPane.showInputDialog("Insert text file: ");
			if(!new File(inputFileLine).exists()) {
				System.out.println("Can not find " + inputFileLine);
			}
			System.out.println("Attempting to read " + inputFileLine);
			System.out.println("br = " + br);
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(inputFileLine)));
			System.out.println("br = " + br);

			//Create a text file 
			PrintWriter writer = new PrintWriter("NEW FILE NAME", "UTF-8");

			//Write the first line to the text file
			//styles and stuff here. 
			writer.println("<!-- The beginning -->\n<!DOCTYPE html>\n<html lang=\"en\">\n<head></head>\n<meta charset=\"utf-8\">\n<body>\n<style>\n\n#wrap_state{\n\twidth: 650px;\n\tfloat: left;" +
					"\n\tpadding-top: 20px;\n\tpadding-left: 20px;\n}\n\n#left_col{\n\tfloat: left;\n}\n\n#right_col{\n\tfloat: right;\n}\n#box{\n\tborder: 5px solid  #cccccc;\n\twidth: 300px;\n\theight: 100px;\n}\n\n\n</style>");


			writer.println("<div class = \"wrap_state\" id =\"\">"); //open category div 1

			String prev_state = "Alabama";

			int count_total = 0;
			int count_state = 0;

			//while loop to write each of the cards
			while((inputFileLine = br.readLine()) != null) {

				count_total++;

				//get the values
				String state = inputFileLine.substring(0, inputFileLine.indexOf(','));
				String name = inputFileLine.substring(state.length() + 1, inputFileLine.indexOf(',', state.length() + 1));
				String publication = inputFileLine.substring(name.length() + state.length() + 2, inputFileLine.indexOf(',', state.length() + name.length() + 2));
				String handle = inputFileLine.substring(name.length() + state.length() + publication.length() + 3, inputFileLine.indexOf(',', name.length() + state.length() + publication.length() + 3));
				String website = inputFileLine.substring(name.length() + state.length() + publication.length() + handle.length() + 4, inputFileLine.length());

				//trim all the values
				state = state.trim();
				name = name.trim();
				publication = publication.trim();
				website = website.trim();
				handle = handle.trim();

				if(!state.equalsIgnoreCase(prev_state)) 
				{
					writer.println("</div> \n<!--End of " + prev_state + "-->");//close state div

					//this is a new state
					count_state= 0;
					//write the identifying comment
					writer.println("<!--State " + count_total + ": " + state + "-->");

					//write the beginning state div for the card
					writer.println("<div class = \"wrap_state\" id =\"" + state +"\">"); 


				}

				count_state++;
				if(count_state%2 == 0)
				{
					//right column

					writer.println("<div class = \"box right\">"); //open a right column div
					writer.println("<img align = \"left\" style = \"padding: 5px\" src = \"https://api.twitter.com/1/users/profile_image?screen_name=" + handle + "&size=bigger \"> ");
					writer.println("<p align = \"left\">" + name + "<a href = \"" + website + "\"><br> " + publication + "</a></p>");
					writer.println("<a href=\"https://twitter.com/" + handle + "\" class=\"twitter-follow-button\" data-show-count=\"false\">follow " + handle + "</a>");
					writer.println("<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>");
					writer.println("</div>");//close box div
				}

				else
				{
					//left column
					writer.println("<div class = \"box left\">");//open box div
					writer.println("<img align = \"left\" style = \"padding: 5px\" src = \"https://api.twitter.com/1/users/profile_image?screen_name=" + handle + "&size=bigger \"> ");
					writer.println("<p align = \"left\">" + name + "<a href = \"" + website + "\"><br> " + publication + "</a></p>");
					writer.println("<a href=\"https://twitter.com/" + handle + "\" class=\"twitter-follow-button\" data-show-count=\"false\">follow " + handle + "</a>");
					writer.println("<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>");
					writer.println("</div>");//close box div

				}



				prev_state = state;

				//used for testing
				//System.out.println("Line: " + inputFileLine);
				//System.out.println("State: " + state);
				//System.out.println("Name: " + name );
				//System.out.println("Publication " + publication);
				//System.out.println("Handle: "  + handle);
				//System.out.println("website: " + website);


			}//while

			//write the end code for the html file. 
			writer.println("</div><script type=\"text/javascript\" src=\"../d3/d3.v3.js\"></script><script type=\"text/javascript\"></script></body></html>");//includes the div for ending the last state

			writer.flush();
			writer.close();
			br.close();

		} catch(IOException e) {
			System.out.println("Read error: " + e.getMessage());
		}


	}
}
