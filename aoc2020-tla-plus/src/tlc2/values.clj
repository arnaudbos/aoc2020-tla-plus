(ns tlc2.values
  (:refer-clojure :exclude [sequence])
  (:import [tlc2.value.impl StringValue
                            IntValue
                            BoolValue
                            RecordValue
                            Value
                            TupleValue
                            SetEnumValue]
           [util UniqueString]
           [clojure.lang PersistentHashSet]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;                        ----==| TLA+ UTILS |==----                          ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmulti convert class)
(defmethod convert StringValue [value] (-> value .val .toString))
(defmethod convert String [s] (StringValue. ^String s))
(defmethod convert IntValue [value] (.val value))
(defmethod convert Integer [i] (IntValue/gen i))
(defmethod convert Boolean [b] (BoolValue. b))
(defmethod convert TupleValue [value] (.getElems value))
(defmethod convert SetEnumValue [value] (let [elems (.-elems value)
                                              arr (make-array Value (.capacity elems))]
                                          (.copyInto elems arr)
                                          (set arr)))
(defmethod convert PersistentHashSet [s] (SetEnumValue. ^"[Ltlc2.value.impl.Value;"
                                           (into-array Value s)
                                           true))

(defn record-keys [ks]
  (->> ks
    (map #(-> % name UniqueString/uniqueStringOf))
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