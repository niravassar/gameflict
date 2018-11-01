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
}
