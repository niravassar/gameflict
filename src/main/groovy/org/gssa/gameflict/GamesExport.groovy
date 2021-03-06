package org.gssa.gameflict

/**
 * This contains all the data for a game export - games and conflicts
 */
class GamesExport {
    List<Game> games = []
    List<GameConflict> gameConflicts = []
    List<CoachConflict> coachConflicts = []

    static String constructHeaderRow() {
        ["#","Date","Start","Age","Field", "League", "Home Coach", "Away Coach"].join(',')
    }
}

