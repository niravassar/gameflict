Gameflict app Summary

Gameflict is intended to combine soccer game schedules exported from gotsoccer.
The application will load the schedules into the app database and export a master schedule. The xls files to be loaded
have to be formatted to fit the application.
For example certain columns of information have to be in order and the file must be saved as a .csv type.
It will also calculate game conflicts if two games are on the same field at the same time, along with coaches conflicts
when coaches have conflicting games. The application will launch from a command window and you can access it in your browser.
See the help menu for more information once the app is running.

How to Install

1. You must have JRE 1.8 installed on your computer. If you are not sure, just skip this step and chances are its already installed.
Most computers have it out of the box. If you have problems come back here: https://www.java.com/en/download/help/download_options.xml
2. Find the file gameflict.zip on google drive. Click this tiny url below to see where it will take you

https://preview.tinyurl.com/y7tuchbh

Create a new directory in your Documents folder called: "gameflict".
Download the zip from google drive and then save it to the new directory you just created.  Unzip the file. It will create a folder
with files in it, along with some other: deleteDb.bat, startGameflict.bat, and gameflict-1.0.jar. The .jar file is the application,
and the .bat files are the execution files.
3. Click on the startGameflict.bat file. This will start the app in a black window. It will take a minute or two to start
running in a Command Prompt window. When it stops logging output you will see it stop with http://localhost:9001 at the end.
This means its ready to launch.
4. Go to http://localhost:9001 in your browser. The app is running on your computer on not on the internet. The database to your app
is the file gameflictDb.mv.db. This type of application is called a "locally running browser application," meaning there is a
website application on your computer, but not on the internet.
5. Go to the Help menu for more instructions on how to use the app.

Try to Demo the app

Use the sample file demo_game_with_conflict.csv to play with the application the first time. It will be in the same folder "gameflict"
you just created. Upload the file, then to Game menu to see the games in the database. Visually look at the games and examine the conflicts.
Identify which games would be conflicts and note them mentally. Now click the "Create Export" button. View he resulting file and see the application
calculated conflict as rows near the end of the file. The application can consume files from various gotsoccer schedules and combine into a master.
It will also examine the games and calculate conflicts. You can adjust the schedules and reload and the application will reexamine the gamesfor conflicts again.
