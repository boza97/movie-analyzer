(ns movie-analyzer.analysis-test
  (:require
    [clojure.test :refer :all]
    [movie-analyzer.analysis :as analysis]))

(def test-movies
  [{:title    "Movie 1"
    :rating   8.0
    :genres   ["Drama"]
    :director "Director 1"
    :year     2000}

   {:title    "Movie 2"
    :rating   9.0
    :genres   ["Drama" "Crime"]
    :director "Director 1"
    :year     2005}

   {:title    "Movie 3"
    :rating   7.0
    :genres   ["Crime"]
    :director "Director 2"
    :year     1995}

   {:title    "Movie 4"
    :rating   nil
    :genres   ["Drama"]
    :director "Director 2"
    :year     nil}])

(deftest filter-by-genre-test
  (testing "Filters movies by genre"
    (let [drama (analysis/filter-by-genre test-movies "Drama")]
      (is (= 3 (count drama)))
      (is (= #{"Movie 1" "Movie 2" "Movie 4"}
             (set (map :title drama)))))))

(deftest titles-by-genre-test
  (testing "Returns titles for a given genre"
    (is (= #{"Movie 1" "Movie 2" "Movie 4"}
           (set (analysis/titles-by-genre test-movies "Drama"))))))

(deftest average-by-test
  (testing "Calculates average while ignoring nil values"
    (is (= 8.0
           (analysis/average-by test-movies :rating)))))

(deftest average-rating-test
  (testing "Calculates average movie rating"
    (is (= 8.0
           (analysis/average-rating test-movies)))))

(deftest average-rating-for-genre-test
  (testing "Calculates average rating per genre"
    (is (= 8.5
           (analysis/average-rating-for-genre test-movies "Drama")))
    (is (= 8.0
           (analysis/average-rating-for-genre test-movies "Crime")))
    (is (= 0
           (analysis/average-rating-for-genre test-movies "Sci-Fi")))))

(deftest average-rating-after-year-test
  (testing "Calculates average rating after a given year"
    (is (= 9.0
           (analysis/average-rating-after-year test-movies 2001)))
    (is (= 8.5
           (analysis/average-rating-after-year test-movies 1999)))
    (is (= 0
           (analysis/average-rating-after-year test-movies 2020)))))

(deftest movies-by-director-test
  (testing "Groups movies by director"
    (let [by-director (analysis/movies-by-director test-movies)]
      (is (= 2 (count (get by-director "Director 1"))))
      (is (= 2 (count (get by-director "Director 2"))))
      (is (= #{"Movie 1" "Movie 2"}
             (set (map :title (get by-director "Director 1"))))))))

(deftest movies-by-genre-test
  (testing "Groups movies by genre"
    (let [by-genre (analysis/movies-by-genre test-movies)]
      (is (= 3 (count (get by-genre "Drama"))))
      (is (= 2 (count (get by-genre "Crime"))))
      (is (= #{"Movie 1" "Movie 2" "Movie 4"}
             (set (map :title (get by-genre "Drama"))))))))

(deftest average-rating-by-director-test
  (testing "Calculates average rating per director"
    (let [result (analysis/average-rating-by-director test-movies)
          as-map (into {} result)]
      (is (= 8.5 (get as-map "Director 1")))
      (is (= 7.0 (get as-map "Director 2"))))))
