---- MODULE DayTwo ----
EXTENDS DayTwoParser, InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input

S == {ParseEntry(Input[i]) : i \in DOMAIN Input}

ValidPassword(entry) ==
    LET count == CountOccurrences(entry.password, entry.letter)
    IN /\ count >= entry.min
       /\ count <= entry.max

ASSUME Assumptions ==
    \* No dups in input
    /\ Len(Input) = Cardinality(S)
    \* Input well formed
    /\ \A entry \in S :
        /\ entry.min > 0
        /\ entry.max > 0
    \* At least one valid password
    /\ \E entry \in S :
        ValidPassword(entry)

VARIABLE valid

Init == valid = {}

Next == valid' = {entry \in S : ValidPassword(entry)}

\* Inverse invariant
NoValidPassword == valid = {}
====