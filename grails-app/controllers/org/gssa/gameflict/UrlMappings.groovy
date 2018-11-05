package org.gssa.gameflict

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "gameFlict")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
