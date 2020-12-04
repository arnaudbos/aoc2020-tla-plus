---- MODULE DayOne ----
EXTENDS InputHelper, Integers, Sequences, TLC, FiniteSets

CONSTANT Input, Sum

S == {Input[i] : i \in DOMAIN Input}

ASSUME Assumptions ==
    \* No dups in input
    /\ Len(Input) = Cardinality(S)
    \* There actually is a solution
    /\ \E x \in S : \E y \in S \ {x} : x + y = Sum

(* THIS VERSION DID NOT WORK *)
\* VARIABLE solution
\*
\* \* Initial state is, purposedly, not the solution
\* Init == solution = {Sum+1, Sum}
\*
\* \* Pair matches predicate
\* CheckSolution(pair, Pred(_,_)) ==
\*     LET
\*         x == CHOOSE x \in pair : TRUE
\*         y == CHOOSE y \in pair \ {x} : TRUE
\*     IN Pred(x, y)
\*
\* Next ==
\*     \* Enabling condition
\*     /\ CheckSolution(solution, LAMBDA x, y : x + y # Sum)
\*     \* Action "find solution"
\*     /\ LET candidates == {s \in SUBSET S : Cardinality(s) = 2}
\*        IN solution' \in {c \in candidates : CheckSolution(c, LAMBDA x, y : x + y = Sum)}
\*
\* \* Inverse invariant
\* Solution == CheckSolution(solution, LAMBDA x, y : x + y # Sum)

(* THIS VERSION DID *)
VARIABLE x, y

Init ==
    /\ x \in S
    /\ y \in S \ {x}

\* Never actually evaluated since invariant is broken in initial state
Next == Assert(FALSE, "wups")

\* Inverse invariant
Solution == x + y # Sum

expr == 285 * 1735
====