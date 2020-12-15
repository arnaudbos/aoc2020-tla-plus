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
           [clojure.lang PersistentHashSet
                         PersistentArrayMap
                         PersistentVector
                         LazySeq]))

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
(defmethod convert TupleValue [value] (map convert (.getElems value)))
(defmethod convert SetEnumValue [value] (let [elems (.-elems value)
                                              arr (make-array Value (.capacity elems))]
                                          (.copyInto elems arr)
                                          (set (map convert arr))))
(defmethod convert PersistentHashSet [s] (SetEnumValue. ^"[Ltlc2.value.impl.Value;"
                                           (into-array Value (map convert s))
                                           true))
(defmethod convert PersistentArrayMap [m] (let [ks (keys m)
                                                r-keys (->> ks
                                                         (map #(-> % name UniqueString/uniqueStringOf))
                                                         (into-array UniqueString))
                                                r-vals (->> ks
                                                         (map m)
                                                         (map convert)
                                                         (into-array Value))]
                                            (RecordValue.
                                              ^"[Lutil.UniqueString;"     r-keys
                                              ^"[Ltlc2.value.impl.Value;" r-vals
                                              false)))
(defmethod convert PersistentVector [s] (TupleValue. ^"[Ltlc2.value.impl.Value;" (->> s
                                                                                   (map convert)
                                                                                   (into-array Value))))
(defmethod convert LazySeq [s] (TupleValue. ^"[Ltlc2.value.impl.Value;" (->> s
                                                                          (map convert)
                                                                          (into-array Value))))