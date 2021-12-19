# GamersNationSpring

What is Gamers Nation?  
Gamers Nation is a desktop application made to help League of Legends players find other players with similar gaming behavior.
This projects goal is to reduce the toxic vibe in game, by giving the players the ability to search for other players that has similar gaming behavior instead
of purely on their gaming skills.


## Can i use this?
The development of Gamers Nation is still under process and this is therefore only a prototype.
However you can still download the project and try it out.
You can registre as a user and search for matches from the duppy profiles. However the Login feature is still not integrated.

## Prerequisites
The program is writen in Java 11, therefore you most install Java on your computer if you don't already have it installed.

You also need to download and install [PostgreSQL](https://www.postgresql.org). Create a Postgre database and call it **"gamersnationdb"**.
Otherwise if you chose to name it something els, you need to go to the *"application.properties"* file and change the name of the database to name you've chosen for your database.

The program sends some HTTP GET requests to RIOT Games API. Therefore you need to get an API Key from [Riot developer portal](developer.riotgames.com) and add it in *RiotAPIManager.class*.


