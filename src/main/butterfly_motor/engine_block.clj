(ns butterfly-motor.engine-block
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]
   [butterfly-motor.piston :as piston] :reload
   [butterfly-motor.utils :as u]
   [butterfly-motor.params :as p]))

(binding [m/*fn* 150]

  (def outer-cylinder
    (m/cylinder p/engine-block-outer-radius
                p/engine-block-height
                :center true))

  (def inner-cylidner
    (m/cylinder p/engine-block-inner-radius
                p/engine-block-height
                :center true))

  (def block-shell
    (m/difference outer-cylinder
                  inner-cylidner))

  (def output-bottom-cylinder
    (->> (m/circle (+ p/engine-block-inner-radius p/wall-thickness))
         (m/extrude-linear {:height 10 :center false})))

  (def output-bottom-inner-cylinder
    (->> (m/circle p/engine-block-inner-radius)
         (m/extrude-linear {:height 10 :center false})))

  (def output-top-cylinder
    (->> (m/circle p/engine-block-output-radius)
         (m/extrude-linear {:height p/wall-thickness :center false})
         (m/translate [0 0 20])))

  (def output-top-inner-cylinder
    (->> (m/circle (- p/engine-block-output-radius p/wall-thickness))
         (m/extrude-linear {:height p/wall-thickness :center false})
         (m/translate [0 0 20])))

  (def output-air-outlet-mask
    (->> (m/square (* 4 p/wall-thickness) (* 2 (+ p/engine-block-inner-radius p/wall-thickness))
                   :center true)
         (m/extrude-linear {:height (* 2 p/wall-thickness) :center false})))

  (def output-outer-hull
    (m/hull output-bottom-cylinder
            output-top-cylinder))

  (def engine-block
    (->> (m/difference
          (m/union block-shell
                   (->> (m/difference output-outer-hull
                                      output-bottom-inner-cylinder
                                      (->> output-air-outlet-mask
                                           (m/translate [0 0 4])))
                        (m/translate [0 0 (/ p/engine-block-height 2)])))
          piston/piston-masks)))
  )
