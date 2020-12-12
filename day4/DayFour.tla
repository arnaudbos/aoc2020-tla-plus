---- MODULE DayFour ----
EXTENDS DayFourParser, InputHelper, Integers, FiniteSets, Sequences

CONSTANT Input, Fields

PASSPORTS == ParseRows(Input)
S == {PASSPORTS[i] : i \in DOMAIN PASSPORTS}

ValidPassport(passport) ==
    \/ DOMAIN passport = Fields
    \/ DOMAIN passport = Fields \ {"cid"}

ASSUME Assumptions ==
    \* No dups in input
    /\ Len(PASSPORTS) = Cardinality(S)
    \* Input well formed
    /\ \A passport \in S :
        DOMAIN passport \subseteq Fields
    \* At least one valid password
    /\ \E passport \in S :
        ValidPassport(passport)

VARIABLE valid

Init == valid = {}

Next == valid' = {passport \in S : ValidPassport(passport)}

\* Inverse invariant
NoValidPassport == valid = {}
====