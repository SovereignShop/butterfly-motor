(ns butterfly-motor.utils
  (:require
   [scad-clj.model :as m]
   [scad-clj.scad :as s]))

(def pi Math/PI)
(def pi2 (* 2 Math/PI))

(defn semi-circle [r angle]
  (let [n-steps (or m/*fn* 10)
        step-size (/ angle n-steps)]
    (m/polygon
     (cons [0 0]
           (for [step (range (inc n-steps))]
             (let [d (* step step-size)]
               [(* r (Math/cos d)) (* r (Math/sin d))]))))))

(defn semi-ovol [rx ry angle]
  (let [n-steps (or m/*fn* 10)
        step-size (/ angle n-steps)]
    (m/polygon
     (for [step (range (inc n-steps))]
       (let [d (* step step-size)]
         [(* rx (Math/cos d))
          (* ry (Math/sin d))])))))

(defn ovol
  [rx ry]
  (let [n-steps (or m/*fn* 100)]
    (m/polygon
     (for [x (range n-steps)]
       (let [d (* x (/ (* 2 Math/PI) n-steps))]
         [(* rx (Math/cos d))
          (* ry (Math/sin d))])))))

(defn half [x] (/ x 2))

(defn egg
  [rx ry]
  (->> (semi-ovol rx ry Math/PI)
       (m/rotatec [0 0 (- (/ Math/PI 2))])
       (m/extrude-rotate {:angle 360})))

(defn closest-multiple [n x]
  (- n (rem n x)))

(defn bearing-cylinder [r h t]
  (let [h (/ h 2)
        r (/ r 2)
        d (- r (half t))
        poly (m/polygon [[0 h] [d h] [(+ d (half t)) (- h (half t))]
                         [(+ d (half t)) (- (- h (half t)))]
                         [d (- h)]
                         [0 (- h)]])]
    (m/extrude-rotate {:angle 360} poly)))

(defn bearing-mask [ir r h t]
  (let [h (/ h 2)
        r (half r)
        d (- r (half t))
        poly (m/polygon [[0 h] [d h] [(+ d (half t)) (- h (half t))]
                         [(+ d (half t)) (- (- h (half t)))]
                         [d (- h)]
                         [0 (- h)]])]
    (->> (m/union poly (m/rotatec [0 0 pi] poly))
         (m/translate [(+ ir t r) 0])
         (m/extrude-rotate {:angle 360}))))

(defn sides-to-angles [a b c]
  (let [C (Math/acos (/ (- (Math/pow c 2) (+ (Math/pow a 2) (Math/pow b 2)))
                        (- (* 2 a b))))
        B (Math/asin (/ (* b (Math/sin C))
                        c))
        A (Math/asin (/ (* a (Math/sin C))
                        c))]
    [A B C]))

(sides-to-angles 10 10 3)

;; Find the angle between two adjacent bearing cylinders

(binding [m/*fn* 100]
  (defn bearing [ir or h t]
    (let [tolerance 0.1
          d (- or ir (* 2 t))
          dt (+ d tolerance)
          mask (bearing-mask ir dt h t)
          bearing (bearing-cylinder d h t)
          spacer-bearing (bearing-cylinder (- d (half t)) h (half t))
          bearing-ring (->> (m/difference (m/circle or)
                                          (m/circle ir))
                            (m/extrude-linear {:height h :center true}))
          r (+ ir (half (- or ir)))
          [_ _ angle] (sides-to-angles r r d)
          positioned-bearing (->> bearing
                                  (m/translate [(+ ir t (half d) (half tolerance)) 0 0]))]
      #_(m/union bearing (->> spacer-bearing
                              (m/translate [0 0 8])))
      (m/union
       (m/difference bearing-ring mask)
       (for [x (range (quot pi2 angle))]
         (->> positioned-bearing
              (m/rotatec [0 0 (* x angle)]))))))

  (bearing 6 10 6 1))

(defn modularize [tree]
  (let [hashify
        (fn [t]
          (when (list? t)
            (apply merge-with
                   into
                   {(hash t) [t]}
                   (map modularize t))))
        hash-table (hashify tree)]))

(comment

  (binding [m/*fn* 100]
    (->> (semi-circle 10 Math/PI)
         (s/write-scad)
         (spit "test.scad")))
  )
