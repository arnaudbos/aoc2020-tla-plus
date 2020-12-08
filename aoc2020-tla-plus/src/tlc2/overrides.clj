(ns tlc2.overrides
  (:import [tlc2.overrides DayTwoParser
                           DayThreeParser]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                                                            ;;
;;              ----==|🎄 Advent Of Code 2020 Parsers 🎄|==----                ;;
;;              ----==|🎄        TLC Overrides        🎄|==----                ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(gen-class
  :name "tlc2.overrides.TLCOverrides"
  :implements [tlc2.overrides.ITLCOverrides]
  :prefix "overrides-"
  :main false
  :method [[^{Override {}}
            get [] "[Ljava.lang.Class;"]])

(defn overrides-get [_]
  (into-array Class [DayTwoParser
                     DayThreeParser
                     ]))