(ns aoc.day5
  (:require [aoc.day5.impl :as impl]
            [tlc2.values :as values]))

(defn valid? [s]
  (->> s
    values/convert
    impl/valid?
    values/convert))

(defn parse [seat]
  (->>
    seat
    values/convert
    impl/parse
    values/convert))