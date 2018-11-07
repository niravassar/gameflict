package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

/**
 * Tests the service layer with games already entered in the database in setup
 */
@Integration
@Rollback
class GameIntSpec extends Specification {

    GameService gameService

    def setup() {
        gameService.gameEntry(200, "10/28/2018","9:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018", "Nirav Assar", "Kirk Challgren")
        gameService.gameEntry(201, "10/28/2018","10:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018", "Bob", "Nirav Assar")
        gameService.gameEntry(202, "10/31/2018","10:15 AM","U10",
                "GSSA Oak Grove Park #5A","GSSA Rec Fall 2018", "Jon", "Rob")
        gameService.gameEntry(203, "11/15/2018","5:15 PM","U13",
                "Oakgrove #1","GSSA Rec Fall 2018", "Nirav Assar", "Kirk Challgren")
        gameService.gameEntry(204, "11/15/2018","10:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018", "Nirav Assar", "Kirk Challgren")
        gameService.gameEntry(205, "11/15/2018","12:00 PM","U10",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018", "Mark", "Steve")

    }

    def cleanup() {
    }

    void "test query all games by date"() {
        when:
        LocalDate localDate = gameService.parseDate("10/29/2018")
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        List<Game> games = gameService.findAllGamesOrAfterDate(date)

        then:
        games.size() == 4
        games[0].gameNumber == 202
    }

    void "test query all games - no date passed"() {
        when:
        List<Game> games = gameService.findAllGamesOrAfterDate()

        then:
        games.size() == 6
        games[0].gameNumber == 200
    }

    void "test groupBy date and field"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByFieldAndDate()
        Set<String> keys = groups.keySet()
        List<Game> mm2GamesOct28 = groups.get(keys[0])

        then:
        groups.size() == 4
        mm2GamesOct28.size() == 2
        mm2GamesOct28[0].gameNumber == 200
        mm2GamesOct28[1].gameNumber == 201
    }

    void "test groupBy date - for coaches conflict purpose"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByDate()
        Set<String> keys = groups.keySet()
        List<Game> oct28Games = groups.get(keys[0])

        then:
        groups.size() == 3
        oct28Games.size() == 2
        oct28Games[0].gameNumber == 200
        oct28Games[1].gameNumber == 201
    }

    void "test game conflicts for one group - conflict exists"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByFieldAndDate()
        Set<String> keys = groups.keySet()
        List<Game> mm2GamesOct28 = groups.get(keys[0])
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsForOneGroup(keys[0], mm2GamesOct28)

        then:
        gameConflicts.size() == 2
        gameConflicts[0].key == "MM2A-2018-10-28"
        gameConflicts[0].game1.gameNumber == 200
    }

    void "test coach conflicts for one day - conflict exists"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByDate()
        Set<String> keys = groups.keySet()
        List<Game> oct28Games = groups.get(keys[0])
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsForOneDay(keys[0], oct28Games)

        then:
        coachConflicts.size() == 2
        coachConflicts[0].key == "2018-10-28"
        coachConflicts[0].game1.gameNumber == 200
        coachConflicts[0].getConflictMessage() == "Coach Conflict 2018-10-28 Game #200 has Nirav Assar and Kirk Challgren while #201 has Bob and Nirav Assar"
    }

    void "test game conflicts for one group - no conflicts"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByFieldAndDate()
        Set<String> keys = groups.keySet()
        List<Game> mm2GamesNov15 = groups.get(keys[3])
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsForOneGroup(keys[3], mm2GamesNov15)

        then:
        gameConflicts.size() == 0
    }

    void "test coach conflicts for one day - no conflicts"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllGamesAndGroupByDate()
        Set<String> keys = groups.keySet()
        List<Game> nov15Games = groups.get(keys[2])
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsForOneDay(keys[2], nov15Games)

        then:
        coachConflicts.size() == 0
    }

    void "test game conflicts for all groups"() {
        when:
        List<GameConflict> gameConflicts = gameService.calculateGameConflictsAfterDate()

        then:
        gameConflicts.size() == 2
        gameConflicts[1].key == "MM2A-2018-10-28"
        gameConflicts[1].game1.gameNumber == 201
    }

    void "test coach conflicts for all groups"() {
        when:
        List<CoachConflict> coachConflicts = gameService.calculateCoachConflictsAfterDate()

        then:
        coachConflicts.size() == 2
        coachConflicts[1].key == "2018-10-28"
        coachConflicts[1].game1.gameNumber == 201
        coachConflicts[1].getConflictMessage() == "Coach Conflict 2018-10-28 Game #201 has Bob and Nirav Assar while #200 has Nirav Assar and Kirk Challgren"
    }
}
