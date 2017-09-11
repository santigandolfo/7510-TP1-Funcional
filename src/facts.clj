(ns facts
	(:require [extra :refer :all]
	 :require [clojure.string :refer :all])
)

(defn is-fact? [fact]
	(not (= nil (re-matches FACT-FORMAT fact)))
)

(defn get-fact-type [fact]
  (subs fact 0 (index-of fact "("))
)
