(ns butterfly-motor.utils
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]))

(def pi Math/PI)

(defn semi-circle [r angle]
  (let [n-steps (or m/*fn* 10)
        step-size (/ angle n-steps)]
    (m/polygon
     (cons [0 0]
           (for [step (range (inc n-steps))]
             (let [d (* step step-size)]
               [(* r (Math/cos d)) (* r (Math/sin d))]))))))

(defn semi-ovol [rx ry angle]
  (let [n-steps (or m/*fn* 10)
        step-size (/ angle n-steps)]
    (m/polygon
     (for [step (range (inc n-steps))]
       (let [d (* step step-size)]
         [(* rx (Math/cos d))
          (* ry (Math/sin d))])))))

(defn ovol
  [rx ry]
  (let [n-steps (or m/*fn* 100)]
    (m/polygon
     (for [x (range n-steps)]
       (let [d (* x (/ (* 2 Math/PI) n-steps))]
         [(* rx (Math/cos d))
          (* ry (Math/sin d))])))))

(defn half [x] (/ x 2))

(defn egg
  [rx ry]
  (->> (semi-ovol rx ry Math/PI)
       (m/rotatec [0 0 (- (/ Math/PI 2))])
       (m/extrude-rotate {:angle 360})))

(defn closest-multiple [n x]
  (- n (rem n x)))


(defn modularize [tree]
  (let [hashify
        (fn [t]
          (when (list? t)
            (apply merge-with
                   into
                   {(hash t) [t]}
                   (map modularize t))))
        hash-table (hashify tree)]))

(comment

  (binding [m/*fn* 100]
    (->> (semi-circle 10 Math/PI)
         (s/write-scad)
         (spit "test.scad")))
  )
