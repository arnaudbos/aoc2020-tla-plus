(ns aoc
  (:require [aoc.day2 :as day2]
            [aoc.day3 :as day3]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄            Day 2            🎄|==----                ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(gen-class
  :name tlc2.overrides.DayTwoParser
  :prefix "day2-"
  :main false
  :methods [^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ParseEntry"
                                               :module     "DayTwoParser"}}
             parse [tlc2.value.impl.StringValue] tlc2.value.impl.RecordValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "CountOccurrences"
                                               :module     "DayTwoParser"}}
             count [tlc2.value.impl.StringValue tlc2.value.impl.StringValue] tlc2.value.impl.IntValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "CharAt"
                                               :module     "DayTwoParser"}}
             charAt [tlc2.value.impl.StringValue tlc2.value.impl.IntValue] tlc2.value.impl.StringValue]
            ]
  )

(defn day2-parse [s]
  (day2/parse s))

(defn day2-count [s c]
  (day2/count-occurrences s c))

(defn day2-charAt [s i]
  (day2/char-at s i))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄            Day 3            🎄|==----                ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(gen-class
  :name tlc2.overrides.DayThreeParser
  :prefix "day3-"
  :main false
  :methods [^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidRow"
                                               :module     "DayThreeParser"}}
             validRow [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ParseRow"
                                               :module     "DayThreeParser"}}
             parse [tlc2.value.impl.StringValue] tlc2.value.impl.TupleValue]
            ]
  )

(defn day3-validRow [s]
  (day3/valid? s))

(defn day3-parse [s]
  (day3/parse s))