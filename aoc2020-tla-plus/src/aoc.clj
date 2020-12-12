(ns aoc
  (:require [aoc.day2 :as day2]
            [aoc.day3 :as day3]
            [aoc.day4 :as day4]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄            DAY 2            🎄|==----                ;;
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
             charAt [tlc2.value.impl.StringValue tlc2.value.impl.IntValue] tlc2.value.impl.StringValue]])



(defn day2-parse [s]
  (day2/parse s))

(defn day2-count [s c]
  (day2/count-occurrences s c))

(defn day2-charAt [s i]
  (day2/char-at s i))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄            DAY 3            🎄|==----                ;;
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
             parse [tlc2.value.impl.StringValue] tlc2.value.impl.TupleValue]])



(defn day3-validRow [s]
  (day3/valid? s))

(defn day3-parse [s]
  (day3/parse s))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄            DAY 4            🎄|==----                ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(gen-class
  :name tlc2.overrides.DayFourParser
  :prefix "day4-"
  :main false
  :methods [^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ParseRows"
                                               :module     "DayFourParser"}}
             parse [tlc2.value.impl.TupleValue] tlc2.value.impl.TupleValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidByr"
                                               :module     "DayFourParser"}}
             validByr [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidIyr"
                                               :module     "DayFourParser"}}
             validIyr [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidEyr"
                                               :module     "DayFourParser"}}
             validEyr [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidHgt"
                                               :module     "DayFourParser"}}
             validHgt [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidHcl"
                                               :module     "DayFourParser"}}
             validHcl [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidEcl"
                                               :module     "DayFourParser"}}
             validEcl [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidPid"
                                               :module     "DayFourParser"}}
             validPid [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]
            ^{:static true}
            [^{tlc2.overrides.TLAPlusOperator {:identifier "ValidCid"
                                               :module     "DayFourParser"}}
             validCid [tlc2.value.impl.StringValue] tlc2.value.impl.BoolValue]])

(defn day4-parse [s]
  (day4/parse s))

(defn day4-validByr [s]
  (day4/valid-byr? s))

(defn day4-validIyr [s]
  (day4/valid-iyr? s))

(defn day4-validEyr [s]
  (day4/valid-eyr? s))

(defn day4-validHgt [s]
  (day4/valid-hgt? s))

(defn day4-validHcl [s]
  (day4/valid-hcl? s))

(defn day4-validEcl [s]
  (day4/valid-ecl? s))

(defn day4-validPid [s]
  (day4/valid-pid? s))

(defn day4-validCid [s]
  (day4/valid-cid? s))
