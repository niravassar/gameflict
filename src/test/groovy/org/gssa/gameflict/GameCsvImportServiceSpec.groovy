package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class GameCsvImportServiceSpec extends HibernateSpec implements ServiceUnitTest<GameCsvImportService>{

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    void "test parse csv"() {
        when:
        File gameCsv = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        List<String[]> gameCsvValues = service.parseCsv(gameCsv.path)

        then:
        gameCsvValues.size() == 6
        gameCsvValues[0].toString() == "[935, 10/27/2018, 9:00 AM, U8, GSSA Bob Jones #1A, Nirav Assar, Kirk Challgren]"
        gameCsvValues[5].toString() == "[971, 11/06/2018, 7:15 PM, U8, GSSA Meadowmere #4C, Nirav Assar, Joe]"
    }

    void "test convert to gamerows"() {
        when:
        File gameCsv = new File("$SAMPLE_XLS_PATH/GSSARECF186_sample.csv")
        List<String[]> gameCsvValues = service.parseCsv(gameCsv.path)
        List<GameRow> gameRows = service.convertIntoGameRows(gameCsvValues)

        then:
        gameRows.size() == 6
        gameRows[3].gameNumber == 513
        gameRows[3].dateAsString == "10/28/2018"
    }
}
