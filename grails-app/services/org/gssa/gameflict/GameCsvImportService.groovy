package org.gssa.gameflict

import com.opencsv.CSVReader
import grails.gorm.transactions.Transactional

@Transactional
class GameCsvImportService {

    protected List<String[]> parseCsv(String fileName) {
        CSVReader reader = new CSVReader(new FileReader(fileName))
        List<String[]> gameCsvValues = reader.readAll()
        gameCsvValues.remove(0)
        gameCsvValues
    }

    protected List<GameRow> convertIntoGameRows(List<String[]> gameCsvValues) {
        List<GameRow> gameRows = []
        for (csvRow in gameCsvValues) {
            GameRow gameRow = new GameRow()
            gameRow.with {
                gameNumber = csvRow[0].toInteger()
                dateAsString = csvRow[1]
                timeAsString = csvRow[2]
                ageGroupAsString = csvRow[3]
                fieldName = csvRow[4]
            }
            gameRows << gameRow
        }
        gameRows
    }
}
