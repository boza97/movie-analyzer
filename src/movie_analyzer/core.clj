(ns movie-analyzer.core
  (:require
    [movie-analyzer.analysis :as analysis]
    [movie-analyzer.parser :as parser]))

(def movies (parser/load-movies))

(defn -main
  []
  (println "Movies titles:" (analysis/get-titles movies))
  (println "Total number of movies:" (count movies))
  (println "Sci-Fi movies:" (analysis/titles-by-genre movies "Sci-Fi"))
  (println "Average movies rating:" (analysis/average-rating movies))
  (println "Best rated movie:" (:title (analysis/best-rated-movie movies)))
  (println "Number of movies with rating higher then 8.8:" (analysis/count-high-rated movies 8.8))
  (println "Average rating of Drama movies:" (analysis/average-rating-for-genre movies "Drama"))
  (println "Average ratings by genre:" (analysis/finalize-averages (analysis/average-rating-by-genre movies)))
  (println "Average ratings after year 2000:" (analysis/average-rating-after-year movies 2000))
  )


