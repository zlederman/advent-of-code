#_{:clj-kondo/ignore [:namespace-name-mismatch]}
(ns exercise-3)
(require '[clojure.string :as str])
(require '[clojure.set :as set])
(defn parse-input [file-name]
  (str/split-lines (slurp file-name)))

(defn split-to-sets [line]
  (let [chars (char-array line)
        len (count chars)
        mid-point (/ len 2)]
    (map set (split-at mid-point chars))
  )
  )
(defn get-intersections [sets]
  (reduce some (first sets) sets)
)

(defn get-priority [chr]
  (let [ascii-val (int chr)]
    (cond
      (and (>= ascii-val 65) (<= ascii-val 90))
      (- ascii-val 38)
      (and (>= ascii-val 97) (<= ascii-val 122))
      (- ascii-val 96)
      )))

(defn determine-bad-item [line]
  (-> (split-to-sets line)
      (get-intersection)
      (get-priority)))

(reduce + (map determine-bad-item (parse-input "aoc-day-3/data")))


(defn get-trips[raw]
   (map str/split-lines (re-seq #"[A-Za-z]+\n[A-Za-z]+\n[A-Za-z]+" raw))
  )

(defn list-of-trip-sets [list-of-trips]
  (map 
   (fn [trip] (map #(-> (char-array %) (set)) trip))
   list-of-trips)) 

(defn get-all [file-name]
   (list-of-trip-sets (get-trips (slurp file-name)))
  )

(defn intersections [list-of-trip-sets] 
  (set/intersection 
   (second list-of-trip-sets)
   (first list-of-trip-sets) 
   (last list-of-trip-sets)) 
  )

(defn exercise-three [file-name]
  
  {:part-one (reduce + (map determine-bad-item (parse-input file-name))),
   :part-two (reduce + 
                     (map #(-> (intersections %) (first) (get-priority))
                          (get-all file-name)))}
  )

(exercise-three "aoc-day-3/data")