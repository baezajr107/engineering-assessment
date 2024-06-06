# Engineering Solution
Hello, I made a thing. I'm calling it "The Food Truck Finder+". The intent was to make something that was as close to a marketable product and enterprise grade solution as possible. It's built from the ground up to exist in a real world environment handling useful data and operations. With that said, here are the features:

### Containerization
In a world where scalibility, portability, and compatibility are of the utmost importance, containerizing runtimes is a must. The application is built to run in docker. I've defined a docker compose file because it runs using a mysql instance for persistence of the data and the containers need to coordinate. With that, it's basically plug and play. This application is ready to be deployed and scaled to a production environment at a moment's notice given an orchestration layer like Kubernetes.

### Swagger/OpenAPI
Of all the tools I utilize for productivity, Swagger is probably my favorite. No more scrounging for outdated postman collections or navigating a labyrinth of confluence pages. The specs for the API now come straight from the source. When I've done frontend work, having a live and interactable interface with which to reference and test is an absolute force multiplier. I have it at the basic level of auto generation detail, but the API's can be documented as verbosely as is necessary.

### Liquibase
Liquibase was made by the people who got tired of having to manage folders full of sql scripts hoping that the different databases stayed in sync. Liquibase gives the application control over the database schema so that anywhere the application is run, its database automatically has the most up to date version. It also helps getting newer developers spun up faster since they need to put less work into standing up a local environment. 

### Live data ingestion
I defined a python script that can access the data API and pull it into a locally persisted database. My vision was for this to be run in a cloud functon with a schedule on it to make sure we had updated data. There are a few reasons for doing it the way I did:
  * Having the ingestion be separate from the query code decouples the two so they can be configured and operated in whichever way is optimal with minimal interference between them.
  * It was written in python due to the turnaround time in development. If the API spec changes and production stops getting fed data, the ability to fight the fire can be catered directly to the urgency. From updating the code in the repository and running it through the CI/CD for less urgent occasions, to modifying it directly in the function in the case of a P0 outage, it allows for that level of agility. Getting things back up and running is incredibly important when minutes are money.
  * Pulling live data vs static allows us to avoid getting stale results. And persisting the data instead of reaching out to the API every time avoids rate limiting(reducing costs) and improves performance from colocation of the data and the application accessing it. 

### Base data filtering
I've defined a few fields with which we can filter the dataset. The filter parameters in the API take a comma separates list of strings. With the convenience features of spring, adding new filter fields is incredibly simple. I've also added an API that pulls a live set of valid filter values for the sake of convenience. The fields are as follows:
  * applicant: This is the name of the food truck. Pretty straight forward.
  * status: This is the current state of the food truck. It's useful primarily in determining which ones still exist.
  * foodItems: This is the list of things the food trucks sell. As you'll see in the code, this field is all over the place in terms of quality and consistency, so it's difficult to leverage as a way to get trucks based on specific items.

### Find Closest Food Truck
This is an API that will take a normal address("1234 mulberry lane Fairy Tale Land, FT 54321") and leverage Google's Geocoding API to get coordinates for it and determine which food truck is closest to that address. Keep in mind, this isn't just find the closest food truck to your house. It's the closest food truck to any address(in san francisco). Want some after work noodles? Maybe some post workout street tacos? It's right there. The filtering is a bit bare on it. I wanted to add a filter for type of food but the food types data just didn't make that easy enough to do within the timeframe. 

As a minor add, I've included the jupyter notebooks I used to prototype the API functionality. The thought process is important for these types of things, so I think it's useful insight.

## Things I would put into "The Food Truck Finder+ Pro"

### Terraform
This was next on my list, but it was a bit too much of a time investment to be within scope for this POC. I maintain my own GCP sandbox environment. So I had planned to actually deploy it as an alternative to running locally. That being said, Infrastructure as Code is just the standard operating procedure at this point. I've used terraform on a good number of projects and the clarity it provides while being able to scale to N environments is a game changer. 

### CI/CD
The next part of my plan was to implement a CI/CD pipeline using either cloud build or github actions to make it so I don't have to manually throw the runtimes into the environment after every commit. 

### Find Closest Food Truck Hotstop
Building off the find closest food truck API, what if you could find the closest hotspot. What I mean  by that is to imagine a heatmap. Using some fun math tricks, we can define the set of "peaks" in the location grouping of the food trucks, and use the same logic as the find closest food truck to get you to the closest hotspot. Then you can be surrounded by all the food you want!

## How to run it
It's built in Docker, so you primarily just need that. I also didn't containerize the python script, so you'll need to install python and the relevant dependencies manually.
The order of operations should be as follows:
  * Run the Docker Compose file to spin up the application and database. The application will handle instantiating the schema within the database. You're going to need my Google API key, which I'll pass on through the recruiter. Throw it into the docker compose file where it says KEY
  * Once that's up and running, run the python script to populate the data.
  * After the data is populated, you can navigate to http://localhost:8080/swagger-ui/index.html to get the swagger UI and start playing with the API's.

Here's some relevant commands to run from the root directory once you've cloned the repo. 
cd FoodTruckFinder
docker-compose up --build

cd DataIngestor
pip3 install sqlalchemy
pip3 install pandas
python3 FoodTruckIngestion.py
