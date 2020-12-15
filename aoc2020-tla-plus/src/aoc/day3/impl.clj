(ns aoc.day3.impl)

(def truth-value
  {\. false
   \# true})

(defn valid? [row]
  (every? #(contains? truth-value %) row))

;(valid? "..#....#..#...#...##.#........f")

(defn parse [row]
  (map truth-value row))

;(parse "..#....#..#...#...##.#.........")