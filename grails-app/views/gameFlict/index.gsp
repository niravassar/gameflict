<%@ page import="org.gssa.gameflict.League" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>GameFlict</title>
</head>
<body>

    <div id="import" role="main">
        <section class="row colset-2-its">
            <h2>Select Game CSV and League</h2>
            <g:if test="${flash.message}">
                <div class="message" role="alertdialog">
                    ${flash.message}
                </div>
            </g:if>
            <g:uploadForm action="uploadGameCsv" >
                <fieldset>
                    <div class="form-group">
                        <input type="file" name="csvFile"  class="form-control"/>
                    </div>
                </fieldset>
                <fieldset>
                    <g:select id="league" name="league" from="${League.list()}" noSelection="['':'---Choose League--']"/>
                </fieldset>
                <fieldset>
                    <g:submitButton name="uploadbutton" class="save" value="Upload" />
                </fieldset>
            </g:uploadForm>
        </section>
    </div>
</body>
</html>
