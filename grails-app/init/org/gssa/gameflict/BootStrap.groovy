package org.gssa.gameflict

import java.time.LocalDate
import java.time.LocalTime

class BootStrap {

    FieldCreator fieldCreator = new FieldCreator()
    GameService gameService

    def init = { servletContext ->

        if (Field.count() < 1) {
            fieldCreator.createFields()
            fieldCreator.createLeagues()
            createSampleGame()
        }


    }
    def destroy = {
    }

    private Game createSampleGame() {
        League gssaRec = League.findByName("GSSA Rec")
        Field mm1 = Field.findByName("MM1")
        LocalTime nineAm = LocalTime.of(9,00)
        LocalDate Oct31 = LocalDate.of(2018, 10, 31)
        gameService.createOrUpdate(409, Oct31, nineAm, AgeGroup.U9, mm1, gssaRec)
    }
}
