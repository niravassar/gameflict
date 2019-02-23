package org.gssa.gameflict

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

import java.time.LocalDate
import java.time.LocalTime

class GameServiceSpec extends HibernateSpec implements ServiceUnitTest<GameService>{

    GameflictCreator gameflictCreator = new GameflictCreator()
    LocalTime nineAm = LocalTime.of(9,00)
    LocalDate oct31 = LocalDate.of(2018, 10, 31)
    LocalTime tenAm = LocalTime.of(10,00)
    LocalDate nov1 = LocalDate.of(2018, 11, 1)
    String homeCoach = "Nirav Assar"
    String visitorCoach = "Kirk Challgren"

    List<Class> getDomainClasses() { [Field, FieldNickName, Game, League, Team]}

    def setup() {
        gameflictCreator.createFields()
        gameflictCreator.createLeagues()
        gameflictCreator.createAgeGroups()
    }

    /*************** test game entry *******************************/

    void "test create game"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 1
        game.gameNumber == 409
        game.field.name == "MM1"
        game.dateAsLocalDate.isEqual(oct31)
        game.time.equals(nineAm)
        game.homeCoach == "Nirav Assar"
        game.visitorCoach == "Kirk Challgren"
    }


    void "test gameEntry"() {
        given:
        def mockFieldService = Mock(FieldService)
        service.fieldService = mockFieldService

        when:
        service.gameEntry(205, "10/28/2018","9:00 AM","U9",
                "MM1","GSSA Rec",homeCoach, visitorCoach)
        Game game = Game.findByGameNumber(205)

        then:
        1 * mockFieldService.findFieldByName("MM1") >> Field.findByName("MM1")
        game.gameNumber == 205
        game.dateAsLocalDate.toString() == "2018-10-28"
        game.ageGroup.durationMinutes == 75
    }

    void "test game is updated when time is changed"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        game = service.createOrUpdateGame(409, nov1, tenAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 1
        game.gameNumber == 409
        game.field.name == "MM1"
        game.dateAsLocalDate.isEqual(nov1)
        game.time.equals(tenAm)
        game.homeCoach == "Nirav Assar"
    }

    void "test game update - same game number but different league"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        League gssaNMSCL = League.findByName("GSSA NMCSL")
        Field mm1 = Field.findByName("MM1")
        String homeCoach = "Nirav Assar"
        AgeGroup U9 = AgeGroup.findByName("U9")
        AgeGroup U13 = AgeGroup.findByName("U13")
        Game game = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(409, nov1, tenAm, U13, mm1, gssaNMSCL, homeCoach, visitorCoach)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        game.gameNumber == 409
        game.league.name == "GSSA Rec"
        game2.gameNumber == 409
        game2.league.name == "GSSA NMCSL"
    }

    /**************** test team ***********************************************/

    void "test teamEntry"() {
        when:
        service.teamEntry("Longhorns", "U9", "Nirav Assar")
        Team team = Team.findByTeamName("Longhorns")

        then:
        team.teamName == "Longhorns"
        team.ageGroup.toString() == "U9"
        team.coachName == "Nirav Assar"
    }

    void "test teamEntry update"() {
        when:
        service.teamEntry("Longhorns", "U9", "Nirav Assar")
        service.teamEntry("Longhorns", "U10", "Nirav Assar")
        Team team = Team.findByTeamName("Longhorns")

        then:
        team.teamName == "Longhorns"
        team.ageGroup.toString() == "U10"
        team.coachName == "Nirav Assar"
    }

    /*************** test methods to parse data *******************************/

    void "test match ageGroup"() {
        when:
        AgeGroup u9AgeGroup = service.matchAgeGroup("U9")
        AgeGroup u13AgeGroup = service.matchAgeGroup("U13")

        then:
        u9AgeGroup.name == "U9"
        u9AgeGroup.durationMinutes == 75
        u13AgeGroup.name  == "U13"
        u13AgeGroup.durationMinutes == 90
    }

    void "test parse localdate"() {
        when:
        LocalDate localDate1 = service.parseDate("08/15/18")
        LocalDate localDate2 = service.parseDate("10/31/2018")
        LocalDate localDate3 = service.parseDate("1/1/18")
        LocalDate localDate4 = service.parseDate("03/1/2018")

        then:
        localDate1.toString() == "2018-08-15"
        localDate2.toString() == "2018-10-31"
        localDate3.toString() == "2018-01-01"
        localDate4.toString() == "2018-03-01"
    }

    void "test parse localtime"() {
        when:
        LocalTime localTime1 = service.parseTime("9:00 AM")
        LocalTime localTime2 = service.parseTime("2:15 PM")

        then:
        localTime1.toString() == "09:00"
        localTime2.toString() == "14:15"
    }

    /*************** test game conflicts methods *******************************/

    void "test game block time"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        List<Game> gameList = Game.list()
        GameBlockTime gameBlockTime = game.gameBlockTime

        then:
        gameList.size() == 1
        gameBlockTime.game.gameNumber == 409
        gameBlockTime.startTime.toString() == "09:00"
        gameBlockTime.endTime.toString() == "10:15"
    }

    void "test game overlapping true"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, tenAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        boolean result1 = game1.isGameOverlapping(game2)
        boolean result2 = game2.isGameOverlapping(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        result1
    }

    void "test game overlapping true - same time"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        boolean result1 = game1.isGameOverlapping(game2)
        boolean result2 = game2.isGameOverlapping(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        result1
        result2
    }

    void "test game overlapping false"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, tenAm.plusHours(1), U9, mm1, gssaRec, homeCoach, visitorCoach)
        boolean result1 = game1.isGameOverlapping(game2)
        boolean result2 = game2.isGameOverlapping(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        !result1
        !result2
    }

    void "test is coach involved in game - false "() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, tenAm.plusHours(1), U9, mm1, gssaRec, "Bob", "Ted")
        boolean result1 = game1.isCoachInvolved(game2)
        boolean result2 = game2.isCoachInvolved(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        !result1
        !result2
    }

    void "test is coach involved in game - true homeCoach"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, tenAm.plusHours(1), U9, mm1, gssaRec, homeCoach, "Ted")
        boolean result1 = game1.isCoachInvolved(game2)
        boolean result2 = game2.isCoachInvolved(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        result1
        result2
    }

    void "test is coach involved in game - true visitorCoach"() {
        when:
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        AgeGroup U9 = AgeGroup.findByName("U9")
        Game game1 = service.createOrUpdateGame(409, oct31, nineAm, U9, mm1, gssaRec, homeCoach, visitorCoach)
        Game game2 = service.createOrUpdateGame(410, oct31, tenAm.plusHours(1), U9, mm1, gssaRec, "Bob", visitorCoach)
        boolean result1 = game1.isCoachInvolved(game2)
        boolean result2 = game2.isCoachInvolved(game1)
        List<Game> gameList = Game.list()

        then:
        gameList.size() == 2
        result1
        result2
    }

}
