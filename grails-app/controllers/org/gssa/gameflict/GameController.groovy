package org.gssa.gameflict

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class GameController {

    GameService gameService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [gameCount: gameService.count(), gameInstanceList: gameService.list(params)]
    }

    def create() {
        [gameInstance: new Game(params)]
    }

    def save(Game game) {
        if (game == null) {
            notFound()
            return
        }

        try {
            gameService.save(game)
        } catch (ValidationException e) {
            respond game.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'game.label', default: 'Game'), game.id])
                redirect game
            }
            '*' { respond game, [status: CREATED] }
        }
    }

    def edit(Long id) {
        [gameInstance:  gameService.get(id)]
    }

    def update(Game game) {
        if (game == null) {
            notFound()
            return
        }

        try {
            gameService.save(game)
        } catch (ValidationException e) {
            respond game.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'game.label', default: 'Game'), game.id])
                redirect game
            }
            '*'{ respond game, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        gameService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'game.label', default: 'Game'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'game.label', default: 'Game'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
