(ns butterfly-motor.piston
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]
   [butterfly-motor.utils :as u]
   [butterfly-motor.params :as p]))

(binding [m/*fn* 150]

  (def piston-cube-height
    (- p/piston-height (- p/piston-wheel-outer-radius p/piston-wheel-inner-radius)))

  (def piston-wheel-mask-radius
    (inc p/piston-wheel-outer-radius))

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

  (def piston-wheel-mask
    (->> (m/union (m/circle piston-wheel-mask-radius)
                  (->> (m/square (* 2 piston-wheel-mask-radius) piston-wheel-mask-radius :center false)
                       (m/translate [(- piston-wheel-mask-radius) 0])))
         (m/extrude-linear {:height (inc p/piston-wheel-height) :center true})))

  (def piston-wheel-axle
    (->> (m/circle p/piston-wheel-inner-radius)
         (m/extrude-linear {:height (+  p/piston-width 1) :center true})
         (m/rotatec [(/ u/pi 2) 0 0])))

  (def piston-wheel-axle-mask
    (->> (m/square (* 2 p/piston-wheel-inner-radius) (* 2 p/piston-wheel-inner-radius))
         (m/translate [0 p/piston-wheel-inner-radius 0])
         (m/union (m/circle p/piston-wheel-inner-radius))
         (m/extrude-linear {:height (+ p/piston-width 3/2) :center true})
         (m/rotatec [(/ u/pi 2) 0 0])))

  (def piston-wheel-assembly
    (m/union piston-wheel-axle-mask
             (->> piston-wheel-mask
                  (m/rotatec [(/ u/pi 2) 0 0]))))

  (def piston-mask
    (->> (m/union
          (->> piston-wheel-mask
               (m/rotatec [(/ u/pi 2) 0 0])
               (m/translate [0 0 (- (u/half (inc p/piston-height))
                                    piston-wheel-mask-radius)]))
          (->> (m/square p/piston-width p/piston-length :center true)
               (m/extrude-linear {:height (inc p/piston-height) :center true})))
         (m/rotatec [(- (/ u/pi 2)) 0 0])
         (m/translate [0 (+ (/ (inc p/piston-height) 2) -1/2 p/engine-block-inner-radius) 0])))

  (def piston-masks
    (m/union
     (for [x (range 8)]
       (->> piston-mask
            (m/rotatec [0 0 (* x (/ (* Math/PI 2) 8))])))))

  (def piston
    (m/difference
     (->> (m/square p/piston-width p/piston-length :center true)
          (m/extrude-linear {:height piston-cube-height :center true}))
     (m/union
      (->> piston-wheel-assembly (m/translate [0 0 (- (u/half piston-cube-height) 1)]))
      (->> piston-gasket (m/translate [0 0 first-gasket-z-position]))
      (->> piston-gasket (m/translate [0 0 (+ first-gasket-z-position (* 2 p/piston-gasket-thickness))])))))
  )
