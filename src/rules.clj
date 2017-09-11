(ns rules
	(:require [extra :refer :all]
	 :require [clojure.string :refer :all])
)

(defn is-rule? [rule]
	(not (= nil (re-matches RULE-FORMAT rule)))
)

(defn get-rule-type [rule]
	(subs rule 0 (index-of rule "("))
)

(defn get-rule-parameters [rule]
	(split (subs rule (inc (index-of rule "(")) (index-of rule ")")) #", ")
)

(defn get-rule-facts [rule]
	(split (replace (subs rule (+ 3 (index-of rule ":- "))) #"\), " ");") #";")
)

(defn create-rule [rule]
	(let 	[	type (get-rule-type rule)
				parameters (get-rule-parameters rule)
				facts (get-rule-facts rule)
			]
		{	:rule-type type
		 	:rule-parameters parameters
		 	:rule-facts facts
		 	}
	)
)

(defn replace-rule-fact-parameters [fact parameters]
	(replace fact (re-pattern (join "|" (keys parameters))) parameters)
)

(defn generate-rule-facts [database rule]
	(let 	[	rule-type (get-rule-type rule)
				rule-parameters (get-rule-parameters rule)
				hashmap-rule (first (filter #(= (:rule-type %) rule-type) (:list-of-rules database)))
				parameters (zipmap (:rule-parameters hashmap-rule) rule-parameters)
			]
		(map #(replace-rule-fact-parameters % parameters) (:rule-facts hashmap-rule))
	)
)
