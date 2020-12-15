(ns aoc.day4
  (:require [aoc.day4.impl :as impl]
            [tlc2.values :as values]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                        ----==| PARSE ROWS |==----                          ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn parse [rows]
  (let [rows (values/convert rows)
        passports (->> rows
                    impl/partition-passports
                    (map impl/parse-passport))]
    (values/convert passports)))

(parse
  (values/convert
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
     "eyr:1928 pid:557376401 hgt:182cm iyr:2013"]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                      ----==| VALIDATE FIELDS |==----                       ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- validate [spec s]
  (->> s
    values/convert
    (s/valid? spec)
    values/convert))

(defn valid-byr? [s]
  (validate ::impl/byr s))

(defn valid-iyr? [s]
  (validate ::impl/iyr s))

(defn valid-eyr? [s]
  (validate ::impl/eyr s))

(defn valid-hgt? [s]
  (validate ::impl/hgt s))

(defn valid-hcl? [s]
  (validate ::impl/hcl s))

(defn valid-ecl? [s]
  (validate ::impl/ecl s))

(defn valid-pid? [s]
  (validate ::impl/pid s))

(defn valid-cid? [s]
  (validate ::impl/cid s))