---- MODULE DayTwoBis ----
EXTENDS DayTwoParser, InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input

S == {ParseEntry(Input[i]) : i \in DOMAIN Input}

ValidPassword(entry) ==
    LET min == entry.min
        max == entry.max
        letter == entry.letter
        password == entry.password
    IN \/ /\ CharAt(password, min) = letter
          /\ CharAt(password, max) # letter
       \/ /\ CharAt(password, min) # letter
          /\ CharAt(password, max) = letter

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