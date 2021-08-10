(ns butterfly-motor.cylinder-engine
  "Parametric n-cylinder pneumatic engine."
  (:require
   [butterfly-motor.utils :as u]
   [butterfly-motor.engine-block :as engine-block] :reload
   [butterfly-motor.engine-housing :as engine-housing] :reload
   [butterfly-motor.engine-intake :as engine-intake] :reload
   [butterfly-motor.params :as p] :reload
   [scad-app.core :as scad-app]
   [scad-clj.model :as m]))

(def intake-assembly
  (m/union
   engine-intake/intake
   engine-housing/engine-case))

(def full-assembly
  (m/union
   intake-assembly
   (->> engine-housing/engine-case-lid
        (m/translate [0 0 (- (+ (/ p/engine-outer-block-height 2)
                                p/engine-housing-lid-inset-distance))]))
   (->> engine-block/engine-block
        (m/rotatec [0 0 (/ u/pi 8)]))))

(defn build [& {:keys [render?] :or {render? true}}]
  (scad-app/build-all
   [{:name "engine-block" :model-main intake-assembly}
    {:name "engine-housing-lid" :model-main engine-housing/engine-case-lid}
    {:nmae "engine-block" :model-main engine-block/engine-block}
    {:name "full-assembly" :model-main full-assembly}]
   {:render render?}))

(defn -main [& _]
  (build))
