(ns aoc.day5.impl)

(defn valid? [s]
  (let [[row col] (partition-all 7 s)]
    (and
      (= (count row) 7)
      (= (count col) 3)
      (every? #{\F \B} row)
      (every? #{\L \R} col))))

;(valid? "FBBFFBFLRL")

(def bsp                                           ;binary space partitioning
  #{\F \L})

(defn parse [seat]
  (->>
    seat
    seq
    (map #(contains? bsp %))
    (partition-all 7)))

;(parse "FBBFFBFLRL")