(ns movie-analyzer.parser
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

(defn load-movies []
  (mapify
    (map select-columns
         (rest
           (parse
             (slurp filename))))))
