(ns butterfly-motor.engine-housing
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]
   [butterfly-motor.engine-intake :as intake]
   [butterfly-motor.params :as p]
   [butterfly-motor.utils :as u]))

(defn housing-shape
  "Sum a circle function with four revolutions of a sin function."
  [circle-radius sin-wave-amplitude]
  (let [angle (* 2 u/pi)
        n-steps 70
        step-size (/ angle n-steps)]
    (m/polygon
     (for [step (range (inc n-steps))
           :let [x (* step step-size)
                 a (+ (+ circle-radius p/wall-thickness)
                      (* sin-wave-amplitude
                         (+ 1 (Math/sin (* 4 (- x (/ u/pi 8)))))))]]
       [(* a (Math/cos x))
        (* a (Math/sin x))]))))

(def outer-shape
  (housing-shape p/engine-block-outer-radius (/ p/piston-height 3)))

(def inner-shape
  (housing-shape
   (- p/engine-block-outer-radius p/wall-thickness)
   (/ p/piston-height 3)))

(def lid-shape
  (housing-shape (+ p/engine-block-outer-radius p/wall-thickness)
                 (/ p/piston-length 2)))

(def engine-case-side
  (->> (m/difference outer-shape inner-shape)
       (m/extrude-linear {:height (+ p/engine-outer-block-height p/intake-hull-height) :center true})))

(def engine-bottom
  (m/difference
   (->> outer-shape
        (m/extrude-linear {:height p/intake-hull-height :center false})
        (m/translate [0 0 (- (u/half (+ p/intake-hull-height p/engine-outer-block-height)) )]))
   intake/intake-inner-hull))

(def engine-case
  (m/union engine-case-side engine-bottom))

(def engine-case-lid
  (m/difference
   (->> (m/difference lid-shape
                      (m/circle (+ p/engine-block-inner-radius
                                   p/wall-thickness
                                   p/tolerance)))
        (m/extrude-linear {:height p/engine-housing-lid-height :center false}))
   (->> engine-case-side
        (m/translate [0 0 (+ (/ p/engine-outer-block-height 2)
                             p/engine-housing-lid-inset-distance)]))))

(do
  (->> engine-case
       (s/write-scad)
       (spit "test.scad")))
