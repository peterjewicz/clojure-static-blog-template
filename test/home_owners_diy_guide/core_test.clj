(ns home-owners-diy-guide.core-test
  (:require [clojure.test :refer :all]
            [blog-template.config.times :refer [find-published-time time-compare]]))


(def dummy-posts
  [
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2021-01-05T14:08:40\"
       :last-updated \"jan 5, 2021\"}}])"]
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2020-01-05T14:08:40\"
       :last-updated \"jan 5, 2020\"}}])"]
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2021-02-05T14:08:40\"
       :last-updated \"jan 5, 2021\"}}])"]])

(def sorted-dummy-posts
  [
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2021-02-05T14:08:40\"
       :last-updated \"jan 5, 2021\"}}])"]
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2021-01-05T14:08:40\"
       :last-updated \"jan 5, 2021\"}}])"]
   ["/ahello-world.clj"
    "{:header
      {:title \"aHello World\"
       :meta-desc \"A base hello World Page\"
       :published \"2020-01-05T14:08:40\"
       :last-updated \"jan 5, 2020\"}}])"]])



(deftest sort-by-time-compare
  (let [time1 "2021-01-05T14:08:40"
        time2 "2021-02-01T14:08:40"
        time3 "2021-03-11T14:08:40"]
    (is (time-compare time3 time2))
    (is (time-compare time2 time1))
    (is (not (time-compare time1 time3)))))

(deftest test-find-published-time
  (is (= "2021-01-05T14:08:40" (find-published-time (first dummy-posts)))))


(deftest test-sorting-dates
  (is (= sorted-dummy-posts (sort-by find-published-time time-compare dummy-posts))))

