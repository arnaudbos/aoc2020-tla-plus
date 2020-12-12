---- MODULE DayFourBis ----
EXTENDS DayFourParser, InputHelper, Integers, FiniteSets, Sequences

CONSTANT Input, Fields

PASSPORTS == ParseRows(Input)
S == {PASSPORTS[i] : i \in DOMAIN PASSPORTS}

ValidKey(k, s) ==
    CASE k = "byr" -> ValidByr(s)
      [] k = "iyr" -> ValidIyr(s)
      [] k = "eyr" -> ValidEyr(s)
      [] k = "hgt" -> ValidHgt(s)
      [] k = "hcl" -> ValidHcl(s)
      [] k = "ecl" -> ValidEcl(s)
      [] k = "pid" -> ValidPid(s)
      [] k = "cid" -> ValidCid(s)
      []     OTHER -> Assert(FALSE, "wups")

ValidPassport(passport) ==
    /\ \/ DOMAIN passport = Fields
       \/ DOMAIN passport = Fields \ {"cid"}
    /\ \A key \in DOMAIN passport : ValidKey(key, passport[key])

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