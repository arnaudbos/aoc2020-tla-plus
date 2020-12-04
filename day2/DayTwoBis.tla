---- MODULE DayTwoBis ----
EXTENDS InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input

S == {Input[i] : i \in DOMAIN Input}

CharAt(password, index) ==
    SubString(password, index, index)

ValidPassword(entry) ==
    LET min == LowestNumberOfTimes(entry)
        max == HighestNumberOfTimes(entry)
        letter == Letter(entry)
        password == Password(entry)
    IN \/ /\ CharAt(password, min) = letter
          /\ CharAt(password, max) # letter
       \/ /\ CharAt(password, min) # letter
          /\ CharAt(password, max) = letter

ASSUME Assumptions ==
    \* No dups in input
    /\ Len(Input) = Cardinality(S)
    \* Input well formed
    /\ \A entryIdx \in DOMAIN Input :
        LET entry == Input[entryIdx]
        IN /\ Len(Password(entry)) > 0
           /\ Len(Letter(entry)) = 1
           /\ LowestNumberOfTimes(entry) > 0
           /\ HighestNumberOfTimes(entry) > 0
    \* At least one valid password
    /\ \E entryIdx \in DOMAIN Input :
        LET entry == Input[entryIdx]
        IN ValidPassword(entry)

VARIABLE valid

Init == valid = {}

Next == valid' = {entry \in S : ValidPassword(entry)}

\* Inverse invariant
NoValidPassword == valid = {}
====