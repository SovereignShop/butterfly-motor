(ns butterfly-motor.engine-housing
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]
   [butterfly-motor.engine-intake :as intake]
   [butterfly-motor.params :as p]
   [butterfly-motor.utils :as u]))

(binding [m/*fn* 200]

  (defn housing-shape
    "Sum a circle function with four revolutions of a sin function."
    [circle-radius sin-wave-amplitude]
    (let [angle (* 2 u/pi)
          n-steps (or m/*fn* 70)
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
    (housing-shape p/engine-block-outer-radius (/ p/piston-height 3.3)))

  (def inner-shape
    (housing-shape
     (- p/engine-block-outer-radius p/wall-thickness)
     (/ p/piston-height 3.3)))

  (def lid-shape
    (housing-shape (+ p/engine-block-outer-radius p/wall-thickness)
                   (/ p/piston-height 3.3)))

  (def engine-case-side
    (->> (m/difference outer-shape inner-shape)
         (m/extrude-linear {:height (+ p/engine-outer-block-height p/intake-hull-height
                                       (- p/engine-housing-lid-height
                                          p/engine-housing-lid-inset-distance))
                            :center true})
         (m/translate [0 0 (u/half (- p/engine-housing-lid-height
                                      p/engine-housing-lid-inset-distance))])))

  (def engine-bottom
    (m/difference
     (->> outer-shape
          (m/extrude-linear {:height p/intake-hull-height :center true}))
     intake/intake-inner-hull))

  (def engine-case
    (->> (m/union engine-case-side
                  (->> engine-bottom
                       (m/translate [0 0 (u/half p/engine-outer-block-height)])
                       (m/rotatec [0 u/pi 0])))
         (m/translate [0 0 (- (u/half p/intake-hull-height))])))

  (def engine-case-lid
    (m/difference
     (->> (m/difference lid-shape
                        (m/circle (+ p/engine-block-inner-radius
                                     p/wall-thickness
                                     p/tolerance)))
          (m/extrude-linear {:height p/engine-housing-lid-height :center false}))
     (->> engine-case-side
          (m/translate [0 0 (+ (/ (+ p/intake-hull-height p/engine-outer-block-height) 2)
                               p/engine-housing-lid-inset-distance)]))))
  )
