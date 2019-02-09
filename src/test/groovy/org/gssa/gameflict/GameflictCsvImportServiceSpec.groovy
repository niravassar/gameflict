package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class GameflictCsvImportServiceSpec extends HibernateSpec implements ServiceUnitTest<GameflictCsvImportService>{

    static final String SAMPLE_XLS_PATH = "src/integration-test/resources"

    void "test parse csv for games"() {
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

    /**************** parse teams ************************************/

    void "test parse csv for teams"() {
        when:
        File teamsCsv = new File("$SAMPLE_XLS_PATH/GSSA Sample Teams - Three.csv")
        List<String[]> teamCsvValues = service.parseCsv(teamsCsv.path)

        then:
        teamCsvValues.size() == 3
        teamCsvValues[0].toString() == "[U7, Lightning, Viney Kharbanda]"
        teamCsvValues[1].toString() == "[U9, Longhorns, Nirav Assar]"
        teamCsvValues[2].toString() == "[U14, FIREBALLS, Bradford Storm]"
    }

    void "test convert to teamrows"() {
        when:
        File teamCsv = new File("$SAMPLE_XLS_PATH/GSSA Sample Teams - Three.csv")
        List<String[]> teamCsvValues = service.parseCsv(teamCsv.path)
        List<TeamRow> teamRows = service.convertIntoTeamRows(teamCsvValues)

        then:
        teamRows.size() == 3
        teamRows[1].teamName == "Longhorns"
        teamRows[1].ageGroupAsString == "U9"
        teamRows[1].coachName == "Nirav Assar"
    }

}
