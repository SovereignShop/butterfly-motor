(ns butterfly-motor.cylinder-engine
  "Parametric n-cylinder pneumatic engine."
  (:require
   [butterfly-motor.utils :as u]
   [butterfly-motor.engine-block :as engine-block] :reload
   [butterfly-motor.engine-housing :as engine-housing] :reload
   [butterfly-motor.engine-intake :as engine-intake] :reload
   [butterfly-motor.params :as p] :reload
   [scad-clj.model :as m]
   [scad-clj.scad :as s]))

(def full-assembly
  (m/union
   (->> engine-housing/engine-case
        (m/rotatec [u/pi 0 0]))
   engine-intake/intake
   #_(->> engine-housing/engine-case-lid
        (m/translate [0 0 (- (+ (/ p/engine-outer-block-height 2)
                                p/engine-housing-lid-inset-distance))]))
   (->> engine-block/engine-block
        (m/rotatec [0 0 (/ u/pi 8)]))))

(->> (m/union full-assembly)
     (s/write-scad)
     (spit "test.scad"))

#_(->> engine-block
  (s/write-scad)
  (spit "test.scad"))
