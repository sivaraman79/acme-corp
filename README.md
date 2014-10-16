# Flight data analyzer [![Build Status](https://travis-ci.org/thekalinga/acme-corporation-flightdata.svg?branch=master)][1]

## TODO
- Unit & functional tests to verify the overall intent - In progress
- ~~Javadocs~~
- ~~Fixing the issue with Best daily flight arrival filter~~
- ~~Best performing flight arrivals~~
- ~~Sub-Optimal flight arrivals~~
- ~~Best daily flight arrivals~~
- ~~Support for both JSON & CSV writers. Flexibility to add new writers is inplace~~
- ~~Redesign API~~
- ~~Newer & better abstractions~~
- ~~Better names for packages & classes~~
- ~~Split data filter from data handler (Single responsibility principle)~~

## Instructions on how to build
- Run `gradlew.bat eclipse` from command prompt and import the project into eclipse [for Windows]
- Run `./gradlew eclipse` from shell rompt and import the project into eclipse [for Unix flavors]
- Execute the main program from the class Launcher
- The processed files will be generated at **bin/best_performance.json**, **bin/other_flights.csv** & **bin/daily_best_flights.csv**


[1]: https://travis-ci.org/thekalinga/acme-corporation-flightdata
