# REST-API-CLIENT

"COMPANY": CODTECH IT SOLUTIONS

"NAME": HINAL ANIL MISTRY

"INTERN ID": CT04DK953

"DOMAIN": JAVA PROGRAMMING

"DURATION": 4 WEEKS

"MENTOR": NEELA SANTHOSH

## DESCRIPTION ##

Task 2: Rest APIs Client

This Java desktop application is a real weather forecasting system whereby users can retrieve the current weather of any city. By just entering the city name in a graphical user interface, which is designed using Java Swing for its graphical user interface and using the Open-Meteo API, the program is quite simple to use and effective, visually intuitively displaying live weather information. This project shows very well how dynamic, real-time desktop environment information can be delivered by contemporary Java applications that interface with outside web APIs.

The city-based weather search capability of the app is among its key features. A simple text field allows users to enter the name of any city. Clicking the search button causes the app to make an API call to the Open-Meteo Geocoding API in order to obtain the city's geographic coordinates—latitude and longitude. A second Open-Meteo Forecast API call is made once the location's coordinates have been gathered. This demand retrieves current weather data including temperature, humidity, wind speed, and a numerical weather condition code.

Upon launching, the constructor WeatherApp() initializes the GUI window using JFrame f. Inside the constructor, a JLabel l prompts the user to "Enter City:", and a JTextField t is provided to accept user input. A JButton b with a search icon ("🔍") triggers the API request when clicked. Styling is applied by setting the background color of f(JFrame) to a dark blue shade, while lcon and buttons have white foreground text.
Inside actionPerformed, extracted weather parameters are stored in individual variables: temp1 (temperature), hum1 (humidity), wind1 (wind speed), and code (weather condition code). Based on code, two strings are determined—img, which matches to a local image file (e.g., "clear-sky", "rain", "snowy"), and codeName, a textual description of the weather (e.g., "Clear Sky", "Moderate Rain").

Swing components are then dynamically created to display this data:
•	cityName: JLabel displaying the formatted city name.
•	DateTime: JLabel showing the current date using LocalDateTime currDateTime and DateTimeFormatter curr_format.
•	today: ImageIcon for current weather, resized into curr_resize and wrapped in JLabel img1.
•	humidity and WindSpeed: ImageIcons for humidity and wind, resized into hum_resize and wind_resize, and displayed using JLabel img2 and img3 respectively.
•	weathername: JLabel displaying codeName.
•	temp, humi, windy: JLabels showing numerical values for temperature, humidity, and wind speed.
•	h and w: Static labels reading "Humidity" and "Wind" for clarity.

All these components are added to JFrame f using setBounds to position them appropriately. The GUI is updated dynamically using f.revalidate() to reflect changes after the user initiates a search. Furthermore, simple error-handling techniques solve issues like failed API calls or data unavailability, thereby increasing reliability and customer experience.

Technically, the program uses several main Java libraries and APIs. For GUI design, these are javax.swing; for HTTP requests to the Open-Meteo APIs, java.net.HttpURLConnection; for JSON response parsing, org.json.simple; and for other tasks, java.net.HttpURLConnection. It also uses java.time to show the actual date in a user-friendly form. Together, these technologies provide a strong foundation for merging outside data with desktop programs.
	
From a user experience point of view, the app offers quick and short weather forecasts showing the current temperature in Celsius, percentage humidity, wind speed in kilometers per hour, and a qualitative weather condition along with its related icon. Users find it simple to interact with the app and immediately get information thanks to the straightforward design combined with responsive elements.

This Java Weather Forecast Application  elegantly demonstrates how to retrieve and display real-time information aesthetically. Open to more development, the project is not only learning-based but also has potential upgrades. It’s a fantastic starting point for programmers interested in improving their knowledge of real-time Java applications.
