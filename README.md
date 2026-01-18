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

The dataset is a public IMDB CSV file containing information such as:

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

## How to Run

```bash
lein run
```

## Technologies Used

- **Clojure** (JVM)
- **Leiningen**
- **IntelliJ IDEA** with Cursive plugin
- **clojure.test** for unit testing

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
