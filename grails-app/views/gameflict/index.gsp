<%@ page import="org.gssa.gameflict.League" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Gameflict</title>
</head>
<body>

    <g:if test="${flash.message && messageType == "fail"}" >
        <div class="alert alert-danger" role="alert">
            <p><b>${flash.message}</b></p>
        </div>
    </g:if>
    <g:if test="${flash.message && messageType == "success"}" >
        <div class="alert alert-success" role="alert">
            <p><b>${flash.message}</b></p>
        </div>
    </g:if>

    <div id="upload" role="main" class="bg-info">
        <section class="row colset-2-its">
            <g:uploadForm action="uploadGameCsv" >
                <fieldset>
                    <h1 style="font-weight: bold;">Select Game CSV and League</h1>
                </fieldset>
                <fieldset>
                    <div class="form-group">
                        <input type="file" name="gameCsvFile"  class="form-control" style="width: 25%;" />
                    </div>
                </fieldset>
                <fieldset>
                    <g:select id="league" name="league" from="${League.list()}" noSelection="['':'---Choose League--']"/>
                </fieldset>
                <fieldset>
                    <g:submitButton name="uploadbutton" class="btn btn-primary" value="Upload Games"/>
                </fieldset>
            </g:uploadForm>
        </section>
    </div>

    <hr/>

    <div id="createExport" role="main" class="bg-success">
        <section class="row colset-2-its">
            <fieldset>
                <h1 style="font-weight: bold;">Create Games Export</h1>
            </fieldset>
            <fieldset>
                <a class="btn btn-primary" style="text-decoration: none" href="<g:createLink controller="gameflict" action="createGamesExport"/>">
                    Create Export
                </a>
            </fieldset>
        </section>
    </div>

    <hr/>

<div id="upload2" role="main" class="bg-info">
    <section class="row colset-2-its">
        <br>
        <g:uploadForm action="uploadTeamCsv" >
            <fieldset>
                <h1 style="font-weight: bold;">Select Team CSV</h1>
            </fieldset>
            <fieldset>
                <div class="form-group">
                    <input type="file" name="teamCsvFile"  class="form-control" style="width: 25%;" />
                </div>
            </fieldset>
            <fieldset>
                <g:submitButton name="uploadbutton" class="btn btn-primary" value="Upload Teams"/>
            </fieldset>
        </g:uploadForm>
    </section>
</div>
</body>
</html>
