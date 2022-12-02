#_{:clj-kondo/ignore [:namespace-name-mismatch]}
(ns exercise-2)
(require '[clojure.string :as str])
(def score-map {:w 6, :t 3, :l 0})
(def mappings-me {:X 1, :Y 2, :Z 3})
(def true-mapping {:X :l, :Y :t, :Z :w})
(def mappings-you {:A 1, :B 2, :C 3})
(def result-mat-me [[:t :w :l]
                    [:l :t :w]
                    [:w :l :t]])
(def result-mat-you [[:t :l :w]
                     [:w :t :l]
                     [:l :w :t]])
(def pt-mappings [[:C :A :B]
                  [:A :B :C]
                  [:B :C :A]])


(defn open-file [file-name]
  (slurp file-name))

(defn map-to-score [rnd] 
  (let  [you (first rnd)
         me  (last rnd)]
    [(get mappings-you you) (get mappings-me me)]
))

(defn get-outcome [rnd mat]
  (let [you-idx (first rnd)
        me-idx (last rnd)] 
    (nth (nth mat (- you-idx 1)) (- me-idx 1))))

(defn parse-input [file-name]
  (map (fn [line] (map keyword (str/split line #" ")))
       (str/split-lines (open-file file-name))))

(defn get-outcome-score [rnd mat]
  (get score-map (get-outcome rnd mat))
)

(defn get-round-scores [rnd]
  (let [choice-scores (map-to-score rnd)
        me-outcome-score (get-outcome-score choice-scores result-mat-me)
        you-outcome-score (get-outcome-score choice-scores result-mat-you)]
    (map + choice-scores [you-outcome-score me-outcome-score])
  ) 
)

(defn get-me-score [kw-rnd] 
  (let [num-vec (map-to-score kw-rnd)
        outcome (get true-mapping (last kw-rnd))
        choice (get-outcome num-vec pt-mappings)
        ]
    (+ (get score-map outcome) (get mappings-you choice))
    ))

(defn get-totals [me-you-scores]
  (let [you-scores (map #(nth % 0) me-you-scores)
        me-scores (map #(nth % 1) me-you-scores)]
    [(reduce + 0 you-scores) (reduce + 0 me-scores)]
    )
  )

(defn exercise-two [file-name]
  (let [input (parse-input file-name)]
    {:part-one (get-totals (map get-round-scores input)), 
     :part-two (reduce + 0 (map get-me-score input))} 
    )
  )

(exercise-two "aoc-day-2/data")