<%@ page import="org.gssa.gameflict.League" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Help</title>
    <asset:stylesheet src="w3.css"/>
</head>
<body>
    <div class="w3-container" style="width: 42%;">
        <div class="w3-panel w3-round-xxlarge w3-teal">
            <p>Sample CSV File</p>
        </div>
        <div class="w3-panel w3-leftbar w3-border-red">
            <p>Here is a sample CSV File that is compatible with Gameflict. Make sure any file uploaded is similar to
            this one.</p>
            <br/>
            <a class="btn btn-primary" style="text-decoration: none" href="${resource(dir: 'csv', file: "sample_games.csv")}" target="_blank">
                Sample Games CSV
            </a>
        </div>

        <div class="w3-panel w3-round-xxlarge w3-teal">
            <p>Summary</p>
        </div>
        <div class="w3-panel w3-leftbar w3-border-red">
            <p>Gameflict is intended to combine soccer game schedules exported from gotsoccer.  The application
            will load the schedules into the app database and export a master schedule. It will also calculate game conflicts if
            two games are on the same field at the same time.</p>
            <br/>
            <p>Excel files from gotsoccer must be formatted into csv files in order for processing to occur. The file must have columns in the order and format
            specified on this Help page.</p>
        </div>

        <div class="w3-panel w3-round-xxlarge w3-teal">
            <p>File Formatting</p>
        </div>
        <div class="w3-panel w3-leftbar w3-border-red">
            <p>The time column must be formatted such as: 9:00 AM.</p>
            <p>Use excel formatting tools to format the time in the excel file</p>
            <br/>
            <p>The excel file must have its columns ordered in this order:</p>
            <p>#, Date , Start, Age ,Field</p>
            <br/>
            <p>The names of the columns are not critical but the order of them is</p>
            <p>Save the file as a .csv format</p>
        </div>

        <div class="w3-panel w3-round-xxlarge w3-teal">
            <p>Admin Help</p>
        </div>
        <div class="w3-panel w3-leftbar w3-border-red">
            <p>For Technical assistance with this app, please contact: </p>
            <p>Assar Java Consulting: Nirav Assar, nirav_p_assar@yahoo.com, 512-695-3465</p>
        </div>
    </div>
</body>
</html>
