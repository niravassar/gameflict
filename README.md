# Gameflict

Local application that combines gotsoccer game schedules into a master schedule and finds conflicts.  The database is a
h2 file database. The user must have java JRE 8 installed.

## Deploying the application locally

1. `gradlew packageZip` - executes `assemble` and then zips up bat files alongside the runnable jar for a user to use on desktop. 
The result is in `build/dist`.
2. Find the file `build/dist/gameflict.zip`. Extract all the contents to a directory. 
3. Click on the `startGameflict.bat` file. This will start the app in a command window and you will be able to access it on your browser. In the 
same folder you will see a database file appear, along with a script that can be used to delete the db and start from scratch. 
When uploading a csv file, the app will make a copy of the file for you. 
4. go to `http://localhost:9001`.

## Development

1. `grails run-app` for development mode.
2. `grails test-app` runs the full suite of server side tests.