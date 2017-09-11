(ns logical-interpreter
	(:require [extra :refer :all]
	 :require [rules :refer :all]
	 :require [facts :refer :all]
	 :require [database :refer :all]
	 :require [clojure.string :refer :all])
)

(defn query-is-a-fact [database fact]
	(let 	[	fact-type (get-fact-type fact)
				list-of-fact-types (map get-fact-type (database :list-of-facts))
			]
		(in? list-of-fact-types fact-type)
	)
)

(defn query-is-a-fact-of-database [database query-fact]
	(let 	[	list-of-facts (database :list-of-facts)
			]
		(in? list-of-facts query-fact)
	)
)

(defn query-is-a-rule [database rule]
	(let 	[	rule-type (get-rule-type rule)
				list-of-rule-types (map :rule-type (database :list-of-rules))
			]
		(in? list-of-rule-types rule-type)
	)
)

(defn evaluate-query
	"Returns true if the rules and facts in database imply query, false if not. If
	either input can't be parsed, returns nil"
	[database query]
	(let 	[ parsed-database (generate-parsed-database database)
			]
		(if (or (nil? parsed-database) (invalid-query? query))
			 nil 
			(cond
				(query-is-a-fact parsed-database query)
					(query-is-a-fact-of-database parsed-database query)
				(query-is-a-rule parsed-database query)
					(query-rule-and-facts-are-in-database parsed-database query)
				:else nil
				
			)
		)
	)
)
