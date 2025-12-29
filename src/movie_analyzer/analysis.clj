(ns movie-analyzer.analysis)

(defn filter-by-genre [movies genre]
  (filter
    (fn [m] (some (fn [g] (= g genre)) (:genres m)))
    movies))

(defn titles-by-genre [movies genre]
  (map :title (filter-by-genre movies genre)))

(defn average-by [movies value-fn]
  (let [values (filter some? (map value-fn movies))]
    (if (empty? values)
      0
      (/ (reduce + values) (count values)))))

(defn average-rating [movies]
  (average-by movies :rating))

(defn best-rated-movie [movies]
  (reduce
    (fn [best movie]
      (if (> (:rating movie) (:rating best)) movie best))
    (first movies)
    movies))

(defn count-high-rated [movies min-rating]
  (reduce
    (fn [acc movie]
      (if (> (:rating movie) min-rating) (+ acc 1) acc))
    0 movies))

(defn average-rating-for-genre [movies genre]
  (average-by
    (filter-by-genre movies genre)
    :rating))

(defn average-rating-by-genre [movies]
  (reduce
    (fn [acc m]
      (reduce
        (fn [a g]
          (let [current (get a g {:sum 0 :count 0})]
            (assoc a g
                     {:sum   (+ (:sum current) (:rating m))
                      :count (inc (:count current))})))
        acc
        (:genres m)))
    {}
    movies))

(defn finalize-averages [stats]
  (map
    (fn [[genre data]]
      [genre (/ (:sum data) (:count data))])
    stats))

(defn average-rating-after-year [movies year]
  (average-by
    (filter
      (fn [m]
        (and (:year m)
             (> (:year m) year)))
      movies)
    :rating))

(defn movies-by-director [movies]
  (group-by :director movies))

(defn movies-by-genre [movies]
  (reduce
    (fn [acc m]
      (reduce
        (fn [a g]
          (assoc a g (conj (get a g []) m)))
        acc
        (:genres m)))
    {}
    movies))

(defn average-rating-by-director [movies]
  (map
    (fn [[director ms]]
      [director (average-rating ms)])
    (movies-by-director movies)))



