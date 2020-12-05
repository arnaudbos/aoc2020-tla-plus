---- MODULE DayThree ----
EXTENDS InputHelper, TLC, Integers, Sequences

CONSTANT Input

ASSUME Assumptions ==
    \* Input well formed
    /\ \A rowIdx \in DOMAIN Input :
        LET row == Input[rowIdx]
        IN /\ ValidRow(row)
           /\ \A otherIdx \in DOMAIN Input \ {rowIdx} :
              Len(row) = Len(Input[otherIdx])

VARIABLE position, slope, trees

vars == <<position, slope, trees>>

SIZE == Len(Head(Input))

Position(c, r) ==
    [column |-> c, row |-> r]

Slope(cDiff, rDiff) ==
    [columnDiff |-> cDiff, rowDiff |-> rDiff]

MoveAccordingToSlope(pos, s) ==
    Position(pos.column + s.columnDiff, pos.row + s.rowDiff)

RECURSIVE RowAux(_,_)
RowAux(rows, row) ==
    IF row = 0
    THEN Head(rows)
    ELSE RowAux(Tail(rows), row - 1)

Row(rows, row) ==
    RowAux(rows, row - 1)

\* expr == Row(Input, 3) = Head(Tail(Tail(Input)))

CellAt(row, column) ==
    CharAt(Input[row], ((column - 1) % SIZE) + 1)

\* expr == CellAt(1, 39)

Init ==
    /\ position = Position(1, 1)
    \* /\ slope = Slope(1, 1)
    \* /\ slope = Slope(3, 1)
    \* /\ slope = Slope(5, 1)
    \* /\ slope = Slope(7, 1)
    /\ slope = Slope(1, 2)
    /\ trees = 0

Traverse ==
    \/ \* If we haven't reached the bottom
       /\ position.row <= Len(Input)
       \* And we're hitting a tree
       /\ IsaTree(CellAt(position.row, position.column))
       \* Traverse the map following the toboggan slope
       /\ position' = MoveAccordingToSlope(position, slope)
       \* Count the tree
       /\ trees' = trees + 1
       /\ slope' = slope
    \/ \* If we haven't reached the bottom
       /\ position.row <= Len(Input)
       \* And we're not hitting a tree
       /\ ~IsaTree(CellAt(position.row, position.column))
       \* Traverse the map following the toboggan slope
       /\ position' = MoveAccordingToSlope(position, slope)
       /\ UNCHANGED <<trees, slope>>

Done ==
    \* If we have passed the bottom
    /\ position.row > Len(Input)
    \* Done
    /\ UNCHANGED vars

Next ==
    \/ Traverse
    \/ Done

\* Inverese invariant
BottomNeverReached == position.row <= Len(Input)

expr == 61 * 257 * 64 * 47 * 37
====