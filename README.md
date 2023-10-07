# Project Overview
This project lets you interact with a web app to browse information on current and former tennis players.
## Initial setup

This app uses statistical data from [this Github repository](https://github.com/JeffSackmann/tennis_atp) 
maintained by Jeff Sackmann. In order to provide good user experience, there are a few
initial setup steps required to convert that statistical data to a local database.

1. Clone this repository to your machine - referred to as "code repository" in these instructions.
2. Clone [the other repository](https://github.com/JeffSackmann/tennis_atp) to your machine - referred to as "data repository" in these instructions.
3. In file explorer, navigate to the `tennis_atp` root folder of the data repository, and copy all `.csv` files to the `src\main\resources` folder of the code repository.
4. Open the data repository in IntelliJ and build the project.
5. In IntelliJ, open the `src\main\kotlin\or\appchallenge2023\tennis\sqldelight\data` source folder.
6. Run the following targets in this order:
   * `PopulateMatches`
   * `PopulateRankings`
   * `PopulateRankingDates`
   * `PopulatePlayers`
7. After this is done, you should have an `atp.db` file in the root folder of the code repository. It should be around 140-150MB.

# How to interact with the web app
1. Open the `src\main\kotlin\or\appchallenge2023\tennis\Application.kt` file.
2. Run the `main()` function by clicking the green arrow next to it.
3. Open any browser and navigate to http://127.0.0.1:8080/

From the landing page, you can browse a variety of information on players, tournaments, and rankings:
* Tournament details
* Player details
* List of tournaments for a specific year
* List of rankings for a specific year
* Rankings for a specific week
* Head to head history between two players
* Search players by name

# Dependency credits
* [ATP Database](https://github.com/JeffSackmann/tennis_atp/tree/master)
* [Ktor](https://ktor.io/)
* [SQLDelight](https://github.com/cashapp/sqldelight)
* [Jackson Parsing Library](https://github.com/FasterXML/jackson)
* [Wikidata Toolkit](https://github.com/Wikidata/Wikidata-Toolkit)
* [Logback](https://logback.qos.ch/)