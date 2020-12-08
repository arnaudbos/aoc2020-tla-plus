(ns aoc.day2
  (:require [aoc.day2.impl :as impl]
            [tlc2.values :as values]))

(defn parse [s]
  (let [m (impl/parse (values/convert s))
        m {:min (-> m :min values/convert)
           :max (-> m :max values/convert)
           :letter (-> m :letter values/convert)
           :password (-> m :password values/convert)}]
    (values/record m)))

(defn count-occurrences [s c]
  (let [s (values/convert s)
        c (values/convert c)]
    (->
      (impl/count-occurrences s c)
      values/convert)))

(defn char-at [s i]
  (let [s (values/convert s)
        i (values/convert i)]
    (->
      (impl/char-at s (dec i))
      values/convert)))
