(ns movie-analyzer.core)

(def movies
  [{:title "Inception" :year 2010 :genre "Sci-Fi" :rating 8.8}
   {:title "The Godfather" :year 1972 :genre "Crime" :rating 9.2}
   {:title "Interstellar" :year 2014 :genre "Sci-Fi" :rating 8.6}
   {:title "The Dark Knight" :year 2008 :genre "Action" :rating 9.0}
   {:title "Pulp Fiction" :year 1994 :genre "Crime" :rating 8.9}
   {:title "The Matrix" :year 1999 :genre "Sci-Fi" :rating 8.7}
   {:title "Parasite" :year 2019 :genre "Thriller" :rating 8.6}
   {:title "Spirited Away" :year 2001 :genre "Animation" :rating 8.6}])

(defn get-titles [movies] (map :title movies))

(defn filter-by-genre [movies genre]
  (filter (fn [m] (= (:genre m) genre)) movies))

(defn sum-ratings [movies]
  (reduce (fn [total movie] (+ total (:rating movie))) 0 movies))

(defn average-rating [movies]
  (if (empty? movies)
    0
    (/ (sum-ratings movies) (count movies))))

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

(defn -main
  []
  (println "Movies titles:" (get-titles movies))
  (println "Total number of movies:" (count movies))
  (println "Sci-Fi movies:" (get-titles (filter-by-genre movies "Sci-Fi")))
  (println "Average movies rating:" (average-rating movies))
  (println "Best rated movie:" (:title (best-rated-movie movies)))
  (println "Number of movies with rating higher then 8.8:" (get-titles (count-high-rated movies 8.8)))
  )


