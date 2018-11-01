package org.gssa.gameflict

class BootStrap {

    FieldCreator fieldCreator = new FieldCreator()

    def init = { servletContext ->

        if (Field.count() < 1) {
            fieldCreator.createFields()
            fieldCreator.createLeagues()
        }


    }
    def destroy = {
    }
}
