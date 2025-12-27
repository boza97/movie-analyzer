(ns movie-analyzer.core
  (:require [clojure.string :as str]))

(def filename "resources/imdb_top_1000.csv")
(def movie-keys
  [:title :year :genres :rating :director :votes :gross])

(defn select-columns [row]
  [(nth row 1)                                              ; title
   (nth row 2)                                              ; year
   (nth row 5)                                              ; genres
   (nth row 6)                                              ; rating
   (nth row 9)                                              ; director
   (nth row 14)                                             ; votes
   (nth row 15)])                                           ; gross

(defn clean-string [s]
  (str/replace s #"^\"|\"$" ""))

(defn clean-number-string [s]
  (str/replace
    (str/replace
      (str/replace s "\"" "")
      "," "")
    "\r" ""))

(defn str->int-safe [s]
  (if (or (nil? s) (= (clean-string s) "PG") (= (clean-number-string s) ""))
    nil
    (Integer/parseInt (clean-number-string s))))

(defn str->double-safe [s]
  (if (or (nil? s) (= s ""))
    nil
    (Double/parseDouble s)))

(defn str->genres [s]
  (str/split s #",\s*"))

(def conversions
  {:title    identity
   :year     str->int-safe
   :genres   str->genres
   :rating   str->double-safe
   :director identity
   :votes    str->int-safe
   :gross    str->int-safe})

(defn convert [movie-key value]
  ((get conversions movie-key) (clean-string value)))

(defn parse [string]
  (map
    (fn [line]
      (str/split
        line
        #",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
    (str/split string #"\n")))

(defn mapify [rows]
  (map
    (fn [row]
      (reduce
        (fn [row-map pair]
          (assoc row-map
            (first pair)
            (convert (first pair) (second pair))))
        {}
        (map vector movie-keys row)))
    rows))

(def movies
  (mapify
    (map select-columns
         (rest
           (parse
             (slurp filename))))))


(defn get-titles [movies] (map :title movies))

(defn filter-by-genre [movies genre]
  (filter
    (fn [m] (some (fn [g] (= g genre)) (:genres m)))
    movies))

(defn titles-by-genre [movies genre]
  (map :title (filter-by-genre movies genre)))


(defn sum-ratings [movies]
  (reduce + (map :rating movies)))

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

(defn -main
  []
  (println "Movies titles:" (get-titles movies))
  (println "Total number of movies:" (count movies))
  (println "Sci-Fi movies:" (get-titles (filter-by-genre movies "Sci-Fi")))
  (println "Average movies rating:" (average-rating movies))
  (println "Best rated movie:" (:title (best-rated-movie movies)))
  (println "Number of movies with rating higher then 8.8:" (count-high-rated movies 8.8))
  (println "Average rating of Drama movies:" (average-rating-for-genre movies "Drama"))
  (println "Average ratings by genre:" (finalize-averages (average-rating-by-genre movies)))
  (println "Average ratings after year 2000:" (average-rating-after-year movies 2000))
  )


