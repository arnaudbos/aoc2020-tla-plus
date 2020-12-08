---- MODULE DayThree ----
EXTENDS DayThreeParser, InputHelper, TLC, Integers, Sequences

CONSTANT Input

ASSUME Assumptions ==
    \* Input well formed
    /\ \A rowIdx \in DOMAIN Input :
        LET row == Input[rowIdx]
        \* Rows contain only \. and \# chars
        IN /\ ValidRow(row)
        \* Rows all have the same length
           /\ \A otherIdx \in DOMAIN Input \ {rowIdx} :
              Len(row) = Len(Input[otherIdx])

\* Parse Input so we can deal with sequences of booleans
\* rather than strings (thanks Florent Le Gac for the idea)
ROWS == [i \in DOMAIN Input |-> ParseRow(Input[i])]
SIZE == Len(Head(ROWS))

VARIABLE position, slope, trees

vars == <<position, slope, trees>>

Position(c, r) ==
    [column |-> c, row |-> r]

Slope(cDiff, rDiff) ==
    [columnDiff |-> cDiff, rowDiff |-> rDiff]

MoveAccordingToSlope(pos, s) ==
    Position(pos.column + s.columnDiff, pos.row + s.rowDiff)

IsaTree(row, column) ==
    LET offset == ((column - 1) % SIZE) + 1
    IN ROWS[row][offset]

\* expr == IsaTree(1, 39)

Init ==
    /\ position = Position(1, 1)
    /\ slope = Slope(1, 1)
    \* /\ slope = Slope(3, 1)
    \* /\ slope = Slope(5, 1)
    \* /\ slope = Slope(7, 1)
    \* /\ slope = Slope(1, 2)
    /\ trees = 0

Traverse ==
    \/ \* If we haven't reached the bottom
       /\ position.row <= Len(Input)
       \* And we're hitting a tree
       /\ IsaTree(position.row, position.column)
       \* Traverse the map following the toboggan slope
       /\ position' = MoveAccordingToSlope(position, slope)
       \* Assumulating trees
       /\ trees' = trees + 1
       /\ slope' = slope
    \/ \* If we haven't reached the bottom
       /\ position.row <= Len(Input)
       \* And we're not hitting a tree
       /\ ~IsaTree(position.row, position.column)
       \* Traverse the map following the toboggan slope
       /\ position' = MoveAccordingToSlope(position, slope)
       \* Without accumulating trees
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

\* expr == 61 * 257 * 64 * 47 * 37
====