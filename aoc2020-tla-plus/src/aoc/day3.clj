(ns aoc.day3
  (:require [aoc.day3.impl :as impl]
            [tlc2.values :as values]))

(defn valid? [s]
  (->> s
    values/convert
    impl/valid?
    values/convert))

(defn parse [row]
  (->>
    row
    values/convert
    impl/parse
    (map values/convert)
    (values/sequence)))