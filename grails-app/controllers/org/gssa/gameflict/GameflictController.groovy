package org.gssa.gameflict

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.multipart.MultipartFile

import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
import java.time.LocalDate

import static org.springframework.http.HttpStatus.OK

class GameflictController {

    @Value('${grails.mime.types.csv}')
    String csvMimeType
    @Value('${grails.converters.encoding}')
    String encoding

    GameflictService gameflictService

    def index() { }

    def uploadGameCsv(){
        String fileName = retrieveUploadFileName("gameCsvFile")
        String leagueName = retrieveLeagueName()

        if (fileName.isEmpty() || leagueName.isEmpty()) {
            flash.message = 'The uploaded file is empty or the league is not selected!'
            return render(view:'index', model: [messageType: "fail"])
        } else {
            int numOfGames = gameflictService.importAndSaveGames(fileName, leagueName)
            if (numOfGames > 0) {
                flash.message = "Uploaded $numOfGames games from ${fileName} with league ${leagueName}"
            }
            return render(view:'index', model: [messageType: "success"])
        }
    }

    def uploadTeamCsv(){
        String fileName = retrieveUploadFileName("teamCsvFile")
        if (fileName.isEmpty()) {
            flash.message = 'The uploaded team file is empty!'
            return render(view:'index', model: [messageType: "fail"])
        }

      println "this code reached here"
    }

    def createGamesExport() {
        GamesExport gamesExport = gameflictService.createGamesExport()
        log.info gamesExport.dump()

        List<String> gameExportLines = []
        gameExportLines << GamesExport.constructHeaderRow()
        for (g in gamesExport.games) {
            String csvRow = g.constructRow()
            gameExportLines << csvRow
        }

        for (c in gamesExport.gameConflicts) {
            String gameConflictRow = c.constructRow()
            gameExportLines << gameConflictRow
        }

        for (c in gamesExport.coachConflicts) {
            String coachConflictRow = c.constructRow()
            gameExportLines << coachConflictRow
        }

        LocalDate localDate = LocalDate.now()
        ServletOutputStream outs = prepareCsvResponse(response, "gamesExport-${localDate}", gameExportLines)

        outs.flush()
        outs.close()
    }

    /*********************************************************************************************/

    protected String retrieveUploadFileName(String fileName) {
        MultipartFile csvFile = request.getFile(fileName)

        if (csvFile.getOriginalFilename() == "") {
            return ""
        }

        // write the file to local file
        File newFile = new File("${csvFile.getOriginalFilename()}.copy")
        FileOutputStream outputStream = new FileOutputStream(newFile)
        byte[] bytes = csvFile.getBytes()
        outputStream.write(bytes)
        outputStream.close()

        String fileNamePath = newFile.getAbsolutePath()
        fileNamePath
    }

    protected retrieveLeagueName() {
        String leagueName = params.league
        leagueName
    }

    protected ServletOutputStream prepareCsvResponse(HttpServletResponse response, String fileName, List<String> csvLines) {
        response.status = OK.value()
        response.contentType = "${csvMimeType};charset=${encoding}"
        response.setHeader "Content-disposition", "attachment; filename=\"${fileName}.csv\""
        ServletOutputStream outs = response.outputStream

        csvLines.each { String line ->
            outs << "${line}\n"
        }
        outs
    }
}
