(ns movie-analyzer.ml)

(defn valid-for-ml? [m]
  (and (:year m) (:votes m) (:rating m)))

(defn prepare-dataset [movies]
  (filter valid-for-ml? movies))

(defn split-dataset [movies]
  (let [shuffled (shuffle movies)
        split-at (int (* 0.8 (count shuffled)))]
    {:train (take split-at shuffled)
     :test  (drop split-at shuffled)}))

(def initial-weights
  {:bias    7.0
   :w-year  0.0
   :w-votes 0.0})

(defn normalize-movie [m]
  (assoc m
    :year (/ (:year m) 2025.0)
    :votes (/ (:votes m) 1000000.0)))


(defn predict [weights movie]
  (+ (:bias weights)
     (* (:w-year weights) (:year movie))
     (* (:w-votes weights) (:votes movie))))

(defn update-weights [weights movie learning-rate]
  (let [prediction (predict weights movie)
        error (- prediction (:rating movie))]
    {:bias    (- (:bias weights)
                 (* learning-rate error))
     :w-year  (- (:w-year weights)
                 (* learning-rate error (:year movie)))
     :w-votes (- (:w-votes weights)
                 (* learning-rate error (:votes movie)))}))

(defn train-model [movies epochs learning-rate]
  (loop [weights initial-weights
         remaining epochs]
    (if (zero? remaining)
      weights
      (recur
        (reduce
          (fn [w movie]
            (update-weights w movie learning-rate))
          weights
          movies)
        (dec remaining)))))

(defn mean-absolute-error [model movies]
  (/ (reduce
       (fn [sum movie]
         (+ sum
            (Math/abs
              (double
                (- (predict model movie)
                   (:rating movie))))))
       0.0
       movies)
     (count movies)))

