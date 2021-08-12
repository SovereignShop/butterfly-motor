(ns butterfly-motor.params
  (:require
   [butterfly-motor.utils :as u]))

(def nozzle-diameter 0.8)
(def tolerance 0.2)                     ; Default tolerance
(def pi Math/PI)

;; Make wall thickness a multiple of nozzle diameter
(def wall-thickness (u/closest-multiple 2 nozzle-diameter))

(def piston-wheel-outer-radius 4.5)
(def piston-wheel-inner-radius 2)
(def piston-wheel-height 4)

(def piston-gasket-initial-offset 3)
(def piston-height 15)
(def piston-width 5)
(def piston-length 8)

(def piston-gasket-size 4)
(def piston-gasket-inset-distance 2)
(def piston-gasket-outset-distance (- piston-gasket-size piston-gasket-inset-distance))
(def piston-gasket-thickness 2)

(def intake-outer-radius (- 10 tolerance wall-thickness))
(def intake-inner-radius (- intake-outer-radius wall-thickness))
(def intake-hull-height 6)
(def center-cylinder-height (+ piston-length
                               (* 2 wall-thickness)
                               (* 2 piston-gasket-outset-distance)))

(def engine-block-outer-radius (+ intake-outer-radius piston-height tolerance))
(def engine-block-inner-radius (+ intake-outer-radius tolerance))
(def engine-block-height (+ piston-length
                            (* 2 wall-thickness)
                            (* 2 piston-gasket-outset-distance)))

(def engine-block-output-radius 6)

(def engine-housing-lid-height 4)
(def engine-housing-lid-inset-distance 2)

(def intake-height (+ (/ center-cylinder-height 2) piston-length))
(def intake-opening-mask-height piston-length #_(- engine-block-height (* 2 wall-thickness)))
(def intake-mask-height (- engine-block-height wall-thickness))
(def intake-outlet-mask-height (- intake-height (/ engine-block-height 2)))

(def outlet-height intake-height)

(def engine-outer-block-height engine-block-height)
