(ns database
	(:require [extra :refer :all]
	 :require [rules :refer :all]
	 :require [facts :refer :all]
	 :require [clojure.string :refer :all]
	)
)

(defn format-database [database]
	(remove blank? (map trim-elements (split database  #"\.")))
)

(defn invalid-query? [query]
	(and (not (is-fact? query)) (not (is-rule? query)))
)

(defn invalid-database? [formated-database]
	(in? (map #(invalid-query? %)  formated-database) true)
)

(defn generate-parsed-database [database]
	(let 	[	formated-database (format-database database)
				list-of-facts (filter is-fact? formated-database)
				list-of-rules (map create-rule (filter is-rule? formated-database))
			]
		(if (invalid-database? formated-database) 
			nil
			{	:list-of-facts list-of-facts
				:list-of-rules list-of-rules
			}
		)
	)
)

(defn query-rule-and-facts-are-in-database [database query-rule]
	(let 	[	list-of-facts (:list-of-facts database)
				list-of-rule-facts (generate-rule-facts database query-rule)
			]
		(not (in? (map #(in? list-of-facts %) list-of-rule-facts) false))
	)
)
