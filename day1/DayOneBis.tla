---- MODULE DayOneBis ----
EXTENDS InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input, Sum

S == {Input[i] : i \in DOMAIN Input}

ASSUME Assumptions ==
    /\ Len(Input) = Cardinality(S)
    /\ \E x \in S : \E y \in S \ {x} : \E z \in S \ {x, y} : x + y + z = Sum

VARIABLE x, y, z

Init ==
    /\ x \in S
    /\ y \in S \ {x}
    /\ z \in S \ {x, y}

Next == Assert(FALSE, "wups")

Solution == x + y + z # Sum

result == 503 * 550 * 967
====
