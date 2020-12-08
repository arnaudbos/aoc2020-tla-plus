(ns tlc2.values
  (:refer-clojure :exclude [sequence])
  (:import [tlc2.value.impl StringValue
                            IntValue
                            BoolValue
                            RecordValue
                            Value
                            TupleValue]
           [clojure.lang Keyword]
           [util UniqueString]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                        ----==| TLA+ Utils |==----                          ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmulti convert class)
(defmethod convert StringValue [value] (-> value .val .toString))
(defmethod convert String [s] (StringValue. ^String s))
(defmethod convert IntValue [value] (.val value))
(defmethod convert Integer [i] (IntValue/gen i))
(defmethod convert Keyword [k] (-> k name UniqueString/uniqueStringOf))
(defmethod convert Boolean [b] (BoolValue. b))

(defn record-keys [ks]
  (->> ks
    (map convert)
    (into-array UniqueString)))

(defn record [m]
  (let [ks (keys m)
        r-keys (record-keys ks)
        r-vals (into-array Value (map m ks))]
    (RecordValue.
      ^"[Lutil.UniqueString;"     r-keys
      ^"[Ltlc2.value.impl.Value;" r-vals
      false)))

(defn sequence [s]
  (TupleValue. ^"[Ltlc2.value.impl.Value;" (into-array Value s)))