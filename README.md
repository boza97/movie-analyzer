# Movie Analyzer – Clojure Project

This project was developed as part of the course
**Tools and Methods of Artificial Intelligence and Software Engineering**
at the Faculty of Organizational Sciences.

The goal of the project is to demonstrate functional programming concepts in **Clojure** through analysis of a
real-world dataset, as well as a simple demonstration of machine learning principles.

---

## Project Overview

The application analyzes an IMDB movies dataset (Top 1000 movies) and provides:

- Functional data processing using `map`, `filter`, and `reduce`
- Grouping and aggregation of movies by genre and director
- Calculation of average ratings and basic statistics
- Unit tests for core analysis functions
- A simple machine learning demo using linear regression

---

## Dataset

The dataset is a public [`IMDB CSV`](https://www.kaggle.com/datasets/omarhanyy/imdb-top-1000) file containing information such as:

- Movie title
- Release year
- Genres
- IMDB rating
- Director
- Number of votes
- Gross revenue

---

## Project Structure

src/  
└── movie_analyzer/  
├── core.clj ; Application entry point  
├── parser.clj ; CSV parsing and data preparation  
├── analysis.clj ; Functional data analysis  
└── ml.clj ; Simple machine learning demo

test/  
└── movie_analyzer/  
└── analysis_test.clj

---

## CSV Parsing

The input dataset for this project is a CSV file with a lot of columns about movies.
The main problems i had while parsing the CSV were:
- Some columns can contain commas inside values, for example Gross: 28,341,469
- Some columns can have null / empty values, and also values like PG (i have excluded this since it is not relevant)
- At the end of some rows there is \r character

Because of these problems i did CSV parsing manually using regular expressions.
Basically i split tge line by comma only when the comma is outside quotes.

Also i have added helper functions for cleaning and converting values:
- clean-string - removes quotes at the start/end of string
-  clean-number-string - removes quotes, commas \r character
-  str->int-safe and str->double-safe - safe conversion to numbers

The dataset contains more columns then i need, so i have created select-columns function which takes only the columns used in this project:
- title
- year
- genres
- rating
- directior
- votes
- gross

---

## Machine Learning Demo

The project includes a simple linear regression model implemented from scratch.  
The model predicts a movie's IMDB rating based on:

- Release year
- Number of votes

The dataset is split into training and test sets (80/20), and model performance is evaluated using **Mean Absolute
Error (MAE)**.

The purpose of this part is educational — to demonstrate the basic workflow of training and evaluating a model — not to
build an optimized predictor.

---

## AI Tools Usage

During project creation i have used AI tools as help for learning and understanding.
- In parser.clj it helped me with regex idea and tips how to manually parse CSV data. Ther i was following the example from the Book: Clojure for the brave and true and updated it for my needs.
  I didn't want to use any csv parsing library.
- I have also used it for guidelines for implementing the ML part and simple linear regression.

All code was manually implemented, checked and tested by me.

---


## How to Run

```bash
lein run
```

## Technologies Used

- **Clojure** (JVM)
- **Leiningen**
- **IntelliJ IDEA** with Cursive plugin
- **clojure.test** for unit testing

---

## Documentation

The project documentation is available in the repository:

- [`doc/seminarski_imdb_filmovi_clojure.pdf`](doc/seminarski_imdb_filmovi_clojure.pdf)

---

## Resources

- [Clojure for the Brave and True](https://www.braveclojure.com/)
- [Official clojure docs](https://clojure.org/)
- [Split csv - stackoverflow](https://stackoverflow.com/questions/18144431/split-a-csv-where-some-entries-have-double-quotes?utm_source=chatgpt.com)
- [Linear regression in clojure](https://clojurepatterns.com/17/1/1/)

---

## License

Copyright © 2026 Božidar Mastilović

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.


