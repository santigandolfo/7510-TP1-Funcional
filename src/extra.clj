(ns extra
	(:require [clojure.string :refer :all])
)

(def FACT-FORMAT #"\w+\((\w+, )*\w+\)")
(def RULE-FORMAT #"\w+\((\w+, )*\w+\) :- ((\w+\((\w+, )*\w+\)), )*(\w+\((\w+, )*\w+\))")

( defn in? [list element]
  (not (nil? (some #(= element %) list)))
)

(defn trim-elements [database]
	(trim-newline (trim database))
)
