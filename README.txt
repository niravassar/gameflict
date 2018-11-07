Gameflict app Summary

Gameflict is intended to combine soccer game schedules exported from gotsoccer.
The application will load the schedules into the app database and export a master schedule.
It will also calculate game conflicts if two games are on the same field at the same time, along with coaches conflicts
when two coaches have conflicting games.

How to Install

1. You must have JRE 1.8 installed on your computer. Please go here: https://www.java.com/en/download/help/download_options.xml
2. Find the file gameflict.zip. Extract all the contents to a local directory.
3. Click on the `startGameflict.bat` file. This will start the app in a command window and you will be able to access it on your browser.
In the same folder you will see a database file appear, along with a script that can be used to delete the db and start from scratch.
When uploading a csv file, the app will make a copy of the file for you.
4. Go to `http://localhost:9001`.
5. Go to the Help menu for more instructions on how to use the app.

Database

Local application that combines gotsoccer game schedules into a master schedule and finds conflicts.  The database is a
h2 file database. The user must have java JRE 8 installed.

Sample File

Use the sample file demo_game_with_conflict.csv to play with the application the first time.