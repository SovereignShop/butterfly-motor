(ns butterfly-motor.cylinder-engine
  "Parametric n-cylinder pneumatic engine."
  (:require
   [butterfly-motor.utils :as u]
   [scad-app.core :as scad-app]
   [scad-clj.model :as m]))

(binding [m/*fn* 150]
  (require
   '[butterfly-motor.params :as p] :reload
   '[butterfly-motor.engine-block :as engine-block] :reload
   '[butterfly-motor.engine-housing :as engine-housing] :reload
   '[butterfly-motor.engine-intake :as engine-intake] :reload)

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
          (m/rotatec [0 0 (/ u/pi 8)])))))

(defn build [& {:keys [render?] :or {render? true}}]
  (scad-app/build-all
   [{:name "engine-bufferfly-housing" :model-main intake-assembly}
    {:name "engine-housing-lid" :model-main engine-housing/engine-case-lid}
    {:name "engine-block" :model-main engine-block/engine-block}
    {:name "full-assembly" :model-main full-assembly}]
   {:render render?}))

(defn -main [& _]
  (build))
