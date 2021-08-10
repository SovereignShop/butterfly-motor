(ns butterfly-motor.engine-intake
  (:require
   [butterfly-motor.params :as p] :reload
   [butterfly-motor.utils :as u]
   [scad-clj.model :as m]
   [scad-clj.scad :as s]))

(def intake-mask-translation
  (partial m/translate [(* p/wall-thickness  (Math/cos (/ u/pi 8)))
                        (* p/wall-thickness  (Math/sin (/ u/pi 8)))]))

(def intake-mask-wedge
  (->> (u/semi-circle (- p/intake-inner-radius p/wall-thickness)
                      (/ u/pi 4))
       intake-mask-translation))

(def intake-mask
  (->> intake-mask-wedge
       (m/rotatec [0 0 (/ u/pi 4)])))

(def intake-masks
  (m/union
   (for [x (range 4)]
     (->> intake-mask
          (m/rotatec [0 0 (* x (/ (* u/pi 2) 4))])))))

(def outlet-masks
  (m/union
   (for [x (range 4)]
     (->> intake-mask-wedge
          (m/rotatec [0 0 (* x (/ (* u/pi 2) 4))])))))

(def intake-outer-hull
  (->>
   (m/hull (->> (m/cylinder 5 1 :center false)
                (m/translate [0 0 (dec p/intake-hull-height)]))
           (m/cylinder p/intake-outer-radius 1 :center false))
   (m/translate [0 0 (- (u/half p/intake-hull-height))])))

(def intake-inner-hull
  (->>
   (m/hull (->> (m/cylinder (- 5 p/wall-thickness) 1 :center false)
                (m/translate [0 0 (dec p/intake-hull-height)]))
           (m/cylinder (- p/intake-outer-radius p/wall-thickness) 1 :center false))
   (m/translate [0 0 (- (u/half p/intake-hull-height))])))

(def intake-hull
  (m/difference intake-outer-hull
                intake-inner-hull))

(def intake-circle
  (m/circle p/intake-outer-radius))

(def intake-cylinder
  (->> intake-circle
       (m/extrude-linear {:height p/center-cylinder-height :center true})))

(def intake-opening-mask
  (->> (u/semi-circle (+ 3 p/intake-outer-radius) (/ u/pi 4))
       intake-mask-translation))

(def intake-opening-masks
  (->> (for [x (range 8)]
         (->> intake-opening-mask
              (m/rotatec [0 0 (* x (/ (* u/pi 2) 8))])))
       (m/extrude-linear {:height p/intake-opening-mask-height :center true})))

(def intake
  (m/difference
   (m/union intake-cylinder)
   (m/union
    (->> intake-masks
         (m/extrude-linear {:height p/intake-mask-height :center true})
         (m/translate [0 0 p/wall-thickness]))
    intake-opening-masks
    (->> outlet-masks
         (m/extrude-linear {:height p/intake-mask-height :center true})
         (m/translate [0 0 (- p/wall-thickness)])))))

#_(->> intake
     (s/write-scad)
     (spit "test.scad"))
