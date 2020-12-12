(ns aoc.day4.impl
  (:require [lambdaisland.regal :as regal]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;        ----==| PARTITION ROWS INTO PASSPORT REPRESENTATIONS |==----        ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- empty-row? [row]
  (= "" row))

(defn- empty-seq? [row-seq]
  (= '("") row-seq))

(defn partition-passports [rows]
  (->>
    rows
    (partition-by (complement empty-row?))
    (remove empty-seq?)
    (map (partial clojure.string/join " "))))

#_(partition-passports
    ["hgt:186cm",
     "byr:1971 pid:556900517 cid:334 hcl:#efcc98 ecl:brn",
     "iyr:2014",
     "",
     "iyr:2020 byr:1928",
     "cid:200 hgt:193cm",
     "ecl:grn hcl:#7d3b0c",
     "",
     "cid:233",
     "hcl:a3046e pid:626863952 ecl:lzr iyr:2029 eyr:2024 byr:2000 hgt:193cm",
     "",
     "cid:244",
     "hcl:#866857 ecl:amb byr:1931",
     "eyr:1928 pid:557376401 hgt:182cm iyr:2013"])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                     ----==| PASSPORT REGEXES |==----                       ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- mk-reg [tla]                                ;Three-letter acronym
  [:cat
   [:capture tla]
   ":"
   [:capture [:+ [:not :whitespace]]]
   [:* :whitespace]])

;; byr (Birth Year)
(def byr-reg (mk-reg "byr"))

;; iyr (Issue Year)
(def iyr-reg (mk-reg "iyr"))

;; eyr (Expiration Year)
(def eyr-reg (mk-reg "eyr"))

;; hgt (Height)
(def hgt-reg (mk-reg "hgt"))

;; hcl (Hair Color)
(def hcl-reg (mk-reg "hcl"))

;; ecl (Eye Color)
(def ecl-reg (mk-reg "ecl"))

;; pid (Passport ID)
(def pid-reg (mk-reg "pid"))

;; cid (Country ID)
(def cid-reg (mk-reg "cid"))

(def passport-reg
  (regal/regex [:+
                [:alt
                 byr-reg
                 iyr-reg
                 eyr-reg
                 hgt-reg
                 hcl-reg
                 ecl-reg
                 pid-reg
                 cid-reg]]))

(defn- clear-nil [m]
  (dissoc m nil))

(defn parse-passport [s]
  (if-let [[_ & captures] (re-matches passport-reg s)]
    (->> captures
      (partition 2)
      (map vec)
      (into {})
      clear-nil)
    (throw (ex-info "wups" {}))))

#_(parse-passport
    "hgt:186cm byr:1971 pid:556900517 cid:334 hcl:#efcc98 ecl:brn iyr:2014")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                      ----==| PASSPORT SPECS |==----                        ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defonce digits (set "0123456789"))

(s/def ::string
  (s/and
    string?
    (s/conformer seq)))

(def conform-number
  (s/conformer
    (comp
      #(Integer/parseInt %)
      clojure.string/join)))

(s/def ::number
  (s/and
    ::string
    (s/* digits)
    conform-number))

; byr (Birth Year) - four digits; at least 1920 and at most 2002.
(s/def ::byr (s/and
               ::number
               #(<= 1920 % 2002)))

; iyr (Issue Year) - four digits; at least 2010 and at most 2020.
(s/def ::iyr (s/and
               ::number
               #(<= 2010 % 2020)))

; eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
(s/def ::eyr (s/and
               ::number
               #(<= 2020 % 2030)))

; hgt (Height) - a number followed by either cm or in:
;     If cm, the number must be at least 150 and at most 193.
;     If in, the number must be at least 59 and at most 76.

(def conform-height
  (s/conformer
    (fn [m]
      (-> m
        (update :height (comp
                          #(Integer/parseInt %)
                          clojure.string/join))
        (update :unit (comp
                        clojure.string/join
                        (partial map second)))))))

(s/def ::hgt-cm (s/and
                  ::string
                  (s/cat
                    :height (s/* digits)
                    :unit (s/cat :c #{\c} :m #{\m}))
                  conform-height
                  #(<= 150 (:height %) 193)))

(s/def ::hgt-in (s/and
                  ::string
                  (s/cat
                    :height (s/* digits)
                    :unit (s/cat :i #{\i} :n #{\n}))
                  conform-height
                  #(<= 59 (:height %) 76)))

(s/def ::hgt (s/or :cm ::hgt-cm :in ::hgt-in))

; hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
(def conform-color
  (s/conformer
    (fn [m]
      (-> m
        (update :color (comp
                         clojure.string/join
                         (partial map second)))))))

(s/def ::hcl (s/and
               ::string
               (s/cat
                 :# #{\#}
                 :color (s/* (s/alt
                               :digit digits
                               :letter #{\a \b \c \d \e \f})))
               #(= 6 (count (:color %)))
               conform-color))

; ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})

; pid (Passport ID) - a nine-digit number, including leading zeroes.
(s/def ::pid (s/and
               ::string
               (s/* digits)
               clojure.string/join
               #(= (count %) 9)))

; cid (Country ID) - ignored, missing or not.
(s/def ::cid (constantly true))