---- MODULE DayTwo ----
EXTENDS InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input

S == {Input[i] : i \in DOMAIN Input}

RECURSIVE CountAux(_,_,_)
CountAux(str, c, acc) ==
    IF Len(str) = 0
    THEN acc
    ELSE IF Head(str) = c
         THEN CountAux(Tail(str), c, acc + 1)
         ELSE CountAux(Tail(str), c, acc)

Count(password, letter) ==
    CountAux(password, letter, 0)

ValidPassword(entry) ==
    LET min == LowestNumberOfTimes(entry)
        max == HighestNumberOfTimes(entry)
        letter == Letter(entry)
        password == Password(entry)
        count == Count(password, letter)
    IN /\ count >= min
       /\ count <= max

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