## TODO
- Unit & functional tests to verify the overall intent
- Javadocs
- Better naming strategy, both for packages & classes
- Separate selection strategy & data processor

## Completed
- Best performing flight arrivals
- Sub-Optimal flight arrivals
- Best daily flight arrivals
- Support for both JSON & CSV writers. Support for future writer is already inplace

## Instructions on how to build
- Run `gradlew.bat eclipse` from command prompt and import the project into eclipse [for Windows]
- Run `./gradlew eclipse` from shell rompt and import the project into eclipse [for Unix flavors]
- Execute the main program from the class Launcher
- The processed files will be generated at **bin/best_performance.json**, **bin/other_flights.csv** & **bin/daily_best_flights.csv**
