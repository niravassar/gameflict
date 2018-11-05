# Gameflict

Local application that combines gotsoccer game schedules into a master schedule and finds conflicts.  The database is a
h2 file database. The user must have java JRE 8 installed.

## Deploying the application locally

1. `gradlew packageZip` - executes `assemble` and then zips up bat files alongside the runnable jar for a user to use on desktop. 
The result is in `build/dist`.
2. place the jar from `/build/libs` into a separate directory (eg `/gameflict`). Copy the files from `/bat` folder into 
the same directory as the destination of the jar.
3. Click on the `startGameflict.bat` file.
4. go to `http://localhost:9001`.

## Develepment

1. `grail run-app` for development mode.
2. `test-app` runs the full suite of server side tests.