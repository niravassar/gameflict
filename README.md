# Gameflict

Local application that combines gotsoccer game schedules into a master schedule and finds conflicts.  The database is a
h2 file database. The user must have java JRE 8 installed.

## Deploying the application locally

1. `gradlew assemble` - creates a runnable jar.
2. place the jar from `/build/libs` into a separate directory (eg `/gameFlict`). Copy the files from `/bat` folder into 
the same directory as the destination of the jar.
3. Click on the `startGameFlict.bat` file.
4. go to `http://localhost:8080`.

## Develepment

1. `grail run-app` for development mode.
2. `test-app` runs the full suite of server side tests.