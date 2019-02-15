package org.gssa.gameflict

class BootStrap {

    GameflictCreator gameflictCreator = new GameflictCreator()

    def init = { servletContext ->

        if (Field.count() < 1) {
            gameflictCreator.createFields()
            gameflictCreator.createLeagues()
            gameflictCreator.createAgeGroups()
        }


    }
    def destroy = {
    }
}
