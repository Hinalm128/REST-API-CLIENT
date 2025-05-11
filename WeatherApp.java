import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class WeatherApp implements ActionListener
{
	private static JFrame f;
	private static JLabel l,cityName,weathername,temp,humi,windy,img1,img2,img3;
	private static JTextField t;
	private static JButton b;
	private static ImageIcon today,currResized,humidity,humResided,WindSpeed,windResized;
	private static Image curr_resize,hum_resize,wind_resize;
	
	public WeatherApp()
	{
		//Creating layout for the Application.
		f = new JFrame("Weather Forecast App.");

		l = new JLabel("Enter City: ");
		l.setForeground(new Color(255, 255, 255));
		
		t = new JTextField(15);
		
		b = new JButton("🔍");
		b.setForeground(new Color(255, 255, 255));
		b.setBackground(Color.DARK_GRAY);
		b.addActionListener(this);

		//Adding Positioning to the components.
		l.setBounds(30, 20, 75, 25);
		f.add(l);
		t.setBounds(105, 20, 200, 25);
		f.add(t);
		b.setBounds(315, 20, 50, 25);
		f.add(b);

		//Changing the color of the JFrame.
		f.getContentPane().setBackground(new Color(0, 1, 41));
		
		f.setSize(410,100);
		f.setLayout(null);
		f.setVisible(true);
	}
	private static JSONObject getLocationData(String city)
	{
		//Since we will be making HTTP call ....So replacing all the whitespaces with '+' or concatation symbol.
		city=city.replaceAll(" ", "+");
		
		//https://geocoding-api.open-meteo.com/v1/search?name=Mumbai&count=1&language=en&format=json.......Replacing Mumbai with city.
		String urlString="https://geocoding-api.open-meteo.com/v1/search?name="+city+"&count=1&language=en&format=json";
		
		try
		{
			//Fetching APIs respose based on API Link.
			HttpURLConnection apiConn= fetchApiResponse(urlString);
			
			//Check the Response Status.........200-means sucessfull
			if(apiConn.getResponseCode() != 200)
			{
				System.out.println("Error: Could not connect to API ");
				return null;
			}
			
			//Read the response and convert store String.
			String jsonResponse= readApiResponse(apiConn);
			
			//Parsing the string into a JSON object.
			JSONParser parser = new JSONParser();
			JSONObject resJsonObj = (JSONObject) parser.parse(jsonResponse);
			
			//Retrieving location Data ...........results is an arrey of json object.
			JSONArray locData=(JSONArray) resJsonObj.get("results");
			return (JSONObject) locData.get(0);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static String readApiResponse(HttpURLConnection apiConn)
	{
		
		try
		{
			//To store resulting JSON data.
			StringBuilder resultJson= new StringBuilder();
			
			//To Read inputStream of the HttpURLConnection.
			Scanner s=new Scanner(apiConn.getInputStream());
			
			//looping through the response and append it to the StingBuilder.
			while(s.hasNext())
			{
				resultJson.append(s.nextLine());
			}
			
			//Releasing resources.
			s.close();
			
			//Return json data as a string.
			return resultJson.toString();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static HttpURLConnection fetchApiResponse(String urlString) 
	{
		try
		{
			//Creating Connection.
			URL url=new URI(urlString).toURL();
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			
			//Seting request method to get.
			conn.setRequestMethod("GET");
			return conn;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static JSONObject displayWeatherData(double latitude, double longitude) 
	{
		try
		{
			//https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code
			String url="https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code";

			//Fetching APIs respose based on API Link.			
			HttpURLConnection apiConn= fetchApiResponse(url);
			
			//Check the Response Status.........200-means sucessfull
			if(apiConn.getResponseCode() != 200)
			{
				System.out.println("Error: Could not connect to API ");
				return null;
			}
			
			//Read the response and convert store String.
			String jsonResponse= readApiResponse(apiConn);
			
			//Parsing the string into a JSON object.
			JSONParser parser = new JSONParser();
			JSONObject JsonObj = (JSONObject) parser.parse(jsonResponse);
			
			//Retrieving & Returning location Data. 
			return (JSONObject)JsonObj.get("current");			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//Get location data
		JSONObject cityLocationData =(JSONObject)getLocationData(t.getText());
		double latitude = (double) cityLocationData.get("latitude");
		double longitude = (double) cityLocationData.get("longitude");
		
		//Store the Weather data in an JSONObject.
		JSONObject currweatherJson=(JSONObject)displayWeatherData(latitude,longitude);
		
		//Storing the Weather data into variables.
		double temp1=(double) currweatherJson.get("temperature_2m");
		  
		long hum1=(long)currweatherJson.get("relative_humidity_2m");
		  
		double wind1=(double) currweatherJson.get("wind_speed_10m");
		  
		long code=(long)currweatherJson.get("weather_code");
	
		//Based on the information provided by the "api.open-meteo.com", the data is further divided based on the conditions.
		String img;;
		if(code==0 || code==1|| code==2 || code==3)
			img="clear-sky";
		else if(code==45 || code==48)
			img="fog";
		else if(code==51 || code==53 || code==55 || code==56|| code==57)
			img="drizzle";
		else if(code==61 || code==63|| code==65|| code==67|| code==66|| code==80|| code==81|| code==82)
			img="rain";
		else if(code==85 || code==86|| code==71|| code==73|| code==75|| code==77)
			img="snowy";
		else if(code==95 || code==96|| code==99)
			img="thunder";
		else
			img="weather";
		
		String codeName;
		if(code==0)
			codeName="Clear Sky";
		else if(code==1)
			codeName="Mainly Clear";
		else if(code==2 || code==3)
			codeName="Party Cloudy";
		else if(code==45 || code==48)
			codeName="Mist Fog";
		else if(code==51 || code==53 || code==55)
			codeName="Drizzle Light";
		else if( code==56|| code==57)
			codeName="Freezing Drizzle";
		else if(code==61)
			codeName="SLight Rain";
		else if(code==63)
			codeName="Moderate Rain";
		else if(code==65)
			codeName="Heavy Rain";
		else if(code==66)
			codeName="Freeezing Rain";
		else if(code==67)
			codeName="Chill Heavy Rain";
		else if(code==71)
			codeName="Slight Snowfall";
		else if(code==73)
			codeName="Moderate Snowfall";
		else if(code==75)
			codeName="Heavy Snowfall";
		else if(code==77)
			codeName="Snow Grains";
		else if(code==80)
			codeName="Slight Rain Showers";
		else if(code==81)
			codeName="Moderate Rain Showers";
		else if(code==82)
			codeName="Heavy Rain Showers";
		else if( code==85)
			codeName="Slight Snowfall";
		else if( code==86)
			codeName="Heavy Snowfall";
		else if(code==95)
			codeName="Thunderstrom";
		else if(code==96)
			codeName="Slight Thunderstrom";
		else if(code==99)
			codeName="Heavy Thunderstrom";
		
		else
			codeName="---------------------";
		
		//Creating GUI Components, and Setting thier Size & fontStyle.
		
		//Converting the text(City Name) enter by User.
		cityName=new JLabel(t.getText().substring(0, 1).toUpperCase()+t.getText().substring(1).toLowerCase());
		cityName.setForeground(new Color(255, 255, 255));
		cityName.setFont(new Font("Arial", Font.BOLD, 16));
		
		LocalDateTime currDateTime = LocalDateTime.now();
		DateTimeFormatter curr_format = DateTimeFormatter.ofPattern("EEEE, MMMM dd"); // date formater
		String formattedDate = currDateTime.format(curr_format); // adding format to date
		JLabel DateTime = new JLabel(formattedDate); // displaying it on the JLabel
		
		DateTime.setForeground(new Color(255, 255, 255)); 
		DateTime.setFont(new Font("Arial", Font.BOLD, 24));
		
		//Current Weather Image 
		today = new ImageIcon("img/"+img+".png"); 
		curr_resize = today.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
		currResized = new ImageIcon(curr_resize);
		img1 = new JLabel(currResized); // Create a JLabel to display the image
		
		//Humidty Weather image 
		humidity = new ImageIcon("img/humid.png");
		hum_resize = humidity.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		humResided = new ImageIcon(hum_resize);
		img2 = new JLabel(humResided);
		  
		//Wind Speed image 
		WindSpeed = new ImageIcon("img/wind.png"); 
		wind_resize = WindSpeed.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		windResized = new ImageIcon(wind_resize);
		img3 = new JLabel(windResized);
		  
		weathername=new JLabel(String.format("%21s",codeName));
		weathername.setForeground(new Color(255, 255, 255));
		weathername.setFont(new Font("Arial", Font.BOLD, 16));
		
		temp=new JLabel(temp1+"°C");
		temp.setForeground(new Color(255, 255, 255));
		temp.setFont(new Font("Arial", Font.BOLD, 24));
		
		humi=new JLabel(hum1+" %");
		humi.setForeground(new Color(255, 255, 255));
		humi.setFont(new Font("Arial", Font.BOLD, 13));
		
		JLabel h=new JLabel("Humidity");
		h.setForeground(new Color(255, 255, 255));
		h.setFont(new Font("Arial", Font.BOLD, 13));
		
		windy=new JLabel(wind1+" km/hr");
		windy.setForeground(new Color(255, 255, 255));
		windy.setFont(new Font("Arial", Font.BOLD, 13));

		JLabel w=new JLabel("Wind");
		w.setForeground(new Color(255, 255, 255));
		w.setFont(new Font("Arial", Font.BOLD, 13));

		//Adding Components to the JFrame & Setting thier Position in the JFrame.
		DateTime.setBounds(90, 70, 350, 35);
		f.add(DateTime);
		
		cityName.setBounds(155, 100, 100, 25);
		f.add(cityName);
		
		img1.setBounds(95, 130, 200, 200);
		f.add(img1);
		
		weathername.setBounds(110,325,400,25);
		f.add(weathername);
		
		temp.setBounds(160, 355, 100, 25);
		f.add(temp);
		
		img2.setBounds(20,410,50,50);
		f.add(img2);
		
		humi.setBounds(80,415,100,25);
		f.add(humi);

		h.setBounds(80,435,100,25);
		f.add(h);
		
		img3.setBounds(250,410,50,50);
		f.add(img3);
		
		windy.setBounds(310,415,100,25);
		f.add(windy);
		
		w.setBounds(310,435,100,25);
		f.add(w);
		
		//Setting the Size of the Frame.
		f.setSize(410, 550);
		
		//To add components dynamically in swing.
		f.revalidate();
	}
	
	public static void main(String[] args) 
	{
		new WeatherApp();
	}

}
