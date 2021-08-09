(ns butterfly-motor.piston
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]
   [butterfly-motor.utils :as u]
   [butterfly-motor.params :as p]))

#_(binding [m/*fn* 100])

(def first-gasket-z-position
  (+ (- (/ p/piston-height 2)) p/piston-gasket-initial-offset))

(def piston-gasket-outer-square
  (m/square (+ p/piston-width p/piston-gasket-outset-distance)
            (+ p/piston-length p/piston-gasket-outset-distance)
            :center true))

(def piston-gasket-inner-square
  (m/square (- p/piston-width p/piston-gasket-inset-distance)
            (- p/piston-length p/piston-gasket-inset-distance)
            :center true))

(def piston-gasket-shape
  (m/difference piston-gasket-outer-square
                piston-gasket-inner-square))

(def piston-gasket
  (->> piston-gasket-shape
       (m/extrude-linear {:height p/piston-gasket-thickness :center true})))

(def piston-mask
  (->> (m/square (+ p/piston-width
                    p/piston-gasket-outset-distance)
                 (+ p/piston-length p/piston-gasket-outset-distance) )
       (m/extrude-linear {:height (* 2 p/engine-block-outer-radius) :center true})
       (m/rotatec [(/ Math/PI 2) 0 0])))

(def piston-masks
  (m/union
   (for [x (range 8)]
     (->> piston-mask
          (m/rotatec [0 0 (* x (/ (* Math/PI 2) 8))])))))

(def piston-wheel
  (m/sphere p/piston-ball-radius))

(def piston-wheel-mask
  (m/union piston-wheel
           (->> (m/square (+ p/piston-width -1.2)
                          (+ p/piston-length p/piston-gasket-outset-distance -3)
                          :center true)
                (m/extrude-linear {:height 6 :center true}))
           (->> (u/egg p/piston-ball-radius
                       (inc p/piston-ball-radius))
                (m/rotatec [(u/half u/pi) 0 0]))))

(def piston
  (m/difference
   (->> (m/square p/piston-width p/piston-length :center true)
        (m/extrude-linear {:height p/piston-height :center true}))
   (m/union
    (->> piston-wheel-mask (m/translate [0 0 (- (u/half p/piston-height) 1)]))
    (->> piston-gasket (m/translate [0 0 first-gasket-z-position]))
    (->> piston-gasket (m/translate [0 0 (+ first-gasket-z-position (* 2 p/piston-gasket-thickness))])))))


(comment

  (->> (m/union piston)
       (s/write-scad )
       (spit "test.scad"))

  )
