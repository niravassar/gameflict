package org.gssa.gameflict

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.codehaus.groovy.runtime.GStringImpl
import spock.lang.Specification

import java.time.LocalDate
import java.time.ZoneId

@Integration
@Rollback
class GameIntSpec extends Specification {

    GameService gameService

    def setup() {
        gameService.gameEntry(200, "10/28/2018","09:00:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018")
        gameService.gameEntry(201, "10/28/2018","10:00:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018")
        gameService.gameEntry(202, "10/31/2018","10:15:00 AM","U10",
                "GSSA Oak Grove Park #5A","GSSA Rec Fall 2018")
        gameService.gameEntry(203, "11/15/2018","05:15:00 PM","U13",
                "Oakgrove #1","GSSA Rec Fall 2018")
        gameService.gameEntry(204, "11/15/2018","10:00:00 AM","U9",
                "GSSA Meadowmere #2A","GSSA Rec Fall 2018")

    }

    def cleanup() {
    }

    void "test query all games by date"() {
        setup:
        List<Game> gameList = Game.list()
        assert gameList.size() == 4

        when:
        LocalDate localDate = gameService.parseDate("10/29/2018")
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        List<Game> games = gameService.findAllGamesAfterDate(date)

        then:
        games.size() == 2
        games[0].gameNumber == 202
    }

    void "test query all games by field and day"() {
        setup:
        List<Game> gameList = Game.list()
        assert gameList.size() == 4

        when:
        LocalDate localDate = gameService.parseDate("10/28/2018")
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        Field field = Field.findByName("MM2A")
        List<Game> games = gameService.findAllGamesByFieldAndDate(date, field)

        then:
        games.size() == 2
        games[0].gameNumber == 200
        games[1].gameNumber == 201
    }

    void "test groupBy date and field"() {
        when:
        Map<String, List<Game>> groups = gameService.findAllAndGroupByFieldAndDate()
        Set<String> keys = groups.keySet()
        List<Game> mm2GamesOct28 = groups.get(keys[0])

        then:
        groups.size() == 4
        mm2GamesOct28.size() == 2
        mm2GamesOct28[0].gameNumber == 200
        mm2GamesOct28[1].gameNumber == 201
    }
}
