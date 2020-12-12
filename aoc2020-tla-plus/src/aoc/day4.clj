(ns aoc.day4
  (:require [aoc.day4.impl :as impl]
            [tlc2.values :as values]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                        ----==| PARSE ROWS |==----                          ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- convert [rows]
  (->> rows
    values/convert
    (map values/convert)))

(defn- convert-val [m k v]
  (assoc m k (values/convert v)))

(defn- record [passport]
  (->> passport
    (reduce-kv convert-val {})
    values/record))

(defn parse [rows]
  (let [rows (convert rows)
        passports (->> rows
                    impl/partition-passports
                    (map impl/parse-passport))]
    (->> passports
      (map record)
      (values/sequence))))

#_(parse
    (values/sequence
      [(values/convert "hgt:186cm"),
       (values/convert "byr:1971 pid:556900517 cid:334 hcl:#efcc98 ecl:brn"),
       (values/convert "iyr:2014"),
       (values/convert ""),
       (values/convert "iyr:2020 byr:1928"),
       (values/convert "cid:200 hgt:193cm"),
       (values/convert "ecl:grn hcl:#7d3b0c"),
       (values/convert ""),
       (values/convert "cid:233"),
       (values/convert "hcl:a3046e pid:626863952 ecl:lzr iyr:2029 eyr:2024 byr:2000 hgt:193cm"),
       (values/convert ""),
       (values/convert "cid:244"),
       (values/convert "hcl:#866857 ecl:amb byr:1931"),
       (values/convert "eyr:1928 pid:557376401 hgt:182cm iyr:2013")]))

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