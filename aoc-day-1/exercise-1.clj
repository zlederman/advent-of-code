#_{:clj-kondo/ignore [:namespace-name-mismatch]}
(ns exercise-1)
(require '[clojure.string :as str])

(defn open-file [file-name] 
  (slurp file-name))

(defn seperate [raw-text]
  (map str/split-lines (str/split raw-text #"\n\n"))
)

(defn get-total-cals [list-of-str-cals]
  (reduce + 0 (map #(Integer/parseInt %) list-of-str-cals))
)
(defn get-top-three-total [coll-of-total]
  (reduce + 0 (take-last 3 (sort coll-of-total)))
  )
 
(let [coll-total-cals (map get-total-cals
                           (seperate
                            (open-file "aoc-day-1/data")))] 
  {
   :top-three (get-top-three-total coll-total-cals)
   :number-one (apply max coll-total-cals)})















