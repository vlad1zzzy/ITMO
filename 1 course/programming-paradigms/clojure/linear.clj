(defn twice [f] #(f (f %1)))
(defn triple [f] #(f (f (f %1))))

(defn linearOperation [func]
  (fn [& arguments] (apply mapv func arguments)))

(def v+ (linearOperation +))
(def v- (linearOperation -))
(def v* (linearOperation *))
(def vd (linearOperation /))
(def m+ ((twice linearOperation) +))
(def m- ((twice linearOperation) -))
(def m* ((twice linearOperation) *))
(def md ((twice linearOperation) /))
(def c+ ((triple linearOperation) +))
(def c- ((triple linearOperation) -))
(def c* ((triple linearOperation) *))
(def cd ((triple linearOperation) /))

(defn scalar [v1 v2]
  (apply + (v* v1 v2)))

(defn v*s [v s] (mapv #(* %1 s) v))

(defn m*s [m s] (mapv #(v*s %1 s) m))

(defn m*v [mat vec] (mapv #(scalar %1 vec) mat))

(defn vect [v1 v2]
  (mapv #(let [first (mod %1 3)
               second (mod (+ %1 1) 3)]
           (- (* (nth v1 first) (nth v2 second))
              (* (nth v1 second) (nth v2 first)))) [1, 2, 3]))

(defn transpose [mat]
  (apply mapv vector mat))

(defn m*m [mat1 mat2]
  (mapv (fn [m1]
          (mapv (fn [m2]
                  ((fn [row col] (apply + (map * row col))) m1 m2)) (transpose mat2)))
        mat1))