package org.gssa.gameflict;

enum AgeGroup {

    U4(1.5),
    U5(1.5),
    U6(1.0),
    U7(1.0),
    U8(1.0),
    U9(1.25),
    U10(1.25),
    U11(1.5),
    U12(1.5),
    U13(1.5),
    U14(2.0),
    U15(2.0),
    U16(2.0),
    U17(2.0),
    U18(2.0)

    Double duration

    AgeGroup(BigDecimal duration) {
        this.duration = duration
    }

}