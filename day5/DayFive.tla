---- MODULE DayFive ----
EXTENDS DayFiveParser, InputHelper, TLC, Sequences, Integers, FiniteSets, Reals

Rows == BOOLEAN \X BOOLEAN \X BOOLEAN \X BOOLEAN \X BOOLEAN \X BOOLEAN \X BOOLEAN
Columns == BOOLEAN \X BOOLEAN \X BOOLEAN

CONSTANT Input

ASSUME Assumptions ==
    \* Input well formed
    /\ \A idx \in DOMAIN Input :
        LET seat == Input[idx]
        IN /\ ValidSeat(seat)
           /\ ParseSeat(seat) \in {<<s[1], s[2]>> : s \in Rows \X Columns}

RECURSIVE XOR0(_,_,_)
XOR0(left, right, result) ==
    IF Len(left) = 0 \/ Len(right) = 0
    THEN result
    ELSE XOR0(Tail(left), Tail(right), result \o <<Head(left) # Head(right)>>)

XOR(left, right) ==
    IF Len(left) # Len(right)
    THEN Assert(FALSE, "left and right operands not same length, implement leftpad maybe?")
    ELSE XOR0(left, right, <<>>)

\* expr == XOR(<<TRUE, TRUE, TRUE>>, <<FALSE, TRUE, FALSE>>)

RECURSIVE ToDenaryAux(_,_)
ToDenaryAux(bits, acc) ==
    IF Len(bits) = 0
    THEN acc
    ELSE LET bit == Head(bits)
         IN ToDenaryAux(Tail(bits), acc + IF bit THEN 2 ^ (Len(bits) - 1) ELSE 0)

ToDenary(bits) == ToDenaryAux(bits, 0)

\* expr == ToDenary(XOR(<<TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE>>, <<TRUE, FALSE, TRUE, FALSE, FALSE, TRUE, TRUE>>))

Row(seat) ==
    ToDenary(XOR(<<TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE>>, seat[1]))

\* expr == Row(SEATS[1])

Col(seat) ==
    ToDenary(XOR(<<TRUE, TRUE, TRUE>>, seat[2]))

\* expr == Col(SEATS[1])

\* seat ID: multiply the row by 8, then add the column
SeatId(seat) == Row(seat) * 8 + Col(seat)

\* expr == 119 = SeatId(<<<<TRUE, TRUE, TRUE, FALSE, FALSE, FALSE, TRUE>>, <<FALSE, FALSE, FALSE>>>>)

Seats == {SeatId(ParseSeat(Input[i])) : i \in DOMAIN Input}

highest ==
    CHOOSE highest \in Seats :
        \A s \in Seats \ {highest} :
            highest >= s

mySeat ==
    CHOOSE s \in 1..highest :
        /\ {s} \ Seats = {s}            \* s \notin Seats
        /\ {s - 1, s + 1} \ Seats = {}  \* s - 1 \in Seats /\ s + 1 \in Seats
====