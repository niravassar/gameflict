package org.gssa.gameflict

/**
 * This contains all the data for a game export - games and conflicts
 */
class GamesExport {
    List<Game> games = []
    List<GameConflict> gameConflicts = []

    static String constructHeaderRow() {
        ["#","Date","Start","Age","Field", "League", "Coach Name1", "Coach Name2"].join(',')
    }
}

