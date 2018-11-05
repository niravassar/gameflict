package org.gssa.gameflict

import org.springframework.web.multipart.MultipartFile

class GameFlictController {


    GameFlictService gameFlictService

    def index() { }

    def uploadGameCsv(){
        String fileName = retrieveUploadFileName()
        String leagueName = retrieveLeagueName()

        if (fileName.isEmpty() || leagueName.isEmpty()) {
            flash.message = 'The uploaded file is empty or the league is not selected!'
        }
        return redirect(action:'index')
    }

    def createGamesExport() {
        println "asdkfhasdklfjasdklfj"
    }

    protected String retrieveUploadFileName() {
        MultipartFile csvFile = request.getFile("csvFile") // get Excel file from form

        if (csvFile.getOriginalFilename() == "") {
            return ""
        }

        // write the file to local file
        File newFile = new File(csvFile.getOriginalFilename())
        FileOutputStream outputStream = new FileOutputStream(newFile)
        byte[] bytes = csvFile.getBytes()
        outputStream.write(bytes)
        outputStream.close()

        String fileName = newFile.getAbsolutePath()
        fileName
    }

    protected retrieveLeagueName() {
        String leagueName = params.league
        leagueName
    }
}
