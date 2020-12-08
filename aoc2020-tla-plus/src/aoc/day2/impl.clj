(ns aoc.day2.impl
  (:require [lambdaisland.regal :as regal]))

(def re
  (regal/regex [:cat
                [:capture [:+ :digit]]
                "-"
                [:capture [:+ :digit]]
                [:* :whitespace]
                [:capture [:class [\a \z]]]
                ":"
                [:* :whitespace]
                [:capture [:+ [:class [\a \z]]]]]))

(def groups
  [:min :max :letter :password])

(defn parse [s]
  (if-let [[_ & captures] (re-matches re s)]
    (let [m (->> [groups captures]
              (apply map vector)
              (into {}))]
      (-> m
        (update :min #(Integer/parseInt %))
        (update :max #(Integer/parseInt %))))
    (throw (ex-info "wups" {}))))

;(parse "16-18 h: vhhhhhhhhhhhhphhrnh")

(defn count-occurrences [s c]
  (->
    (re-pattern c)
    (re-seq s)
    count))

(defn char-at [s i]
  (str (nth s i)))