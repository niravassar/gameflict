package org.gssa.gameflict

import com.opencsv.CSVReader
import grails.gorm.transactions.Transactional

/**
 * Parses the csv files with games
 */
@Transactional
class GameCsvImportService {

    List<String[]> parseCsv(String fileName) {
        CSVReader reader = new CSVReader(new FileReader(fileName))
        List<String[]> gameCsvValues = reader.readAll()
        gameCsvValues.remove(0)
        gameCsvValues
    }

    List<GameRow> convertIntoGameRows(List<String[]> gameCsvValues) {
        List<GameRow> gameRows = []
        for (csvRow in gameCsvValues) {
            GameRow gameRow = new GameRow()
            gameRow.with {
                gameNumber = csvRow[0].toInteger()
                dateAsString = csvRow[1]
                timeAsString = csvRow[2]
                ageGroupAsString = csvRow[3]
                fieldName = csvRow[4]
                if (csvRow.length >= 6) {
                    coachName1 = csvRow[5]
                }
                if (csvRow.length >= 7) {
                    coachName2 = csvRow[6]
                }
            }
            gameRows << gameRow
        }
        gameRows
    }
}
