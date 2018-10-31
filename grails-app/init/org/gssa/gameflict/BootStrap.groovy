package org.gssa.gameflict

class BootStrap {

    def init = { servletContext ->

        FieldCreator fieldCreator = new FieldCreator()
        fieldCreator.createFields()
    }
    def destroy = {
    }
}
